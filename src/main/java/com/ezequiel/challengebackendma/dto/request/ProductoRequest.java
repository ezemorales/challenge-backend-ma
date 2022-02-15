package com.ezequiel.challengebackendma.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductoRequest {

  @NotBlank(message = "Tenes que ingresar un ID")
  @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$", message = "UUID No valido")
  private String id;
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
