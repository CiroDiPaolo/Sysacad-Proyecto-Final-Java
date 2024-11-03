package ControlArchivos;

import Consultas.consultaArchivo;
import Excepciones.ArchivoNoEncontrado;
import Excepciones.ArchivoYaExistenteException;
import Excepciones.EntidadYaExistente;
import Excepciones.excepcionPersonalizada;
import Modelo.Comision;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Iterator;

import static ControlArchivos.manejoArchivos.leerArchivoJSON;
import static ControlArchivos.manejoArchivos.verificarArchivoCreado;
import static Path.Path.pathComisiones;

public final class manejoArchivosComisiones {

    /**
     * Metodo que crea un archivo JSON por cada comision
     *
     * @param codigoCarrera
     * @param fileName
     */
    public static void crearArchivoComision(String fileName, String codigoCarrera) {

        if (!verificarArchivoCreado(pathComisiones + codigoCarrera + "/", fileName)) {

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
     *
     * @param codigoCarrera
     * @param anioActual
     * @return
     */
    public static String generarNombreArchivoComision(String codigoCarrera, int anioActual) {

        return "COMISIONES_" + codigoCarrera + "_" + anioActual + ".json";

    }

    public static void cargarArchivoJSON(String codigoCarrera, String fileName, JSONObject obj) {

        JSONArray jsonArray;

        try {

            jsonArray = new JSONArray(leerArchivoJSON(pathComisiones + codigoCarrera + "/" + fileName + ".json"));
            jsonArray.put(obj);

        } catch (Exception e) {
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

    public static boolean cargarComisionAJSON(String path, JSONObject comision) throws EntidadYaExistente {
        System.out.println("Ruta del archivo: " + path);
        System.out.println("Directorio de trabajo: " + System.getProperty("user.dir"));

        if (!consultaArchivo.buscarClave(path, comision.getString("id"), "id") && !consultaArchivo.buscarClave(path, comision.getString("nombre"), "nombre")) {

            JSONArray jsonArray;

            try {
                BufferedReader reader = new BufferedReader(new FileReader(path));
                StringBuilder jsonStringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    jsonStringBuilder.append(line);
                }
                reader.close();
                jsonArray = new JSONArray(jsonStringBuilder.toString());
            } catch (IOException | JSONException e) {
                jsonArray = new JSONArray();
            }

            jsonArray.put(comision);

            try (FileWriter file = new FileWriter(path)) {
                file.write(jsonArray.toString(4));
                return true;
            } catch (IOException | JSONException e) {
                excepcionPersonalizada.excepcion("Ocurrió un error en el programa. Si el problema persiste, comuníquese con su distribuidor.");
            }

        } else {
            throw new EntidadYaExistente("La comision cargada ya existe.");
        }
        return false;
    }


}
