package Usuarios;

import ControlArchivos.manejoArchivos;
import ControlArchivos.manejoArchivosEstudiante;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import Modelo.EstadoAlumnoMateria;
import Modelo.EstadoAlumnoMesa;
import Modelo.iCRUD;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public final class Estudiante extends Usuario implements iCRUD {

    private String codigoCarrera;
    private ArrayList<EstadoAlumnoMateria> materias;

    public Estudiante(String nombre, String apellido, String dni, String legajo, String contrasenia, String correo, LocalDate fechaDeAlta, boolean actividad, String codigoCarrera) {
        super(nombre, apellido, dni, legajo, contrasenia, correo, fechaDeAlta, actividad);
        this.codigoCarrera = codigoCarrera;
    }

    public Estudiante(String nombre, String apellido, String dni, String legajo, String contrasenia, String codigoCarrera, String correo) {
        super(nombre, apellido, dni, legajo, contrasenia, correo);
        this.codigoCarrera = codigoCarrera;
        materias = new ArrayList<>();
    }

    public Estudiante(String nombre, String apellido, String dni, String codigoCarrera, String correo) {
        super(nombre, apellido, dni,correo);
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

    /**
     * Crea un alumno si es que sus campos estan completos y el dni del alumno no se repite en la carrera.
     * @param path
     * @return true si lo agregó al archivo de estudiantes
     * @throws EntidadYaExistente
     * @throws CamposVaciosException
     */
    @Override
    public boolean crear(String path) throws EntidadYaExistente, CamposVaciosException, DatosIncorrectosException {
        if(Usuario.esDniValido(this.getDni()))
        {
            if(Usuario.esCorreoValido(this.getCorreo())){
                if(!this.getDni().isEmpty() && !this.getNombre().isEmpty() && !this.getApellido().isEmpty() && !this.getCorreo().isEmpty())
                {
                    this.setLegajo(generarLegajo(Estudiante.class, path));
                    this.setContrasenia(this.getDni());

                    JSONObject jsonObject = this.estudianteAJSONObject();

                    if(!manejoArchivosEstudiante.compararEstudianteDNICarrera(path, jsonObject)) {
                        return manejoArchivos.guardarObjetoJSON(path, jsonObject);
                    } else {
                        throw new EntidadYaExistente("El estudiante ya tiene legajo para la carrera con código " + jsonObject.getString("codigoCarrera"));
                    }
                }else{
                    throw new CamposVaciosException("Intentó enviar campos vacios. Verifique que los campos esten completos y vuelva a intentar");
                }
            }else {
                throw new DatosIncorrectosException("El mail es invalido. Por favor ingrese un mail valido.");
            }


        }else {
            throw new DatosIncorrectosException("El dni es invalido. Solo puede contener numeros.");
        }


    }

    @Override
    public boolean actualizar(String path, JSONObject jsonObject) {
        if(!Usuario.compararJSONObjectConUsuario(jsonObject,this))
        {
            JSONObject estudianteActualizado = this.estudianteAJSONObject();

        }


        return false;
    }

    @Override
    public boolean leer(String path, String legajo) {

        JSONObject estudianteJSON = manejoArchivosEstudiante.retornarEstudiante(legajo,path);

        if(estudianteJSON!=null)
        {

        }

        return false;
    }

    @Override
    public boolean borrar(String path) {
        return false;
    }

    /**
     * Convierte un estudiante a un JSONObject
     * @return JSONObject
     */
    public JSONObject estudianteAJSONObject() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("nombre", this.getNombre());
        jsonObject.put("apellido", this.getApellido());
        jsonObject.put("dni", this.getDni());
        jsonObject.put("legajo", this.getLegajo());
        jsonObject.put("contrasenia", this.getContrasenia());
        jsonObject.put("codigoCarrera",this.getCodigoCarrera());
        jsonObject.put("correo",this.getCorreo());
        jsonObject.put("actividad",this.isActividad());
        jsonObject.put("fechaDeAlta",this.getFechaDeAlta().toString());

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

    /**
     * Convierte un JSONObject a un estudiante
     * @param jsonObject
     * @return Estudiante
     */
    public static Estudiante JSONObjectAEstudiante(JSONObject jsonObject) {
        String nombre = jsonObject.getString("nombre");
        String apellido = jsonObject.getString("apellido");
        String dni = jsonObject.getString("dni");
        String legajo = jsonObject.getString("legajo");
        String contrasenia = jsonObject.getString("contrasenia");
        String codigoCarrera = jsonObject.getString("codigoCarrera");
        String correo = jsonObject.getString("correo");
        boolean actividad = jsonObject.getBoolean("actividad");
        String fechaStr = jsonObject.getString("fecha");
        LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_DATE);
        Estudiante estudiante = new Estudiante(nombre, apellido, dni, legajo, contrasenia, correo, fecha, actividad, codigoCarrera);

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


