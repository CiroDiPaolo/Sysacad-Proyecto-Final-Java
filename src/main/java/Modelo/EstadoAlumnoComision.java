package Modelo;

public class EstadoAlumnoComision {
    private String codigoAlumno;
    private String nombreAlumno;
    private boolean actividad;

    public EstadoAlumnoComision(String codigoAlumno, String nombreAlumno) {
        this.codigoAlumno = codigoAlumno;
        this.nombreAlumno = nombreAlumno;
        actividad = true;
    }

    public EstadoAlumnoComision() {
        codigoAlumno = "";
        nombreAlumno = "";
        actividad = false;
    }

    //Getters

    public String getCodigoAlumno() {
        return codigoAlumno;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public boolean isActividad() {
        return actividad;
    }

    //Setters

    public void setCodigoAlumno(String codigoAlumno) {
        this.codigoAlumno = codigoAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public void setActividad(boolean actividad) {
        this.actividad = actividad;
    }


}
