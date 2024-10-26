package ControlArchivos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static ControlArchivos.manejoArchivos.leerArchivoJSON;

public class manejoArchivosEstudiante {

    /**
     * Metodo que compara un DNI de un estudiante
     * @param filePath
     * @param estudiante
     * @return
     */
    public static boolean compararEstudianteDNICarrera(String filePath, JSONObject estudiante) {

        boolean existe = false;

        try {
            // Leer el archivo JSON
            JSONTokener tokener = new JSONTokener(new FileReader(filePath));
            JSONArray estudiantesArray = new JSONArray(tokener);

            // Obtener el DNI del estudiante recibido
            String dniEstudiante = estudiante.getString("dni");
            String codigoCarreraEstudiante = estudiante.getString("codigoCarrera");
            // Iterar sobre los estudiantes en el archivo
            for (int i = 0; i < estudiantesArray.length(); i++) {
                JSONObject estudianteExistente = estudiantesArray.getJSONObject(i);
                String dniExistente = estudianteExistente.getString("dni");
                String codigoCarreraExistente = estudianteExistente.getString("codigoCarrera");
                // Comparar los DNIs y codigo de carrera
                if ((dniEstudiante.equals(dniExistente)) && (codigoCarreraEstudiante.equals(codigoCarreraExistente))) {
                    existe = true; // Coincidencia encontrada
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage());
        }

        return existe; // No se encontró coincidencia
    }

    /**
     * Metodo que escribe un archivo JSON
     * @param filePath
     * @param estudiante
     */
    public static boolean guardarEstudianteJSON(String filePath, JSONObject estudiante) {
        JSONArray jsonArray;
        // Inicializar el JSONArray
        try {
            // Leer el contenido existente del archivo
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
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
            // Si no se puede leer, comenzamos con un nuevo JSONArray
            jsonArray = new JSONArray();
        } catch (JSONException e) {
            System.out.println("El archivo no contiene un JSON válido, se creará uno nuevo.");
            // Si hay un error de JSON, iniciamos un nuevo JSONArray
            jsonArray = new JSONArray();
        }

        // Agregar la nueva persona
        try {

            jsonArray.put(estudiante);

            // Escribir el arreglo actualizado de nuevo en el archivo
            FileWriter file = new FileWriter(filePath);
            file.write(jsonArray.toString(4)); // Formateo a 4 espacios de indentación
            file.close();
            System.out.println("Persona guardada con éxito.");
            return true;
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error al convertir la persona a JSON: " + e.getMessage());
        }

        return false;
    }


}
