package Control.InicioSesion;

import ControlArchivos.manejoArchivosEstudiante;
import Modelo.Carrera;
import Path.Path;
import Usuarios.*;

public final class Data {

    private static Estudiante estudiante;//Se guarda el estudiante que ingreso al sistema para futuras operaciones

    private static String legajo;

    private static String aux;

    private static Carrera carrera;

    public static Carrera getCarrera(){
        return carrera;
    }

    public void setCarrera(Carrera carrera){
        this.carrera = carrera;
    }

    public static String getLegajo() {
        return legajo;
    }

    public static void setLegajo(String legajo) {
        Data.legajo = legajo;

    }

    public static Estudiante getEstudiante() {
        return estudiante;
    }

    static void setEstudiante(String legajo) {

        estudiante =  Estudiante.JSONObjectAEstudiante(manejoArchivosEstudiante.retornarEstudiante(Data.getAux(), Path.fileNameAlumnos));
    }

    public static void setEstudiante(Estudiante estudiante) { Data.estudiante = estudiante; }

    public static String getAux() { return aux; }

    public void setAux(String aux) { this.aux = aux; }

}