package com.digis01.KMedranoProgramacionNCapasJulio25.Controller;

import com.digis01.KMedranoProgramacionNCapasJulio25.DAO.AlumnoDAOImplementation;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Alumno;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("alumno")
public class AlumnoController {

    @Autowired
    private AlumnoDAOImplementation alumnoDAOImplementation;

    @GetMapping // verbo http GET, POST, PUT, DELETE, PATCH
    public String Index(Model model) {
        Result result = alumnoDAOImplementation.GetAll();

        if (result.correct) {
            model.addAttribute("alumnos", result.objects);
        } else {
            model.addAttribute("alumnos", null);
        }

        return "AlumnoIndex";
    }

    @GetMapping("alumnoDetail/{idAlumno}")
    public String AlumnoDetail(@PathVariable int idAlumno, Model model) {

        Result result = alumnoDAOImplementation.DireccionesByIdAlumno(idAlumno);

        if (result.correct) {
            model.addAttribute("alumno", result.object);
        } else {
            return "Error";
        }

        return "AlumnoDetail";
    }

    @GetMapping("add") // localhost:8081/alumno/add
    public String add(Model model) {

        model.addAttribute("Alumno", new Alumno());

        return "AlumnoForm";
    }

    //Proceso de agregado
    @PostMapping("add") // localhost:8081/alumno/add
    public String Add(@ModelAttribute Alumno alumno) {

        Result result = alumnoDAOImplementation.Add(alumno);
        return "redirect:/alumno";
    }
}
