package Modelo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

/**
 * La clase representa el estado de un Estudiante/Alumno en una comision
 */
public final class EstadoAlumnoComision {
    private String lejagoAlumno;
    private String nombreAlumno;
    private boolean actividad;

    public EstadoAlumnoComision(String lejagoAlumno, String nombreAlumno, boolean actividad) {
        this.lejagoAlumno = lejagoAlumno;
        this.nombreAlumno = nombreAlumno;
        this.actividad = actividad;
    }

    public EstadoAlumnoComision(String codigoAlumno, String nombreAlumno) {
        this.lejagoAlumno = codigoAlumno;
        this.nombreAlumno = nombreAlumno;
        actividad = true;
    }

    public EstadoAlumnoComision() {
        lejagoAlumno = "";
        nombreAlumno = "";
        actividad = false;
    }

    //Getters

    public String getLejagoAlumno() {
        return lejagoAlumno;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public boolean isActividad() {
        return actividad;
    }

    //Setters

    public void setLejagoAlumno(String lejagoAlumno) {
        this.lejagoAlumno = lejagoAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public void setActividad(boolean actividad) {
        this.actividad = actividad;
    }

    //Metodos

    @Override
    public String toString() {
        return "EstadoAlumnoComision{" +
                "lejagoAlumno='" + lejagoAlumno + '\'' +
                ", nombreAlumno='" + nombreAlumno + '\'' +
                ", actividad=" + actividad +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstadoAlumnoComision that = (EstadoAlumnoComision) o;
        return Objects.equals(lejagoAlumno, that.lejagoAlumno);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(lejagoAlumno);
    }

    public JSONObject AlumnoComisionAJSONObject ()
    {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("legajoAlumno",lejagoAlumno);
        jsonObject.put("nombreAlumno",nombreAlumno);
        jsonObject.put("actividad",actividad);
        return jsonObject;
    }

    public static EstadoAlumnoComision JSONObjectAEstadoAlumnoComision(JSONObject jsonObject)
    {
        return new EstadoAlumnoComision(jsonObject.getString("legajoAlumno"),jsonObject.getString("nombreAlumno"),jsonObject.getBoolean("actividad"));
    }


}
