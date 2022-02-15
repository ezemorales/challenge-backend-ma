package com.ezequiel.challengebackendma.service;

import com.ezequiel.challengebackendma.dto.request.ProductoRequest;
import com.ezequiel.challengebackendma.entity.Producto;
import com.ezequiel.challengebackendma.exception.BadRequestException;
import com.ezequiel.challengebackendma.repository.IProductoRepository;
import com.ezequiel.challengebackendma.utils.PatchHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.json.JsonMergePatch;
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
class ProductoServiceTest {

  @InjectMocks
  private ProductoService productoService;

  @Mock
  private IProductoRepository productoRepository;
  @Mock
  private PatchHelper patchHelper;

  private Producto producto1;
  private Producto producto2;

  private List<Producto> productos = new ArrayList<>();

  @BeforeEach
  void setUp() {
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

    productos.add(producto1);
    productos.add(producto2);
  }

  @Test
  void findAll() {
    Mockito.when(productoRepository.findAll()).thenReturn(productos);

    List<Producto> pedidos = productoService.findAll();

    Assertions.assertNotNull(pedidos);
    Assertions.assertEquals(2, pedidos.size());
  }

  @Test
  void findByUUID() {
    Mockito.when(productoRepository.findById("ebde40ac-882e-4d1e-883c-fae7f8645667"))
        .thenReturn(Optional.of(producto1));

    Producto producto = productoService.findByUUID("ebde40ac-882e-4d1e-883c-fae7f8645667");

    Assertions.assertNotNull(producto);
    Assertions.assertEquals("ebde40ac-882e-4d1e-883c-fae7f8645667", producto.getId());
  }

  @Test
  void crearProducto() {
    ProductoRequest productoRequest = ProductoRequest.builder()
        .id("ebde40ac-882e-4d1e-883c-fae7f8645667")
        .descripcionCorta("Pizza de jamon y morrones")
        .descripcionLarga("Mozzarella, jamon, morrones y aceitunas verdes")
        .nombre("Jamon y Morrones")
        .precioUnitario("550.00").build();

    Mockito.when(productoRepository.save(producto1)).thenReturn(producto1);

    Producto producto = productoService.crearProducto(productoRequest);

    Assertions.assertNotNull(producto);
    Assertions.assertEquals(550.00, producto.getPrecioUnitario());


  }

  @Test
  void crearProductoExistente() {
    ProductoRequest productoRequest = ProductoRequest.builder()
        .id("ebde40ac-882e-4d1e-883c-fae7f8645667")
        .descripcionCorta("Pizza de jamon y morrones")
        .descripcionLarga("Mozzarella, jamon, morrones y aceitunas verdes")
        .nombre("Jamon y Morrones")
        .precioUnitario("550.00").build();

    Mockito.when(productoRepository.findById("ebde40ac-882e-4d1e-883c-fae7f8645667"))
        .thenReturn(Optional.of(producto1));

    Assertions.assertThrows(BadRequestException.class, () -> {
      productoService.crearProducto(productoRequest);
    });
  }

  @Test
  void modificarProducto() {
    String productoAModificar = "ebde40ac-882e-4d1e-883c-fae7f8645667";

    Producto p = new Producto();
    p.setId("ebde40ac-882e-4d1e-883c-fae7f8645667");
    p.setNombre("Jamon y Morrones");
    p.setDescripcionCorta("Descripcion Cambio");
    p.setDescripcionLarga("Mozzarella, jamon, morrones y aceitunas verdes");
    p.setPrecioUnitario(550.00);

    Map<String, Object> differences = new HashMap<String, Object>();
    differences.put("descripcionCorta", "Descripcion Cambio");

    Mockito.when(productoRepository.findById(productoAModificar))
        .thenReturn(Optional.of(producto1));
    Mockito.when(patchHelper
        .mergePatch(Mockito.any(JsonMergePatch.class), Mockito.any(Producto.class), Mockito.any())).thenReturn(p);

    Mockito.when(productoRepository.save(p)).thenReturn(p);
    productoService.modificarProducto(productoAModificar, differences);

    Assertions
        .assertNotEquals(differences.get("descripcionCorta"), producto1.getDescripcionCorta());
  }

  @Test
  void borrarProducto() {
    String productoABorrar = "ebde40ac-882e-4d1e-883c-fae7f8645667";

    Mockito.when(productoRepository.findById(productoABorrar)).thenReturn(Optional.of(producto1));
    productoService.borrarProducto(productoABorrar);

    Mockito.verify(productoRepository).delete(producto1);
  }
}