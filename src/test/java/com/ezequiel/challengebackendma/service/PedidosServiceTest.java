package com.ezequiel.challengebackendma.service;

import com.ezequiel.challengebackendma.dto.request.DetalleRequest;
import com.ezequiel.challengebackendma.dto.request.PedidoRequest;
import com.ezequiel.challengebackendma.entity.PedidoCabecera;
import com.ezequiel.challengebackendma.entity.PedidoDetalle;
import com.ezequiel.challengebackendma.entity.Producto;
import com.ezequiel.challengebackendma.exception.NotFoundException;
import com.ezequiel.challengebackendma.repository.IPedidoCabeceraRepository;
import com.ezequiel.challengebackendma.service.PedidosService;
import com.ezequiel.challengebackendma.service.ProductoService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class PedidosServiceTest {

  private List<PedidoCabecera> pedidoCabeceraList = new ArrayList<>();
  private List<PedidoCabecera> pedidoCabeceraListPorFecha = new ArrayList<>();
  private PedidoCabecera pedidoConDescuento;
  private PedidoCabecera pedidoSinDescuento;
  private PedidoCabecera pedidoSinDescuento2;
  private PedidoDetalle pedidoDetalle1;
  private PedidoDetalle pedidoDetalle2;
  private Producto producto1;
  private Producto producto2;

  @InjectMocks
  private PedidosService pedidosService;
  @Mock
  private ProductoService productoService;
  @Mock
  private IPedidoCabeceraRepository pedidoCabeceraRepository;


  @BeforeEach
  public void setUp() {
    //Creo productos
    producto1 = new Producto();
    producto1.setId("ebde40ac-882e-4d1e-883c-fae7f8645667");
    producto1.setNombre("Jamon y Morrones");
    producto1.setDescripcionCorta("Pizza de jamon y morrones");
    producto1.setDescripcionLarga("Mozzarella, jamon, morrones y aceitunas verdes");
    producto1.setPrecioUnitario(550.00);

    producto2 = new Producto();
    producto2.setId("ebde40ac-882e-4d1e-883c-fae7f8645666");
    producto2.setNombre("Palmitos");
    producto2.setDescripcionCorta("Pizza de palmitos");
    producto2.setDescripcionLarga("Mozzarella, palmitos y aceitunas verdes");
    producto2.setPrecioUnitario(680.00);

    //Creo PedidosDetalle
    pedidoDetalle1 = new PedidoDetalle();
    pedidoDetalle1.setId(1L);
    pedidoDetalle1.setProducto(producto1);
    pedidoDetalle1.setCantidad(4);
    pedidoDetalle1.setImporte(pedidoDetalle1.calcularImporte());

    pedidoDetalle2 = new PedidoDetalle();
    pedidoDetalle2.setId(2L);
    pedidoDetalle2.setProducto(producto2);
    pedidoDetalle2.setCantidad(2);
    pedidoDetalle2.setImporte(pedidoDetalle2.calcularImporte());

    //Creo PedidosCabecera pedido con descuento
    pedidoConDescuento = new PedidoCabecera();
    pedidoConDescuento.setId(1L);
    pedidoConDescuento.setDireccion("Mariani 5208");
    pedidoConDescuento.setEmail("ezequielmorales5@icloud.com");
    pedidoConDescuento.setTelefono("2235636012");
    pedidoConDescuento.setHorario("21:30");
    pedidoConDescuento.setFechaAlta("2022-02-15");
    pedidoConDescuento.setMontoTotal(1540.00);
    pedidoConDescuento.setAplicoDescuento(true);
    pedidoConDescuento.setEstado("PENDIENTE");
    pedidoConDescuento.setListaDetalles(new ArrayList<>());
    pedidoConDescuento.addPedidoDetalle(pedidoDetalle1);

    //Creo PedidosCabecera pedido sin descuento
    pedidoSinDescuento = new PedidoCabecera();
    pedidoSinDescuento.setId(2L);
    pedidoSinDescuento.setDireccion("Carballo 2554");
    pedidoSinDescuento.setEmail("maria@icloud.com");
    pedidoSinDescuento.setTelefono("4799734");
    pedidoSinDescuento.setHorario("20:30");
    pedidoSinDescuento.setFechaAlta("2022-02-14");
    pedidoSinDescuento.setMontoTotal(1360.00);
    pedidoSinDescuento.setAplicoDescuento(false);
    pedidoSinDescuento.setEstado("PENDIENTE");
    pedidoSinDescuento.setListaDetalles(new ArrayList<>());
    pedidoSinDescuento.addPedidoDetalle(pedidoDetalle2);

    //Creo PedidosCabecera pedido sin descuento
    pedidoSinDescuento2 = new PedidoCabecera();
    pedidoSinDescuento2.setId(2L);
    pedidoSinDescuento2.setDireccion("Carballo 2554");
    pedidoSinDescuento2.setEmail("maria@icloud.com");
    pedidoSinDescuento2.setTelefono("4799734");
    pedidoSinDescuento2.setHorario("20:30");
    pedidoSinDescuento2.setFechaAlta("2022-02-14");
    pedidoSinDescuento2.setMontoTotal(1360.00);
    pedidoSinDescuento2.setAplicoDescuento(false);
    pedidoSinDescuento2.setEstado("PENDIENTE");
    pedidoSinDescuento2.setListaDetalles(new ArrayList<>());
    pedidoSinDescuento2.addPedidoDetalle(pedidoDetalle2);

    //FindAll
    pedidoCabeceraList.add(pedidoConDescuento);
    pedidoCabeceraList.add(pedidoSinDescuento);
    pedidoCabeceraList.add(pedidoSinDescuento2);

    //FindByFecha
    pedidoCabeceraListPorFecha.add(pedidoSinDescuento);
    pedidoCabeceraListPorFecha.add(pedidoSinDescuento2);
  }

  @Test
  void findAll() {

    Mockito.when(pedidoCabeceraRepository.findAll()).thenReturn(pedidoCabeceraList);

    List<PedidoCabecera> pedidos = pedidosService.findAll();

    Assertions.assertNotNull(pedidos);
    Assertions.assertEquals(3, pedidos.size());
    Assertions
        .assertEquals(pedidoCabeceraList.get(0).getMontoTotal(), pedidos.get(0).getMontoTotal());

  }

  @Test
  void findByFechaAlta() {
    Mockito.when(pedidoCabeceraRepository.findByFechaAlta("2022-02-14"))
        .thenReturn(pedidoCabeceraListPorFecha);

    List<PedidoCabecera> pedidos = pedidosService.findByFechaAlta("2022-02-14");

    Assertions.assertNotNull(pedidos);
    Assertions.assertEquals(2, pedidos.size());
    Assertions
        .assertNotEquals("2022-02-15", pedidos.get(0).getFechaAlta());
    Assertions
        .assertEquals("2022-02-14", pedidos.get(0).getFechaAlta());
  }

  @Test
  void crearPedidoConDescuento() {

    List<DetalleRequest> detalleRequestList = new ArrayList<>();
    DetalleRequest detalleRequest1 = DetalleRequest.builder()
        .producto("ebde40ac-882e-4d1e-883c-fae7f8645667")
        .cantidad(4)
        .build();

    detalleRequestList.add(detalleRequest1);

    PedidoRequest pedidoRequest = PedidoRequest.builder()
        .direccion("Mariani 5208")
        .email("ezequielmorales5@icloud.com")
        .telefono("2235636012")
        .horario("21:30")
        .detalles(detalleRequestList).build();

    Mockito.when(productoService.findByUUID(detalleRequest1.getProducto()))
        .thenReturn(producto1);

    PedidoCabecera pedido = pedidosService.crearPedido(pedidoRequest);

    Assertions.assertEquals(pedido.getDireccion(), pedidoRequest.getDireccion());
    Assertions.assertEquals(pedido.getEmail(), pedidoRequest.getEmail());
    Assertions.assertEquals(pedido.getHorario(), pedidoRequest.getHorario());
    Assertions.assertTrue(pedido.getAplicoDescuento());
    Assertions.assertEquals(pedido.getListaDetalles().get(0).getProducto().getId(),
        pedidoRequest.getDetalles().get(0).getProducto());
    Assertions.assertEquals(1540.00, pedido.getMontoTotal());

    Mockito.verify(productoService).findByUUID(detalleRequest1.getProducto());
    Mockito.verify(pedidoCabeceraRepository).save(pedido);


  }

  @Test
  void crearPedidoSinDescuento() {

    List<DetalleRequest> detalleRequestList = new ArrayList<>();
    DetalleRequest detalleRequest1 = DetalleRequest.builder()
        .producto("ebde40ac-882e-4d1e-883c-fae7f8645666")
        .cantidad(2)
        .build();

    detalleRequestList.add(detalleRequest1);

    PedidoRequest pedidoRequest = PedidoRequest.builder()
        .direccion("Mariani 5208")
        .email("ezequielmorales5@icloud.com")
        .telefono("2235636012")
        .horario("21:30")
        .detalles(detalleRequestList).build();

    Mockito.when(productoService.findByUUID(detalleRequest1.getProducto())).thenReturn(producto2);

    PedidoCabecera pedido = pedidosService.crearPedido(pedidoRequest);

    Assertions.assertEquals(pedido.getDireccion(), pedidoRequest.getDireccion());
    Assertions.assertEquals(pedido.getEmail(), pedidoRequest.getEmail());
    Assertions.assertEquals(pedido.getHorario(), pedidoRequest.getHorario());
    Assertions.assertFalse(pedido.getAplicoDescuento());
    Assertions.assertEquals(pedido.getListaDetalles().get(0).getProducto().getId(),
        pedidoRequest.getDetalles().get(0).getProducto());
    Assertions.assertEquals(1360.00, pedido.getMontoTotal());

    Mockito.verify(productoService).findByUUID(detalleRequest1.getProducto());
    Mockito.verify(pedidoCabeceraRepository).save(pedido);

  }

  @Test
  void crearPedidoConErrores() {

    List<DetalleRequest> detalleRequestList = new ArrayList<>();
    DetalleRequest detalleRequest1 = DetalleRequest.builder()
        .producto("ebde40ac-882e-4d1e-883c-fae7f8645669")
        .cantidad(2)
        .build();

    detalleRequestList.add(detalleRequest1);

    PedidoRequest pedidoRequest = PedidoRequest.builder()
        .direccion("Mariani 5208")
        .email("ezequielmorales5@icloud.com")
        .telefono("2235636012")
        .horario("21:30")
        .detalles(detalleRequestList).build();

    Mockito.when(productoService.findByUUID(detalleRequest1.getProducto()))
        .thenThrow(new NotFoundException("Producto no encontrado"));

    Assertions.assertThrows(NotFoundException.class, () -> {
      pedidosService.crearPedido(pedidoRequest);
    });

    Mockito.verify(productoService).findByUUID(detalleRequest1.getProducto());

  }
}