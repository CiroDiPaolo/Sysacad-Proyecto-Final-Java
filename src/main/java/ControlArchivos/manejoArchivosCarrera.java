package ControlArchivos;

import Consultas.consultaArchivo;
import Excepciones.ArchivoYaExistenteException;
import Excepciones.CarreraInexistenteException;
import Modelo.Carrera;
import Modelo.Materia;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import Path.Path;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static ControlArchivos.manejoArchivos.leerArchivoJSON;
import static Path.Path.pathCarreras;
import static Path.Path.pathComisiones;

public class manejoArchivosCarrera {

    /**
     * Metodo que carga unca carrera a un archivo JSON si es que la carrera no existe.
     * @param path
     * @param carrera
     * @return void
     */
    public static void cargarJSONcarrera(String path, JSONObject carrera) throws ArchivoYaExistenteException {

        if(!consultaArchivo.buscarClave(path,carrera.getString("id"),"id")){

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
                file.write(jsonArray.toString(4));
                file.close();

            } catch (IOException e) {
                System.out.println("Error al escribir en el archivo: " + e.getMessage());
            } catch (JSONException e) {
                System.out.println("Error al convertir la carrera a JSON: " + e.getMessage());
            }

        }else{

            throw new ArchivoYaExistenteException("La carrera ya existeeeeeeeee");

        }


    }

    /**
     * Pasa una carrera a un JSONObject y carga la carrera.
     * @param path
     * @param c
     */

    public static void crearJSONCarrera(String path, Carrera c){

        JSONObject obj = manejoArchivosCarrera.carreraAJSONObject(c);

        try {
            cargarJSONcarrera(path ,obj);
        } catch (ArchivoYaExistenteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convierte una Carrera a un JSONObject
     * @param c
     * @return JSONObject
     */
    public static JSONObject carreraAJSONObject(Carrera c){

        JSONObject obj = new JSONObject();

        obj.put("nombre",c.getNombre());
        obj.put("id",c.getId());
        obj.put("plan",c.getPlan());
        obj.put("actividad",c.isActividad());
        obj.put("materias",c.getMaterias());

        return obj;
    }

    /**
     * Convierte un JSONObject a una carrera
     * @param jsonObject
     * @return Carrera
     */
    public static Carrera JSONObjectACarrera(JSONObject jsonObject) {
        // Extraemos los datos de Carrera desde el JSONObject
        String id = jsonObject.getString("id");
        String nombre = jsonObject.getString("nombre");
        String plan = jsonObject.getString("plan");
        boolean actividad = jsonObject.getBoolean("actividad");

        // Convertimos el JSON de materias a HashMap<String, Materia>
        HashMap<String, Materia> materias = new HashMap<>();
        JSONObject materiasJson = jsonObject.getJSONObject("materias");

        for (String key : materiasJson.keySet()) {
            JSONObject materiaJson = materiasJson.getJSONObject(key);
            Materia materia = Materia.JSONObjectAMateria(materiaJson);
            materias.put(materia.getId(), materia);
        }

        // Creamos y devolvemos una instancia de Carrera
        return new Carrera(id, nombre, plan, materias, actividad);
    }

    /**
     * Metodo que crea una carpeta de una carrera
     * @param codigoCarrera
     */
    public static void crearCarpetaCarrera(String codigoCarrera) throws ArchivoYaExistenteException {

        File folder = new File(pathComisiones + codigoCarrera);
        if (!folder.exists()) {

            folder.mkdir();

        }else{

            throw new ArchivoYaExistenteException("La carrera ya existe.");

        }

    }

    /**
     * Devuelve una lista de todas las carreras del campo especificado por parametro
     * @param fileName
     * @param campo
     * @return ArrayList<String></>
     */
    public static ArrayList<String> obtenerCampoEspecificoDeCarrera(String fileName, String campo) {

        ArrayList<String> carreras = new ArrayList<>();

        JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));

        for (int i = 0; i < arreglo.length(); i++) {
            JSONObject obj = arreglo.getJSONObject(i);
            if(obj.getBoolean("actividad"))
            {
                carreras.add(obj.getString(campo));
            }
        }

        return carreras;

    }

    /**
     * Metodo para traer el codigo de una carrera por su nombre, si se desea traerlo igual aunque la carrera este inactiva/dada de baja con poner true en contarCarrerasInactivas
     * @param nombre
     * @param fileName
     * @param contarCarrerasInactivas
     * @return
     */
    public static String retonarCodigoCarreraPorNombre(String nombre, String fileName, boolean contarCarrerasInactivas)
    {
        JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));

        boolean flag = false;
        int i = 0;
        while(i<arreglo.length() && !flag)
        {
            JSONObject carrera = arreglo.getJSONObject(i);
            if(contarCarrerasInactivas)
            {
                if(nombre.equals(carrera.getString("nombre")))
                {
                    return (carrera.getString("id"));
                }
            }else
            {
                if(nombre.equals(carrera.getString("nombre")) && (carrera.getBoolean("actividad")))
                {
                    return (carrera.getString("id"));
                }
            }
            i++;
        }

        return "";
    }

}
