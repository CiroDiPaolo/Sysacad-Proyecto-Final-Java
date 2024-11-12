package Control.InicioSesion;

import ControlArchivos.manejoArchivosEstudiante;
import ControlArchivos.manejoArchivosProfesor;
import Modelo.Carrera;
import Modelo.Comision;
import Modelo.Materia;
import Usuarios.*;

import static Path.Path.*;

public final class Data {

    private static String legajo;//Se guarda el legajo de quien ingreso al sistema para futuras operaciones
    private static Estudiante estudiante;//Se guarda el estudiante que ingreso al sistema para futuras operaciones
    private static String aux;
    private static Carrera carrera;
    private static Materia materiaData;
    private static Comision comision;
    private static String aux2;
    private static Profesor profesor;

    //Getters

    public static Profesor getProfesor() { return profesor; }

    public static Carrera getCarrera(){ return carrera; }

    public static Materia getMateria(){ return materiaData; }

    public static String getAux() { return aux; }

    public static String getLegajo() { return legajo; }

    public static Estudiante getEstudiante() { return estudiante; }

    public static Comision getComision(){ return comision;}

    //Setters

    public static void setProfesor(String legajo) { Data.profesor = Profesor.JSONObjectAProfesor(manejoArchivosProfesor.retornarProfesor(legajo,fileNameProfesores)); }

    public static void setLegajo(String legajo) { Data.legajo = legajo; }

    static void setEstudiante(String legajo) { estudiante =  Estudiante.JSONObjectAEstudiante(manejoArchivosEstudiante.retornarEstudiante(legajo, fileNameAlumnos)); }

    public static void setEstudiante(Estudiante estudiante) { Data.estudiante = estudiante; }

    public static void setAux(String aux) { Data.aux = aux; }

    public void setMateria(Materia materia){ this.materiaData = materia; }

    public void setCarrera(Carrera carrera){ this.carrera = carrera; }

    public void setComision(Comision comision) {this.comision = comision;}

    public static String getAux2() { return aux2; }

    public static void setAux2(String aux2) { Data.aux2 = aux2; }

}