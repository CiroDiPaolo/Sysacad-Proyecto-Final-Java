package Modelo;

import Excepciones.CamposVaciosException;
import Excepciones.EntidadYaExistente;
import org.json.JSONObject;

public interface iCRUD {
    boolean crear(String path) throws EntidadYaExistente, CamposVaciosException;
    boolean actualizar(String path, JSONObject jsonObject);
    boolean leer(String path);
    boolean borrar(String path);

}
