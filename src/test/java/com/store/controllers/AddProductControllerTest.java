package com.store.controllers;

import com.store.entities.Product;
import com.store.services.ProductService;
import com.store.services.ProductServiceImpl;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddProductControllerTest {
    private ProductService service = mock(ProductServiceImpl.class);
    private ServletContextHandler servletContextHandler = mock(ServletContextHandler.class);

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetCreationPage() throws IOException {
        AddProductController controller = new AddProductController(service);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        servletContextHandler.addServlet(new ServletHolder(controller), "/products/add");
        controller.doGet(request, response);

        when(response.getStatus()).thenReturn(HttpServletResponse.SC_OK);
        when(response.getContentType()).thenReturn("text/html;charset=utf-8");
    }


    @Test
    void testAddProduct() throws IOException {
        AddProductController controller = new AddProductController(service);

        Product product = new Product(1, "David", 1, new Date());

        when(request.getParameter("name")).thenReturn(product.getName());
        when(request.getParameter("price")).thenReturn(String.valueOf(product.getPrice()));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        servletContextHandler.addServlet(new ServletHolder(controller), "/products/add");
        controller.doPost(request, response);

        when(response.getStatus()).thenReturn(HttpServletResponse.SC_CREATED);
    }

}