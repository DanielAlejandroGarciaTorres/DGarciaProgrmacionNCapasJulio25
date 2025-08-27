package com.digis01.KMedranoProgramacionNCapasJulio25.DAO;

import com.digis01.KMedranoProgramacionNCapasJulio25.JPA.Alumno;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AlumnoJPADAOImplementation implements IAlumnoJPADAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll() {

        Result result = new Result();

        try {

            TypedQuery<Alumno> queryAlumno = entityManager.createQuery("FROM Alumno ORDER BY IdAlumno", Alumno.class);
            List<Alumno> alumnos = queryAlumno.getResultList();

            result.objects = new ArrayList<>();

            for (Alumno alumno : alumnos) {
                result.objects.add(new com.digis01.KMedranoProgramacionNCapasJulio25.ML.Alumno(alumno));
            }

            result.correct = true;
            /*Bajar a ML para renderizar*/

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result Add(com.digis01.KMedranoProgramacionNCapasJulio25.ML.Alumno alumnoML) {
        Result result = new Result();

        try {

            Alumno alumnoJPA = new Alumno(alumnoML);

            entityManager.persist(alumnoJPA);
            
            result.correct = true; 

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
