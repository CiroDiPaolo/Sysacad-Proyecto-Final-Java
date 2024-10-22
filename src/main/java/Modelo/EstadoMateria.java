package Modelo;

import java.util.HashMap;

/**
 *La clase EstadoMateria sirve para saber el estado de una materia ya sea que la cursó o no de un alumno.
 * Guarda un codigo de la materia, estado(0 = no regularizada, 1= regularizada, 2 = aprobada), un string con las notas parciales,
 * un string con las mesas de examen en caso que haya rendido examen final, un tomo, un folio y el codigo de la comision en la cual cursó esa materia.
 */
public class EstadoMateria {
    private String codigoMateria;
    private int estado;
    private HashMap<String, Integer> notas;
    private HashMap<String, EstadoMesa> mesasExamen;
    private String tomo;
    private String folio;
    private String codigoComision;

    //Constructores

    public EstadoMateria(String codigoMateria, int estado, HashMap<String, Integer> notas, HashMap<String, EstadoMesa> mesasExamen, String tomo, String folio, String codigoComision) {
        this.codigoMateria = codigoMateria;
        this.estado = estado;
        this.notas = notas;
        this.mesasExamen = mesasExamen;
        this.tomo = tomo;
        this.folio = folio;
        this.codigoComision = codigoComision;
    }

    public EstadoMateria() {
        codigoMateria = "";
        estado = 0;
        tomo = "0";
        folio = "0";
        codigoComision = "0";
    }

    //Getters

    public String getCodigoMateria() {
        return codigoMateria;
    }

    public int getEstado() {
        return estado;
    }

    public HashMap<String, Integer> getNotas() {
        return notas;
    }

    public HashMap<String, EstadoMesa> getMesasExamen() {
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

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setNotas(HashMap<String, Integer> notas) {
        this.notas = notas;
    }

    public void setMesasExamen(HashMap<String, EstadoMesa> mesasExamen) {
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
