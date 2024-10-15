package Modelo;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Materia {

    //Atributos

    private String nombre;
    private Profesor profesor;
    private String salon;
    private LinkedHashSet<Alumno> alumnos;
    private LinkedHashSet<String> horario;

    //Contructores

    public Materia(String nombre, Profesor profesor, String salon) {

        this.nombre = nombre;
        this.horario = new LinkedHashSet<>();
        this.profesor = profesor;
        this.salon = salon;
        this.alumnos = new LinkedHashSet<Alumno>();

    }

    public Materia() {
    }

    //Getters

    public String getNombre() {
        return nombre;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public String getSalon() {
        return salon;
    }

    //Setter

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public void agregarAlumno(Alumno a){

        Iterator<Alumno> iterator = alumnos.iterator();

        for(int i = 0; i< alumnos.size(); i++){

            if(!a.equals(iterator.next())){

                alumnos.add(a);

            }

        }

    }

    public String agregarHorarios(String h){

        String mensaje = "";

        if(!horario.contains(h)){

            horario.add(h);

            mensaje = "El horario se cargo correctamente";

        }else{

            mensaje = "El horario ya esta cargado en la materia";

        }

        return mensaje;

    }

    @Override
    public String toString() {
        return " | Materias: " + nombre + " | Horario: " + horario + " " + profesor + " | Salon:" + salon + "\n";
    }

}
