package com.antonella.gestor_costos;

import com.antonella.gestor_costos.entity.Carrito;
import com.antonella.gestor_costos.entity.CompraIngrediente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GestorCostosApplicationTests {

	@Test
	void deberiaCalcularTotalCorrectamente() {
		// ARRANGE (PREPARAR) -- Given un carrito con una compra de 1000
		Carrito carrito = new Carrito();
		CompraIngrediente compra = new CompraIngrediente();
		compra.setPrecioTotal(1000);

		carrito.agregarCompra(compra);

		//ACT (EJECUTAR) -- When calculo el total
		double total = carrito.calcularTotalGastado();

		//ASSERT (VERIFICAR) -- Then el total debe ser 1000
		assertEquals(1000, total);
	}

	@Test
	void deberiaContarComprasCorrectamente() {
		// ARRANGE (PREPARAR)
		Carrito carrito = new Carrito();
		CompraIngrediente compra = new CompraIngrediente();
		CompraIngrediente compra2 = new CompraIngrediente();

		compra.setPrecioTotal(1000);
		compra2.setPrecioTotal(2000);

		carrito.agregarCompra(compra);
		carrito.agregarCompra(compra2);

		//ACT (EJECUTAR)
		int totalCompras = carrito.getTotalCompras();

		//ASSERT (VERIFICAR)
		assertEquals(2, totalCompras);
	}
}
