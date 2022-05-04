package net.lafar.gc.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/nombre")
    String nombre(){
        return "Hola Mundo";
    }

    @GetMapping("/nombreCompleto/{id}")
    String nombreCompleto(@PathVariable Integer id){
        return "Hola Mundo " + id;
    }

    @GetMapping("/nombreVariable")
    String nombreVariable(@RequestParam(name = "nombre") String nombre){
        return "Hola Mundo " + nombre;
    }

}
