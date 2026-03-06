package com.antonella.gestor_costos.controller;

import com.antonella.gestor_costos.entity.CompraIngrediente;
import com.antonella.gestor_costos.service.RegistroCompras;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//1. Le decimos a Spring que este es un Controlador web
@RestController
public class ProductosController {
    private RegistroCompras registro;

    public ProductosController(RegistroCompras registro) {
        this.registro = registro;
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

    @PostMapping("/compras")
    public String agregarNuevaCompra(@RequestBody CompraIngrediente nuevaCompra) {
        registro.agregarCompra(nuevaCompra);
        return "¡Ingrediente guardado con éxito!";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String manejarErrorDePrecio(IllegalArgumentException exception) {
        return exception.getMessage(); // ¡Paréntesis vacíos!
    }

    @PostMapping("/compras/lote")
    public void agregarVariasCompras(@RequestBody List<CompraIngrediente> compras) {
        registro.agregarVariasCompras(compras);
    }

}
