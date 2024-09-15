package User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Alumno extends Persona {

    private String legajo;
    private String carrera;
    private Turno turno;
    private String contrasenia;

    public enum Turno{

        Mañana,
        Media_mañana,
        Tarde,
        Noche;

    }

    public Alumno(String name, String dni, String legajo, Turno turno, String contrasenia) {
        super(name, dni);
        this.legajo = legajo;
        this.carrera = "Programación";
        this.turno = turno;
        this.contrasenia = contrasenia;
    }

    public Alumno(String legajo, Turno turno, String contrasenia) {
        this.legajo = legajo;
        this.carrera = "Programación";
        this.turno = turno;
        this.contrasenia = contrasenia;
    }

    public Alumno() {

        this.carrera = "Programación";

    }

    //GETTERS

    public String getLegajo() {
        return legajo;
    }

    public String getCarrera() { return carrera; }

    public Turno getTurno() {
        return turno;
    }

    public String getContrasenia() { return contrasenia; }

    //SETTERS

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }

    //METODOS

    public JSONArray cargarAlumno(JSONArray arreglo){

        JSONObject obj1 = new JSONObject();

        try {

            obj1.put("nombre",getName());
            obj1.put("dni",getDni());
            obj1.put("legajo",getLegajo());
            obj1.put("carrera",getCarrera());
            obj1.put("turno",getTurno());
            obj1.put("contrasenia",getContrasenia());

            arreglo.put(obj1);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return arreglo;
    }



    @Override
    public String toString() {
        return "Alumno{" +
                "legajo='" + legajo + '\'' +
                ", carrera=" + carrera +
                ", turno=" + turno +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }

}
