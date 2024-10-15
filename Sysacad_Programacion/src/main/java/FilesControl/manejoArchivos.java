package FilesControl;

import Modelo.Alumno;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class manejoArchivos {

    public static void crearArchivoJSON(String fileName, JSONArray arreglo) {

        try {

            FileWriter file = new FileWriter(fileName);
            file.write(arreglo.toString());
            file.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void escribirArchivoJSON(String fileName, JSONArray arreglo) {

        try {

            FileWriter file = new FileWriter(fileName);
            file.write(arreglo.toString());
            file.flush();
            file.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static JSONTokener leerArchivoJSON(String fileName) {

        JSONTokener tokener = null;

        try {

            tokener = new JSONTokener(new FileReader(fileName));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tokener;
    }

    public static boolean buscarClave(String fileName, String dato, String buscado) {

        boolean flag = false;

        try {

            JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));

            for (int i = 0; i < arreglo.length(); i++) {

                JSONObject obj = arreglo.getJSONObject(i);

                if (obj.getString(buscado).equals(dato)) {

                    flag = true;

                }

            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return flag;
    }

    //metodo prueba para buscar un nombre en un archivo JSON
    public static String buscarNombre(String fileName, String legajo) {

            String nombre = "";

        try {

            JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));

            for (int i = 0; i < arreglo.length(); i++) {

                JSONObject obj = arreglo.getJSONObject(i);

                if (obj.getString("legajo").equals(legajo)) {

                    nombre = obj.getString("nombre");

                }

            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return nombre;

    }


}
