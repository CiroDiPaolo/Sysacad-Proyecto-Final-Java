package Control.InicioSesion;

import Usuarios.*;
import static Consultas.consultaArchivo.*;
import static Path.Path.*;

public final class inicioSesionData {

    private static String legajo;//Se guarda el legajo de quien ingreso al sistema para futuras operaciones
    private static Estudiante estudiante;//Se guarda el estudiante que ingreso al sistema para futuras operaciones

    public static String getLegajo() {
        return legajo;
    }

    public static void setLegajo(String legajo) {
        inicioSesionData.legajo = legajo;

    }

    public static Estudiante getEstudiante() {
        return estudiante;
    }

    static void setEstudiante() {//Se obtiene el estudiante que ingreso al sistema
        estudiante = obtenerEstudiante(fileNameAlumnos, legajo);
    }


}