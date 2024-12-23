package ControlArchivos;

import Modelo.MesaExamen;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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

    /**
     * Retorna un objeto JSON que representa a un estudiante dado su legajo.
     *
     * @param legajo   El legajo del estudiante a buscar.
     * @param fileName El nombre del archivo JSON que contiene los datos de los estudiantes.
     * @return Un objeto JSON que representa al estudiante, o null si no se encuentra.
     */
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

    /**
     * Filtra las notas de parciales de una materia específica.
     *
     * @param parciales     Mapa que contiene las notas de parciales por materia.
     * @param codigoMateria Código de la materia a filtrar.
     * @return Lista de notas de parciales correspondientes a la materia especificada.
     */
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
