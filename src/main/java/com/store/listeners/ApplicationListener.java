package com.store.listeners;

import com.store.repositories.ProductRepository;
import com.store.repositories.UserRepository;
import com.store.repositories.db_config.database.ConnectionFactory;
import com.store.repositories.db_config.database.DataSources;
import com.store.repositories.impl.ProductRepositoryImpl;
import com.store.repositories.impl.UserRepositoryImpl;
import com.store.security.SecurityService;
import com.store.security.SecurityServiceImpl;
import com.store.services.ProductService;
import com.store.services.UserService;
import com.store.services.impl.ProductServiceImpl;
import com.store.services.impl.UserServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DataSources dataSources = new ConnectionFactory();

        //init repository layer
        ProductRepository repository = new ProductRepositoryImpl(dataSources);
        UserRepository userRepository = new UserRepositoryImpl(dataSources);

        //init service layer
        SecurityService securityService = new SecurityServiceImpl();
        ProductService service = new ProductServiceImpl(repository);
        UserService userService = new UserServiceImpl(userRepository);

        sce.getServletContext().setAttribute("ProductService", service);
        sce.getServletContext().setAttribute("SecurityService", securityService);
        sce.getServletContext().setAttribute("UserService", userService);
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}