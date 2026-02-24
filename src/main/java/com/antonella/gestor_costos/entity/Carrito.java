package com.antonella.gestor_costos.entity;

import java.util.ArrayList;
import java.util.List;

public class Carrito {

    List<CompraIngrediente> compras;

    public Carrito(){
        compras = new ArrayList<>();
    }

    public void agregarCompra(CompraIngrediente compra) {
        compras.add(compra);
    }

    public double calcularTotalGastado() {
        double total = 0;
        for (CompraIngrediente compra : compras) {
            total += compra.getPrecioTotal();
        }
        return total;
    }

    public int getTotalCompras() {
        return compras.size();
    }
}
