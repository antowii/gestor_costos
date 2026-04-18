package com.antonella.gestor_costos.controller;

import com.antonella.gestor_costos.entity.CompraIngrediente;
import com.antonella.gestor_costos.service.RegistroCompras;
import org.springframework.http.ResponseEntity;
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
        return registro.agregarCompra(nuevaCompra);
    }

    // Los corchetes {id} significan que esa parte de la URL va a cambiar (ej. /eliminar/1, /eliminar/5)
    @DeleteMapping("/inventario/eliminar/{id}")
    public void eliminarCompraAPI(@PathVariable Long id) {
        // Llama a tu Service para que haga el trabajo sucio
        registro.eliminarCompra(id);
    }

    // Usamos PUT para actualizar. Recibimos el ID por la URL y los nuevos datos por JSON.
    @PutMapping("/inventario/editar/{id}")
    public CompraIngrediente editarCompraAPI(@PathVariable Long id, @RequestBody CompraIngrediente compraEditada) {
        // 1. Le asignamos a la compra el ID exacto que queremos editar
        // para asegurarnos de no sobreescribir otro ingrediente por error.
        compraEditada.setId(id);

        // 2. Usamos tu mismo metodo de guardar.
        // (Nota: En Spring Boot / JPA, si guardas un objeto que ya tiene un ID existente,
        // en lugar de crear uno nuevo, actualiza el que ya existe).
        return registro.agregarCompra(compraEditada);
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

    // MANEJO DE EXCEPCIONES (Traductor a HTTP 400)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarExcepciones(IllegalArgumentException ex) {
        // badRequest() genera el código HTTP 400
        // body() inyecta el texto exacto que pusimos en el Service ("Este ingrediente ya existe...")
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
