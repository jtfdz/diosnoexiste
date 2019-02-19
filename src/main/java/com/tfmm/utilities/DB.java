package com.tfmm.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfmm.models.Response;

import java.sql.*;

public class DB {

    private ResultSet rs;
    private ResultSetMetaData rsmd;
    private PreparedStatement pstmt;
    private Connection con;
    private ObjectMapper objMapper = new ObjectMapper();
    private PropertiesReader prop = PropertiesReader.getInstance();
    public DB(){
        try {
            Class.forName(prop.getValue("dbDriver"));
            this.con= DriverManager.getConnection(prop.getValue("dbUrl"),prop.getValue("dbUser"),prop.getValue("dbPassword"));

        }
        catch(Exception e){
            e.getStackTrace();
        }
    }

    public void execute(String query, Object... values){

        try{
            this.pstmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            for (int i = 0; i < values.length; i++) {
                this.pstmt.setObject(i + 1, values[i]);
            }

            this.pstmt.execute();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void closeCon() {
        try {
            this.con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
