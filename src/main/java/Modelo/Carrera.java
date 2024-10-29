package Modelo;

import ControlArchivos.manejoArchivosCarrera;
import Excepciones.ArchivoYaExistenteException;
import Path.Path;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * La clase Carrera identifica una carrera segun su id y el año del plan;
 * Tiene un nombre y un HashMap de Materia.
 */
public final class Carrera {

    //Atributos

    private String id;
    private String nombre;
    private String plan;
    private HashMap<String, Materia> materias;
    private boolean actividad;

    //Constructores

    public Carrera(String id, String nombre, String plan, HashMap<String, Materia> materias, boolean actividad) {
        this.id = id;
        this.nombre = nombre;
        this.plan = plan;
        this.materias = materias;
        this.actividad = actividad;
    }

    public Carrera(String id, String nombre, String plan, HashMap<String, Materia> materias) {
        this.id = id;
        this.nombre = nombre;
        this.materias = materias;
        this.plan = plan;
        this.actividad = true;

        try {
            crearArchivoCarrera();
        } catch (ArchivoYaExistenteException e) {
            System.out.println(e.getMessage());
        }
    }

    public Carrera() {
        id = "";
        nombre = "";
        plan = "";
        actividad = true;
    }



    //Getters

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPlan() {
        return plan;
    }

    public HashMap<String, Materia> getMaterias() {
        return materias;
    }

    public boolean isActividad() {
        return actividad;
    }

    //Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public void setMaterias(HashMap<String, Materia> materias) {
        this.materias = materias;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setActividad(boolean actividad) {
        this.actividad = actividad;
    }

    //Metodos

    public void crearArchivoCarrera() throws ArchivoYaExistenteException {

        manejoArchivosCarrera.crearCarpetaCarrera(id);
        manejoArchivosCarrera.crearJSONCarrera(Path.pathCarreras, this);

    }

    @Override
    public String toString() {
        return "Carrera{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrera carrera = (Carrera) o;
        return Objects.equals(id, carrera.id) && Objects.equals(nombre, carrera.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }


    /**
     * Convierte una Carrera a un JSONObject
     * @return JSONObject
     */
    public JSONObject carreraAJSONObject(){

        JSONObject obj = new JSONObject();

        obj.put("nombre",this.getNombre());
        obj.put("id",this.getId());
        obj.put("plan",this.getPlan());
        obj.put("actividad",this.isActividad());
        JSONObject materiasJSON = new JSONObject();
        for (Map.Entry<String, Materia> entry : this.getMaterias().entrySet()) {
            materiasJSON.put(entry.getKey(), entry.getValue().materiaAJSONObject());
        }
        obj.put("materias", materiasJSON);

        return obj;
    }

    /**
     * Convierte un JSONObject a una carrera
     * @param jsonObject
     * @return Carrera
     */
    public static Carrera JSONObjectACarrera(JSONObject jsonObject) {

        String id = jsonObject.getString("id");
        String nombre = jsonObject.getString("nombre");
        String plan = jsonObject.getString("plan");
        boolean actividad = jsonObject.getBoolean("actividad");

        HashMap<String, Materia> materias = new HashMap<>();
        JSONObject materiasJson = jsonObject.getJSONObject("materias");

        for (String key : materiasJson.keySet()) {
            JSONObject materiaJson = materiasJson.getJSONObject(key);
            Materia materia = Materia.JSONObjectAMateria(materiaJson);
            materias.put(materia.getId(), materia);
        }

        return new Carrera(id, nombre, plan, materias, actividad);
    }

    /**
     * Imprime la informacion de una carrera
     */
    public void imprimirInformacion() {
        System.out.println("Carrera ID: " + id);
        System.out.println("Nombre de la Carrera: " + nombre);
        System.out.println("Materias y sus correlativas:");

        for (Materia materia : materias.values()) {
            System.out.println("- " + materia.getNombre() + " (ID: " + materia.getId() + ")");
            System.out.println("  Correlativas: " + materia.getCodigoCorrelativasRendir());
        }
    }

}
