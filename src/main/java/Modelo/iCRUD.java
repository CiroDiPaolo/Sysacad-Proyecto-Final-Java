package Modelo;

import Excepciones.CamposVaciosException;
import Excepciones.EntidadYaExistente;

public interface iCRUD {
    boolean crear(String path) throws EntidadYaExistente, CamposVaciosException;
    boolean actualizar(String path);
    boolean leer(String path);
    boolean borrar(String path);

}
