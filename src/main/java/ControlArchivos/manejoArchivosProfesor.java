package ControlArchivos;

import Excepciones.excepcionPersonalizada;
import Usuarios.Profesor;
import Usuarios.Usuario;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import static ControlArchivos.manejoArchivos.leerArchivoJSON;

public class manejoArchivosProfesor {

    /**
     * Retorna una lista de profesores a partir de un archivo JSON.
     *
     * @param fileName El nombre del archivo JSON que contiene los datos de los profesores.
     * @return Una lista de objetos Profesor.
     */
    public static ArrayList<Profesor> retornarProfesores(String fileName){
        JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));
        if(!arreglo.isEmpty())
        {
            ArrayList<Profesor> profesores = new ArrayList<>();
            for (int i = 0; i < arreglo.length(); i++) {
                JSONObject jsonObject = arreglo.getJSONObject(i);

                Profesor profesor = (Profesor)Usuario.JSONObjectAUsuario(jsonObject);
                profesores.add(profesor);
            }
            return profesores;
        }
        return null;
    }

    /**
     * Retorna un objeto JSON que representa a un profesor específico a partir de su legajo.
     *
     * @param legajo   El legajo del profesor a buscar.
     * @param fileName El nombre del archivo JSON que contiene los datos de los profesores.
     * @return Un objeto JSON que representa al profesor encontrado, o null si no se encuentra.
     */
    public static JSONObject retornarProfesor(String legajo,  String fileName)
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
     * Actualiza la información de un profesor en el archivo JSON.
     *
     * @param profesor El objeto JSON que representa al profesor con la información actualizada.
     * @param fileName El nombre del archivo JSON que contiene los datos de los profesores.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public static boolean actualizarProfesor(JSONObject profesor,  String fileName)
    {
        JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));

        JSONArray arregloActualizado = new JSONArray();

        for(int i = 0; i<arreglo.length(); i++)
        {
            JSONObject jsonObject = arreglo.getJSONObject(i);
            if(jsonObject.getString("legajo").equals(profesor.getString("legajo")))
            {
                arregloActualizado.put(profesor);
            }else {
                arregloActualizado.put(jsonObject);
            }
        }

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(arregloActualizado.toString(4));
            return true;
        } catch (IOException | JSONException e) {
            excepcionPersonalizada.excepcion("Ocurrió un error en el programa. Si el problema persiste, comuníquese con su distribuidor.");
        }

        return false;
    }

}
