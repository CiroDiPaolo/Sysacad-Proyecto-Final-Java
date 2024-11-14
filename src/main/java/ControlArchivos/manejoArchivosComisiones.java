package ControlArchivos;

import Control.InicioSesion.Data;
import Excepciones.*;
import Modelo.Comision;
import Usuarios.Estudiante;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.nio.file.*;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static ControlArchivos.manejoArchivos.*;
import static Path.Path.fileNameAlumnos;
import static Path.Path.pathComisiones;

public final class manejoArchivosComisiones {

    /**
     * Metodo que crea un archivo JSON por cada comision
     *
     * @param codigoCarrera
     * @param fileName
     */
    public static void crearArchivoComision(String fileName, String codigoCarrera) {

        if (!verificarArchivoCreado(pathComisiones + codigoCarrera + "/", fileName)) {

            try {

                FileWriter file = new FileWriter(pathComisiones + codigoCarrera + "/" + fileName + ".json");

                file.write("");
                file.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    /**
     * Metodo que genera el nombre de un archivo de comision
     *
     * @param codigoCarrera
     * @param anioActual
     * @return
     */
    public static String generarNombreArchivoComision(String codigoCarrera, int anioActual) {

        return "COMISIONES_" + codigoCarrera + "_" + anioActual + ".json";

    }

    /**
     * Método que carga una comisión a un archivo JSON.
     *
     * @param path     Ruta del archivo JSON donde se cargará la comisión.
     * @param comision Objeto JSON que representa la comisión a cargar.
     * @return true si la comisión se cargó con éxito, false en caso contrario.
     * @throws EntidadYaExistente Si la comisión ya existe en el archivo.
     */
    public static boolean cargarComisionAJSON(String path, JSONObject comision) throws EntidadYaExistente {
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

        boolean flag = false;

        int i = 0;
        while (i < jsonArray.length() && !flag) {
            if (comision.getString("codigoMateria").equals(jsonArray.getJSONObject(i).getString("codigoMateria")) && comision.getString("nombre").equals(jsonArray.getJSONObject(i).getString("nombre"))) {
                flag = true;
            }
            i++;
        }

        if (!flag) {
            jsonArray.put(comision);
            try (FileWriter file = new FileWriter(path)) {
                file.write(jsonArray.toString(4));
                return true;
            } catch (IOException | JSONException e) {
                excepcionPersonalizada.excepcion("Ocurrió un error en el programa. Si el problema persiste, comuníquese con su distribuidor.");
            }
        } else {
            throw new EntidadYaExistente("El nombre de la comisión ya existe en la materia");
        }

        return false;
    }

    /**
     * Método que obtiene todas las comisiones de un archivo JSON para un año y carrera específicos.
     *
     * @param anio      Año de las comisiones a obtener.
     * @param idCarrera Código de la carrera de las comisiones a obtener.
     * @return Lista de objetos Comision correspondientes al año y carrera especificados.
     */
    public static ArrayList<Comision> obtenerComisionesPorAnio(int anio, String idCarrera) {
        JSONArray jsonArray = new JSONArray(leerArchivoJSON(pathComisiones + generarNombreArchivoComision(idCarrera, anio)));
        ArrayList<Comision> comisiones = new ArrayList<>();

        if (!jsonArray.isEmpty()) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                comisiones.add(Comision.JSONObjectAComision(jsonObject));
            }
        }
        return comisiones;
    }

    /**
     * Actualiza una comisión en un archivo JSON.
     *
     * @param path     Ruta del archivo JSON que contiene las comisiones.
     * @param comision Objeto JSON que representa los datos actualizados de la comisión.
     * @return true si la comisión fue actualizada con éxito, false en caso contrario.
     */
    public static boolean actualizarComisionAJSON(String path, JSONObject comision) {
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

        JSONArray updatedJsonArray = new JSONArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (comision.getString("id").equals(jsonObject.getString("id"))) {
                if (!comision.getString("codigoMateria").equals(jsonObject.getString("codigoMateria"))) {
                    String nuevoID = Comision.generarIDComision(comision.getString("codigoCarrera"), comision.getString("codigoMateria"), path);
                    System.out.println("ID anterior: " + jsonObject.getString("id"));
                    System.out.println("Nuevo ID generado: " + nuevoID);
                    comision.put("id", nuevoID);

                }
                updatedJsonArray.put(comision);
            } else {
                updatedJsonArray.put(jsonObject);
            }
        }

        try (FileWriter file = new FileWriter(path)) {
            file.write(updatedJsonArray.toString(4));
            return true;
        } catch (IOException | JSONException e) {
            excepcionPersonalizada.excepcion("Ocurrió un error en el programa. Si el problema persiste, comuníquese con su distribuidor.");
        }

        return false;
    }

    /**
     * Método que retorna una comisión de un archivo JSON.
     *
     * @param filename Nombre del archivo JSON que contiene las comisiones.
     * @param dato     Clave del dato a buscar en el archivo JSON.
     * @param clave    Valor de la clave a buscar en el archivo JSON.
     * @return Objeto Comision correspondiente a la clave dada.
     * @throws CamposVaciosException Si la clave está vacía o es nula.
     */
    public static Comision buscarComision(String filename, String dato, String clave) throws CamposVaciosException {
        if (clave != null && !clave.isEmpty()) {
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

            Comision comision = null;

            for (int i = 0; i < jsonArray.length(); i++) {
                if (clave.equals(jsonArray.getJSONObject(i).getString(dato))) {
                    comision = Comision.JSONObjectAComision(jsonArray.getJSONObject(i));
                }
            }

            return comision;
        } else {
            throw new CamposVaciosException("No elegiste ninguna comisión");
        }
    }

    /**
     * Método que retorna una lista de comisiones activas de una materia específica con cupos disponibles.
     *
     * @param path          Ruta del archivo JSON que contiene las comisiones.
     * @param codigoMateria Código de la materia para filtrar las comisiones.
     * @return Lista de objetos Comision correspondientes a la materia especificada.
     */
    public static ArrayList<Comision> obtenerComisionesDeUnaMateria(String path, String codigoMateria) {
        JSONArray jsonArray = new JSONArray(leerArchivoJSON(path));
        ArrayList<Comision> comisiones = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            if (obj.getString("codigoMateria").equals(codigoMateria)) {
                if (obj.getBoolean("actividad")) {
                    if (Comision.JSONObjectAComision(obj).getCupos() > 0) {
                        comisiones.add(Comision.JSONObjectAComision(obj));
                    }
                }
            }
        }

        return comisiones;
    }

    /**
     * Obtiene un conjunto de comisiones para un año específico.
     *
     * @param path Ruta del directorio que contiene los archivos JSON de comisiones.
     * @param anio Año para filtrar las comisiones.
     * @return Conjunto de objetos Comision correspondientes al año especificado.
     */
    public static HashSet<Comision> obtenerComisionesPorAnio(String path, String anio) {
        HashSet<Comision> comisiones = new HashSet<>();
        Path dirPath = Paths.get(path);

        String regex = "COMISIONES_\\d+_" + anio + "\\.json";
        Pattern pattern = Pattern.compile(regex);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "*.json")) {
            for (Path entry : stream) {
                String fileName = entry.getFileName().toString();
                Matcher matcher = pattern.matcher(fileName);

                if (matcher.matches()) {
                    try {
                        String content = Files.readString(entry).trim();

                        if (!content.isEmpty() && content.startsWith("[")) {

                            JSONArray jsonArray = new JSONArray(content);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                Comision comision = Comision.JSONObjectAComision(json);
                                comisiones.add(comision);
                            }
                        } else {
                            System.err.println("El archivo " + fileName + " no contiene un arreglo JSON válido.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al procesar el archivo " + fileName);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al acceder al directorio: " + dirPath);
            e.printStackTrace();
        }

        return comisiones;
    }

    /**
     * Filtra un conjunto de comisiones por su nombre.
     *
     * @param nombre     Nombre de la comisión a buscar.
     * @param comisiones Conjunto de comisiones a filtrar.
     * @return Conjunto de comisiones que coinciden con el nombre dado.
     */
    public static HashSet<Comision> filtrarComisionesPorNombre(String nombre, HashSet<Comision> comisiones) {
        HashSet<Comision> comisionesFiltradas = new HashSet<>();
        for (Comision comision : comisiones) {
            if (comision.getNombre().equals(nombre)) {
                comisionesFiltradas.add(comision);
            }
        }
        return comisionesFiltradas;
    }

    /**
     * Obtiene una lista de estudiantes que pertenecen a una comisión específica.
     *
     * @param pathAlumnos    Ruta del archivo JSON que contiene los datos de los estudiantes.
     * @param codigoComision Código de la comisión a buscar.
     * @return Lista de objetos Estudiante que pertenecen a la comisión especificada.
     */
    public static ArrayList<Estudiante> obtenerEstudiantesDeUnaComision(String pathAlumnos, String codigoComision) {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();

        JSONArray arregloEstudiantes = new JSONArray(leerArchivoJSON(pathAlumnos));

        for (int i = 0; i < arregloEstudiantes.length(); i++) {
            JSONObject estudianteObj = arregloEstudiantes.getJSONObject(i);
            JSONArray materias = estudianteObj.getJSONArray("materias");

            for (int j = 0; j < materias.length(); j++) {
                JSONObject materia = materias.getJSONObject(j);

                if (materia.getString("codigoComision").equals(codigoComision)) {
                    Estudiante estudiante = Estudiante.JSONObjectAEstudiante(estudianteObj);
                    estudiantes.add(estudiante);
                    break; // Salir del bucle de materias si se encuentra la comisión
                }
            }
        }

        return estudiantes;
    }

    /**
     * Obtiene una lista de números de archivos JSON que contienen comisiones para una carrera específica.
     *
     * @param path          Ruta del directorio que contiene los archivos JSON.
     * @param codigoCarrera Código de la carrera para filtrar los archivos.
     * @return Lista de números de archivos correspondientes a la carrera especificada.
     * @throws IOException Si ocurre un error al acceder al directorio o leer los archivos.
     */
    public static ArrayList<Integer> obtenerNumerosDeArchivos(String path, String codigoCarrera) throws IOException {
        ArrayList<Integer> numeros = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(path))) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry) && entry.toString().endsWith(".json")) {
                    String fileName = entry.getFileName().toString();
                    if (fileName.contains(codigoCarrera)) {
                        String numeroStr = fileName.replaceAll("^COMISIONES_" + codigoCarrera + "_(\\d{4})\\.json$", "$1");
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

    public static boolean actualizarEstudiantesDeUnaComision(String codigoMateria) {

        String codigoAnterior = Data.getComision().getId();

        String codigoNuevo = Data.getAux3();

        boolean resultado = false;

        ArrayList<Estudiante> estudiantes = obtenerEstudiantesDeUnaComision(fileNameAlumnos, codigoAnterior);

        for (Estudiante estudiante : estudiantes) {

            boolean actualizado = false;

            for (int j = 0; j < estudiante.getMaterias().size(); j++) {

                if (estudiante.getMaterias().get(j).getCodigoComision().equals(codigoAnterior)) {

                    estudiante.getMaterias().get(j).setCodigoComision(codigoNuevo);
                    System.out.println("Estudiante " + estudiante.getNombre() + " actualizado");
                    actualizado = true;
                    resultado = true;

                }
            }

            if (actualizado) {
                try {

                    estudiante.actualizar(fileNameAlumnos, estudiante.estudianteAJSONObject());

                } catch (Exception e) {
                    Excepciones.excepcionPersonalizada.alertaAtencion("Error al actualizar los estudiantes de la comisión");
                }
            }
        }

        return resultado;
    }

}
