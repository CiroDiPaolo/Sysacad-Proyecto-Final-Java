package Usuarios;

import Modelo.EstadoAlumnoMateria;

import java.util.ArrayList;

public final class Estudiante extends Usuario {

    private String codigoCarrera;
    private ArrayList<EstadoAlumnoMateria> materias;

    public Estudiante(String name, String apellido, String dni, String legajo, String contrasenia, String codigoCarrera) {
        super(name, apellido, dni, legajo, contrasenia);
        this.codigoCarrera = codigoCarrera;
    }

    public Estudiante() {
        super();
        this.codigoCarrera = "";

    }

    //Getters
    public String getCodigoCarrera() {
        return codigoCarrera;
    }

    public ArrayList<EstadoAlumnoMateria> getMaterias() {
        return materias;
    }

    //Setters

    public void setCodigoCarrera(String codigoCarrera) {
        this.codigoCarrera = codigoCarrera;
    }

    public void setMaterias(ArrayList<EstadoAlumnoMateria> materias) {
        this.materias = materias;
    }
}
