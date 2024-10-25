package ControlArchivos;

import Excepciones.ArchivoYaExistenteException;
import Modelo.Carrera;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class manejoArchivosCarrera {

    /**
     * Metodo que carga un archivo JSON
     * @param path
     * @param carrera
     * @return
     */
    public static void cargarJSONcarrera(String path, JSONObject carrera){

        JSONArray jsonArray;

        try {
            // Leer el contenido existente del archivo
            BufferedReader reader = new BufferedReader(new FileReader(path));
            StringBuilder jsonStringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }

            // Cargar el arreglo existente en jsonArray
            jsonArray = new JSONArray(jsonStringBuilder.toString());
            reader.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            jsonArray = new JSONArray(); // Si no se puede leer, comenzamos con un nuevo JSONArray
        } catch (JSONException e) {
            System.out.println("El archivo no contiene un JSON válido, se creará uno nuevo.");
            jsonArray = new JSONArray(); // Si hay un error de JSON, iniciamos un nuevo JSONArray
        }

        // Agregar la nueva carrera
        try {
            jsonArray.put(carrera);

            // Escribir el arreglo actualizado de nuevo en el archivo
            FileWriter file = new FileWriter(path);
            file.write(jsonArray.toString(4)); // Formateo a 4 espacios de indentación
            file.close();
            System.out.println("Carrera guardada con éxito.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error al convertir la carrera a JSON: " + e.getMessage());
        }

    }

    public static void crearJSONCarrera(String path, Carrera c){

        JSONObject obj = new JSONObject();

        obj.put("nombre",c.getNombre());
        obj.put("id",c.getId());
        obj.put("plan",c.getPlan());
        obj.put("actividad",c.isActividad());
        obj.put("materias",c.getMaterias());

        cargarJSONcarrera(path ,obj);

    }

    /**
     * Metodo que crea una carpeta de una carrera
     * @param codigoCarrera
     */
    public static void crearCarpetaCarrera(String codigoCarrera) throws ArchivoYaExistenteException {

        File folder = new File("Files\\" + codigoCarrera);
        if (!folder.exists()) {

            folder.mkdir();
            System.out.println("creado");

        }else{

            throw new ArchivoYaExistenteException("La carrera ya esxiste");

        }

    }

}
