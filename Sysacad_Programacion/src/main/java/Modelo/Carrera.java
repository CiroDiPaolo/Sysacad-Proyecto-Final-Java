package Modelo;

import java.util.Objects;

public class Carrera {

    //Atributos

    private String id;
    private String nombre;
    private String duracion;
    private String coordinador;

    //Constructores

    public Carrera(String id, String nombre, String duracion, String coordinador) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.coordinador = coordinador;
    }

    public Carrera() {
    }

    //Getters

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getCoordinador() {
        return coordinador;
    }

    //Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public void setCoordinador(String coordinador) {
        this.coordinador = coordinador;
    }

    //Metodos

    @Override
    public String toString() {
        return "Carrera{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", duracion='" + duracion + '\'' +
                ", coordinador='" + coordinador + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrera carrera = (Carrera) o;
        return Objects.equals(id, carrera.id) && Objects.equals(nombre, carrera.nombre) && Objects.equals(duracion, carrera.duracion) && Objects.equals(coordinador, carrera.coordinador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, duracion, coordinador);
    }
}
