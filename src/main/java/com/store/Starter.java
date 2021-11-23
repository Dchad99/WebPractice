package com.store;

import com.store.controllers.*;
import com.store.repositories.ProductRepository;
import com.store.repositories.UserRepository;
import com.store.repositories.impl.ProductRepositoryImpl;
import com.store.repositories.db_config.database.ConnectionFactory;
import com.store.repositories.db_config.database.DataSources;
import com.store.repositories.impl.UserRepositoryImpl;
import com.store.services.ProductService;
import com.store.services.SecurityService;
import com.store.services.UserService;
import com.store.services.impl.ProductServiceImpl;
import com.store.services.impl.SecurityServiceImpl;
import com.store.services.impl.UserServiceImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


public class Starter {
    public static void main(String[] args) throws Exception {
        DataSources dataSources = new ConnectionFactory();

        //init repository layer
        ProductRepository repository = new ProductRepositoryImpl(dataSources);
        UserRepository userRepository = new UserRepositoryImpl(dataSources);

        //init service layer
        SecurityService securityService = new SecurityServiceImpl();
        ProductService service = new ProductServiceImpl(repository);
        UserService userService = new UserServiceImpl(userRepository);

        //init presentation layer
        ProductServlet controller = new ProductServlet(service);
        AddProductServlet addProductServlet = new AddProductServlet(service);
        UpdateProductServlet updateProductServlet = new UpdateProductServlet(service);
        RegisterServlet registerServlet = new RegisterServlet(userService, securityService);
        LoginServlet loginServlet = new LoginServlet(userService, securityService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(controller), "/products");
        context.addServlet(new ServletHolder(controller), "/");
        context.addServlet(new ServletHolder(addProductServlet), "/products/add");
        context.addServlet(new ServletHolder(updateProductServlet), "/products/update");
        context.addServlet(new ServletHolder(registerServlet), "/register");
        context.addServlet(new ServletHolder(loginServlet), "/login");
        context.addServlet(new ServletHolder(new LogoutServlet()), "/logout");

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
    }
}
