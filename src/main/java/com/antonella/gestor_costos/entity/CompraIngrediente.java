package com.antonella.gestor_costos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // Esto le avisa a Java que esta clase ahora es una tabla de base de datos
public class CompraIngrediente {
    // ==========================================
    // 1. ATRIBUTOS
    // ==========================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private double precioTotal;

    // ==========================================
    // 2. CONSTRUCTORES
    // ==========================================

    // Constructor vacío (necesario para Spring/Frameworks)
    public CompraIngrediente() {
    }

    // Constructor con datos para que sea más fácil crear objetos
    public CompraIngrediente(String nombre, double precioTotal) {
        this.nombre = nombre;
        setPrecioTotal(precioTotal); // Usamos el setter para mantener la validación
    }

    // ==========================================
    // 3. GETTERS Y SETTERS (Agrupados por variable)
    // ==========================================

    // --- ID ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // --- Nombre ---
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // --- Precio Total ---
    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        if (precioTotal <= 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.precioTotal = precioTotal;
    }
}
