package com.tfmm.servlets;

import com.tfmm.handlers.RegisterHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegisterHandler handler = new RegisterHandler();
        try{
            resp.setContentType("application/json");
            String json = handler.handle(req, resp);
            resp.getWriter().write(json);

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
