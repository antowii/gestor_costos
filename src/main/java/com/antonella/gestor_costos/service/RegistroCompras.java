package com.antonella.gestor_costos.service;

import com.antonella.gestor_costos.entity.CompraIngrediente;
import com.antonella.gestor_costos.repository.CompraRepository;

public class RegistroCompras {

    private CompraRepository repository;

    public RegistroCompras(){
        repository = new CompraRepository();
    }

    public void agregarCompra(CompraIngrediente compra) {
        if (compra == null) {
            throw new IllegalArgumentException("La compra no puede ser null");
        }
        repository.agregarCompra(compra);
    }

    public double calcularTotalGastado() {
        double total = 0; //Aquí no hay necesidad de poner un if ya que la lista ya está vacía, en cero
        for (CompraIngrediente compra : repository.getCompras()) {
            total += compra.getPrecioTotal();
        }
        return total;
    }

    public int getTotalCompras() {
        return repository.getCompras().size();
    }

    public double calcularPromedio() {
        if (getTotalCompras() == 0) {
           return 0;
       }
        return calcularTotalGastado() / getTotalCompras();
    }

    public CompraIngrediente obtenerCompraMasCara() {
        CompraIngrediente masCara = null;
        for (CompraIngrediente compra : repository.getCompras()) {
            if (masCara == null || compra.getPrecioTotal() > masCara.getPrecioTotal()) {
                masCara = compra;
            }
        }
        return masCara;
    }

    public CompraIngrediente obtenerCompraMasBarata() {
        CompraIngrediente masBarata = null;
        for (CompraIngrediente compra : repository.getCompras()) {
            if (masBarata == null || compra.getPrecioTotal() < masBarata.getPrecioTotal()) {
                masBarata = compra;
            }
        }
        return masBarata;
    }
}
