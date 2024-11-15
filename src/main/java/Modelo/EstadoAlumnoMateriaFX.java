package Modelo;

import java.util.ArrayList;

public class EstadoAlumnoMateriaFX {

    //ATRIBUTOS

    private String codMateria;
    private EstadoMateria estadoMateria;
    private Integer notaParcial1;
    private Integer notaParcial2;
    private ArrayList<EstadoAlumnoMesa> estadoAlumnoMesa;
    private String codigosMesa;
    private String notasMesa;
    private String tomo;
    private String folio;
    private String codComision;

    //CONSTRUCTOR

    public EstadoAlumnoMateriaFX(String codMateria, EstadoMateria estadoMateria, Integer notaParcial1, Integer notaParcial2, ArrayList<EstadoAlumnoMesa> estadoAlumnoMesa, String tomo, String folio, String codComision) {
        this.codMateria = codMateria;
        this.estadoMateria = estadoMateria;
        this.notaParcial1 = notaParcial1;
        this.notaParcial2 = notaParcial2;
        this.estadoAlumnoMesa = estadoAlumnoMesa;
        this.tomo = tomo;
        this.folio = folio;
        this.codComision = codComision;
    }

    public EstadoAlumnoMateriaFX() {
    }

    //GETTERS

    public String getCodMateria() {
        return codMateria;
    }

    public EstadoMateria getEstadoMateria() {
        return estadoMateria;
    }

    public Integer getNotaParcial1() {
        return notaParcial1;
    }

    public Integer getNotaParcial2() {
        return notaParcial2;
    }

    public ArrayList<EstadoAlumnoMesa> getEstadoAlumnoMesa() {
        return estadoAlumnoMesa;
    }

    public String getTomo() {
        return tomo;
    }

    public String getFolio() {
        return folio;
    }

    public String getCodComision() {
        return codComision;
    }

    public String getCodigosMesa(){
        StringBuilder string = new StringBuilder();
        if(this.getEstadoAlumnoMesa() == null || this.getEstadoAlumnoMesa().isEmpty()) {
            return "";
        }
        for(EstadoAlumnoMesa estadoAlumnoMesa1 : this.getEstadoAlumnoMesa()) {
            string.append(estadoAlumnoMesa1.getCodigoMesa()).append(", ");
        }
        if (string.length() > 0) {
            string.setLength(string.length() - 2);
        }
        return string.toString();
    }
    public String getNotasMesa(){
        StringBuilder string = new StringBuilder();
        if(this.getEstadoAlumnoMesa() == null || this.getEstadoAlumnoMesa().isEmpty()) {
            return "";
        }
        for(EstadoAlumnoMesa estadoAlumnoMesa1 : this.getEstadoAlumnoMesa()) {
            string.append(estadoAlumnoMesa1.getNota()).append(", ");
        }

        if (string.length() > 0) {
            string.setLength(string.length() - 2);
        }
        return string.toString();
    }

    //SETTERS

    public void setCodMateria(String codMateria) {
        this.codMateria = codMateria;
    }

    public void setEstadoMateria(EstadoMateria estadoMateria) {
        this.estadoMateria = estadoMateria;
    }

    public void setNotaParcial1(Integer notaParcial1) {
        this.notaParcial1 = notaParcial1;
    }

    public void setNotaParcial2(Integer notaParcial2) {
        this.notaParcial2 = notaParcial2;
    }

    public void setEstadoAlumnoMesa(ArrayList<EstadoAlumnoMesa> estadoAlumnoMesa) {
        this.estadoAlumnoMesa = estadoAlumnoMesa;
    }

    public void setTomo(String tomo) {
        this.tomo = tomo;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public void setCodComision(String codComision) {
        this.codComision = codComision;
    }

    public void setCodigosMesa(String codigosMesa) {
        this.codigosMesa = codigosMesa;
    }

    public void setNotasMesa(String notasMesa) {
        this.notasMesa = notasMesa;
    }

    @Override
    public String toString() {
        return "EstadoAlumnoMateriaFX{" +
                "codMateria='" + codMateria + '\'' +
                ", estadoMateria=" + estadoMateria +
                ", notaParcial1=" + notaParcial1 +
                ", notaParcial2=" + notaParcial2 +
                ", estadoAlumnoMesa=" + estadoAlumnoMesa +
                ", codigosMesa='" + codigosMesa + '\'' +
                ", notasMesa='" + notasMesa + '\'' +
                ", tomo='" + tomo + '\'' +
                ", folio='" + folio + '\'' +
                ", codComision='" + codComision + '\'' +
                '}';
    }
}
