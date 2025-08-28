package com.digis01.KMedranoProgramacionNCapasJulio25.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddireccion")
    private int IdDireccion;
    @Column(name = "calle")
    private String Calle;
    @Column(name = "numerointerior")
    private String NumeroInterior;
    @Column(name = "numeroexterior")
    private String NumeroExterior;
    @ManyToOne
    @JoinColumn(name = "idcolonia")
    public Colonia Colonia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idalumno", nullable = false)
    public Alumno Alumno;
    
    public Direccion() {
    }
    
    public Direccion(com.digis01.KMedranoProgramacionNCapasJulio25.ML.Alumno alumnoML){
//        alumnoML.Direcciones.get(0) -> Direccion ML
        com.digis01.KMedranoProgramacionNCapasJulio25.ML.Direccion direccionML = alumnoML.Direcciones.get(0);
        
        this.IdDireccion = direccionML.getIdDireccion();
        this.Calle = direccionML.getCalle();
        this.NumeroInterior = direccionML.getNumeroInterior();
        this.NumeroExterior = direccionML.getNumeroExterior();
        this.Colonia = new Colonia();
        this.Colonia.setIdColonia(direccionML.Colonia.getIdColonia());
        this.Alumno = new Alumno();
        this.Alumno.setIdAlumno(alumnoML.getIdAlumno());
    }

    public Direccion(int IdDireccion) {
        this.IdDireccion = IdDireccion;
    }

    public int getIdDireccion() {
        return IdDireccion;
    }

    public void setIdDireccion(int IdDireccion) {
        this.IdDireccion = IdDireccion;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getNumeroInterior() {
        return NumeroInterior;
    }

    public void setNumeroInterior(String NumeroInterior) {
        this.NumeroInterior = NumeroInterior;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String NumeroExterior) {
        this.NumeroExterior = NumeroExterior;
    }

}
