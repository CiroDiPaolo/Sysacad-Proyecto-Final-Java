package ControlArchivos;

import java.io.FileWriter;
import java.io.IOException;

public class manejoArchivosComisiones {

    /**
     * Metodo que crea un archivo JSON por cada comision
     * @param path
     * @param fileName
     */
    public static void crearArchivoComision(String path,String fileName, String condigoCarrera){

        try {

            FileWriter file = new FileWriter(path + "/" + condigoCarrera + "/" + fileName + ".json");
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
    public static String generarNombreArchivoComision(String codigoCarrera, int anioActual) {

        return "COMISIONES_" + codigoCarrera + "_" + anioActual;

    }

}
