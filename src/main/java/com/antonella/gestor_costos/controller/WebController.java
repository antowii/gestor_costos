package com.antonella.gestor_costos.controller;

import com.antonella.gestor_costos.entity.CompraIngrediente;
import com.antonella.gestor_costos.service.RegistroCompras;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {
    private RegistroCompras registro;

    // 1. Aquí se crea el constructor para inyectar el Service
    public WebController(RegistroCompras registro) {
        this.registro = registro;
    }

    // 2. Creamos la ruta para el navegador
    @GetMapping("/inventario")
    public String mostrarPaginaInventario(Model model) {
        // 1. Le pedimos al Service la lista de la base de datos
        var listaDeCompras = registro.listaCompleta();

        // 2. Metemos la lista en la caja y le ponemos la etiqueta "misCompras"
        model.addAttribute("misCompras", listaDeCompras);

        // 3. Le pedimos los cálculos al Service y los empacamos con nuevas etiquetas
        model.addAttribute("totalGastado", registro.calcularTotalGastado());
        model.addAttribute("cantidadProductos", registro.getTotalCompras());

        // 4. Enviamos la caja al HTML
        return "index";
        // Al retornar este texto, Spring Boot buscará automáticamente
        // un archivo llamado "index.html" en tus carpetas.
    }

    // 1. Creamos la ruta POST para recibir los datos del formulario web
    @PostMapping("/inventario/guardar")
    public String guardarCompraDesdeWeb(CompraIngrediente nuevaCompra) {
        // 2. Usamos tu Service para guardar en la base de datos
        // (Nota: Asegúrate de que este metodo se llame igual al que tienes en tu Service,
        // a veces le ponen "agregarCompra" o "guardarCompra")
            registro.agregarCompra(nuevaCompra);

        // 3. ¡LA MAGIA! En lugar de devolver una página en blanco,
        // le decimos al navegador "Vuelve a cargar la página principal"
        return "redirect:/inventario";
    }

    // Los corchetes {id} significan que esa parte de la URL va a cambiar (ej. /eliminar/1, /eliminar/5)
    @GetMapping("/inventario/eliminar/{id}")
    public String eliminarCompraDesdeWeb(@PathVariable Long id) {
        // 1. Le pasamos el ID atrapado de la URL a tu Service
        registro.eliminarCompra(id);

        // 2. Recargamos la página mágicamente
        return "redirect:/inventario";
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
