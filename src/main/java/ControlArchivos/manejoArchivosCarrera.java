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
import static ControlArchivos.manejoArchivos.*;
import static Path.Path.pathComisiones;

public final class manejoArchivosCarrera {

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

        }else{

            throw new ArchivoYaExistenteException("La carrera ya existe");

        }


    }

    /**
     * Pasa una carrera a un JSONObject y carga la carrera.
     * @param path
     * @param c
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

    /**
     * Retorna una carrera específica del archivo JSON.
     * @param fileName Nombre del archivo JSON.
     * @param carrera ID de la carrera a retornar.
     * @return La carrera correspondiente al ID proporcionado.
     * @throws CamposVaciosException Si el ID de la carrera está vacío.
     * @throws DatosIncorrectosException Si el ID de la carrera no corresponde a ninguna carrera en el archivo.
     */
    public static Carrera retornarCarrera(String fileName, String carrera) throws CamposVaciosException, DatosIncorrectosException {        if(!carrera.isEmpty())
        {
            JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));
            boolean flag = false;
            int i = 0;

            while(i<arreglo.length() && !flag)
            {
                if(arreglo.getJSONObject(i).getString("id").equals(carrera))
                {
                    return Carrera.JSONObjectACarrera(arreglo.getJSONObject(i));
                }
                i++;
            }

            if(!flag){
                throw new DatosIncorrectosException("El ID " + carrera + " no corresponde a ninguna carrera");
            }
        }else
        {
            throw new CamposVaciosException("Intentaste ingresar un campo vacio. Volve a intentarlo.");
        }

        return null;
    }

    /**
     * Agrega una nueva materia a una carrera específica en el archivo JSON.
     *
     * @param fileName Nombre del archivo JSON.
     * @param nuevaMateria Objeto Materia que se desea agregar.
     * @param idCarrera ID de la carrera a la que se desea agregar la materia.
     * @return true si la materia se agregó correctamente, false en caso contrario.
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
     * Actualiza una materia específica en una carrera del archivo JSON.
     *
     * @param fileName Nombre del archivo JSON.
     * @param nuevaMateria Objeto JSON de la nueva materia a actualizar.
     * @param idCarrera ID de la carrera a la que pertenece la materia.
     * @return true si la materia se actualizó correctamente, false en caso contrario.
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
     * Obtiene una materia específica del archivo JSON.
     * @param fileName Nombre del archivo JSON.
     * @param idMateria ID de la materia a obtener.
     * @return La materia correspondiente al ID proporcionado, o null si no se encuentra.
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
     * Obtiene los nombres de las materias a partir de un conjunto de IDs de materias.
     * @param fileName Nombre del archivo JSON.
     * @param idsMaterias Conjunto de IDs de las materias a obtener.
     * @return Lista de nombres de las materias correspondientes a los IDs proporcionados.
     */
    public static ArrayList<String> obtenerNombresMaterias(String fileName, HashSet<String> idsMaterias) {

        HashSet<String> nombresMateriasSet = new HashSet<>();
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
                        nombresMateriasSet.add(nombreMateria);
                    }
                }
            }
        }

        return new ArrayList<>(nombresMateriasSet);
    }

    /**
     * Actualiza una carrera específica en el archivo JSON.
     * @param path Ruta del archivo JSON.
     * @param carrera Objeto JSON de la carrera a actualizar.
     * @param idviejo ID de la carrera a actualizar.
     * @return true si la carrera se actualizó correctamente, false en caso contrario.
     */
    public static boolean actualizarCarrera(String path, JSONObject carrera, String idviejo) {
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

        JSONArray jsonArray1 = new JSONArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.getJSONObject(i).getString("id").equals(idviejo)) {
                jsonArray1.put(carrera);
            } else {
                jsonArray1.put(jsonArray.getJSONObject(i));
            }
        }

        try {
            FileWriter file = new FileWriter(path);
            file.write(jsonArray1.toString(4));
            file.close();
            return true;

        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error al convertir la carrera a JSON: " + e.getMessage());
        }

        return false;
    }
}
