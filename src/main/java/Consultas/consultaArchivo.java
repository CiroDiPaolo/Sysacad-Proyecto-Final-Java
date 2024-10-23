package Consultas;

import Usuarios.Administrador;
import Usuarios.Estudiante;
import Usuarios.Usuario;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

import static ControlArchivos.manejoArchivos.*;

public final class consultaArchivo {

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

    public static void cambiarContrasenia(String fileName,String legajo,String nuevaContrasenia){

        try {

            JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));

            for (int i = 0; i < arreglo.length(); i++) {
                JSONObject obj = arreglo.getJSONObject(i);

                if (obj.getString("legajo").equals(legajo)) {
                    obj.put("contrasenia", nuevaContrasenia);
                    break;
                }
            }

            // Guardar los cambios en el archivo
            try (FileWriter file = new FileWriter(fileName)) {
                file.write(arreglo.toString());
            } catch (IOException e) {
                throw new RuntimeException("Error al escribir en el archivo: " + e.getMessage());
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Metodo que busca un usuario en el archivo JSON y retorna su nombre y apellido
     *
     * @param fileName
     * @param legajo
     * @return String
     */
    public static String buscarNombreCompleto(String fileName, String legajo) {

        String nombre = "";

        String apellido = "";

        try {

            JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));

            for (int i = 0; i < arreglo.length(); i++) {

                JSONObject obj = arreglo.getJSONObject(i);

                if (obj.getString("legajo").equals(legajo)) {

                    nombre = obj.getString("nombre");

                    apellido = obj.getString("apellido");

                    i = arreglo.length();

                }

            }

            if (nombre.equals("") && apellido.equals("")) {
                throw new RuntimeException("No se encontro el legajo");
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return nombre + " " + apellido;

    }

    /**
     * Metodo que obtiene un estudiante del archivo JSON
     *
     * @param fileName
     * @param legajo
     * @return
     */
    public static Estudiante obtenerEstudiante(String fileName, String legajo) {

        Estudiante estudiante = new Estudiante();

        try {

            JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));

            for (int i = 0; i < arreglo.length(); i++) {

                JSONObject obj = arreglo.getJSONObject(i);

                if (obj.getString("legajo").equals(legajo)) {

                    estudiante.setLegajo(obj.getString("legajo"));
                    estudiante.setName(obj.getString("nombre"));
                    estudiante.setApellido(obj.getString("apellido"));
                    estudiante.setDni(obj.getString("dni"));
                    estudiante.setContrasenia(obj.getString("contrasenia"));

                    i = arreglo.length();

                }

            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return estudiante;
    }

}

