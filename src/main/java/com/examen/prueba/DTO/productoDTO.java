package com.examen.prueba.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class productoDTO {
    private String descripcion;
    private Double largo;
    private Double Ancho;
    private String Color;
    private String CodigoBarras;
}
