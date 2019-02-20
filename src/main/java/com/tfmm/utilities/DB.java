package com.tfmm.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfmm.models.Response;

import java.lang.reflect.Method;
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

    public <T> T executeQuery(String query,Class<T> c, Object... values){
        T obj = null;
        try{
            this.pstmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            for(int i = 0; i < values.length; i++){
                this.pstmt.setObject(i + 1, values[i]);
            }
            this.rs = this.pstmt.executeQuery();
            rsmd = rs.getMetaData();
            obj = c.newInstance();
                while(rs.next()){
                    for (int i = 1; i <= this.rsmd.getColumnCount(); i++) {
                        String columnName = this.rsmd.getColumnLabel(i);
                        for(Method m : c.getDeclaredMethods()){
                            if (containsIgnoreCase(m.getName(), columnName) && m.getName().startsWith("set")) {
                                m.invoke(obj, rs.getObject(i));
                            }
                        }
                    }
                }

        } catch (Exception e){
            e.printStackTrace();
        }
        return obj;
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

    private boolean containsIgnoreCase(String str, String searchStr)     {
        if(str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
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
