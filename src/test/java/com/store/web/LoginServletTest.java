package com.store.web;

import com.store.entities.User;
import com.store.services.SecurityService;
import com.store.services.UserService;
import com.store.services.impl.SecurityServiceImpl;
import com.store.services.impl.UserServiceImpl;
import com.store.web.servlets.LoginServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginServletTest {
    private final UserService service = mock(UserServiceImpl.class);
    private final SecurityService securityService = mock(SecurityServiceImpl.class);
    private final ServletContextHandler servletContextHandler = mock(ServletContextHandler.class);

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLogin() throws IOException, ServletException {
        User user = new User(1, "David", "qwer1234", "qwer");
        LoginServlet loginServlet = new LoginServlet(service, securityService);

        when(request.getParameter("username")).thenReturn(user.getUsername());
        when(request.getParameter("password")).thenReturn(user.getPassword());

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        servletContextHandler.addServlet(new ServletHolder(loginServlet), "/login");
        loginServlet.doPost(request, response);

        when(response.getStatus()).thenReturn(HttpServletResponse.SC_OK);
    }

    @Test
    void testWhenUserIsNotAuthorized() throws IOException {
        User user = new User(1, "", "", "qwer");
        LoginServlet loginServlet = new LoginServlet(service, securityService);

        when(request.getParameter("username")).thenReturn(user.getUsername());
        when(request.getParameter("password")).thenReturn(user.getPassword());

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        servletContextHandler.addServlet(new ServletHolder(loginServlet), "/login");
        loginServlet.doPost(request, response);

        when(response.getStatus()).thenReturn(HttpServletResponse.SC_UNAUTHORIZED);
    }


}