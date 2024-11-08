package Modelo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *La clase EstadoMateria sirve para saber el estado de una materia ya sea que la cursó o no de un alumno.
 * Guarda un codigo de la materia, estado(no regularizada, regularizada,aprobada), un string con las notas parciales,
 * un string con las mesas de examen en caso que haya rendido examen final, un tomo, un folio y el codigo de la comision en la cual cursó esa materia.
 */
public final class EstadoAlumnoMateria {

    private String codigoMateria;
    private EstadoMateria estado;
    private HashMap<String, Integer> notas;
    private HashMap<String, EstadoAlumnoMesa> mesasExamen;
    private String tomo;
    private String folio;
    private String codigoComision;

    public EstadoAlumnoMateria(String codigoMateria, EstadoMateria estado, HashMap<String, Integer> notas, HashMap<String, EstadoAlumnoMesa> mesasExamen, String tomo, String folio, String codigoComision) {
        this.codigoMateria = codigoMateria;
        this.estado = estado;
        this.notas = notas;
        this.mesasExamen = mesasExamen;
        this.tomo = tomo;
        this.folio = folio;
        this.codigoComision = codigoComision;
    }

    public EstadoAlumnoMateria(String codigoMateria, EstadoMateria estado, String tomo, String folio, String codigoComision) {
        this.codigoMateria = codigoMateria;
        this.estado = estado;
        this.tomo = tomo;
        this.folio = folio;
        this.codigoComision = codigoComision;
    }

    public EstadoAlumnoMateria() {
        codigoMateria = "";
        estado = null;
        tomo = "0";
        folio = "0";
        codigoComision = "0";
    }

    // Constructor de copia profunda
    public EstadoAlumnoMateria(EstadoAlumnoMateria estado) {
        this.codigoMateria = estado.codigoMateria;
        this.codigoComision = estado.codigoComision;
        this.tomo = estado.tomo;
        this.folio = estado.folio;
        this.notas = new HashMap<>(estado.notas);
        this.mesasExamen = new HashMap<>(estado.mesasExamen);
    }

    //Getters

    public String getCodigoMateria() {
        return codigoMateria;
    }

    public EstadoMateria getEstado() {
        return estado;
    }

    public HashMap<String, Integer> getNotas() {
        return notas;
    }

    public HashMap<String, EstadoAlumnoMesa> getMesasExamen() {
        return mesasExamen;
    }

    public String getTomo() {
        return tomo;
    }

    public String getFolio() {
        return folio;
    }

    public String getCodigoComision() {
        return codigoComision;
    }

    //Setters

    public void setCodigoMateria(String codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public void setEstado(EstadoMateria estado) {
        this.estado = estado;
    }

    public void setNotas(HashMap<String, Integer> notas) {
        this.notas = notas;
    }

    public void setMesasExamen(HashMap<String, EstadoAlumnoMesa> mesasExamen) {
        this.mesasExamen = mesasExamen;
    }

    public void setTomo(String tomo) {
        this.tomo = tomo;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public void setCodigoComision(String codigoComision) {
        this.codigoComision = codigoComision;
    }

}
