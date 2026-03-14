package com.antonella.gestor_costos.controller;

import com.antonella.gestor_costos.entity.CompraIngrediente;
import com.antonella.gestor_costos.service.RegistroCompras;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class WebController {
    private RegistroCompras registro;

    // 1. Aquí se crea el constructor para inyectar el Service
    public WebController(RegistroCompras registro) {
        this.registro = registro;
    }

    // 2. Creamos la ruta para el navegador
    @GetMapping("/inventario")
    public List<CompraIngrediente> obtenerInventario() {
        // Simplemente retornamos la lista cruda que nos da el Service.
        // El @RestController se encargará de transformarla en JSON automáticamente.
        return registro.listaCompleta();
    }

    // 1. Creamos la ruta POST para recibir los datos del formulario web
    @PostMapping("/inventario/guardar")
    // @RequestBody le dice a Java: "Toma el JSON que envía Angular y conviértelo en el objeto CompraIngrediente"
    public CompraIngrediente guardarCompraDesdeWeb(@RequestBody CompraIngrediente nuevaCompra) {
        // 1. Usamos tu Service con su escudo de seguridad intacto
        registro.agregarCompra(nuevaCompra);
        // 2. En lugar de redirigir la página, simplemente devolvemos el objeto guardado
        return nuevaCompra;
    }

    // Los corchetes {id} significan que esa parte de la URL va a cambiar (ej. /eliminar/1, /eliminar/5)
    @DeleteMapping("/inventario/eliminar/{id}")
    public void eliminarCompraAPI(@PathVariable Long id) {
        // Llama a tu Service para que haga el trabajo sucio
        registro.eliminarCompra(id);
    }

    @GetMapping("/inventario/editar/{id}")
    public String mostrarFormularioDeEdicion(@PathVariable Long id, Model model) {

        // 1. Buscamos el ingrediente específico en la base de datos
        CompraIngrediente ingredienteEncontrado = registro.obtenerCompraPorId(id);

        // 2. Lo metemos en la caja (Model) para enviarlo al HTML
        model.addAttribute("ingrediente", ingredienteEncontrado);

        // 3. Le decimos a Spring Boot que abra un archivo llamado "editar.html"
        return "editar";
    }
}
