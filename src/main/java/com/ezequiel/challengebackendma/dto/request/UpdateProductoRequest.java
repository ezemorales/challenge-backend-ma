package com.ezequiel.challengebackendma.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProductoRequest {

  @NotBlank(message = "Tenes que ingresar un NOMBRE")
  private String nombre;
  @NotBlank(message = "Tenes que ingresar una DESCRIPCION CORTA")
  private String descripcionCorta;
  @NotBlank(message = "Tenes que ingresar una DESCRIPCION LARGA")
  private String descripcionLarga;
  @NotBlank(message = "Tenes que ingresar un PRECIO UNITARIO")
  @Min(message = "Debes ingresar un valor mayor a 0", value = 1)
  private String precioUnitario;


}
