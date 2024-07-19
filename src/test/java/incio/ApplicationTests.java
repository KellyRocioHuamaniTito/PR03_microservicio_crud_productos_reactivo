package incio;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import model.Producto;
import reactor.test.StepVerifier;
import service.ProductosService;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class ApplicationTests {



	@Autowired
	ProductosService service;

	@Test
	@Order(1)
	void testProductosCategoria() {
		StepVerifier.create(service.productoCategoria("Alimentación"))
		.expectNextMatches(p->p.getNombre().equals("Azucar"))
		.expectNextMatches(p->p.getNombre().equals("Leche"))
		.expectNextMatches(p->p.getNombre().equals("Huevos"))
		.verifyComplete();

	}
	@Test
	@Order(2)
	void testEliminarProductos() {
		StepVerifier.create(service.eliminarProducto(103))
		.expectNextMatches(p->p.getNombre().equals("Mesa"))
		.verifyComplete();
	}

	@Test
	@Order(3)
	void testAltaProducto() {
		Producto pr = new Producto(250,"ptest","cat1", 10, 2);
		StepVerifier.create(service.altaProducto(pr))
		.expectComplete() //como se espera que solo diga completado o sea un void
		.verify();
	}

	@Test
	@Order(4)
	void testCatalogo(){
		StepVerifier.create(service.catalogo())
		.expectNextCount(8)
		.verifyComplete();
	}
}
