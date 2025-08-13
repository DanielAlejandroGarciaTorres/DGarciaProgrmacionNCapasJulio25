
package com.digis01.KMedranoProgramacionNCapasJulio25.DAO;

import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Estado;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOImplementation implements IEstado{

   
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    @Override
    public Result GetAllEstado() {
        
        Result result = new Result();
        
        try{
            
            jdbcTemplate.execute("CALL GetAllEstado(?)", (CallableStatementCallback<Boolean>) callableStatement ->{
                
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                
                result.objects = new ArrayList<>();
                
                while(resultSet.next()){
                    Estado estado = new Estado();
                    
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombre(resultSet.getString("Nombre"));
                    
                    result.objects.add(estado);
                }
                
                result.correct = true;
                
                return true;
            });
            
            
            
        }
        catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        
        
        
        return result;
        
        
    }
    
}
