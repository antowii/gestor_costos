package com.antonella.gestor_costos.service;

import com.antonella.gestor_costos.entity.CompraIngrediente;
import com.antonella.gestor_costos.repository.CompraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroCompras {

    private CompraRepository repository;

    public RegistroCompras(CompraRepository repository){
        this.repository = repository;
    }

    public void agregarCompra(CompraIngrediente compra) {
        if (compra == null) {
            throw new IllegalArgumentException("La compra no puede ser null");
        }
        repository.save(compra);
    }

    public double calcularTotalGastado() {
        double total = 0; //Aquí no hay necesidad de poner un if ya que la lista ya está vacía, en cero
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

    public List<CompraIngrediente> listaCompleta() {
        return repository.findAll();
    }

    public void agregarVariasCompras(List<CompraIngrediente> compras) {
        if (compras == null || compras.isEmpty()) {
            throw new IllegalArgumentException("La lista de compras no puede estar vacía");
        }
        repository.saveAll(compras);
    }
}
