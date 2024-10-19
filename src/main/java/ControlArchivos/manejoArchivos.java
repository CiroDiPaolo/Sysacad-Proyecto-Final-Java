package ControlArchivos;

import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class manejoArchivos {

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


}
