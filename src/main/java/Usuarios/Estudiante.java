package Usuarios;

import Modelo.EstadoMateria;

import java.util.ArrayList;

public final class Estudiante extends Usuario {

    private String codigoCarrera;
    private ArrayList<EstadoMateria> materias;

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

    public ArrayList<EstadoMateria> getMaterias() {
        return materias;
    }

    //Setters

    public void setCodigoCarrera(String codigoCarrera) {
        this.codigoCarrera = codigoCarrera;
    }

    public void setMaterias(ArrayList<EstadoMateria> materias) {
        this.materias = materias;
    }
}
