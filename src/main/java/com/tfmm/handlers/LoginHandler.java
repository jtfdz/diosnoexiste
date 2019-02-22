package com.tfmm.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfmm.models.LoginModel;
import com.tfmm.models.Response;
import com.tfmm.utilities.DB;
import com.tfmm.utilities.MD5Encrypter;
import com.tfmm.utilities.PropertiesReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginHandler {

    public String handle(HttpServletRequest req, HttpServletResponse res) throws IOException{
        Response<?> resp = new Response<>();
        ObjectMapper objMapper = new ObjectMapper();
        PropertiesReader prop = PropertiesReader.getInstance();
        DB db = new DB();
        MD5Encrypter enc = new MD5Encrypter();
        LoginModel model = null;
        try {
            model = objMapper.readValue(req.getReader(), LoginModel.class);
            model.setPassword(enc.hashString(model.getPassword()));
            LoginModel dbModel = db.<LoginModel>executeQuery(prop.getValue("loginQuery"), LoginModel.class, model.getUsername());
            String message;
            int status;
            if(dbModel.getUsername() == null){
                message = "User not found";
                status = 404;
            }else{
                if(dbModel.getPassword().equals(model.getPassword())){
                    message = "Login successful";
                    status = 200;
                }else{
                    message = "Incorrect data";
                    status = 400;
                }

            }
            resp.setStatus(status);
            resp.setMessage(message);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            db.closeCon();
        }
        return objMapper.writeValueAsString(resp);

    }
}
