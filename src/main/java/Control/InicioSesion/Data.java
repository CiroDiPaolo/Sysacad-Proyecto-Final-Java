package Control.InicioSesion;

import Usuarios.*;

public final class Data {

    private static Estudiante estudiante;//Se guarda el estudiante que ingreso al sistema para futuras operaciones

    public static String getLegajo() {
        return legajo;
    }

    public static void setLegajo(String legajo) {
        Data.legajo = legajo;

    }

    public static Estudiante getEstudiante() {
        return estudiante;
    }

    }



}