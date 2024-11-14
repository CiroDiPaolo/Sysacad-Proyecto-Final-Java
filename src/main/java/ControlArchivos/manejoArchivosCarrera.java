package ControlArchivos;

import Consultas.consultaArchivo;
import Excepciones.*;
import Modelo.Carrera;
import Modelo.Materia;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

import static ControlArchivos.manejoArchivos.leerArchivoJSON;
import static ControlArchivos.manejoArchivos.sobreescribirArchivoJSON;
import static Path.Path.pathComisiones;
import static ControlArchivos.manejoArchivos.sobreescribirArchivoJSON;

/**
 * Clase de utilidades para manejar archivos relacionados con carreras, permitiendo
 * crear, modificar y recuperar datos de carreras y sus materias desde archivos JSON.
 */
public final class manejoArchivosCarrera {

    /**
     * Carga una carrera en un archivo JSON si esta no existe previamente en el archivo.
     *
     * @param path Ruta del archivo JSON donde se almacenará la carrera.
     * @param carrera Objeto JSON que representa la carrera a agregar.
     * @throws ArchivoYaExistenteException Si la carrera ya existe en el archivo.
     */
    public static void cargarJSONcarrera(String path, JSONObject carrera) throws ArchivoYaExistenteException {
        if(!consultaArchivo.buscarClave(path,carrera.getString("id"),"id")){

            JSONArray jsonArray;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(path));
                StringBuilder jsonStringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    jsonStringBuilder.append(line);
                }
                jsonArray = new JSONArray(jsonStringBuilder.toString());
                reader.close();
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
                jsonArray = new JSONArray();
            } catch (JSONException e) {
                System.out.println("El archivo no contiene un JSON válido, se creará uno nuevo.");
                jsonArray = new JSONArray();
            }

            try {
                jsonArray.put(carrera);
                FileWriter file = new FileWriter(path);
                file.write(jsonArray.toString(4));
                file.close();
            } catch (IOException e) {
                System.out.println("Error al escribir en el archivo: " + e.getMessage());
            } catch (JSONException e) {
                System.out.println("Error al convertir la carrera a JSON: " + e.getMessage());
            }
        } else {
            throw new ArchivoYaExistenteException("La carrera ya existe");
        }
    }

    /**
     * Convierte una carrera a JSONObject y la carga en el archivo JSON especificado.
     *
     * @param path Ruta del archivo JSON donde se almacenará la carrera.
     * @param c Objeto Carrera que se convertirá y agregará al archivo.
     */
    public static void crearJSONCarrera(String path, Carrera c){
        JSONObject obj = c.carreraAJSONObject();
        try {
            cargarJSONcarrera(path ,obj);
        } catch (ArchivoYaExistenteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crea una carpeta específica para una carrera utilizando su código como nombre.
     *
     * @param codigoCarrera Código de la carrera.
     * @throws ArchivoYaExistenteException Si la carpeta de la carrera ya existe.
     */
    public static void crearCarpetaCarrera(String codigoCarrera) throws ArchivoYaExistenteException {
        File folder = new File(pathComisiones + codigoCarrera);
        if (!folder.exists()) {
            folder.mkdir();
        } else {
            throw new ArchivoYaExistenteException("La carrera ya existe.");
        }
    }

    /**
     * Obtiene una lista de valores de un campo específico para cada carrera activa.
     *
     * @param fileName Nombre del archivo JSON que contiene las carreras.
     * @param campo Nombre del campo a extraer.
     * @return Lista de valores del campo para cada carrera activa.
     */
    public static ArrayList<String> obtenerCampoEspecificoDeCarrera(String fileName, String campo) {
        ArrayList<String> carreras = new ArrayList<>();
        JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));

        for (int i = 0; i < arreglo.length(); i++) {
            JSONObject obj = arreglo.getJSONObject(i);
            if(obj.getBoolean("actividad")) {
                carreras.add(obj.getString(campo));
            }
        }
        return carreras;
    }

    /**
     * Obtiene el código de una carrera según su nombre. Incluye carreras inactivas
     * si el parámetro especificado es verdadero.
     *
     * @param nombre Nombre de la carrera.
     * @param fileName Nombre del archivo JSON que contiene las carreras.
     * @param contarCarrerasInactivas Si es true, incluye carreras inactivas en la búsqueda.
     * @return Código de la carrera o cadena vacía si no se encuentra.
     */
    public static String retonarCodigoCarreraPorNombre(String nombre, String fileName, boolean contarCarrerasInactivas) {
        JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));
        boolean flag = false;
        int i = 0;
        while(i<arreglo.length() && !flag) {
            JSONObject carrera = arreglo.getJSONObject(i);
            if(contarCarrerasInactivas) {
                if(nombre.equals(carrera.getString("nombre"))) {
                    return (carrera.getString("id"));
                }
            } else {
                if(nombre.equals(carrera.getString("nombre")) && (carrera.getBoolean("actividad"))) {
                    return (carrera.getString("id"));
                }
            }
            i++;
        }
        return "";
    }

    /**
     * Recupera una carrera como objeto Carrera a partir de su código.
     *
     * @param fileName Nombre del archivo JSON que contiene las carreras.
     * @param carrera Código de la carrera a buscar.
     * @return Objeto Carrera correspondiente al código dado.
     * @throws CamposVaciosException Si el código de la carrera está vacío.
     * @throws DatosIncorrectosException Si el código no corresponde a ninguna carrera.
     */
    public static Carrera retornarCarrera(String fileName, String carrera) throws CamposVaciosException, DatosIncorrectosException {
        if(!carrera.isEmpty()) {
            JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));
            boolean flag = false;
            int i = 0;

            while(i<arreglo.length() && !flag) {
                if(arreglo.getJSONObject(i).getString("id").equals(carrera)) {
                    return Carrera.JSONObjectACarrera(arreglo.getJSONObject(i));
                }
                i++;
            }

            if(!flag) {
                throw new DatosIncorrectosException("El ID " + carrera + " no corresponde a ninguna carrera");
            }
        } else {
            throw new CamposVaciosException("Intentaste ingresar un campo vacio. Volve a intentarlo.");
        }
        return null;
    }

    /**
     * Agrega una nueva materia a la carrera especificada por su ID.
     *
     * @param fileName Nombre del archivo JSON que contiene las carreras.
     * @param nuevaMateria Objeto Materia a agregar.
     * @param idCarrera ID de la carrera a la que se agregará la materia.
     * @return true si la materia se agregó con éxito.
     * @throws EntidadYaExistente Si la materia ya existe en la carrera.
     */
    public static boolean agregarMateria(String fileName, Materia nuevaMateria, String idCarrera) throws EntidadYaExistente {
        JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));
        boolean carreraEncontrada = false;

        for (int i = 0; i < arreglo.length(); i++) {
            JSONObject carreraJSON = arreglo.getJSONObject(i);

            if (carreraJSON.getString("id").equals(idCarrera)) {
                carreraEncontrada = true;

                JSONArray materias = carreraJSON.getJSONArray("materias");
                for (int j = 0; j < materias.length(); j++) {
                    JSONObject materiaJSON = materias.getJSONObject(j);
                    if (materiaJSON.getString("id").equals(nuevaMateria.getId())) {
                        throw new EntidadYaExistente("La materia ya existe en la carrera");
                    }
                }
                materias.put(nuevaMateria.materiaAJSONObject());
                carreraJSON.put("materias", materias);
                try {
                    sobreescribirArchivoJSON(fileName, arreglo);
                } catch (ParametroPeligrosoException e) {
                    e.getMessage();
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Actualiza una materia existente en la carrera especificada.
     *
     * @param fileName Nombre del archivo JSON que contiene las carreras.
     * @param nuevaMateria Objeto JSON que representa los datos actualizados de la materia.
     * @param idCarrera ID de la carrera a la que pertenece la materia.
     * @return true si la materia fue actualizada con éxito.
     */
    public static boolean actualizarMateria(String fileName, JSONObject nuevaMateria, String idCarrera) {
        JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));
        for (int i = 0; i < arreglo.length(); i++) {
            JSONObject carreraJSON = arreglo.getJSONObject(i);

            if (carreraJSON.getString("id").equals(idCarrera)) {
                JSONArray materias = carreraJSON.optJSONArray("materias");

                if (materias != null) {
                    for (int j = 0; j < materias.length(); j++) {
                        JSONObject materiaActual = materias.getJSONObject(j);

                        if (materiaActual.getString("id").equals(nuevaMateria.getString("id"))) {
                            materias.put(j, nuevaMateria);
                            break;
                        }
                    }
                } else {
                    materias = new JSONArray();
                    materias.put(nuevaMateria);
                    carreraJSON.put("materias", materias);
                }

                arreglo.put(i, carreraJSON);
                try{
                    sobreescribirArchivoJSON(fileName, arreglo);
                }catch (ParametroPeligrosoException e)
                {
                    e.getMessage();
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Obtiene una materia específica de una carrera por su ID.
     *
     * @param fileName Nombre del archivo JSON que contiene las carreras.
     * @param idMateria ID de la materia a buscar.
     * @return Objeto Materia correspondiente al ID dado o null si no se encuentra.
     */
    public static Materia obtenerMateria(String fileName,String idMateria){
        JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));
        for (int i = 0; i < arreglo.length(); i++) {
            JSONObject carreraJSON = arreglo.getJSONObject(i);
            JSONArray materias = carreraJSON.optJSONArray("materias");

            if (materias != null) {
                for (int j = 0; j < materias.length(); j++) {
                    JSONObject materiaActual = materias.getJSONObject(j);

                    if (materiaActual.getString("id").equals(idMateria)) {
                        return Materia.JSONObjectAMateria(materiaActual);
                    }
                }
            }
        }

        return null;
    }

    /**
     * Obtiene una lista de nombres de materias a partir de un conjunto de IDs.
     *
     * @param fileName Nombre del archivo JSON que contiene las carreras.
     * @param idsMaterias Conjunto de IDs de materias a buscar.
     * @return Lista de nombres de las materias correspondientes a los IDs dados.
     */
    public static ArrayList<String> obtenerNombresMaterias(String fileName, HashSet<String> idsMaterias) {
        ArrayList<String> nombresMaterias = new ArrayList<>();
        JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));

        for (int i = 0; i < arreglo.length(); i++) {
            JSONObject carreraJSON = arreglo.getJSONObject(i);
            JSONArray materias = carreraJSON.optJSONArray("materias");

            if (materias != null) {
                for (int j = 0; j < materias.length(); j++) {
                    JSONObject materiaActual = materias.getJSONObject(j);

                    String idMateria = materiaActual.getString("id");

                    if (idsMaterias.contains(idMateria)) {
                        String nombreMateria = materiaActual.getString("nombre");
                        nombresMaterias.add(nombreMateria);
                    }
                }
            }
        }

        return nombresMaterias;
    }
}
