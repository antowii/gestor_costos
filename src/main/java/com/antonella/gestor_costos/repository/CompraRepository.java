package com.antonella.gestor_costos.repository;

import com.antonella.gestor_costos.entity.CompraIngrediente;

import java.util.ArrayList;
import java.util.List;

public class CompraRepository {
    private List<CompraIngrediente> compras;

    public CompraRepository() {
        compras = new ArrayList<>();
    }

    public void agregarCompra(CompraIngrediente compra) {
        compras.add(compra);
    }

    public List<CompraIngrediente> getCompras() {
        return compras;
    }
}
