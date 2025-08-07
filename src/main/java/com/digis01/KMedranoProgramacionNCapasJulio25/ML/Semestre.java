package com.digis01.KMedranoProgramacionNCapasJulio25.ML;

public class Semestre {
    
    private int IdSemestre;
    private String Nombre;

    public Semestre(){}
    
    public Semestre(int idSemestre, String nombre){
        this.IdSemestre = idSemestre;
        this.Nombre = nombre;
    }
    
    public int getIdSemestre() {
        return IdSemestre;
    }

    public void setIdSemestre(int IdSemestre) {
        this.IdSemestre = IdSemestre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    
}
