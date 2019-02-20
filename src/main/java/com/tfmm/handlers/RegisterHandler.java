package com.tfmm.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfmm.models.RegisterModel;
import com.tfmm.models.Response;
import com.tfmm.utilities.DB;
import com.tfmm.utilities.MD5Encrypter;
import com.tfmm.utilities.PropertiesReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RegisterHandler {

    public String handle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Response<?> res = new Response<>();
        ObjectMapper objMapper = new ObjectMapper();

        PropertiesReader props = PropertiesReader.getInstance();
        DB db = new DB();
        RegisterModel model = objMapper.readValue(req.getReader(), RegisterModel.class);
        MD5Encrypter enc = MD5Encrypter.getInstance();
        try {
            String birthDayDate = model.getBirthday();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse(birthDayDate);
            java.sql.Date sqlBdayDate = new java.sql.Date(date.getTime());
            db.execute(props.getValue("registerQuery"), model.getName(),
                    model.getLastname(),
                    model.getUsername(),
                    enc.hashString(model.getPassword()),
                    model.getEmail(),
                    sqlBdayDate ,
                    model.getSex());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        res.setStatus(200);
        res.setMessage("User registered");
        db.closeCon();
        String json = objMapper.writeValueAsString(res);
        return json;
    }
}
