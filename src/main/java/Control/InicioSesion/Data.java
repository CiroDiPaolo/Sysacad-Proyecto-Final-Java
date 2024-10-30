package Control.InicioSesion;

import Modelo.Carrera;
import Modelo.Materia;
import Usuarios.*;
import static Consultas.consultaArchivo.*;
import static Path.Path.*;

public final class Data {

    private static String legajo;//Se guarda el legajo de quien ingreso al sistema para futuras operaciones
    private static Estudiante estudiante;//Se guarda el estudiante que ingreso al sistema para futuras operaciones
    private static Carrera carrera;
    private static Materia materiaData;

    public static Carrera getCarrera(){
        return carrera;
    }

    public void setCarrera(Carrera carrera){
        this.carrera = carrera;
    }

    public static Materia getMateria(){
        return materiaData;
    }

    public void setMateria(Materia materia){
        this.materiaData = materia;
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

    static void setEstudiante() {//Se obtiene el estudiante que ingreso al sistema
        estudiante = obtenerEstudiante(fileNameAlumnos, legajo);
    }



}