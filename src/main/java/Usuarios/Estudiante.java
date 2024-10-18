package Usuarios;

import Modelo.Carrera;
import Modelo.Materia;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

public final class Estudiante extends Usuario {

    private Carrera carrera;
    private HashSet<Materia> materiasCursadas;
    private HashSet<Materia> materiasAprobadas;
    private HashSet<Materia> materiasInscriptas;
    private HashSet<Materia> materiasRegularizadas;
    private HashSet<Materia> materiasDesaprobadas;

    public Estudiante(String name, String apellido, String dni, String legajo, String contrasenia, Carrera carrera) {

        super(name, apellido, dni, legajo, contrasenia);
        this.carrera = carrera;
        materiasCursadas = new HashSet<>();
        materiasAprobadas = new HashSet<>();
        materiasInscriptas = new HashSet<>();
        materiasRegularizadas = new HashSet<>();
        materiasDesaprobadas = new HashSet<>();

    }

    public Estudiante() {

        materiasCursadas = new HashSet<>();
        materiasAprobadas = new HashSet<>();
        materiasInscriptas = new HashSet<>();
        materiasRegularizadas = new HashSet<>();
        materiasDesaprobadas = new HashSet<>();

    }

    //GETTERS

    public Carrera getCarrera() { return carrera; }

    public HashSet<Materia> getMateriasCursadas() { return materiasCursadas; }

    public HashSet<Materia> getMateriasAprobadas() { return materiasAprobadas; }

    public HashSet<Materia> getMateriasInscriptas() { return materiasInscriptas; }

    public HashSet<Materia> getMateriasRegularizadas() { return materiasRegularizadas; }

    public HashSet<Materia> getMateriasDesaprobadas() { return materiasDesaprobadas; }

    //METODOS

    public JSONArray cargarAlumno(JSONArray arreglo){

        JSONObject obj1 = new JSONObject();

        JSONArray materiasCursadas = new JSONArray();
        JSONArray materiasAprobadas = new JSONArray();
        JSONArray materiasInscriptas = new JSONArray();
        JSONArray materiasRegularizadas = new JSONArray();
        JSONArray materiasDesaprobadas = new JSONArray();

        try {

            obj1.put("nombre",getName());
            obj1.put("dni",getDni());
            obj1.put("legajo",getLegajo());
            obj1.put("carrera",getCarrera());
            obj1.put("contrasenia",getContrasenia());

            for (Materia materia : getMateriasCursadas()) {
                materiasCursadas.put(materia);
            }

            obj1.put("materiasCursadas",materiasCursadas);

            for (Materia materia : getMateriasAprobadas()) {
                materiasAprobadas.put(materia);
            }

            obj1.put("materiasAprobadas",materiasAprobadas);

            for (Materia materia : getMateriasInscriptas()) {
                materiasInscriptas.put(materia);
            }

            obj1.put("materiasInscriptas",materiasInscriptas);

            for (Materia materia : getMateriasRegularizadas()) {
                materiasRegularizadas.put(materia);
            }

            obj1.put("materiasRegularizadas",materiasRegularizadas);

            for (Materia materia : getMateriasDesaprobadas()) {
                materiasDesaprobadas.put(materia);
            }

            obj1.put("materiasDesaprobadas",materiasDesaprobadas);

            arreglo.put(obj1);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return arreglo;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "carrera=" + carrera +
                ", materiasCursadas=" + materiasCursadas +
                ", materiasAprobadas=" + materiasAprobadas +
                ", materiasInscriptas=" + materiasInscriptas +
                ", materiasRegularizadas=" + materiasRegularizadas +
                ", materiasDesaprobadas=" + materiasDesaprobadas +
                '}';
    }

}
