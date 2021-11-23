package com.store.controllers;

import com.store.services.ProductService;
import com.store.services.impl.ProductServiceImpl;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UpdateProductServletTest {
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
    void testGetUpdatePage() throws IOException {
        UpdateProductServlet controller = new UpdateProductServlet(service);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        servletContextHandler.addServlet(new ServletHolder(controller), "/products/update");
        controller.doGet(request, response);

        when(response.getStatus()).thenReturn(HttpServletResponse.SC_OK);
        when(response.getContentType()).thenReturn("text/html;charset=utf-8");
    }



}