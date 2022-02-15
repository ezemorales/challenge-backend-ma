package com.ezequiel.challengebackendma.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PedidoRequest {

  private String direccion;
  @Email(message = "Ingresa un email valido")
  private String email;
  @Pattern(regexp = "[0-9]{1,10}", message = "Tienes que ingresar un numero valido")
  private String telefono;
  @Pattern(regexp = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", message = "Ingresa un horario valido")
  private String horario;
  @Valid
  private List<DetalleRequest> detalles;

}
