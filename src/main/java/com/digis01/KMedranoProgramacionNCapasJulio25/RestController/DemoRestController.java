package com.digis01.KMedranoProgramacionNCapasJulio25.RestController;

import com.digis01.KMedranoProgramacionNCapasJulio25.JPA.Alumno;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo")
public class DemoRestController {

        @GetMapping
        public Alumno Index(){
            return new Alumno();
        }
        
        @GetMapping("/suma")
        public String Suma(@RequestParam int numeroA, @RequestParam int numeroB){
            return "La suma es " + (numeroA + numeroB);
        }
        
        @PostMapping("Saludo")
        public String Saludo(@RequestBody Alumno alumno){
            return "Hola " + alumno.getNombre();
        }
        
        
        
        
}
