package com.antonella.gestor_costos.entity;

public class CompraIngrediente {
    private String nombre; // Nuevo campo
    private double precioTotal;

    // Constructor vacío (necesario para Spring/Frameworks)
    public CompraIngrediente() {}

    // Constructor con datos para que sea más fácil crear objetos en los tests
    public CompraIngrediente(String nombre, double precioTotal) {
        this.nombre = nombre;
        setPrecioTotal(precioTotal);
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        if (precioTotal <= 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.precioTotal = precioTotal;
    }

    public String getNombre() {
        return nombre;
    }
}
