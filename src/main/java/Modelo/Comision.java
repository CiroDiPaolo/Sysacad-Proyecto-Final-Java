package Modelo;

import ControlArchivos.manejoArchivosComisiones;
import Excepciones.ArchivoNoEncontrado;
import Excepciones.ArchivoYaExistenteException;
import java.util.Calendar;
import java.util.HashSet;
import static ControlArchivos.manejoArchivosComisiones.*;

/**
 * La clase Comision esta diseñada para identificar cada comision segun la carrera, el año, la materia, el nombre y el turno.
 * A traves de ella se le puede poner cupos y saber en qué aula y que profesor está asignado.
 */
public final class Comision {

    //Atributos

    private static int id = 0;
    private Turno turno;
    private String nombre;
    private String codigoMateria;
    private String codigoCarrera;
    private String codigoProfesor;
    private int anio;
    private String aula;
    private int cupos;
    private boolean actividad;
    private HashSet<EstadoAlumnoComision> estadoAlumnoComisionHashSet;

    //Constructores

    public Comision(String nombre,Turno turno, String codigoMateria, String codigoCarrera, String codigoProfesor, String aula, int cupos) {
        this.turno = turno;
        this.nombre = nombre;
        this.codigoMateria = codigoMateria;
        this.codigoCarrera = codigoCarrera;
        this.codigoProfesor = codigoProfesor;
        this.anio = Calendar.getInstance().get(Calendar.YEAR);
        this.aula = aula;
        this.cupos = cupos;
        this.actividad = true;
        this.estadoAlumnoComisionHashSet = new HashSet<>();

    }

    public Comision() {
        turno = null;
        nombre = "";
        codigoMateria = "";
        codigoCarrera = "";
        codigoProfesor = "";
        anio = 0;
        aula = "";
        cupos = 0;
        actividad = false;
    }

    //Getters

    public static int getId() {
        return id;
    }

    public Turno getTurno() {
        return turno;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigoMateria() {
        return codigoMateria;
    }

    public String getCodigoCarrera() {
        return codigoCarrera;
    }

    public String getCodigoProfesor() {
        return codigoProfesor;
    }

    public int getAnio() {
        return anio;
    }

    public String getAula() {
        return aula;
    }

    public int getCupos() {
        return cupos;
    }

    public boolean isActividad() {
        return actividad;
    }

    public HashSet<EstadoAlumnoComision> getEstadoAlumnoComisionHashSet() { return estadoAlumnoComisionHashSet; }

    //Setters

    public static void setId(int id) {
        Comision.id = id;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigoMateria(String codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public void setCodigoCarrera(String codigoCarrera) {
        this.codigoCarrera = codigoCarrera;
    }

    public void setCodigoProfesor(String codigoProfesor) {
        this.codigoProfesor = codigoProfesor;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    public void setActividad(boolean actividad) {
        this.actividad = actividad;
    }
    //Metodos

    @Override
    public String toString() {
        return "Comision{" +
                "turno=" + turno +
                ", nombre='" + nombre + '\'' +
                ", codigoMateria='" + codigoMateria + '\'' +
                ", codigoCarrera='" + codigoCarrera + '\'' +
                ", codigoProfesor='" + codigoProfesor + '\'' +
                ", anio='" + anio + '\'' +
                ", aula='" + aula + '\'' +
                ", cupos=" + cupos +
                '}';
    }


}
