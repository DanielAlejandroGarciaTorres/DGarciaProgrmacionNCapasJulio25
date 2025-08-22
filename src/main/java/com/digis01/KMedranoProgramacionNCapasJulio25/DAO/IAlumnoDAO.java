
package com.digis01.KMedranoProgramacionNCapasJulio25.DAO;

import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Alumno;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Result;

public interface IAlumnoDAO {
    
    Result GetAll(Alumno alumno);// metodo abstracto, es decir, no lleva implementación
    
    Result DireccionesByIdAlumno(int idAlumno);
    
    Result Add(Alumno alumno);
}
