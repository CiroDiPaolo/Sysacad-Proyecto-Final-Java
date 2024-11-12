package ControlArchivos;

import Excepciones.*;
import Modelo.Comision;
import Usuarios.Estudiante;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import Path.Path.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.nio.file.*;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        boolean flag= false;

        int i = 0;
        while(i<jsonArray.length() && !flag)
        {
            if(comision.getString("codigoMateria").equals(jsonArray.getJSONObject(i).getString("codigoMateria")) && comision.getString("nombre").equals(jsonArray.getJSONObject(i).getString("nombre")))
            {
                flag = true;
            }
            i++;
        }

        if(!flag)
        {
            jsonArray.put(comision);
            try (FileWriter file = new FileWriter(path)) {
                file.write(jsonArray.toString(4));
                return true;
            } catch (IOException | JSONException e) {
                excepcionPersonalizada.excepcion("Ocurrió un error en el programa. Si el problema persiste, comuníquese con su distribuidor.");
            }
        }else
        {
            throw new EntidadYaExistente("El nombre de la comisión ya existe en la materia");
        }

        return false;
    }

    public static ArrayList<Comision> obtenerComisionesPorAnio(int anio, String idCarrera)
    {
        JSONArray jsonArray =new JSONArray(leerArchivoJSON(pathComisiones+generarNombreArchivoComision(idCarrera,anio)));
        ArrayList<Comision> comisiones = new ArrayList<>();

        if(!jsonArray.isEmpty())
        {
            for(int i = 0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                comisiones.add(Comision.JSONObjectAComision(jsonObject));
            }
        }
        return comisiones;
    }

    public static boolean actualizarComisionAJSON(String path, JSONObject comision) {

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

        for(int i = 0; i<jsonArray.length();i++)
        {
            if(comision.getString("id").equals(jsonArray.getJSONObject(i).getString("id")))
            {
                jsonArray.put(i,comision);
            }
        }

        try (FileWriter file = new FileWriter(path)) {
            file.write(jsonArray.toString(4));
            return true;
        } catch (IOException | JSONException e) {
            excepcionPersonalizada.excepcion("Ocurrió un error en el programa. Si el problema persiste, comuníquese con su distribuidor.");
        }

        return false;
    }

    public static Comision buscarComision(String filename, String dato, String clave) throws CamposVaciosException
    {
        if(!clave.isEmpty()){
            JSONArray jsonArray;

            try {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
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

            Comision comision = null;

            for(int i = 0; i<jsonArray.length();i++)
            {

                if(clave.equals(jsonArray.getJSONObject(i).getString(dato)))
                {
                    comision = Comision.JSONObjectAComision(jsonArray.getJSONObject(i));
                }
            }

            return comision;
        }else {
            throw new CamposVaciosException("No elegiste ninguna comisión");
        }
    }

    public static ArrayList<Comision> obtenerComisionesDeUnaMateria(String path,String codigoMateria) {

        JSONArray jsonArray = new JSONArray(leerArchivoJSON(path));

        ArrayList<Comision> comisiones = new ArrayList<>();

        for(int i = 0 ; i < jsonArray.length() ; i++){

            JSONObject obj = jsonArray.getJSONObject(i);

            if(obj.getString("codigoMateria").equals(codigoMateria)){

                if(obj.getBoolean("actividad") == true){

                    if(Comision.JSONObjectAComision(obj).getCupos() > 0){

                        comisiones.add(Comision.JSONObjectAComision(obj));

                    }

                }

            }

        }

        return comisiones;

    }

    public static HashSet<Comision> obtenerComisionesPorAnio(String path, String anio) {
        HashSet<Comision> comisiones = new HashSet<>();
        Path dirPath = Paths.get(path);  // Asumimos que path ya es "Files/Comisiones"

        // Expresión regular para el nombre de archivo "COMISIONES_idDeMateria_<anio>.json"
        String regex = "COMISIONES_\\d+_" + anio + "\\.json";
        Pattern pattern = Pattern.compile(regex);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "*.json")) {
            for (Path entry : stream) {
                String fileName = entry.getFileName().toString();
                Matcher matcher = pattern.matcher(fileName);

                // Procesa solo los archivos que coinciden con el formato y el año especificado
                if (matcher.matches()) {
                    try {
                        String content = Files.readString(entry).trim();

                        // Verifica que el contenido esté en formato JSON y empieza con '[' (indicando que es un arreglo)
                        if (!content.isEmpty() && content.startsWith("[")) {
                            // Convertir el contenido a un JSONArray, ya que el archivo es un arreglo de objetos JSON
                            JSONArray jsonArray = new JSONArray(content);

                            // Iterar sobre cada objeto en el arreglo
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);  // Obtener cada objeto
                                Comision comision = Comision.JSONObjectAComision(json);  // Convertir el JSONObject en un objeto Comision
                                comisiones.add(comision);  // Agregar la comisión a la lista
                            }
                        } else {
                            System.err.println("El archivo " + fileName + " no contiene un arreglo JSON válido.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al procesar el archivo " + fileName);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al acceder al directorio: " + dirPath);
            e.printStackTrace();
        }

        return comisiones;
    }

    public static HashSet<Comision> filtrarComisionesPorNombre(String nombre, HashSet<Comision> comisiones) {
        HashSet<Comision> comisionesFiltradas = new HashSet<>();
        for (Comision comision : comisiones) {
            if (comision.getNombre().equals(nombre)) {
                comisionesFiltradas.add(comision);
            }
        }
        return comisionesFiltradas;
    }

    public static ArrayList<Estudiante> obtenerEstudiantesDeUnaComision(String pathAlumnos, String codigoComision) {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();

        JSONArray arregloEstudiantes = new JSONArray(leerArchivoJSON(pathAlumnos));

        for (int i = 0; i < arregloEstudiantes.length(); i++) {
            JSONObject estudianteObj = arregloEstudiantes.getJSONObject(i);
            JSONArray materias = estudianteObj.getJSONArray("materias");

            for (int j = 0; j < materias.length(); j++) {
                JSONObject materia = materias.getJSONObject(j);

                if (materia.getString("codigoComision").equals(codigoComision)) {
                    Estudiante estudiante = Estudiante.JSONObjectAEstudiante(estudianteObj);
                    estudiantes.add(estudiante);
                    break; // Salir del bucle de materias si se encuentra la comisión
                }
            }
        }

        return estudiantes;
    }
}
