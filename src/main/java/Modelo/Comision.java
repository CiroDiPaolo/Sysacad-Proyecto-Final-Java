package Modelo;

import java.util.Date;
import java.util.HashSet;

public final class Comision {

    //Atributos

    private static int id = 0;
    private String nombre;
    private Turno turno;
    private HashSet<String> codigoMateria;
    private String nombreProfe;
    private int anio;
    private int cupos;

    //Constructores

    public Comision(String nombre, Turno turno, HashSet<String> codigoMateria, String nombreProfe, Date anio, int cupos) {
        id = id++;
        this.nombre = nombre;
        this.turno = turno;
        this.codigoMateria = new HashSet<>();
        this.nombreProfe = nombreProfe;
        this.anio = anio.getYear();
        this.cupos = cupos;
    }

    public Comision() {
    }

    //Getters

    public static int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Turno getTurno() {
        return turno;
    }

    public HashSet<String> getCodigoMateria() {
        return codigoMateria;
    }

    public String getNombreProfe() {
        return nombreProfe;
    }

    public int getAnio() {
        return anio;
    }

    public int getCupos() {
        return cupos;
    }

    //Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public void setNombreProfe(String nombreProfe) {
        this.nombreProfe = nombreProfe;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    //Metodos

    @Override
    public String toString() {
        return "Comision{" +
                "nombre='" + nombre + '\'' +
                ", turno=" + turno +
                ", codigoMateria=" + codigoMateria +
                ", nombreProfe='" + nombreProfe + '\'' +
                ", anio=" + anio +
                ", cupos=" + cupos +
                '}';
    }
}
