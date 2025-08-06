package com.digis01.KMedranoProgramacionNCapasJulio25.Controller;

import com.digis01.KMedranoProgramacionNCapasJulio25.DAO.AlumnoDAOImplementation;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("alumno")
public class AlumnoController {
    
    @Autowired
    private AlumnoDAOImplementation alumnoDAOImplementation;
    
    @GetMapping // verbo http GET, POST, PUT, DELETE, PATCH
    public String Index(Model model){
        Result result = alumnoDAOImplementation.GetAll();
        
        if (result.correct) {
            model.addAttribute("alumnos", result.objects);
        } else  {
            model.addAttribute("alumnos", null);
        }
        
        return "AlumnoIndex";
    }
    
    @GetMapping("alumnoDetail/{idAlumno}")
    public String AlumnoDetail(@PathVariable int idAlumno, Model model){
        
        /*
        alumnoDAOImplementation.GetDetail(idAlumno);
        vista que muestre info alumno y una tabla con sus direcciones
        */
        
        return "AlumnoDetail";
    }
}
