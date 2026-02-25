package com.antonella.gestor_costos;

import com.antonella.gestor_costos.entity.RegistroCompras;
import com.antonella.gestor_costos.entity.CompraIngrediente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GestorCostosApplicationTests {

	@Test
	void deberiaCalcularTotalCorrectamente() {
		// ARRANGE (PREPARAR) -- Given un registroCompra con una compra de 1000
		RegistroCompras registroCompras = new RegistroCompras();
		CompraIngrediente compra = new CompraIngrediente();
		compra.setPrecioTotal(1000);

		registroCompras.agregarCompra(compra);

		//ACT (EJECUTAR) -- When calculo el total
		double total = registroCompras.calcularTotalGastado();

		//ASSERT (VERIFICAR) -- Then el total debe ser 1000
		assertEquals(1000, total);
	}

	@Test
	void deberiaContarComprasCorrectamente() {
		// ARRANGE (PREPARAR)
		RegistroCompras registroCompras = new RegistroCompras();
		CompraIngrediente compra = new CompraIngrediente();
		CompraIngrediente compra2 = new CompraIngrediente();

		compra.setPrecioTotal(1000);
		compra2.setPrecioTotal(2000);

		registroCompras.agregarCompra(compra);
		registroCompras.agregarCompra(compra2);

		//ACT (EJECUTAR)
		int totalCompras = registroCompras.getTotalCompras();

		//ASSERT (VERIFICAR)
		assertEquals(2, totalCompras);
	}

	@Test
	void deberiaRetornarCeroCuandoNoHayCompras() { //Test de valor de retorno, no de excepción, se usa assertEquals
		//GIVEN
		RegistroCompras registroCompras = new RegistroCompras();

		//WHEN
		double total = registroCompras.calcularTotalGastado();

		//THEN
		assertEquals(0.0, total);
	}

	@Test
	void deberiaLanzarExcepcionCuandoPrecioEsNegativoEnConstructor() {
		assertThrows(IllegalArgumentException.class, () -> {
			new CompraIngrediente("Harina", -500);
		});
	}

	@Test
	void deberiaLanzarExcepcionCuandoPrecioEsNegativoEnSetter() {
		CompraIngrediente compra = new CompraIngrediente();

		assertThrows(IllegalArgumentException.class, () -> {
			compra.setPrecioTotal(-500);
		});
	}

	@Test
	void deberiaLanzarExcepcionCuandoPrecioEsCeroEnConstructor() {
		assertThrows(IllegalArgumentException.class, () -> {
			new CompraIngrediente("Harina", 0);
		});
	}

	@Test
	void deberiaLanzarExcepcionCuandoPrecioEsCeroEnSetter() {
		CompraIngrediente compra = new CompraIngrediente();

		assertThrows(IllegalArgumentException.class, () -> {
			compra.setPrecioTotal(0);
		});
	}

	@Test
	void deberiaLanzarExcepcionCuandoCompraEsNull() {
		RegistroCompras registroCompras = new RegistroCompras();

		assertThrows(IllegalArgumentException.class, () -> {
			registroCompras.agregarCompra(null);
		});
	}

	@Test
	void deberiaRetornarCeroCuandoNoHayComprasParaPromedio() {
		//GIVEN
		RegistroCompras registroCompras = new RegistroCompras();

		//WHEN
		double promedio = registroCompras.calcularPromedio();

		//THEN
		assertEquals(0.0, promedio);
	}

	@Test
	void deberiaCalcularPromedioCorrectamenteCuandoHayCompras() {
		//GIVEN
		RegistroCompras registroCompras = new RegistroCompras();
		CompraIngrediente compra = new CompraIngrediente();
		CompraIngrediente compra2 = new CompraIngrediente();
		registroCompras.agregarCompra(compra);
		registroCompras.agregarCompra(compra2);

		compra.setPrecioTotal(100);
		compra2.setPrecioTotal(300);

		registroCompras.agregarCompra(compra);
		registroCompras.agregarCompra(compra2);

		//WHEN
		double promedio = registroCompras.calcularPromedio();

		//THEN
		assertEquals(200.0, promedio);
	}
}
