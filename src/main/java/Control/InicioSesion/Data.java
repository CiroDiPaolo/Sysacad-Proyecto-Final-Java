package Control.InicioSesion;

import ControlArchivos.manejoArchivosEstudiante;
import Modelo.Carrera;
import Modelo.Comision;
import Modelo.Materia;
import Modelo.MesaExamen;
import Usuarios.*;

import static Path.Path.*;

public final class Data {

    private static String legajo;//Se guarda el legajo de quien ingreso al sistema para futuras operaciones
    private static Estudiante estudiante;//Se guarda el estudiante que ingreso al sistema para futuras operaciones
    private static String aux;
    private static Carrera carrera;
    private static Materia materiaData;
    private static Comision comision;
    private static MesaExamen mesaExamen;

    //Getters
    public static Carrera getCarrera(){
        return carrera;
    }

    public static Materia getMateria(){
        return materiaData;
    }

    public static String getAux() { return aux; }

    public static String getLegajo() {
        return legajo;
    }

    public static Estudiante getEstudiante() {
        return estudiante;
    }

    public static Comision getComision(){ return comision;}

    public static MesaExamen getMesaExamen(){return mesaExamen;}

    //Setters
    public static void setLegajo(String legajo) {
        Data.legajo = legajo;

    }

    static void setEstudiante(String legajo) {

        estudiante =  Estudiante.JSONObjectAEstudiante(manejoArchivosEstudiante.retornarEstudiante(Data.getAux(), fileNameAlumnos));
    }

    public static void setEstudiante(Estudiante estudiante) { Data.estudiante = estudiante; }

    public void setAux(String aux) { this.aux = aux; }

    public void setMateria(Materia materia){
        this.materiaData = materia;
    }

    public void setCarrera(Carrera carrera){
        this.carrera = carrera;
    }

    public void setComision(Comision comision) {this.comision = comision;}

    public void setMesaExamen(MesaExamen mesaExamen){this.mesaExamen = mesaExamen;}

}