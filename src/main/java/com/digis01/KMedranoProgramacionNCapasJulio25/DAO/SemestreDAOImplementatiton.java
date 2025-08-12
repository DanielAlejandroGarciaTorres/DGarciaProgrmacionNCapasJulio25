package com.digis01.KMedranoProgramacionNCapasJulio25.DAO;

import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Result;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Semestre;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository // logica de base de datos 
public class SemestreDAOImplementatiton implements ISemestreDAO{

    @Autowired // Inyeccion de depencias
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {
        Result result = new Result();
        try{
            
            result.correct = jdbcTemplate.execute("CALL SemestreGetAll(?)", (CallableStatementCallback<Boolean>) callableStatement -> {
            
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                
                result.objects = new ArrayList<>();
                
                while (resultSet.next()) {                    
                    
                    Semestre semestre = new Semestre(
                            resultSet.getInt("IdSemestre"), 
                            resultSet.getString("Nombre"));
                    
                    result.objects.add(semestre);
                    
                }
                
                return true;
            });
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    
}
