package Usuarios;

import ControlArchivos.manejoArchivosEstudiante;
import Excepciones.CamposVaciosException;
import Excepciones.EntidadYaExistente;
import Modelo.EstadoAlumnoMateria;
import Modelo.EstadoAlumnoMesa;
import Modelo.iCRUD;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public final class Estudiante extends Usuario implements iCRUD {

    private String codigoCarrera;
    private ArrayList<EstadoAlumnoMateria> materias;

    public Estudiante(String name, String apellido, String dni, String legajo, String contrasenia, String codigoCarrera) {
        super(name, apellido, dni, legajo, contrasenia);
        this.codigoCarrera = codigoCarrera;
        materias = new ArrayList<>();
    }

    public Estudiante(String name, String apellido, String dni, String codigoCarrera) {
        super(name, apellido, dni);
        this.codigoCarrera = codigoCarrera;
        materias = new ArrayList<>();
    }

    public Estudiante() {
        super();
        this.codigoCarrera = "";
        materias = new ArrayList<>();
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

    @Override
    public boolean crear(String path) throws EntidadYaExistente, CamposVaciosException {
        if(!this.getDni().isEmpty() && !this.getName().isEmpty() && !this.getApellido().isEmpty())
        {
            this.setLegajo(generarLegajo(Estudiante.class, path));
            this.setContrasenia(this.getDni());

            JSONObject jsonObject = this.estudianteAJSONObject();

            if(!manejoArchivosEstudiante.compararEstudianteDNICarrera(path, jsonObject)) {
                return manejoArchivosEstudiante.guardarEstudianteJSON(path, jsonObject);
            } else {
                throw new EntidadYaExistente("El estudiante ya tiene legajo para la carrera con código " + jsonObject.getString("codigoCarrera"));
            }
        }else{
            throw new CamposVaciosException("Intentó enviar campos vacios. Verifique que los campos esten completos y vuelva a intentar");
        }

    }

    @Override
    public boolean actualizar(String path) {
        return false;
    }

    @Override
    public boolean leer(String path) {
        return false;
    }

    @Override
    public boolean borrar(String path) {
        return false;
    }

    public JSONObject estudianteAJSONObject() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", this.getName());
        jsonObject.put("apellido", this.getApellido());
        jsonObject.put("dni", this.getDni());
        jsonObject.put("legajo", this.getLegajo());
        jsonObject.put("contrasenia", this.getContrasenia());
        jsonObject.put("codigoCarrera",this.getCodigoCarrera());

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

        return jsonObject;
    }

    public static Estudiante JSONObjectAEstudiante(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String apellido = jsonObject.getString("apellido");
        String dni = jsonObject.getString("dni");
        String legajo = jsonObject.getString("legajo");
        String contrasenia = jsonObject.getString("contrasenia");
        String codigoCarrera = jsonObject.getString("codigoCarrera");

        Estudiante estudiante = new Estudiante(name, apellido, dni, legajo, contrasenia, codigoCarrera);

        ArrayList<EstadoAlumnoMateria> materias = new ArrayList<>();
        int i = 0;
        while (jsonObject.has("materia" + i)) {
            JSONObject materiaJSON = jsonObject.getJSONObject("materia" + i);
            EstadoAlumnoMateria materia = new EstadoAlumnoMateria(
                    (materiaJSON.getString("codigoMateria")),
                    (materiaJSON.getInt("estado")),
                    (materiaJSON.getString("tomo")),
                    (materiaJSON.getString("folio")),
                    (materiaJSON.getString("codigoComision")
            ));

            // Agregar notas
            JSONArray notasArray = materiaJSON.getJSONArray("notas");
            for (int j = 0; j < notasArray.length(); j++) {
                JSONObject nota = notasArray.getJSONObject(j);
                materia.getNotas().put(nota.getString("key"), nota.getInt("value"));
            }

            // Agregar mesas de examen
            JSONArray mesasExamenArray = materiaJSON.getJSONArray("mesasExamen");
            for (int j = 0; j < mesasExamenArray.length(); j++) {
                JSONObject mesaExamen = mesasExamenArray.getJSONObject(j);
                materia.getMesasExamen().put(mesaExamen.getString("key"), EstadoAlumnoMesa.JSONObjectAEstadoAlumnoMesa(mesaExamen));
            }

            materias.add(materia);
            i++;
        }

        estudiante.setMaterias(materias);
        return estudiante;
    }


}


