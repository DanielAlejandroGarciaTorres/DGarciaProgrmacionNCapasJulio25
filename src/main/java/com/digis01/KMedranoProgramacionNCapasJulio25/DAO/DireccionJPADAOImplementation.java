package com.digis01.KMedranoProgramacionNCapasJulio25.DAO;

import com.digis01.KMedranoProgramacionNCapasJulio25.JPA.Direccion;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Result;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionJPADAOImplementation implements IDireccionJPADAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result Update(com.digis01.KMedranoProgramacionNCapasJulio25.ML.Alumno alumnoML) {
        Result result = new Result();
        try {
            Direccion direccionJPA = new Direccion(alumnoML);
            entityManager.merge(direccionJPA);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }

}
