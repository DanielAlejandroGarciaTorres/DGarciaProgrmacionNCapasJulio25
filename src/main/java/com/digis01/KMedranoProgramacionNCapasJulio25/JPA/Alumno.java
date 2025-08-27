package com.digis01.KMedranoProgramacionNCapasJulio25.JPA;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Alumno {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idalumno")
    private int IdAlumno;
    @Column(name = "nombre")
    private String Nombre;
    @Column(name = "apellidopaterno")
    private String ApellidoPaterno;
    @Column(name = "apellidomaterno")
    private String ApellidoMaterno;
    @Column(name = "username")
    private String UserName;
    @ManyToOne
    @JoinColumn( name = "idsemestre")
    public Semestre Semestre; // propiedad de navegación (No ocupa setter ni getter)
    @Lob
    @Column( name = "imagen")
    private String Imagen;

    @OneToMany(mappedBy = "Alumno", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Direccion> Direcciones = new ArrayList<>();

    public Alumno() {
    }
    
    public Alumno (com.digis01.KMedranoProgramacionNCapasJulio25.ML.Alumno alumnoML){
        this.Nombre = alumnoML.getNombre();
        this.ApellidoPaterno = alumnoML.getApellidoPaterno();
        this.ApellidoMaterno = alumnoML.getApellidoMaterno();
        this.UserName = alumnoML.getUserName();
        this.Imagen = alumnoML.getImagen();
        this.Semestre = new Semestre();
        this.Semestre.setIdSemestre(alumnoML.Semestre.getIdSemestre());
        for (com.digis01.KMedranoProgramacionNCapasJulio25.ML.Direccion Direccione : alumnoML.Direcciones) {
            Direccion direccion = new Direccion();
            direccion.setCalle(Direccione.getCalle());
            direccion.setNumeroInterior(Direccione.getNumeroInterior());
            direccion.setNumeroExterior(Direccione.getNumeroExterior());
            direccion.Colonia = new Colonia();
            direccion.Colonia.setIdColonia(Direccione.Colonia.getIdColonia());
            direccion.Alumno = this;
            
            Direcciones.add(direccion);
        }
    }
    
    public int getIdAlumno() {
        return IdAlumno;
    }

    public void setIdAlumno(int IdAlumno) {
        this.IdAlumno = IdAlumno;
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

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }
    
    
    
}   
