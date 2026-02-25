package com.antonella.gestor_costos.entity;

import java.util.ArrayList;
import java.util.List;

public class RegistroCompras {

    List<CompraIngrediente> compras;

    public RegistroCompras(){
        compras = new ArrayList<>();
    }

    public void agregarCompra(CompraIngrediente compra) {
        if (compra == null) {
            throw new IllegalArgumentException("La compra no puede ser null");
        }
        compras.add(compra);
    }

    public double calcularTotalGastado() {
        double total = 0; //Aquí no hay necesidad de poner un if ya que la lista ya está vacía, en cero
        for (CompraIngrediente compra : compras) {
            total += compra.getPrecioTotal();
        }
        return total;
    }

    public int getTotalCompras() {
        return compras.size();
    }

    public double calcularPromedio() {
        double promedio = 0;
        if (getTotalCompras() == 0) {
           return 0;
       }
        return calcularTotalGastado() / getTotalCompras();
    }
}
