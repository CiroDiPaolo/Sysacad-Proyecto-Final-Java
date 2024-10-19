package Modelo;

import java.util.Date;
import java.util.HashSet;

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
    private String anio;
    private String aula;
    private int cupos;

    //Constructores

    public Comision(Turno turno, String nombre, String codigoMateria, String codigoCarrera, String codigoProfesor, String anio, String aula, int cupos) {
        this.turno = turno;
        this.nombre = nombre;
        this.codigoMateria = codigoMateria;
        this.codigoCarrera = codigoCarrera;
        this.codigoProfesor = codigoProfesor;
        this.anio = anio;
        this.aula = aula;
        this.cupos = cupos;
    }

    public Comision() {
        turno = null;
        nombre = "";
        codigoMateria = "";
        codigoCarrera = "";
        codigoProfesor = "";
        anio = "";
        aula = "";
        cupos = 0;
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

    public String getAnio() {
        return anio;
    }

    public String getAula() {
        return aula;
    }

    public int getCupos() {
        return cupos;
    }


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

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
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
