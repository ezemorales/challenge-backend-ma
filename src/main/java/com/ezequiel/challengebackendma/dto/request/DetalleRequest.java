package com.ezequiel.challengebackendma.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetalleRequest {

  @NotBlank(message = "Tenes que ingresar un ID")
  @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$", message = "UUID No valido")
  private String producto;
  @Min(value = 1, message = "Ingresa una cantidad valida")
  private Integer cantidad;

}
