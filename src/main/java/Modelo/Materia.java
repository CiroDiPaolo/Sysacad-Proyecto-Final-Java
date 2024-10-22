package Modelo;

import Usuarios.Estudiante;
import Usuarios.Profesor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * La clase materia define una materia. La materia tiene un id que corresponde al id legal, un nombre, el año de la carrera en el que se cursa,
 * el cuatrimestre en el que se cursa, si se cursa, y si se rinde. A su vez guarda el codigo de las correlativas que se necesitan para cursarla
 * y el codigo de las correlativas que se necesitan para rendir el examen final.
 */
public final class Materia {

    //Atributos

    private String id;
    private String nombre;
    private String anio;
    private String cuatrimestre;
    private boolean seCursa;
    private boolean seRinde;
    private HashSet<String> codigoCorrelativasCursado;
    private HashSet<String> codigoCorrelativasRendir;
    private boolean actividad;

    //Constructores

    public Materia(String id, String nombre, String anio, String cuatrimestre, boolean seCursa, boolean seRinde, HashSet<String> codigoCorrelativasCursado, HashSet<String> codigoCorrelativasRendir) {
        this.id = id;
        this.nombre = nombre;
        this.anio = anio;
        this.cuatrimestre = cuatrimestre;
        this.seCursa = seCursa;
        this.seRinde = seRinde;
        this.codigoCorrelativasCursado = codigoCorrelativasCursado;
        this.codigoCorrelativasRendir = codigoCorrelativasRendir;
        this.actividad = true;
    }

    public Materia() {
        id = "";
        nombre ="";
        anio = "";
        cuatrimestre = "";
        seCursa = false;
        seRinde = false;
        actividad = false;
    }

    //Getters

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAnio() {
        return anio;
    }

    public String getCuatrimestre() {
        return cuatrimestre;
    }

    public boolean isSeCursa() {
        return seCursa;
    }

    public boolean isSeRinde() {
        return seRinde;
    }

    public HashSet<String> getCodigoCorrelativasCursado() {
        return codigoCorrelativasCursado;
    }

    public HashSet<String> getCodigoCorrelativasRendir() {
        return codigoCorrelativasRendir;
    }

    public boolean isActividad() {
        return actividad;
    }

    //Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public void setCuatrimestre(String cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public void setSeCursa(boolean seCursa) {
        this.seCursa = seCursa;
    }

    public void setSeRinde(boolean seRinde) {
        this.seRinde = seRinde;
    }

    public void setCodigoCorrelativasCursado(HashSet<String> codigoCorrelativasCursado) {
        this.codigoCorrelativasCursado = codigoCorrelativasCursado;
    }

    public void setCodigoCorrelativasRendir(HashSet<String> codigoCorrelativasRendir) {
        this.codigoCorrelativasRendir = codigoCorrelativasRendir;
    }

    public void setActividad(boolean actividad) {
        this.actividad = actividad;
    }


    //Metodos

    @Override
    public String toString() {
        return "Materia{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", anio='" + anio + '\'' +
                ", cuatrimestre='" + cuatrimestre + '\'' +
                ", seCursa=" + seCursa +
                ", seRinde=" + seRinde +
                ", codigoCorrelativasCursado=" + codigoCorrelativasCursado +
                ", codigoCorrelativasRendir=" + codigoCorrelativasRendir +
                '}';
    }
}
