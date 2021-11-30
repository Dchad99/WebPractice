package com.store.web;

import com.store.entities.Product;
import com.store.services.ProductService;
import com.store.services.impl.ProductServiceImpl;
import com.store.web.servlets.products.AddProductServlet;
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

class AddProductServletTest {
    private ProductService service = mock(ProductServiceImpl.class);
    //private ServletContextHandler servletContextHandler = mock(ServletContextHandler.class);

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddProduct() throws IOException {
        AddProductServlet controller = new AddProductServlet();

        Product product = new Product();
        product.setId(1);
        product.setName("David");
        product.setDate(new Date());

        when(request.getParameter("name")).thenReturn(product.getName());
        when(request.getParameter("price")).thenReturn(String.valueOf(product.getPrice()));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        controller.doPost(request, response);

        when(response.getStatus()).thenReturn(HttpServletResponse.SC_CREATED);
    }

}