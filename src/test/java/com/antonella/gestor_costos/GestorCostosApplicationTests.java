package com.antonella.gestor_costos;

import com.antonella.gestor_costos.repository.CompraRepository;
import com.antonella.gestor_costos.service.RegistroCompras;
import com.antonella.gestor_costos.entity.CompraIngrediente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GestorCostosApplicationTests {
	private RegistroCompras registroCompras;
	private CompraRepository compraRepositoryMock; // 1. Declaramos la variable para el mock

	@BeforeEach
	void setUp() {
		// 2. Creamos un Repository falso (mock)
		compraRepositoryMock = Mockito.mock(CompraRepository.class);
		// 3. Le inyectamos el mock al Service al momento de crearlo
		registroCompras = new RegistroCompras(compraRepositoryMock);
	}

	@Test
	void deberiaCalcularTotalCorrectamente() {
		// 1. GIVEN: Creamos los datos falsos y la lista
		CompraIngrediente compra1 = new CompraIngrediente("Harina", 500);
		CompraIngrediente compra2 = new CompraIngrediente("Manjar", 500);
		List<CompraIngrediente> listaSimulada = Arrays.asList(compra1, compra2);

		// 2. EL ENTRENAMIENTO DEL MOCK: Le decimos qué responder
		Mockito.when(compraRepositoryMock.findAll()).thenReturn(listaSimulada);

		// 3. WHEN: Ejecutamos el metodo del Service
		double total = registroCompras.calcularTotalGastado();

		// 4. THEN: Verificamos el resultado
		assertEquals(1000.0, total);
	}

	@Test
	void deberiaContarComprasCorrectamente() {
		// 1. GIVEN: Creamos los datos falsos y la lista
		CompraIngrediente compra1 = new CompraIngrediente("Harina", 500);
		CompraIngrediente compra2 = new CompraIngrediente("Manjar", 1500);
		List<CompraIngrediente> listaSimulada = Arrays.asList(compra1, compra2);

		// 2. EL ENTRENAMIENTO DEL MOCK: Le decimos qué responder
		Mockito.when(compraRepositoryMock.findAll()).thenReturn(listaSimulada);

		// 3. WHEN: Ejecutamos el metodo del Service
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
		// 1. GIVEN: Creamos los datos falsos y la lista
		CompraIngrediente compra1 = new CompraIngrediente("Harina", 500);
		CompraIngrediente compra2 = new CompraIngrediente("Manjar", 1500);
		List<CompraIngrediente> listaSimulada = Arrays.asList(compra1, compra2);

		// 2. EL ENTRENAMIENTO DEL MOCK: Le decimos qué responder
		Mockito.when(compraRepositoryMock.findAll()).thenReturn(listaSimulada);

		// 3. WHEN: Ejecutamos el metodo del Service
		double promedio = registroCompras.calcularPromedio();

		//THEN
		assertEquals(1000.0, promedio);
	}

	@Test
	void deberiaCalcularPromedioConDecimales() {
		// 1. GIVEN: Dos precios que sumen 201 (para que el promedio sea 100.5)
		CompraIngrediente compra1 = new CompraIngrediente("Harina", 100.0);
		CompraIngrediente compra2 = new CompraIngrediente("Manjar", 101.0);
		List<CompraIngrediente> listaSimulada = Arrays.asList(compra1, compra2);

		// 2. ENTRENAR AL MOCK
		Mockito.when(compraRepositoryMock.findAll()).thenReturn(listaSimulada);

		// 3. WHEN & 4. THEN
		assertEquals(100.5, registroCompras.calcularPromedio());
	}

	@Test
	void deberiaRetornarCompraMasCara() {
		// 1. GIVEN: Una barata y una cara
		CompraIngrediente barata = new CompraIngrediente("Harina", 500);
		CompraIngrediente cara = new CompraIngrediente("Manjar", 2500);
		List<CompraIngrediente> listaSimulada = Arrays.asList(barata, cara);

		// 2. ENTRENAR AL MOCK
		Mockito.when(compraRepositoryMock.findAll()).thenReturn(listaSimulada);

		// 3. WHEN
		CompraIngrediente resultado = registroCompras.obtenerCompraMasCara();

		// 4. THEN: Verificamos que el objeto devuelto sea exactamente la variable "cara"
		assertEquals(cara, resultado);
	}

	@Test
	void deberiaRetornarCompraMasBarata() {
		// 1. GIVEN: Una barata y una cara
		CompraIngrediente barata = new CompraIngrediente("Harina", 500);
		CompraIngrediente cara = new CompraIngrediente("Manjar", 2500);
		List<CompraIngrediente> listaSimulada = Arrays.asList(barata, cara);

		// 2. ENTRENAR AL MOCK
		Mockito.when(compraRepositoryMock.findAll()).thenReturn(listaSimulada);

		// 3. WHEN
		CompraIngrediente resultado = registroCompras.obtenerCompraMasBarata();

		// 4. THEN: Verificamos que el objeto devuelto sea exactamente la variable "barata"
		assertEquals(barata, resultado);
	}

	@Test
	void deberiaEditarCompraExitosamente() {
		// 1. GIVEN
		CompraIngrediente compraEditada = new CompraIngrediente("Harina", 800);
		compraEditada.setId(1L);

		// 2. WHEN
		CompraIngrediente resultado = registroCompras.agregarCompra(compraEditada);

		// 3. THEN
		assertEquals(1L, resultado.getId());
		assertEquals(800.0, resultado.getPrecioTotal());
	}

	@Test
	void deberiaBloquearIngredienteDuplicado() {
		// 1. GIVEN: ya existe Harina en el sistema
		CompraIngrediente existente = new CompraIngrediente("Harina", 1000);
		Mockito.when(compraRepositoryMock.findAll()).thenReturn(List.of(existente));

		// 2. WHEN & THEN: intentamos agregar otra Harina (con distinto precio)
		CompraIngrediente duplicado = new CompraIngrediente("Harina", 1200);
		//Capturamos la excepción para validar el mensaje
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			registroCompras.agregarCompra(duplicado);
		});

		// 3. EXTRA ASSERT: ¿El mensaje es correcto?
		assertEquals("Este ingrediente ya existe en el inventario", exception.getMessage());
	}
}
