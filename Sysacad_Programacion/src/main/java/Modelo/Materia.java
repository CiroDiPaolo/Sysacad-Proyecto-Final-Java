package Modelo;

import Usuarios.Estudiante;
import Usuarios.Profesor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

public final class Materia {

    //Atributos

    private String id;
    private String nombre;
    private HashSet<String> codigoCarrera;

    //Constructores

    public Materia(String id, String nombre, HashSet<String> codigoCarrera) {
        this.id = id;
        this.nombre = nombre;
        this.codigoCarrera = codigoCarrera;
    }

    public Materia() {
    }

    //Getters

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public HashSet<String> getCodigoCarrera() {
        return codigoCarrera;
    }

    //Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigoCarrera(HashSet<String> codigoCarrera) {
        this.codigoCarrera = codigoCarrera;
    }

    //Metodos

    @Override
    public String toString() {
        return "Materia{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", codigoCarrera=" + codigoCarrera +
                '}';
    }

}
