package Modelo;

import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import org.json.JSONObject;

/**
 * Interfaz para el CRUD (CREACION, ACTUALIZACION) de entidades
 */
public interface iCRUD {
    /**
     * Crea una entidad en un archivo
     * @param path
     * @return
     * @throws EntidadYaExistente
     * @throws CamposVaciosException
     * @throws DatosIncorrectosException
     */
    boolean crear(String path) throws EntidadYaExistente, CamposVaciosException, DatosIncorrectosException;

    /**
     * Actualiza un objeto en un archivo
     * @param path
     * @param jsonObject
     * @return
     */
    boolean actualizar(String path, JSONObject jsonObject) throws CamposVaciosException, DatosIncorrectosException ;

}
