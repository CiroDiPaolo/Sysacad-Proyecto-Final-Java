package ControlArchivos;

import Excepciones.ArchivoYaExistenteException;
import Excepciones.excepcionPersonalizada;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
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

import static Path.Path.icono;

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
     * Metodo que lee un archivo JSON
     * @param fileName
     * @return
     */
    public static JSONTokener leerArchivoJSON(String fileName) {

        JSONTokener tokener = null;

        try {

            tokener = new JSONTokener(new FileReader(fileName));

        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tokener;
    }

    public static boolean verificarArchivoCreado(String path, String fileName) {

        File file = new File(path + fileName + ".json");

        return file.exists();

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

    /**
     * Busca el ultimo legajo cargado en el archivo especificado
     * @param fileName
     * @return String
     */
    public static String ultimoLegajo(String fileName)
    {
        JSONArray arreglo = new JSONArray(leerArchivoJSON(fileName));
        int ultimaPos = arreglo.length();
        String ultimoLegajo;
        if(ultimaPos>0)
        {
            ultimoLegajo = (String) arreglo.getJSONObject((ultimaPos-1)).get("legajo");

        } else
        {
            ultimoLegajo = "_000000";
        }

        return ultimoLegajo;
    }

    /**
     * Metodo que escribe un archivo JSON
     * @param filePath
     * @param estudiante
     */
    public static boolean guardarObjetoJSON(String filePath, JSONObject estudiante) {

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

            return true;
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error al convertir la persona a JSON: " + e.getMessage());
        }

        return false;
    }

    public static void sobreescribirArchivoJSON(String fileName, JSONArray jsonArray) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            // Escribir el JSONArray en el archivo con un formato bonito (4 espacios de sangría)
            fileWriter.write(jsonArray.toString(4));
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de excepciones, puedes personalizar esto
        }
    }


}
