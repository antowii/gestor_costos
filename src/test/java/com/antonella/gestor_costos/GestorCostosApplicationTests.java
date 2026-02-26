package com.antonella.gestor_costos;

import com.antonella.gestor_costos.entity.RegistroCompras;
import com.antonella.gestor_costos.entity.CompraIngrediente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GestorCostosApplicationTests {
	RegistroCompras registroCompras;

	@BeforeEach
	void setUp() {
		registroCompras = new RegistroCompras();
	}

	@Test
	void deberiaCalcularTotalCorrectamente() {
		// ARRANGE (PREPARAR) -- Given un registroCompra con una compra de 1000
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
		//GIVEN (AQUI FUNCIONA EL BEFORE EACH)

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
		assertThrows(IllegalArgumentException.class, () -> {
			registroCompras.agregarCompra(null);
		});
	}

	@Test
	void deberiaRetornarCeroCuandoNoHayComprasParaPromedio() {
		//GIVEN (AQUI FUNCIONA EL BEFORE EACH)

		//WHEN
		double promedio = registroCompras.calcularPromedio();

		//THEN
		assertEquals(0.0, promedio);
	}

	@Test
	void deberiaCalcularPromedioCorrectamenteCuandoHayCompras() {
		//GIVEN
		CompraIngrediente compra = new CompraIngrediente();
		CompraIngrediente compra2 = new CompraIngrediente();

		compra.setPrecioTotal(100);
		compra2.setPrecioTotal(300);

		registroCompras.agregarCompra(compra);
		registroCompras.agregarCompra(compra2);

		//WHEN
		double promedio = registroCompras.calcularPromedio();

		//THEN
		assertEquals(200.0, promedio);
	}

	@Test
	void deberiaCalcularPromedioConDecimales() {
		//GIVEN
		CompraIngrediente compra1 = new CompraIngrediente();
		CompraIngrediente compra2 = new CompraIngrediente();

		compra1.setPrecioTotal(100);
		compra2.setPrecioTotal(101);

		registroCompras.agregarCompra(compra1);
		registroCompras.agregarCompra(compra2);

		//WHEN
		double promedio = registroCompras.calcularPromedio();

		//THEN
		assertEquals(100.5, promedio, 0.001);
		//El delta es el margen de tolerancia permitido en la comparación.
		//"Acepto que el valor real esté a una pequeña distancia del esperado"
		//Generalmente es 0.001 o 0.0001
		//En este caso es: Acepto valores entre 100.499 y 100.501
		//Para int → no necesitas delta
		//Para double → casi siempre usa delta
	}

	@Test
	void deberiaRetornarCompraMasCara() {
		//GIVEN
		CompraIngrediente compra1 = new CompraIngrediente();
		CompraIngrediente compra2 = new CompraIngrediente();

		compra1.setPrecioTotal(100);
		compra2.setPrecioTotal(300);

		registroCompras.agregarCompra(compra1);
		registroCompras.agregarCompra(compra2);

		//WHEN
		CompraIngrediente compraMasCara = registroCompras.obtenerCompraMasCara();
		//“Quiero crear una variable llamada compraMasCara que va a guardar un objeto CompraIngrediente”

		//THEN
		assertEquals(compra2, compraMasCara);
	}
}
