package ControlArchivos;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ControlArchivos.manejoArchivos.leerArchivoJSON;

public final class manejoArchivosEstudiante {

    /**
     * Metodo que compara un DNI de un estudiante, devuelve false si no lo encontró, devuelve true si lo encontró
     * @param filePath
     * @param estudiante
     * @return
     */
    public static boolean compararEstudianteDNICarrera(String filePath, JSONObject estudiante) {

        try (FileReader fileReader = new FileReader(filePath)) {
            JSONTokener tokener = new JSONTokener(fileReader);
            JSONArray estudiantesArray = new JSONArray(tokener);

            String dniEstudiante = estudiante.getString("dni");
            String codigoCarreraEstudiante = estudiante.getString("codigoCarrera");

            for (int i = 0; i < estudiantesArray.length(); i++) {
                JSONObject estudianteExistente = estudiantesArray.getJSONObject(i);
                String dniExistente = estudianteExistente.getString("dni");
                String codigoCarreraExistente = estudianteExistente.getString("codigoCarrera");
                if (dniEstudiante.equals(dniExistente) && codigoCarreraEstudiante.equals(codigoCarreraExistente)) {
                    return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage());
        }

        return false;
    }


    public static JSONObject retornarEstudiante(String legajo,  String fileName)
    {
        JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));

        boolean encontrado = false;

        int i = 0;

        while(i<arreglo.length() && !encontrado)
        {
            JSONObject jsonObject = arreglo.getJSONObject(i);

            if(jsonObject.getString("legajo").equals(legajo))
            {
                encontrado = true;
            }

            i++;
        }
        if(encontrado)
        {
            return arreglo.getJSONObject((i-1));
        }

        return null;
    }

    public static ArrayList<String> filtrarParcialesPorMateria(Map<String, List<Integer>> parciales, String codigoMateria){

        ArrayList<String> parcialesMateria = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : parciales.entrySet()) {
            if (entry.getKey().equals(codigoMateria)) {
                for (Integer nota : entry.getValue()) {
                    parcialesMateria.add(nota.toString());
                }
            }
        }

        return parcialesMateria;

    }

}
