package com.ezequiel.challengebackendma.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoCabecera {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String direccion;
  private String email;
  private String telefono;
  private String horario;
  private String fechaAlta;
  private Double montoTotal;
  private Boolean aplicoDescuento;
  private String estado;


  @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "pedido_cabecera_id")
  private List<PedidoDetalle> listaDetalles;

  public PedidoCabecera(String direccion, String email, String telefono, String horario) {
    this.direccion = direccion;
    this.email = email;
    this.telefono = telefono;
    this.horario = horario;
    this.estado = "PENDIENTE";
    this.listaDetalles = new ArrayList<>();
  }


  @PrePersist
  public void prePersist() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    fechaAlta = dateFormat.format(new Date());
  }

  public Double calcularMontoTotal() {
    Double total = 0.0;
    int size = listaDetalles.size();

    for (int i = 0; i < size; i++) {
      total += listaDetalles.get(i).calcularImporte();
    }
    return total;
  }

  public void addPedidoDetalle(PedidoDetalle detalle) {
    this.listaDetalles.add(detalle);
  }
}
