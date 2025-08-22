package com.digis01.KMedranoProgramacionNCapasJulio25.Controller;

import com.digis01.KMedranoProgramacionNCapasJulio25.DAO.AlumnoDAOImplementation;
import com.digis01.KMedranoProgramacionNCapasJulio25.DAO.EstadoDAOImplementation;
import com.digis01.KMedranoProgramacionNCapasJulio25.DAO.MunicipioDAOImplementation;
import com.digis01.KMedranoProgramacionNCapasJulio25.DAO.SemestreDAOImplementatiton;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Alumno;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Colonia;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Direccion;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.ErrorCM;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Estado;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Municipio;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Result;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Semestre;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("alumno")
public class AlumnoController {

    @Autowired
    private AlumnoDAOImplementation alumnoDAOImplementation;

    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;

    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;

    @Autowired
    private SemestreDAOImplementatiton semestreDAOImplementatiton;

    @GetMapping // verbo http GET, POST, PUT, DELETE, PATCH
    public String Index(Model model) {
        Result result = alumnoDAOImplementation.GetAll(new Alumno("", "", ""));

        model.addAttribute("alumnoBusqueda", new Alumno());

        if (result.correct) {
            model.addAttribute("alumnos", result.objects);
        } else {
            model.addAttribute("alumnos", null);
        }

        return "AlumnoIndex";
    }

    @PostMapping
    public String Index(Model model, @ModelAttribute("alumnoBusqueda") Alumno alumnoBusqueda ) {

        Result result = alumnoDAOImplementation.GetAll(alumnoBusqueda);
        
//        model.addAttribute("alumnoBusqueda", alumnoBusqueda);
        model.addAttribute("alumnos", result.objects);
        
        return "AlumnoIndex";
    }

    @GetMapping("/action/{IdAlumno}") // localhost:8081/alumno/add
    public String add(Model model, @PathVariable("IdAlumno") int IdAlumno) {
//        Result result = semestreDAOImplementatiton.GetAll();

        if (IdAlumno == 0) {
            model.addAttribute("semestres", semestreDAOImplementatiton.GetAll().objects);
            model.addAttribute("Alumno", new Alumno());

            return "AlumnoForm";
        } else {
            Result result = alumnoDAOImplementation.DireccionesByIdAlumno(IdAlumno);

            if (result.correct) {
                model.addAttribute("Alumno", result.object);
            } else {
                return "Error";
            }

            return "AlumnoDetail";
        }

    }

    @GetMapping("formEditable")
    public String formEditable(
            @RequestParam int IdAlumno,
            @RequestParam(required = false) Integer IdDireccion,
            Model model) {

        if (IdDireccion == null) {  // Editar Usuario
            /* recuperar información del usuario*/
            Alumno alumno = new Alumno(); //esto lo haré manual
            alumno.setIdAlumno(IdAlumno);
            alumno.Direcciones = new ArrayList<>();
            alumno.Direcciones.add(new Direccion(-1));
            model.addAttribute("Alumno", alumno);
        } else if (IdDireccion == 0) {//Agregar direccion
            model.addAttribute("Alumno", new Alumno(IdAlumno));
        } else { // editar direccion
            /*recuperar información de la direccion*/
            Alumno alumno = new Alumno(2);
            alumno.Direcciones = new ArrayList<>();
            Direccion direccion = new Direccion(2, "Test", "15", "422", new Colonia(1));
            direccion.Colonia.Municipio = new Municipio();
            direccion.Colonia.Municipio.Estado = new Estado();
            direccion.Colonia.Municipio.Estado.setIdEstado(3);
            alumno.Direcciones.add(direccion);
            model.addAttribute("Alumno", alumno);
            model.addAttribute("estados", estadoDAOImplementation.GetAllEstado().objects);
        }

        return "AlumnoForm";
    }

    //Proceso de agregado
    @PostMapping("add") // localhost:8081/alumno/add
    public String Add(@Valid @ModelAttribute("Alumno") Alumno alumno,
            BindingResult bindingResult,
            Model model,
            @RequestParam("imagenFile") MultipartFile imagen) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("Alumno", alumno);
            return "AlumnoForm";
        } else {

            if (imagen != null) {
                String nombre = imagen.getOriginalFilename();
                //archivo.jpg
                //[archivo,jpg]
                String extension = nombre.split("\\.")[1];
                if (extension.equals("jpg")) {
                    try {
                        byte[] bytes = imagen.getBytes();
                        String base64Image = Base64.getEncoder().encodeToString(bytes);
                        alumno.setImagen(base64Image);
                    } catch (Exception ex) {
                        System.out.println("Error");
                    }

                }
            }

            Result result = alumnoDAOImplementation.Add(alumno);
            return "redirect:/alumno";
        }
    }

    //getMunicipioByEstado?IdEstado=7 -- requestParam
    //getMunicipioByestado/7 -- pathVariable
    @GetMapping("getMunicipiosByIdEstado/{IdEstado}")
    @ResponseBody // retorne un dato estructurado - JSON
    public Result MunicipioByEstado(@PathVariable int IdEstado) {

        return municipioDAOImplementation.MunicipioByEstado(IdEstado);
    }

    @GetMapping("cargamasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }

    @PostMapping("cargamasiva")
    public String CargaMasiva(@RequestParam("archivo") MultipartFile file, Model model, HttpSession session) {

        String root = System.getProperty("user.dir");
        String rutaArchivo = "/src/main/resources/archivos/";
        String fechaSubida = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
        String rutaFinal = root + rutaArchivo + fechaSubida + file.getOriginalFilename();

        // 7/0
        // if (divisor == 0) throw("error")
        try {
            file.transferTo(new File(rutaFinal));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        if (file.getOriginalFilename().split("\\.")[1].equals("txt")) {
            List<Alumno> alumnos = ProcesarTXT(new File(rutaFinal));
            List<ErrorCM> errores = ValidarDatos(alumnos);

            if (errores.isEmpty()) {
                model.addAttribute("listaErrores", errores);
                model.addAttribute("archivoCorrecto", true);
                session.setAttribute("path", rutaFinal); // esto no regresa a la vista
            } else {
                model.addAttribute("listaErrores", errores);
                model.addAttribute("archivoCorrecto", false);
            }

            //si lista errores diferente de vacio, intentar desplegar lista de errores en carga masiva
        } else {
            // excel
            List<Alumno> alumnos = ProcesarExcel(new File(rutaFinal));
            List<ErrorCM> errores = ValidarDatos(alumnos);

            if (errores.isEmpty()) {
                model.addAttribute("listaErrores", errores);
                model.addAttribute("archivoCorrecto", true);
                session.setAttribute("path", rutaFinal); // esto no regresa a la vista
            } else {
                model.addAttribute("listaErrores", errores);
                model.addAttribute("archivoCorrecto", false);
            }

        }

        return "CargaMasiva";
    }

    @GetMapping("cargamasiva/procesar")
    public String CargaMasiva(HttpSession session) {
        try {

            String ruta = session.getAttribute("path").toString();

            List<Alumno> alumnos;

            if (ruta.split("\\.")[1].equals("txt")) {
                alumnos = ProcesarTXT(new File(ruta));
            } else {
                alumnos = ProcesarExcel(new File(ruta));
            }

            for (Alumno alumno : alumnos) {
                alumnoDAOImplementation.Add(alumno);
            }

            session.removeAttribute("path");

        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return "redirect:/alumno";
    }

    private List<Alumno> ProcesarTXT(File file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String linea = "";
            List<Alumno> alumnos = new ArrayList<>();
            while ((linea = bufferedReader.readLine()) != null) {
                String[] campos = linea.split("\\|");
                Alumno alumno = new Alumno();
                alumno.setNombre(campos[0]);
                alumno.setApellidoPaterno(campos[1]);
                alumno.setApellidoMaterno(campos[2]);
                alumno.setUserName(campos[3]);
                alumno.Semestre = new Semestre();
                alumno.Semestre.setIdSemestre(Integer.parseInt(campos[4]));
                alumnos.add(alumno);
            }
            return alumnos;
        } catch (Exception ex) {
            System.out.println("error");
            return null;
        }
    }

    private List<Alumno> ProcesarExcel(File file) {

        List<Alumno> alumnos = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Alumno alumno = new Alumno();
                alumno.setNombre(row.getCell(0) != null ? row.getCell(0).toString() : "");
                alumno.setApellidoPaterno(row.getCell(1).toString());
                alumno.Semestre = new Semestre();
                alumno.Semestre.setIdSemestre(row.getCell(4) != null ? (int) row.getCell(4).getNumericCellValue() : 0);

                alumnos.add(alumno);
            }
            return alumnos;
        } catch (Exception ex) {
            return null;
        }

    }

    private List<ErrorCM> ValidarDatos(List<Alumno> alumnos) {
        List<ErrorCM> errores = new ArrayList<>();
        int linea = 1;
        for (Alumno alumno : alumnos) {
            if (alumno.getNombre() == null || alumno.getNombre() == "") {
                errores.add(new ErrorCM(linea, alumno.getNombre() == "" ? "vacio" : alumno.getNombre(), "Nombre es un campo obligatorio"));
            }
            if (alumno.getApellidoPaterno() == null || alumno.getApellidoPaterno() == "") {
                errores.add(new ErrorCM(linea, alumno.getApellidoPaterno(), "Campo obligatorio"));
            }

            if (alumno.Semestre.getIdSemestre() == 0) {
                errores.add(new ErrorCM(linea, alumno.Semestre.getIdSemestre() + "", "Numero de colonia no valido"));
            }
            linea++;
        }

        return errores;
    }
}
