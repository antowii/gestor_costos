package com.antonella.gestor_costos.controller;

import com.antonella.gestor_costos.entity.CompraIngrediente;
import com.antonella.gestor_costos.service.RegistroCompras;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//1. Le decimos a Spring que este es un Controlador web
@RestController
public class ProductosController {
    private RegistroCompras registro;

    public ProductosController() {
        registro = new RegistroCompras();
        CompraIngrediente producto1 = new CompraIngrediente("Harina", 500);
        CompraIngrediente producto2 = new CompraIngrediente("Manjar", 1000);

        registro.agregarCompra(producto1);
        registro.agregarCompra(producto2);
    }
    //2. Le decimos qué ruta de internet va a escuchar
    @GetMapping("/bienvenida")
    public String darBienvenida() {
        //3. Lo que queremos que vea el usuario en su pantalla
        return "¡Hola! Bienvenidos al sistema de gestor de costos.";
    }

    @GetMapping("/total")
    public double getTotal() {
        return registro.calcularTotalGastado();
    }

    @GetMapping("/compras")
    public List<CompraIngrediente> getCompras() {
        return registro.listaCompleta();
    }

}
