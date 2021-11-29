package com.store.web.servlets;

import com.store.web.util.ResourceParser;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/*")
public class ResourcesLoadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       resp.getOutputStream().write(this.getClass()
                .getClassLoader()
                .getResourceAsStream(ResourceParser.getFileSource(req.getRequestURI()))
                .readAllBytes());
    }
}
