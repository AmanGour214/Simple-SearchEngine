package com.Accio;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Myservlet") // servlet is a part of operation in whole server;
// we are extending the property httpservlet ;
public class Myservlet extends HttpServlet {
    // request and response are the two type of request .request is for clint side and resonse for server side.
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws IOException {
        // response is setting in htms contains ;
        response.setContentType("text/html");
        // this is for getting response for outpute
       PrintWriter out = response.getWriter();
        // out put on the screen it is going to write html code ;
        out.println("<h3>This is my Servlet</h3>");

    }
}
