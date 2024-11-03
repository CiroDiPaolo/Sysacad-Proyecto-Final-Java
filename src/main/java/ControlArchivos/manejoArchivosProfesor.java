package ControlArchivos;

import Usuarios.Profesor;
import Usuarios.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import static ControlArchivos.manejoArchivos.leerArchivoJSON;

public class manejoArchivosProfesor {

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

}
