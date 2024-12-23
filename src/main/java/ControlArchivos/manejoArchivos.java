package ControlArchivos;

import Excepciones.ParametroPeligrosoException;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

public final class manejoArchivos {

    /**
     * Metodo que lee un archivo JSON
     * @param fileName
     * @return
     */
    public static JSONTokener leerArchivoJSON(String fileName) {
        JSONTokener tokener = null;
        File file = new File(fileName);

        try {
            if (!file.exists()) {
                try (FileWriter writer = new FileWriter(fileName)) {
                    writer.write("[]");
                }
            }

            if (file.length() == 0) {
                try (FileWriter writer = new FileWriter(fileName)) {
                    writer.write("[]");
                }
            }

            tokener = new JSONTokener(new FileReader(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tokener;
    }

    /**
     * Metodo que verifica si un archivo fue creado
     * @param path
     * @param fileName
     * @return
     */
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
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder jsonStringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }

            jsonArray = new JSONArray(jsonStringBuilder.toString());
            reader.close();
        } catch (IOException e) {
            jsonArray = new JSONArray();
        } catch (JSONException e) {
            jsonArray = new JSONArray();
        }

        try {
            jsonArray.put(estudiante);
            FileWriter file = new FileWriter(filePath);
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

    /**
     * Metodo que guarda un arreglo de alumnos en un archivo JSON
     * @param path
     * @param arreglo
     */
    public static boolean guardarArchivo(String path, JSONArray arreglo) {

        try {

            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(arreglo.toString(4));
            fileWriter.close();

            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que sobreescribe un archivo JSON
     * @param fileName
     * @param jsonArray
     */
    public static void sobreescribirArchivoJSON(String fileName, JSONArray jsonArray) throws ParametroPeligrosoException {
        if (jsonArray == null || jsonArray.isEmpty()) {
            throw new ParametroPeligrosoException("Ocurrió un error. No se pudo completar la operación. Si el problema persiste llame a su distribuidor");
        }

        try (FileWriter fileWriter = new FileWriter(fileName)) {

            fileWriter.write(jsonArray.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que verifica si una fecha es valida
     * @param fecha
     * @return
     */
    public static boolean esFormatoFechaValida(String fecha) {
        String regexFecha = "\\d{4}-\\d{2}-\\d{2}";
        return Pattern.matches(regexFecha, fecha);
    }

    /**
     * Metodo que verifica si una hora es valida
     * @param hora
     * @return
     */
    public static boolean esFormatoHoraValida(String hora) {
        String regexHora = "\\d{2}:\\d{2}";
        return Pattern.matches(regexHora, hora);
    }

    /**
     * Metodo que verifica si una hora es valida en un rango horario
     * @param hora
     * @return
     */
    public static boolean esHoraValidaEnRango(LocalTime hora) {
        LocalTime horaInicio = LocalTime.of(0, 0);  // 00:00
        LocalTime horaFin = LocalTime.of(23, 59);   // 23:59

        return !hora.isBefore(horaInicio) && !hora.isAfter(horaFin);
    }

    /**
     * Metodo que verifica si una fecha es valida en un rango de fechas
     * @param fecha
     * @return
     */
    public static boolean esFechaValidaEnRango(LocalDate fecha) {
        LocalDate fechaActual = LocalDate.now();
        LocalDate ultimoDiaAnioSiguiente = LocalDate.of(fechaActual.getYear() + 1, 12, 31);
        return !fecha.isBefore(fechaActual) && !fecha.isAfter(ultimoDiaAnioSiguiente);
    }

}
