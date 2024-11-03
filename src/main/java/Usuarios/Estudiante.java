package Usuarios;

import ControlArchivos.manejoArchivos;
import ControlArchivos.manejoArchivosCarrera;
import ControlArchivos.manejoArchivosEstudiante;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import Modelo.*;
import Path.Path;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Esta clase hereda de Usuario y representa un Estudiante en el sistema.
 */
public final class Estudiante extends Usuario implements iCRUD {

    private String codigoCarrera;
    private ArrayList<EstadoAlumnoMateria> materias;

    public Estudiante(String nombre, String apellido, String dni, String legajo, String contrasenia, String correo, LocalDate fechaDeAlta, boolean actividad, String codigoCarrera) {
        super(nombre, apellido, dni, legajo, contrasenia, correo, fechaDeAlta, actividad);
        this.codigoCarrera = codigoCarrera;
    }

    public Estudiante(Estudiante estudiante) {
        this.setNombre(estudiante.getNombre());
        this.setApellido(estudiante.getApellido());
        this.setDni(estudiante.getDni());
        this.setLegajo(estudiante.getLegajo());
        this.setContrasenia(estudiante.getContrasenia());
        this.setCodigoCarrera(estudiante.getCodigoCarrera());
        this.setCorreo(estudiante.getCorreo());
        this.setActividad(estudiante.getActividad());
        this.setFechaDeAlta(estudiante.getFechaDeAlta());
        this.setMaterias(estudiante.getMaterias());
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
    public boolean actualizar(String path, JSONObject jsonObject) throws DatosIncorrectosException {

        boolean resultado = false;

        if(!Usuario.compararJSONObjectConUsuario(jsonObject,this))
        {

            JSONArray arreglo = new JSONArray(manejoArchivos.leerArchivoJSON(path));

            for(int i = 0; i<arreglo.length(); i++)
            {
                JSONObject estudiante = arreglo.getJSONObject(i);
                if(estudiante.getString("legajo").equals(this.getLegajo()))
                {

                    arreglo.put(i,jsonObject);
                    if(manejoArchivos.guardarArchivo(path,arreglo)){

                        resultado = true;

                    }
                }
            }

        }else{

            throw new DatosIncorrectosException("No se realizaron cambios en los datos del alumno");

        }

        return resultado;

    }

    @Override
    public boolean leer(String path, String legajo) {
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
        jsonObject.put("actividad",this.getActividad());
        jsonObject.put("fechaDeAlta",this.getFechaDeAlta().toString());

        JSONArray materias = new JSONArray();

        for(int i = 0 ; i < this.getMaterias().size() ; i++){

            JSONObject materia = new JSONObject();

            materia.put("id",this.getMaterias().get(i).getCodigoMateria());
            materia.put("estado",this.getMaterias().get(i).getEstado());
            materia.put("tomo",this.getMaterias().get(i).getTomo());
            materia.put("folio",this.getMaterias().get(i).getFolio());
            materia.put("codigoComision",this.getMaterias().get(i).getCodigoComision());

            JSONArray notas = new JSONArray();

            for(int j = 0 ; j < this.getMaterias().get(i).getNotas().size() ; j++){

                for (String key : this.getMaterias().get(i).getNotas().keySet()) {

                    JSONObject nota = new JSONObject();
                    nota.put(key,this.getMaterias().get(i).getNotas().get(key));
                    notas.put(nota);

                }

            }

            materia.put("notas", notas);

            JSONArray mesasExamen = new JSONArray();

            for(int j = 0 ; j< this.getMaterias().get(j).getMesasExamen().size() ; j++){

                JSONObject mesaExamen = new JSONObject();

                mesaExamen.put("key",this.getMaterias().get(j).getMesasExamen().keySet());
                mesaExamen.put("value",this.getMaterias().get(j).getMesasExamen().get(j).getNota());
                mesaExamen.put("presente",this.getMaterias().get(j).getMesasExamen().get(j).isPresente());

                mesasExamen.put(mesaExamen);

            }

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
        String fechaStr = jsonObject.getString("fechaDeAlta");
        LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_DATE);
        Estudiante estudiante = new Estudiante(nombre, apellido, dni, legajo, contrasenia, correo, fecha, actividad, codigoCarrera);

        ArrayList<EstadoAlumnoMateria> materias = new ArrayList<>();
        JSONArray materiasArray = jsonObject.getJSONArray("materias");

        for (int i = 0; i < materiasArray.length(); i++) {
            JSONObject materia = materiasArray.getJSONObject(i);
            String codigoMateria = materia.getString("id");
            EstadoMateria estado = EstadoMateria.valueOf(materia.getString("estado"));
            String tomo = materia.getString("tomo");
            String folio = materia.getString("folio");
            String codigoComision = materia.getString("codigoComision");

            HashMap<String, Integer> notas = new HashMap<>();
            JSONArray notasArray = materia.getJSONArray("notas");
            for (int j = 0; j < notasArray.length(); j++) {
                JSONObject nota = notasArray.getJSONObject(j);
                String key = nota.keys().next();
                int value = nota.getInt(key);
                notas.put(key, value);
            }

            HashMap<String, EstadoAlumnoMesa> mesasExamen = new HashMap<>();
            JSONArray mesasExamenArray = materia.getJSONArray("mesasExamen");
            for (int j = 0; j < mesasExamenArray.length(); j++) {
                JSONObject mesaExamen = mesasExamenArray.getJSONObject(j);
                String key = mesaExamen.getString("key");
                int value = mesaExamen.getInt("value");
                boolean presente = mesaExamen.getBoolean("presente");
                mesasExamen.put(key, new EstadoAlumnoMesa(key, value, presente));
            }

            materias.add(new EstadoAlumnoMateria(codigoMateria, estado, notas, mesasExamen, tomo, folio, codigoComision));

        }

        estudiante.setMaterias(materias);
        return estudiante;
    }

    public boolean compararEstudiantes(Estudiante that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        return getActividad() == that.getActividad() &&
                getNombre().equals(that.getNombre()) &&
                getApellido().equals(that.getApellido()) &&
                getDni().equals(that.getDni()) &&
                getLegajo().equals(that.getLegajo()) &&
                getContrasenia().equals(that.getContrasenia()) &&
                getCodigoCarrera().equals(that.getCodigoCarrera()) &&
                getCorreo().equals(that.getCorreo()) &&
                getFechaDeAlta().equals(that.getFechaDeAlta()) &&
                getMaterias().equals(that.getMaterias());
    }

    public HashMap<String, String> obtenerMaterias(){

        HashMap<String, Materia> materiasCarrera = (manejoArchivosCarrera.retornarCarrera(Path.pathCarreras,this.getCodigoCarrera())).getMaterias();

        HashMap<String, String> materiasEstudiante = new HashMap<>();

        for(int i = 0 ; i<this.getMaterias().size() ; i++){

            if(materiasCarrera.containsKey(this.getMaterias().get(i).getCodigoMateria())){
                materiasEstudiante.put(this.getMaterias().get(i).getCodigoMateria(),materiasCarrera.get(this.getMaterias().get(i).getCodigoMateria()).getNombre());
            }

        }

        return materiasEstudiante;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "codigoCarrera='" + codigoCarrera + '\'' +
                ", materias=" + materias +
                '}';
    }
}


