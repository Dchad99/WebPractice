package com.store;

import com.store.controllers.AddProductController;
import com.store.controllers.ProductController;
import com.store.repositories.ProductRepository;
import com.store.repositories.ProductRepositoryImpl;
import com.store.repositories.database.ConnectionFactory;
import com.store.repositories.database.DataSources;
import com.store.server.AllRequestsServlet;
import com.store.services.ProductService;
import com.store.services.ProductServiceImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


public class Starter {
    public static void main(String[] args) throws Exception {
        AllRequestsServlet allRequestsServlet = new AllRequestsServlet();

        DataSources dataSources = new ConnectionFactory();
        ProductRepository repository = new ProductRepositoryImpl(dataSources);
        ProductService service = new ProductServiceImpl(repository);


        ProductController controller = new ProductController(service);
        AddProductController addProductController = new AddProductController(service);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(allRequestsServlet), "/*");
        context.addServlet(new ServletHolder(controller), "/products");
        context.addServlet(new ServletHolder(addProductController), "/products/add");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}
