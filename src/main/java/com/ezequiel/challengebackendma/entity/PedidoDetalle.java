package com.ezequiel.challengebackendma.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDetalle {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "producto_id", nullable = false)
  private Producto producto;

  private Integer cantidad;
  private Double importe;

  public void setImporte() {
    this.importe = calcularImporte();
  }

 /* @PrePersist
  public void guardarImporte() {
    importe = calcularImporte();
  }*/

  public Double calcularImporte() {
    importe = cantidad.doubleValue() * producto.getPrecioUnitario();

    return importe;
  }
}
