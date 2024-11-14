package ControlArchivos;

import Excepciones.CamposVaciosException;
import Excepciones.EntidadYaExistente;
import Excepciones.excepcionPersonalizada;
import Modelo.EstadoAlumnoMesa;
import Modelo.MesaExamen;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import static ControlArchivos.manejoArchivos.leerArchivoJSON;
import static Path.Path.pathMesaExamen;

public class manejoArchivosMesaExamen {

    public static String generarNombreArchivoMesaExamen(String codigoCarrera, int anioActual) {

        return "EXAMEN_" + codigoCarrera + "_" + anioActual + ".json";

    }

    public static boolean cargarMesaExamenAJSON(String path, JSONObject mesaExamen) throws EntidadYaExistente {
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

        boolean conflictoEncontrado = false;
        HashSet<Object> vocalesNuevos = new HashSet<>(mesaExamen.getJSONArray("vocales").toList());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject mesaExistente = jsonArray.getJSONObject(i);
            LocalDate fechaExistente = LocalDate.parse(mesaExistente.getString("fecha"));
            LocalTime horaExistente = LocalTime.parse(mesaExistente.getString("hora"));

            if (mesaExamen.getString("codigoPresidente").equals(mesaExistente.getString("codigoPresidente")) &&
                    fechaExistente.equals(LocalDate.parse(mesaExamen.getString("fecha"))) &&
                    horaExistente.equals(LocalTime.parse(mesaExamen.getString("hora")))) {
                conflictoEncontrado = true;
                break;
            }

            HashSet<Object> vocalesExistentes = new HashSet<>(mesaExistente.getJSONArray("vocales").toList());
            if (fechaExistente.equals(LocalDate.parse(mesaExamen.getString("fecha"))) &&
                    horaExistente.equals(LocalTime.parse(mesaExamen.getString("hora"))) &&
                    (vocalesNuevos.stream().anyMatch(vocalesExistentes::contains))) {
                conflictoEncontrado = true;
                break;
            }

            if (vocalesNuevos.contains(mesaExistente.getString("codigoPresidente")) &&
                    fechaExistente.equals(LocalDate.parse(mesaExamen.getString("fecha"))) &&
                    horaExistente.equals(LocalTime.parse(mesaExamen.getString("hora")))) {
                conflictoEncontrado = true;
                break;
            }
        }

        if (!conflictoEncontrado) {
            jsonArray.put(mesaExamen);
            try (FileWriter file = new FileWriter(path)) {
                file.write(jsonArray.toString(4));
                return true;
            } catch (IOException | JSONException e) {
                excepcionPersonalizada.excepcion("Ocurrió un error en el programa. Si el problema persiste, comuníquese con su distribuidor.");
            }
        } else {
            throw new EntidadYaExistente("Conflicto encontrado: presidente o vocal en el mismo horario y fecha en otra mesa de examen.");
        }
        return false;
    }

    public static boolean actualizarMesaExamenAJSON(String path, JSONObject mesaExamen) throws EntidadYaExistente {
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

        boolean conflictoEncontrado = false;
        boolean materiaDiferente = false;
        HashSet<Object> vocalesNuevos = new HashSet<>(mesaExamen.getJSONArray("vocales").toList());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject mesaExistente = jsonArray.getJSONObject(i);
            LocalDate fechaExistente = LocalDate.parse(mesaExistente.getString("fecha"));
            LocalTime horaExistente = LocalTime.parse(mesaExistente.getString("hora"));

            if (mesaExamen.getString("id").equals(mesaExistente.getString("id"))) {
                if (!mesaExamen.getString("codigoMateria").equals(mesaExistente.getString("codigoMateria"))) {
                    materiaDiferente = true;
                }
                continue;
            }

            if (mesaExamen.getString("codigoPresidente").equals(mesaExistente.getString("codigoPresidente")) &&
                    fechaExistente.equals(LocalDate.parse(mesaExamen.getString("fecha"))) &&
                    horaExistente.equals(LocalTime.parse(mesaExamen.getString("hora")))) {
                conflictoEncontrado = true;
                break;
            }

            HashSet<Object> vocalesExistentes = new HashSet<>(mesaExistente.getJSONArray("vocales").toList());
            if (fechaExistente.equals(LocalDate.parse(mesaExamen.getString("fecha"))) &&
                    horaExistente.equals(LocalTime.parse(mesaExamen.getString("hora"))) &&
                    (vocalesNuevos.stream().anyMatch(vocalesExistentes::contains))) {
                conflictoEncontrado = true;
                break;
            }

            if (vocalesNuevos.contains(mesaExistente.getString("codigoPresidente")) &&
                    fechaExistente.equals(LocalDate.parse(mesaExamen.getString("fecha"))) &&
                    horaExistente.equals(LocalTime.parse(mesaExamen.getString("hora")))) {
                conflictoEncontrado = true;
                break;
            }
        }

        if (conflictoEncontrado) {
            throw new EntidadYaExistente("Conflicto encontrado: presidente o vocal en el mismo horario y fecha en otra mesa de examen.");
        }

        if (materiaDiferente) {
            mesaExamen.put("id", MesaExamen.generarIDMesaExamen(mesaExamen.getString("codigoCarrera"), mesaExamen.getString("codigoMateria"), path));
        }

        JSONArray jsonActualizado = new JSONArray();
        boolean mesaActualizada = false;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject mesaExistente = jsonArray.getJSONObject(i);

            if (mesaExistente.getString("id").equals(mesaExamen.getString("id"))) {
                jsonActualizado.put(mesaExamen);
                mesaActualizada = true;
            } else {
                jsonActualizado.put(mesaExistente);
            }
        }

        if (!mesaActualizada) {
            jsonActualizado.put(mesaExamen);
        }

        try (FileWriter file = new FileWriter(path)) {
            file.write(jsonActualizado.toString(4));
            return true;
        } catch (IOException | JSONException e) {
            excepcionPersonalizada.excepcion("Ocurrió un error en el programa. Si el problema persiste, comuníquese con su distribuidor.");
        }

        return false;
    }

    public static ArrayList<MesaExamen> obtenerMesaExamenPorAnio(int anio, String idCarrera)
    {
        JSONArray jsonArray =new JSONArray(leerArchivoJSON(pathMesaExamen+generarNombreArchivoMesaExamen(idCarrera,anio)));
        ArrayList<MesaExamen> mesaExamen = new ArrayList<>();

        if(!jsonArray.isEmpty())
        {
            for(int i = 0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                mesaExamen.add(MesaExamen.fromJSONObject(jsonObject));
            }
        }
        return mesaExamen;
    }

    public static MesaExamen buscarMesaExamen(String filename, String dato, String clave) throws CamposVaciosException
    {
        if(!clave.isEmpty() && clave != null){
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

            MesaExamen mesaExamen = null;

            for(int i = 0; i<jsonArray.length();i++)
            {

                if(clave.equals(jsonArray.getJSONObject(i).getString(dato)))
                {
                    mesaExamen = MesaExamen.fromJSONObject(jsonArray.getJSONObject(i));
                }
            }

            return mesaExamen;
        }else {
            throw new CamposVaciosException("No elegiste ninguna mesa de examen");
        }
    }

    public static ArrayList<Integer> obtenerNumerosDeArchivos(String path, String codigoCarrera) throws IOException {
        ArrayList<Integer> numeros = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(path))) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry) && entry.toString().endsWith(".json")) {
                    String fileName = entry.getFileName().toString();
                    if (fileName.contains(codigoCarrera)) {
                        String numeroStr = fileName.replaceAll("^EXAMEN_" + codigoCarrera + "_(\\d{4})\\.json$", "$1");
                        if (!numeroStr.equals(fileName)) {
                            try {
                                numeros.add(Integer.parseInt(numeroStr));
                            } catch (NumberFormatException e) {
                                e.getMessage();
                            }
                        }
                    }
                }
            }
        }
        return numeros;
    }

    public static void actualizarEstadoEstudiante(MesaExamen mesa, String idEstudiante, int nota) {

        Iterator<EstadoAlumnoMesa> it = mesa.getAlumnosInscriptos().iterator();

        while (it.hasNext()) {
            EstadoAlumnoMesa estado = it.next();
            if (estado.getCodigoMesa().equals(idEstudiante)) {
                estado.setNota(nota);
                break;
            }
        }

    }

}
