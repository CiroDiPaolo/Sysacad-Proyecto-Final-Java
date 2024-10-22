package Modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;

public final class MesaExamen {

    private int id;
    private Turno turno;
    private String codigoCarrera;
    private String codigoMateria;
    private String codigoPresidente;
    private HashSet<String> vocales;
    private LocalDate fecha;
    private LocalTime hora;
    private int cupos;
    private String aula;
    private boolean actividad;

    public MesaExamen(int id, Turno turno, String codigoCarrera, String codigoMateria, String codigoPresidente, HashSet<String> vocales, LocalDate fecha, LocalTime hora, int cupos, String aula) {
        this.id = id;
        this.turno = turno;
        this.codigoCarrera = codigoCarrera;
        this.codigoMateria = codigoMateria;
        this.codigoPresidente = codigoPresidente;
        this.vocales = vocales;
        this.fecha = fecha;
        this.hora = hora;
        this.cupos = cupos;
        this.aula = aula;
        this.actividad = true;
    }

    public MesaExamen() {
        turno = null;
        codigoCarrera = "";
        codigoPresidente = "";
        codigoMateria = "";
        cupos = 0;
        aula = "";
        actividad = false;
    }

    //Getters

    public int getId() {
        return id;
    }

    public Turno getTurno() {
        return turno;
    }

    public String getCodigoCarrera() {
        return codigoCarrera;
    }

    public String getCodigoMateria() {
        return codigoMateria;
    }

    public String getCodigoPresidente() {
        return codigoPresidente;
    }

    public HashSet<String> getVocales() {
        return vocales;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public int getCupos() {
        return cupos;
    }

    public String getAula() {
        return aula;
    }

    public boolean isActividad() {
        return actividad;
    }

    //Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public void setCodigoCarrera(String codigoCarrera) {
        this.codigoCarrera = codigoCarrera;
    }

    public void setCodigoMateria(String codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public void setCodigoPresidente(String codigoPresidente) {
        this.codigoPresidente = codigoPresidente;
    }

    public void setVocales(HashSet<String> vocales) {
        this.vocales = vocales;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public void setActividad(boolean actividad) {
        this.actividad = actividad;
    }
}
