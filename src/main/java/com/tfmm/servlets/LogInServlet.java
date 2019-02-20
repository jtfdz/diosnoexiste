package com.tfmm.servlets;

import com.tfmm.handlers.LoginHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login", name = "LoginServlet")
public class LogInServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginHandler handler = new LoginHandler();
        try{
            resp.setContentType("application/json");
            String json = handler.handle(req, resp);
            resp.getWriter().write(json);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
