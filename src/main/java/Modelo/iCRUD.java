package Modelo;

import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import org.json.JSONObject;

/**
 * Interfaz para el CRUD (CREACION, ACTUALIZACION, LEER Y ELIMINACION) de entidades
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
    boolean actualizar(String path, JSONObject jsonObject) throws DatosIncorrectosException, CamposVaciosException;

    /**
     * Lee una entidad de un archivo
     * @param path
     * @param id
     * @return
     */
    boolean leer(String path, String id);

    /**
     * Elimina(baja logica) una entidad en un archivo.
     * @param path
     * @return
     */
    boolean borrar(String path);

}
