package Usuarios;

import ControlArchivos.manejoArchivos;
import Excepciones.DNICargadoException;
import Modelo.EstadoAlumnoMateria;
import Path.Path;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public final class Estudiante extends Usuario {

    private String codigoCarrera;
    private ArrayList<EstadoAlumnoMateria> materias;

    public Estudiante(String name, String apellido, String dni, String legajo, String contrasenia, String codigoCarrera) {
        super(name, apellido, dni, legajo, contrasenia);
        this.codigoCarrera = codigoCarrera;
    }

    public Estudiante() {
        super();
        this.codigoCarrera = "";

    }

    //Getters
    public String getCodigoCarrera() {
        return codigoCarrera;
    }

    public ArrayList<EstadoAlumnoMateria> getMaterias() {
        return materias;
    }

    //Setters

    public void setCodigoCarrera(String codigoCarrera) {
        this.codigoCarrera = codigoCarrera;
    }

    public void setMaterias(ArrayList<EstadoAlumnoMateria> materias) {
        this.materias = materias;
    }

    //Metodos

    public void cargarEstudianteJSON() throws DNICargadoException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", this.getName());
        jsonObject.put("apellido", this.getApellido());
        jsonObject.put("dni", this.getDni());
        jsonObject.put("legajo", this.getLegajo());
        jsonObject.put("contrasenia", this.getContrasenia());
        jsonObject.put("codigoCarrera", this.getCodigoCarrera());

        for (int i = 0; i < this.getMaterias().size(); i++) {

            JSONObject materia = new JSONObject();

            materia.put("codigoMateria", this.getMaterias().get(i).getCodigoMateria());
            materia.put("estado", this.getMaterias().get(i).getEstado());
            materia.put("tomo", this.getMaterias().get(i).getTomo());
            materia.put("folio", this.getMaterias().get(i).getFolio());
            materia.put("codigoComision", this.getMaterias().get(i).getCodigoComision());

            JSONArray notas = new JSONArray();

            for (String key : this.getMaterias().get(i).getNotas().keySet()) {

                JSONObject nota = new JSONObject();
                nota.put("key", key);
                nota.put("value", this.getMaterias().get(i).getNotas().get(key));
                notas.put(nota);

            }
            materia.put("notas", notas);

            JSONArray mesasExamen = new JSONArray();

            for (String key : this.getMaterias().get(i).getMesasExamen().keySet()) {

                JSONObject mesaExamen = new JSONObject();
                mesaExamen.put("key", key);
                mesaExamen.put("value", this.getMaterias().get(i).getMesasExamen().get(key));
                mesasExamen.put(mesaExamen);
            }
            materia.put("mesasExamen", mesasExamen);

            jsonObject.put("materia" + i, materia);

        }

        if(manejoArchivos.compararDNI(Path.fileNameAlumnos, jsonObject)) {

            throw new DNICargadoException("El DNI ya se encuentra cargado");

        }else{

            manejoArchivos.guardarEstudianteJSON(Path.fileNameAlumnos, jsonObject);

        }

    }

}


