package com.ezequiel.challengebackendma.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "productos")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto implements Serializable {

  @Id
  private String id;
  private String nombre;
  private String descripcionCorta;
  private String descripcionLarga;
  private Double precioUnitario;

}
