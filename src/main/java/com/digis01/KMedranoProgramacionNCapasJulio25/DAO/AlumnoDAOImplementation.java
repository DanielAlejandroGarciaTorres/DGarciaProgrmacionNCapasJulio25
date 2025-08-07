package com.digis01.KMedranoProgramacionNCapasJulio25.DAO;

import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Alumno;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Colonia;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Direccion;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Estado;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Municipio;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Result;
import com.digis01.KMedranoProgramacionNCapasJulio25.ML.Semestre;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository // define que una clase tiene logica de base de datos 
public class AlumnoDAOImplementation implements IAlumnoDAO {

    @Autowired //inyeccion de dependencias 
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {
        Result result = new Result();

        try {

            // clases Wrapper int - INTEGER, double, float, char
            jdbcTemplate.execute("{CALL AlumnoDireccionGetAll(?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                result.objects = new ArrayList<>();
                while (resultSet.next()) {

                    //ML.Alumno alumnotest = (ML.Alumno) (result.objects.get(result.objects.size() - 1));
                    int idAlumno = resultSet.getInt("IdAlumno");

                    if (!result.objects.isEmpty() && idAlumno == ((Alumno) (result.objects.get(result.objects.size() - 1))).getIdAlumno()) {
                        //que la lista no sea vacia, que el id se repita 

                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        //resto de datos
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        // resto de datos
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));

                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));

                        ((Alumno) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);

                    } else {

                        Alumno alumno = new Alumno();

                        alumno.setIdAlumno(idAlumno);
                        alumno.setNombre(resultSet.getString("NombreAlumno"));
                        alumno.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        alumno.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        alumno.setUserName(resultSet.getString("UserName"));
                        alumno.Semestre = new Semestre();
                        alumno.Semestre.setIdSemestre(resultSet.getInt("IdSemestre"));
                        alumno.Semestre.setNombre(resultSet.getString("NombreSemestre"));

                        int idDireccion;
                        if ((idDireccion = resultSet.getInt("IdDireccion")) != 0) {

                            alumno.Direcciones = new ArrayList<>();

                            Direccion direccion = new Direccion();
                            direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                            direccion.setCalle(resultSet.getString("Calle"));
                            //resto de datos
                            direccion.Colonia = new Colonia();
                            direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                            // resto de datos
                            direccion.Colonia.Municipio = new Municipio();
                            direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                            direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));

                            direccion.Colonia.Municipio.Estado = new Estado();
                            direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                            direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));

                            alumno.Direcciones.add(direccion);
                        }
                        result.objects.add(alumno);
                    }
                }
                result.correct = true;
                return 1;
            }
            );

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result DireccionesByIdAlumno(int idAlumno) {
        Result result = new Result();

        try {

            // clases Wrapper int - INTEGER, double, float, char
            jdbcTemplate.execute("{CALL AlumnoDireccionGetByIdAlumno(?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, idAlumno);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

                if (resultSet.next()) {

                    Alumno alumno = new Alumno();

                    alumno.setIdAlumno(idAlumno);
                    alumno.setNombre(resultSet.getString("NombreAlumno"));
                    alumno.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    alumno.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    alumno.setUserName(resultSet.getString("UserName"));
                    alumno.Semestre = new Semestre();
                    alumno.Semestre.setIdSemestre(resultSet.getInt("IdSemestre"));
                    alumno.Semestre.setNombre(resultSet.getString("NombreSemestre"));

                    int idDireccion;
                    if ((idDireccion = resultSet.getInt("IdDireccion")) != 0) {

                        alumno.Direcciones = new ArrayList<>();

                        do {
                            Direccion direccion = new Direccion();
                            direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                            direccion.setCalle(resultSet.getString("Calle"));
                            //resto de datos
                            direccion.Colonia = new Colonia();
                            direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                            // resto de datos
                            direccion.Colonia.Municipio = new Municipio();
                            direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                            direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));

                            direccion.Colonia.Municipio.Estado = new Estado();
                            direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                            direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));

                            alumno.Direcciones.add(direccion);
                        } while (resultSet.next());
                    }
                    result.object = alumno;
                }

                result.correct = true;
                return 1;
            });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
    
    @Override
    public Result Add(Alumno alumno) {

        Result result = new Result();

        try {
            result.correct = jdbcTemplate.execute("CALL AlumnoAdd(?,?,?,?,?,?,?,?,?)", (CallableStatementCallback<Boolean>) callablestatement -> {

                callablestatement.setString(1, alumno.getNombre());
                callablestatement.setString(2, alumno.getApellidoPaterno());
                callablestatement.setString(3, alumno.getApellidoMaterno());
                callablestatement.setString(4, alumno.getUserName());
                callablestatement.setInt(5, alumno.Semestre.getIdSemestre());
                callablestatement.setString(6, alumno.Direcciones.get(0).getCalle());
                callablestatement.setString(7, alumno.Direcciones.get(0).getNumeroInterior());
                callablestatement.setString(8, alumno.Direcciones.get(0).getNumeroExterior());
                callablestatement.setInt(9, alumno.Direcciones.get(0).Colonia.getIdColonia());

                int isCorrect = callablestatement.executeUpdate();

                if (isCorrect == -1) {

                    return true;
                }
                
                return false;
            });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;

        }

        return result;

    }


}
