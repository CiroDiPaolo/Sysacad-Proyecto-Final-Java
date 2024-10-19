package Modelo;

import java.util.HashMap;
import java.util.Objects;

/**
 * La clase Carrera identifica una carrera segun su id y el año del plan;
 * Tiene un nombre y un HashMap de Materia.
 */
public final class Carrera {

    //Atributos

    private String id;
    private String nombre;
    private String plan;
    private HashMap<String, Materia> materias;

    //Constructores

    public Carrera(String id, String nombre, String plan, HashMap<String, Materia> materias) {
        this.id = id;
        this.nombre = nombre;
        this.materias = materias;
        this.plan = plan;
    }

    public Carrera() {
        id = "";
        nombre = "";
        plan = "";
    }

    //Getters

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPlan() {
        return plan;
    }

    public HashMap<String, Materia> getMaterias() {
        return materias;
    }


    //Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public void setMaterias(HashMap<String, Materia> materias) {
        this.materias = materias;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //Metodos

    @Override
    public String toString() {
        return "Carrera{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrera carrera = (Carrera) o;
        return Objects.equals(id, carrera.id) && Objects.equals(nombre, carrera.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }
}
