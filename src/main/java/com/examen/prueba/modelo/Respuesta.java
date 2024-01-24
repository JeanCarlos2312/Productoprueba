package com.examen.prueba.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Respuesta {
    private Integer idUltimoReg;
    private String Mensaje;
    private List<Producto> listado;
}
