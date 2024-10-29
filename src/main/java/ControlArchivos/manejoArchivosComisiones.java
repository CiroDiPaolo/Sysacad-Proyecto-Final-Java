package ControlArchivos;

import Excepciones.ArchivoNoEncontrado;
import Excepciones.ArchivoYaExistenteException;
import Modelo.Comision;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;

import static ControlArchivos.manejoArchivos.leerArchivoJSON;
import static ControlArchivos.manejoArchivos.verificarArchivoCreado;
import static Path.Path.pathComisiones;

public final class manejoArchivosComisiones {

    /**
     * Metodo que crea un archivo JSON por cada comision
     * @param codigoCarrera
     * @param fileName
     */
    public static void crearArchivoComision(String fileName, String codigoCarrera){

        if(!verificarArchivoCreado(pathComisiones +  codigoCarrera + "/", fileName )) {

            try {

                FileWriter file = new FileWriter(pathComisiones + codigoCarrera + "/" + fileName + ".json");

                file.write("");
                file.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    /**
     * Metodo que genera el nombre de un archivo de comision
     * @param codigoCarrera
     * @param anioActual
     * @return
     */
    public static String generarNombreArchivoComision(String codigoCarrera, int anioActual) {

        return "COMISIONES_" + codigoCarrera + "_" + anioActual;

    }

    public static void cargarComisionJSON(String codigoCarrera,String fileName, Comision comision) {

        JSONObject obj = new JSONObject();

        obj.put("nombre", comision.getNombre());
        obj.put("turno", comision.getTurno());
        obj.put("codigoMateria", comision.getCodigoMateria());
        obj.put("codigoCarrera", comision.getCodigoCarrera());
        obj.put("codigoProfesor", comision.getCodigoProfesor());
        obj.put("anio", comision.getAnio());
        obj.put("aula", comision.getAula());
        obj.put("cupos", comision.getCupos());
        obj.put("actividad", comision.isActividad());

        JSONArray arregloLegajos = new JSONArray();

        obj.put("legajosAlumno",arregloLegajos);

        cargarArchivoJSON(codigoCarrera,fileName, obj);

    }

    public static void cargarArchivoJSON(String codigoCarrera,String fileName, JSONObject obj) {

        JSONArray jsonArray;

        try {

            jsonArray = new JSONArray(leerArchivoJSON(pathComisiones + codigoCarrera + "/" + fileName + ".json"));
            jsonArray.put(obj);

        }catch (Exception e) {
            jsonArray = new JSONArray();
            jsonArray.put(obj);
        }

        try (FileWriter file = new FileWriter(pathComisiones + codigoCarrera + "/" + fileName + ".json")) {
            file.write(jsonArray.toString(4));
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
