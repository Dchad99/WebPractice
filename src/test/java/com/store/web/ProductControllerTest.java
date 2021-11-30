package com.store.web;

import com.store.services.ProductService;
import com.store.services.impl.ProductServiceImpl;
import com.store.web.servlets.products.ProductServlet;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

class ProductControllerTest {
    private ProductService service = mock(ProductServiceImpl.class);
   // private ServletContextHandler servletContextHandler = mock(ServletContextHandler.class);

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @SneakyThrows
    @Test
    void testGetPage(){
        ProductServlet controller = new ProductServlet();

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        //servletContextHandler.addServlet(new ServletHolder(controller), "/products");
        //controller.doGet(request, response);

        when(response.getStatus()).thenReturn(HttpServletResponse.SC_OK);
        when(response.getCharacterEncoding()).thenReturn("UTF-8");
        when(response.getContentType()).thenReturn("application/json");
    }

    @Test
    void testDeleteProduct() throws IOException {
        String id = "1";
        when(request.getParameter("productId")).thenReturn(id);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        ProductServlet controller = new ProductServlet();
        //servletContextHandler.addServlet(new ServletHolder(controller), "/products");
        //controller.(request, response);

        when(response.getStatus()).thenReturn(HttpServletResponse.SC_NO_CONTENT);
    }

}