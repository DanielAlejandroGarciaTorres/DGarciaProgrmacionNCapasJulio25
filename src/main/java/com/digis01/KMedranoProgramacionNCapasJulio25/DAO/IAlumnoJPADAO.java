package com.digis01.KMedranoProgramacionNCapasJulio25.DAO;

import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Result;


public interface IAlumnoJPADAO {

    Result GetAll();
    
    Result Add(com.digis01.KMedranoProgramacionNCapasJulio25.ML.Alumno alumno);
    
    Result Delete(int IdAlumno);
    
}
