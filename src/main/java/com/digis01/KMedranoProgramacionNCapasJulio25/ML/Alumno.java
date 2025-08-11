package com.digis01.KMedranoProgramacionNCapasJulio25.ML;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

public class Alumno {
    
    private int IdAlumno;
    private String Nombre;
    @Size(min = 2, max = 20, message = "Texto de entre 2 y 20 letras")
    @NotEmpty(message = "Información necesaria")
    private String ApellidoPaterno;
    private String ApellidoMaterno;
    private String UserName;
    public Semestre Semestre; // propiedad de navegación (No ocupa setter ni getter)
    public List<Direccion> Direcciones;
    
    
    
    public Alumno(){}

    public Alumno(int IdAlumno, String Nombre, String ApellidoPaterno, String ApellidoMaterno, String UserName) {
        this.IdAlumno = IdAlumno;
        this.Nombre = Nombre;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;
        this.UserName = UserName;
    }
    
    
    
    public void setIdAlumno(int idAlumno){
        this.IdAlumno = idAlumno;
    }
    
    public int getIdAlumno(){
        return this.IdAlumno;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public Semestre getSemestre() {
        return Semestre;
    }

    public void setSemestre(Semestre Semestre) {
        this.Semestre = Semestre;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }
    
    
}   
