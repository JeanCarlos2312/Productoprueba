package com.examen.prueba.controller;

import com.examen.prueba.Conexion.Conexion;
import com.examen.prueba.DTO.productoDTO;
import com.examen.prueba.modelo.Producto;
import com.examen.prueba.modelo.Respuesta;
import oracle.jdbc.OracleTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/productos/")
public class ProductoController {

    @PostMapping("nuevo")
    public ResponseEntity<Respuesta> insertarProducto(@RequestBody productoDTO producto)
    {
        Connection cn = null;
        CallableStatement pstmt = null;
        ResultSet rs = null;

        Integer codigoRegistrado = null;
        String mensaje ="";
        List<Producto> listaProductos= new ArrayList<Producto>();
        Producto nuevoProducto = null;
        Respuesta respuesta = new Respuesta();
        try {

            cn = Conexion.getConexion();
            pstmt = cn.prepareCall("{call SP_PRODUCTO_INSERT(?,?,?,?,?,?,?,?)}");
            pstmt.setString(1,producto.getDescripcion());
            pstmt.setDouble(2,producto.getLargo());
            pstmt.setDouble(3,producto.getAncho());
            pstmt.setString(4,producto.getColor());
            pstmt.setString(5,producto.getCodigoBarras());
            pstmt.registerOutParameter(6, OracleTypes.CURSOR);
            pstmt.registerOutParameter(7, OracleTypes.NUMBER);
            pstmt.registerOutParameter(8, OracleTypes.VARCHAR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(6);
            codigoRegistrado = pstmt.getInt(7);
            mensaje = pstmt.getString(8);



            while (rs.next()) {
                nuevoProducto = new Producto();
                nuevoProducto.setId(rs.getLong("id"));
                nuevoProducto.setDescripcion(rs.getString("descripcion"));
                nuevoProducto.setLargo(rs.getDouble("largo"));
                nuevoProducto.setAncho(rs.getDouble("ancho"));
                nuevoProducto.setColor(rs.getString("color"));
                nuevoProducto.setCodigoBarras(rs.getString("codigoBarra"));
                listaProductos.add(nuevoProducto);
            }



            rs.close();
            cn.close();
            cn = null;
        } catch (Exception e) {
            System.out.println("error 01: "+e.getMessage());
            mensaje = e.getLocalizedMessage();
        }finally {
            try {
                if (rs != null)
                    rs.close();

                if (cn != null)
                    cn.close();
            } catch (SQLException e) {
                System.out.println("error 02: "+e.getMessage());
                mensaje = e.getLocalizedMessage();
            }
        }

        respuesta.setMensaje(mensaje);
        respuesta.setIdUltimoReg(codigoRegistrado);
        respuesta.setListado(listaProductos);

        return ResponseEntity.ok(respuesta);

    }
}
