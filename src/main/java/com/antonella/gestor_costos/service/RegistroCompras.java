package com.antonella.gestor_costos.service;

import com.antonella.gestor_costos.entity.CompraIngrediente;
import com.antonella.gestor_costos.repository.CompraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroCompras {

    // ==========================================
    // 1. ATRIBUTOS Y DEPENDENCIAS
    // ==========================================
    private CompraRepository repository;

    // ==========================================
    // 2. CONSTRUCTOR
    // ==========================================
    public RegistroCompras(CompraRepository repository){
        this.repository = repository;
    }

    // ==========================================
    // 3. MÉTODOS TRANSACCIONALES (Crear, Actualizar, Eliminar)
    // ==========================================
    public CompraIngrediente agregarCompra(CompraIngrediente compra) {
        // Validamos primero, antes de que List.of() entre en panico
        validarCompra(compra);
        // Envolvemos el ingrediente único en una lista y se lo pasamos al metodo grande
        agregarVariasCompras(List.of(compra));
        // Retornamos el objeto (Hibernate, por detrás, ya le habrá puesto el ID si es nuevo)
        return compra;
    }

    public void agregarVariasCompras(List<CompraIngrediente> compras) {
        // Usamos un bucle para validar cada ingrediente de la lista antes de guardar
        for (CompraIngrediente ingrediente : compras) {
            validarCompra(ingrediente);
        }
        // Si todos pasaron la validación, guardamos la lista completa de un golpe
        repository.saveAll(compras);
    }

    public void eliminarCompra(Long id) {
        // El repositorio de Spring Data JPA ya trae este metodo listo para usar
        repository.deleteById(id);
    }

    // ==========================================
    // 4. MÉTODOS DE CONSULTA (Lectura)
    // ==========================================
    public List<CompraIngrediente> listaCompleta() {
        return repository.findAll();
    }

    public CompraIngrediente obtenerCompraPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    // ==========================================
    // 5. MÉTODOS DE LÓGICA Y CÁLCULO
    // ==========================================
    public double calcularTotalGastado() {
        double total = 0;
        for (CompraIngrediente compra : repository.findAll()) {
            total += compra.getPrecioTotal();
        }
        return total;
    }

    public int getTotalCompras() {
        return repository.findAll().size();
    }

    public double calcularPromedio() {
        if (getTotalCompras() == 0) {
            return 0;
        }
        return calcularTotalGastado() / getTotalCompras();
    }

    public CompraIngrediente obtenerCompraMasCara() {
        CompraIngrediente masCara = null;
        for (CompraIngrediente compra : repository.findAll()) {
            if (masCara == null || compra.getPrecioTotal() > masCara.getPrecioTotal()) {
                masCara = compra;
            }
        }
        return masCara;
    }

    public CompraIngrediente obtenerCompraMasBarata() {
        CompraIngrediente masBarata = null;
        for (CompraIngrediente compra : repository.findAll()) {
            if (masBarata == null || compra.getPrecioTotal() < masBarata.getPrecioTotal()) {
                masBarata = compra;
            }
        }
        return masBarata;
    }

    // ==========================================
    // 6. MÉTODOS PRIVADOS (Helpers)
    // ==========================================
    private void validarCompra(CompraIngrediente compra) {
        // 1. Primero validamos que el objeto entero no sea nulo
        if (compra == null) {
            throw new IllegalArgumentException("La compra no puede ser nula");
        }
        // 2. AHORA SÍ: Validamos el nombre usando null o isBlank()
        if (compra.getNombre() == null || compra.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del ingrediente es obligatorio y no puede estar vacío");
        }
        // 3. Validación de duplicados
        for (CompraIngrediente guardado : listaCompleta()) {
            //Comparamos el nombre guardado con el nombre del objeto nuevo que está entrando
            if (guardado.getNombre().equalsIgnoreCase(compra.getNombre())) {
                throw new IllegalArgumentException("Este ingrediente ya existe en el inventario");
            }
        }
    }

}