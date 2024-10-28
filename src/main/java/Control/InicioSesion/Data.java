package Control.InicioSesion;

import Usuarios.*;
import static Consultas.consultaArchivo.*;
import static Path.Path.*;

public final class Data {

    private static String legajo;//Se guarda un legajo para operaciones futuras
    private static Estudiante estudiante;//Se guarda el estudiante que ingreso al sistema para futuras operaciones
    private static String aux;

    public static String getLegajo() {
        return legajo;
    }

    public static void setLegajo(String legajo) {
        Data.legajo = legajo;

    }

    public static Estudiante getEstudiante() {
        return estudiante;
    }

    static void setEstudiante() { estudiante = obtenerEstudiante(fileNameAlumnos, legajo); }

    public static void setEstudiante(Estudiante estudiante) { Data.estudiante = estudiante; }

    public static String getAux() { return aux; }

    public void setAux(String aux) { this.aux = aux; }
}