package Modelo;

import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import org.json.JSONObject;

public interface iCRUD {
    boolean crear(String path) throws EntidadYaExistente, CamposVaciosException, DatosIncorrectosException;
    boolean actualizar(String path, JSONObject jsonObject);
    boolean leer(String path);
    boolean borrar(String path);

}
