package ControlArchivos;
import Excepciones.ArchivoYaExistenteException;
import Modelo.Carrera;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.XmlException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.*;
import java.util.Calendar;


public final class manejoArchivos {

    /**
     * Metodo que crea un archivo JSON
     * @param fileName
     * @param arreglo
     */
    public static void crearArchivoJSON(String path, String fileName, JSONArray arreglo) throws ArchivoYaExistenteException {

        try {

            if(!verificarArchivoCreado(path,fileName)){

                FileWriter fileWriter = new FileWriter(path + "/" + fileName);
                fileWriter.write(arreglo.toString(4));
                fileWriter.close();

            }else{

                throw new ArchivoYaExistenteException("El archivo ya existe");

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que carga un archivo JSON
     * @param path
     * @param path
     * @return
     */
    public static void cargarJSONcarrera(String path, JSONObject carrera){

        JSONArray jsonArray;

        try {
            // Leer el contenido existente del archivo
            BufferedReader reader = new BufferedReader(new FileReader(path));
            StringBuilder jsonStringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }

            // Cargar el arreglo existente en jsonArray
            jsonArray = new JSONArray(jsonStringBuilder.toString());
            reader.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            jsonArray = new JSONArray(); // Si no se puede leer, comenzamos con un nuevo JSONArray
        } catch (JSONException e) {
            System.out.println("El archivo no contiene un JSON válido, se creará uno nuevo.");
            jsonArray = new JSONArray(); // Si hay un error de JSON, iniciamos un nuevo JSONArray
        }

        // Agregar la nueva carrera
        try {
            jsonArray.put(carrera);

            // Escribir el arreglo actualizado de nuevo en el archivo
            FileWriter file = new FileWriter(path);
            file.write(jsonArray.toString(4)); // Formateo a 4 espacios de indentación
            file.close();
            System.out.println("Carrera guardada con éxito.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error al convertir la carrera a JSON: " + e.getMessage());
        }

    }

    /**
     * Metodo que escribe un archivo JSON
     * @param filePath
     * @param estudiante
     */
    public static void guardarEstudianteJSON(String filePath, JSONObject estudiante) {
        JSONArray jsonArray;

        // Inicializar el JSONArray
        try {
            // Leer el contenido existente del archivo
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder jsonStringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }

            // Cargar el arreglo existente en jsonArray
            jsonArray = new JSONArray(jsonStringBuilder.toString());
            reader.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            // Si no se puede leer, comenzamos con un nuevo JSONArray
            jsonArray = new JSONArray();
        } catch (JSONException e) {
            System.out.println("El archivo no contiene un JSON válido, se creará uno nuevo.");
            // Si hay un error de JSON, iniciamos un nuevo JSONArray
            jsonArray = new JSONArray();
        }

        // Agregar la nueva persona
        try {

            jsonArray.put(estudiante);

            // Escribir el arreglo actualizado de nuevo en el archivo
            FileWriter file = new FileWriter(filePath);
            file.write(jsonArray.toString(4)); // Formateo a 4 espacios de indentación
            file.close();
            System.out.println("Persona guardada con éxito.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error al convertir la persona a JSON: " + e.getMessage());
        }
    }

    /**
     * Metodo que compara un DNI de un estudiante
     * @param filePath
     * @param estudiante
     * @return
     */
    public static boolean compararDNI(String filePath, JSONObject estudiante) {

        boolean existe = false;

        try {
            // Leer el archivo JSON
            JSONTokener tokener = new JSONTokener(new FileReader(filePath));
            JSONArray estudiantesArray = new JSONArray(tokener);

            // Obtener el DNI del estudiante recibido
            String dniEstudiante = estudiante.getString("dni");

            // Iterar sobre los estudiantes en el archivo
            for (int i = 0; i < estudiantesArray.length(); i++) {
                JSONObject estudianteExistente = estudiantesArray.getJSONObject(i);
                String dniExistente = estudianteExistente.getString("dni");

                // Comparar los DNIs
                if (dniEstudiante.equals(dniExistente)) {
                    existe = true; // Coincidencia encontrada
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage());
        }

        return existe; // No se encontró coincidencia
    }

    /**
     * Metodo que crea un archivo JSON por cada comision
     * @param path
     * @param fileName
     */
    public static void crearArchivoComision(String path,String fileName){

        try {

            FileWriter file = new FileWriter(path + fileName);
            file.write("");
            file.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Metodo que genera el nombre de un archivo de comision
     * @param codigoCarrera
     * @param anioActual
     * @return
     */
    public static String generarNombreArchivoComision(String codigoCarrera, String anioActual) {

        return "COMISIONES_" + codigoCarrera + "_" + anioActual;

    }

    /**
     * Metodo que lee un archivo JSON
     * @param fileName
     * @return
     */
    public static JSONTokener leerArchivoJSON(String fileName) {

        JSONTokener tokener = null;

        try {

            tokener = new JSONTokener(new FileReader(fileName));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tokener;
    }

    public static boolean verificarArchivoCreado(String path, String fileName) {

        File file = new File(path + fileName);
        return file.exists();

    }

    /**
     * Metodo que crea una carpeta de una carrera
     * @param nombreCarrera
     */
    public static void crearCarpetaCarrera(String nombreCarrera) throws ArchivoYaExistenteException {

        File folder = new File("Files\\" + nombreCarrera + "-" + Calendar.getInstance().get(Calendar.YEAR));
        if (!folder.exists()) {

            folder.mkdir();
            System.out.println("creado");

        }else{

            throw new ArchivoYaExistenteException("La carrera ya esxiste");

        }

    }

    public static void crearJSONCarrera(String path, Carrera c){

        JSONObject obj = new JSONObject();

        obj.put("nombre",c.getNombre());
        obj.put("id",c.getId());
        obj.put("plan",c.getPlan());
        obj.put("actividad",c.isActividad());
        obj.put("materias",c.getMaterias());

        cargarJSONcarrera(path ,obj);

    }

    /**
     * Metodo que guarda un arreglo de alumnos en un archivo excel
     * @param stage
     * @param alumnos
     */
    public static void guardarAlumnosExcel(Stage stage, String[] alumnos) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Numeros");

        for (int i = 0; i < alumnos.length; i++) {
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(alumnos[i]);
        }

        // Personalización de los metadatos
        OPCPackage opcPackage = ((XSSFWorkbook) workbook).getPackage();
        POIXMLProperties properties = null;
        try {
            properties = new POIXMLProperties(opcPackage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (OpenXML4JException e) {
            throw new RuntimeException(e);
        } catch (XmlException e) {
            throw new RuntimeException(e);
        }
        properties.getCoreProperties().setCreator("Sysacad PRO");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Excel");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos Excel", "*.xlsx"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
