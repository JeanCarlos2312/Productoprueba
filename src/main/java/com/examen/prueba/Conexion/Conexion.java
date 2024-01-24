package com.examen.prueba.Conexion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
public class Conexion {
    private static final String url = "jdbc:oracle:thin:@192.168.100.43:1521:TEST";
    private static final String user = "SISNICPLUS";
    private static final String password = "test";
    public static synchronized Connection getConexion() {
        Connection cn = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            cn = DriverManager.getConnection(url, user, password);

        } catch (Exception ex) {
            cn = null;
            System.out.println("Fallo Conexion...");
            System.out.println(ex.getStackTrace());
            System.out.println(ex.getMessage());
        } finally {
            return cn;
        }
    }
    public static synchronized void cerrarCall(CallableStatement cl) {
        try{cl.close();}catch(Exception e){}
    }
    public static synchronized void cerrarConexion(ResultSet rs) {
        try{rs.close();} catch (Exception e) {}
    }
    public static synchronized void cerrarConexion(Connection cn) {
        try{cn.close();} catch (Exception e) {}
    }
    public static synchronized void deshacerCambios(Connection cn) {
        try{cn.rollback();}catch (Exception e){}
    }
}
