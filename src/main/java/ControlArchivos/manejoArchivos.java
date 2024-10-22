package ControlArchivos;
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
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;


public final class manejoArchivos {

    /**
     * Metodo que crea un archivo JSON
     * @param fileName
     * @param arreglo
     */
    public static void crearArchivoJSON(String fileName, JSONArray arreglo) {

        try {

            FileWriter file = new FileWriter(fileName);
            file.write(arreglo.toString());
            file.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Metodo que escribe un archivo JSON
     * @param fileName
     * @param estudiante
     */
    public static void escribirArchivoJSON(String fileName, JSONObject estudiante) {

        try {
            File file = new File(fileName);
            JSONArray estudiantesArray;

            // Leer el archivo existente
            if (file.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(fileName)));
                if (content.isEmpty()) {
                    estudiantesArray = new JSONArray();
                } else {
                    estudiantesArray = new JSONArray(content);
                }
            } else {
                estudiantesArray = new JSONArray();
            }

            // Agregar el nuevo estudiante al array
            estudiantesArray.put(estudiante);

            // Escribir el array actualizado de vuelta al archivo
            try (FileWriter fileWriter = new FileWriter(fileName)) {
                fileWriter.write(estudiantesArray.toString());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    /**
     * Metodo que crea una carpeta de una carrera
     * @param nombreCarrera
     */
    public static void crearCarpetaCarrera(String nombreCarrera) {

        File folder = new File("Files\\Carreras" + nombreCarrera + "-" + Calendar.getInstance().get(Calendar.YEAR));
        if (!folder.exists()) {
            folder.mkdir();

            System.out.println("creado");

        }else{

            System.out.println("ya existe");

        }

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
