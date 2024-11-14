package ControlArchivos;

import Excepciones.EntidadYaExistente;
import Modelo.Avisos;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class manejoArchivosAvisos {

    public static boolean comprobarAvisoNoRepetido(JSONArray jsonArray, int id) {

        boolean encontrado = false;
        int i = 0;
        if(!jsonArray.isEmpty())
        {
            while(i<jsonArray.length() && !encontrado)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(id == jsonObject.getInt("id"))
                {
                    encontrado = true;
                }
            }
        }
        return encontrado;
    }

    public static int obtenerSiguienteId(String path) {

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

        int id;
        if(!jsonArray.isEmpty())
        {
            id = jsonArray.getJSONObject(0).getInt("id");
            for(int i = 1; i<jsonArray.length(); i++)
            {
                if(id<jsonArray.getJSONObject(i).getInt("id")){
                    id = jsonArray.getJSONObject(i).getInt("id");
                }
            }
        }else {
            id = 0;
        }
        return (id+1);
    }

    public static boolean guardarAvisoAJSON(String path, JSONObject aviso)
    {

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
        for(int i = 0; i<jsonArray.length();i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (aviso.getInt("id") == jsonObject.getInt("id")) {
                jsonArray1.put(aviso);
            } else {
                jsonArray1.put(jsonObject);
            }
        }

        try {
            jsonArray.put(aviso);
            FileWriter file = new FileWriter(path);
            file.write(jsonArray.toString(4));
            file.close();

            return true;
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error al convertir la persona a JSON: " + e.getMessage());
        }

        return false;
    }

    public static boolean actualizarAvisoAJSON(String path, JSONObject aviso)
    {

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
            for(int i = 0; i<jsonArray.length();i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (aviso.getInt("id") == jsonObject.getInt("id")) {
                    jsonArray1.put(aviso);
                } else {
                    jsonArray1.put(jsonObject);
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
                System.out.println("Error al convertir la persona a JSON: " + e.getMessage());
            }

        return false;
    }

    public static Avisos retornarAviso(String path, int id)
    {
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

        Avisos aviso = null;
        int i = 0;
        while(i<jsonArray.length())
        {
            if(jsonArray.getJSONObject(i).getInt("id") == id){
                aviso = Avisos.JSONObjectAaviso(jsonArray.getJSONObject(i));
                return aviso;
            }
            i++;
        }

        return aviso;
    }

    public static ArrayList<Avisos> retornarAvisos(String path)
    {
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

        ArrayList<Avisos> avisos = new ArrayList<>();

        for(int i = 0; i<jsonArray.length();i++)
        {
            avisos.add(Avisos.JSONObjectAaviso(jsonArray.getJSONObject(i)));
        }

        return avisos;
    }
}
