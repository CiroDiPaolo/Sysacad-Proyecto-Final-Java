package Usuarios;

import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivos;
import ControlArchivos.manejoArchivosCarrera;
import ControlArchivos.manejoArchivosEstudiante;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import Modelo.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static Path.Path.pathCarreras;

/**
 * Esta clase hereda de Usuario y representa un Estudiante en el sistema.
 */
public final class Estudiante extends Usuario implements iCRUD {

    //ATRIBUTOS

    private String codigoCarrera;
    private ArrayList<EstadoAlumnoMateria> materias;

    //CONSTRUCTORES

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
        this.materias = new ArrayList<>();
        for (EstadoAlumnoMateria materia : estudiante.getMaterias()) {
            this.materias.add(new EstadoAlumnoMateria(materia));
        }
    }

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
        super(nombre, apellido, dni, correo);
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

    public List<EstadoAlumnoMateria> getMaterias() {
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
     *
     * @param path
     * @return true si lo agregó al archivo de estudiantes
     * @throws EntidadYaExistente
     * @throws CamposVaciosException
     */
    @Override
    public boolean crear(String path) throws EntidadYaExistente, CamposVaciosException, DatosIncorrectosException {
        if (Usuario.esDniValido(this.getDni())) {
            if (Usuario.esCorreoValido(this.getCorreo())) {
                if (!this.getDni().isEmpty() && !this.getNombre().isEmpty() && !this.getApellido().isEmpty() && !this.getCorreo().isEmpty()) {
                    this.setLegajo(generarLegajo(Estudiante.class, path));
                    this.setContrasenia(this.getDni());

                    JSONObject jsonObject = this.estudianteAJSONObject();

                    if (!manejoArchivosEstudiante.compararEstudianteDNICarrera(path, jsonObject)) {
                        return manejoArchivos.guardarObjetoJSON(path, jsonObject);
                    } else {
                        throw new EntidadYaExistente("El estudiante ya tiene legajo para la carrera con código " + jsonObject.getString("codigoCarrera"));
                    }
                } else {
                    throw new CamposVaciosException("Intentó enviar campos vacios. Verifique que los campos esten completos y vuelva a intentar");
                }
            } else {
                throw new DatosIncorrectosException("El mail es invalido. Por favor ingrese un mail valido.");
            }


        } else {
            throw new DatosIncorrectosException("El dni es invalido. Solo puede contener numeros.");
        }


    }

    /**
     * Actualiza un estudiante donde pasa por verificaciones previas antes de aactualizarse
     *  dentro del archivo y se compara con el estudiante actual con el JSON
     * @param path
     * @param jsonObject
     * @return true si lo leyó
     */
    @Override
    public boolean actualizar(String path, JSONObject jsonObject) throws CamposVaciosException, DatosIncorrectosException {

        boolean resultado = false;

        if (!compararJSONObjectConEstudiante(this,jsonObject)) {

            JSONArray arreglo = new JSONArray(manejoArchivos.leerArchivoJSON(path));

            for (int i = 0; i < arreglo.length(); i++) {
                JSONObject estudiante = arreglo.getJSONObject(i);
                if (estudiante.getString("legajo").equals(this.getLegajo())) {

                    arreglo.put(i, jsonObject);
                    if (manejoArchivos.guardarArchivo(path, arreglo)) {

                        resultado = true;

                    }
                }
            }

        } else {

            throw new DatosIncorrectosException("No se realizaron cambios en los datos del alumno");

        }

        return resultado;

    }

    /**
     * Lee un estudiante del archivo JSON
     *
     * @param path
     * @param legajo
     * @return true si lo leyó
     */
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
     *
     * @return JSONObject
     */
    public JSONObject estudianteAJSONObject() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("nombre", this.getNombre());
        jsonObject.put("apellido", this.getApellido());
        jsonObject.put("dni", this.getDni());
        jsonObject.put("legajo", this.getLegajo());
        jsonObject.put("contrasenia", this.getContrasenia());
        jsonObject.put("codigoCarrera", this.getCodigoCarrera());
        jsonObject.put("correo", this.getCorreo());
        jsonObject.put("actividad", this.getActividad());
        jsonObject.put("fechaDeAlta", this.getFechaDeAlta().toString());

        JSONArray materias = new JSONArray();

        for (int i = 0; i < this.getMaterias().size(); i++) {
            JSONObject materia = new JSONObject();

            materia.put("id", this.getMaterias().get(i).getCodigoMateria());
            materia.put("estado", this.getMaterias().get(i).getEstado());
            materia.put("tomo", this.getMaterias().get(i).getTomo());
            materia.put("folio", this.getMaterias().get(i).getFolio());
            materia.put("codigoComision", this.getMaterias().get(i).getCodigoComision());

            JSONArray notas = new JSONArray();

            if(this.getMaterias().get(i).getNotas().isEmpty()){

                JSONObject nota = new JSONObject();
                nota.put("primerParcial", 0);
                nota.put("segundoParcial", 0);
                notas.put(nota);
            }else{

                for (String key : this.getMaterias().get(i).getNotas().keySet()) {
                    JSONObject nota = new JSONObject();
                    nota.put(key, this.getMaterias().get(i).getNotas().get(key));
                    notas.put(nota);
                }

            }

            materia.put("notas", notas);

            JSONArray mesasExamen = new JSONArray();

            if (this.getMaterias().get(i).getMesasExamen()!= null) {

                for (String key : this.getMaterias().get(i).getMesasExamen().keySet()) {

                    EstadoAlumnoMesa mesaExamen = this.getMaterias().get(i).getMesasExamen().get(key);
                    JSONObject mesaExamenJson = new JSONObject();

                    mesaExamenJson.put(key, mesaExamen.getCodigoMesa());
                    mesaExamenJson.put("nota", mesaExamen.getNota()); // Guardar la nota como int
                    mesaExamenJson.put("presente", mesaExamen.isPresente());
                    mesasExamen.put(mesaExamenJson);
                }
            }

            materia.put("mesasExamen", mesasExamen);
            materias.put(materia);
        }

        jsonObject.put("materias", materias);

        return jsonObject;
    }

    /**
     * Convierte un JSONObject a un estudiante
     *
     * @param jsonObject
     * @return Estudiante
     */
    public static Estudiante JSONObjectAEstudiante(JSONObject jsonObject) {
        Estudiante estudiante = new Estudiante();

        estudiante.setNombre(jsonObject.getString("nombre"));
        estudiante.setApellido(jsonObject.getString("apellido"));
        estudiante.setDni(jsonObject.getString("dni"));
        estudiante.setLegajo(jsonObject.getString("legajo"));
        estudiante.setContrasenia(jsonObject.getString("contrasenia"));
        estudiante.setCodigoCarrera(jsonObject.getString("codigoCarrera"));
        estudiante.setCorreo(jsonObject.getString("correo"));
        estudiante.setActividad(jsonObject.getBoolean("actividad"));
        estudiante.setFechaDeAlta(LocalDate.parse(jsonObject.getString("fechaDeAlta")));

        if(!(jsonObject.getJSONArray("materias").length() == 0)){

        JSONArray materiasJsonArray = jsonObject.getJSONArray("materias");
        ArrayList<EstadoAlumnoMateria> materias = new ArrayList<>();

        for (int i = 0; i < materiasJsonArray.length(); i++) {

            JSONObject materiaJson = materiasJsonArray.getJSONObject(i);
            EstadoAlumnoMateria materia = new EstadoAlumnoMateria();

            materia.setCodigoMateria(materiaJson.getString("id"));
            materia.setEstado(EstadoMateria.valueOf(materiaJson.getString("estado").toUpperCase()));
            materia.setTomo(materiaJson.getString("tomo"));
            materia.setFolio(materiaJson.getString("folio"));
            materia.setCodigoComision(materiaJson.getString("codigoComision"));

            JSONArray notasJsonArray = materiaJson.getJSONArray("notas");
            Map<String, Integer> notas = new HashMap<>();
            for (int j = 0; j < notasJsonArray.length(); j++) {
                JSONObject notaJson = notasJsonArray.getJSONObject(j);
                for (String key : notaJson.keySet()) {
                    notas.put(key, notaJson.getInt(key));
                }
            }
            materia.setNotas((HashMap<String, Integer>) notas);

            JSONArray mesasExamenJsonArray = materiaJson.getJSONArray("mesasExamen");
            HashMap<String, EstadoAlumnoMesa> mesasExamen = new HashMap<>();

            for (int k = 0; k < mesasExamenJsonArray.length(); k++) {
                JSONObject mesaExamenJson = mesasExamenJsonArray.getJSONObject(k);
                String key = mesaExamenJson.keys().next();

                EstadoAlumnoMesa estadoMesa = new EstadoAlumnoMesa();
                estadoMesa.setCodigoMesa(mesaExamenJson.getString(key));
                estadoMesa.setNota(mesaExamenJson.getInt("nota"));
                estadoMesa.setPresente(mesaExamenJson.getBoolean("presente"));

                mesasExamen.put(key, estadoMesa);
            }
            materia.setMesasExamen(mesasExamen);

            materias.add(materia);
        }
        estudiante.setMaterias(materias);

        }


        return estudiante;
    }

    /**
     * Compara dos estudiantes
     *
     * @param that
     * @return true si son iguales
     */
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

    /**
     * Obtiene las materias que cursa un alumno
     *
     * @return Estudiante
     */
    public HashMap<String, String> obtenerMaterias() throws DatosIncorrectosException {
        try {
            HashMap<String, Materia> materiasCarrera = (manejoArchivosCarrera.retornarCarrera(pathCarreras, this.getCodigoCarrera())).getMaterias();
            HashMap<String, String> materiasEstudiante = new HashMap<>();

            for (int i = 0; i < this.getMaterias().size(); i++) {

                if (materiasCarrera.containsKey(this.getMaterias().get(i).getCodigoMateria())) {
                    materiasEstudiante.put(this.getMaterias().get(i).getCodigoMateria(), materiasCarrera.get(this.getMaterias().get(i).getCodigoMateria()).getNombre());
                }
            }

            return materiasEstudiante;
        } catch (CamposVaciosException e) {
            throw new DatosIncorrectosException("No se pudo obtener las materias de la carrera");
        }
    }

    /**
     * Obtiene un estudiante del archivo JSON
     *
     * @return HashMap<String, EstadoAlumnoMesa>
     */
    public HashMap<String, EstadoAlumnoMesa> obtenerMesasDeExamen() {
        HashMap<String, EstadoAlumnoMesa> mesasDeExamen = new HashMap<>();

        for (EstadoAlumnoMateria materia : this.getMaterias()) {
            if (materia.getMesasExamen() != null) {
                for (Map.Entry<String, EstadoAlumnoMesa> entry : materia.getMesasExamen().entrySet()) {
                    mesasDeExamen.put(entry.getKey(), entry.getValue());
                }
            }
        }

        return mesasDeExamen;
    }

    /**
     * Compara un JSONObject con un estudiante
     *
     * @param jsonObject
     * @param usuario
     * @return Estudiante
     */
    public boolean compararJSONObjectConEstudiante(Estudiante usuario,JSONObject jsonObject ) {

        boolean comparar = true;

        if (!jsonObject.getString("nombre").equals(usuario.getNombre())) {
            comparar = false;
        } else if (!jsonObject.getString("apellido").equals(usuario.getApellido())) {
            comparar = false;
        } else if (!jsonObject.getString("dni").equals(usuario.getDni())) {
            comparar = false;
        } else if (!jsonObject.getString("contrasenia").equals(usuario.getContrasenia())) {
            comparar = false;
        } else if (jsonObject.getBoolean("actividad") != usuario.getActividad()) {
            comparar = false;
        } else if (!jsonObject.getString("correo").equals(usuario.getCorreo())) {
            comparar = false;
        } else if (!jsonObject.getString("codigoCarrera").equals(usuario.getCodigoCarrera())) {
            comparar = false;
        } else if (!jsonObject.getString("legajo").equals(usuario.getLegajo())) {
            comparar = false;
        } else if (!jsonObject.getString("fechaDeAlta").equals(usuario.getFechaDeAlta().toString())) {
            comparar = false;
        } else {

            for(int i = 0 ; i<jsonObject.getJSONArray("materias").length() ; i++){

                JSONObject materiaJson = jsonObject.getJSONArray("materias").getJSONObject(i);

                if(!(materiaJson.length() == usuario.getMaterias().size())){
                    comparar = false;
                } else{

                    if(!materiaJson.getString("id").equals(usuario.getMaterias().get(i).getCodigoMateria())){
                        comparar = false;
                    } else if(!materiaJson.getString("tomo").equals(usuario.getMaterias().get(i).getTomo())){
                        comparar = false;
                    } else if(!materiaJson.getString("folio").equals(usuario.getMaterias().get(i).getFolio())){
                        comparar = false;
                    } else if(!materiaJson.getString("codigoComision").equals(usuario.getMaterias().get(i).getCodigoComision())){
                        comparar = false;
                    } else if(!materiaJson.getJSONArray("mesasExamen").toString().equals(usuario.getMaterias().get(i).getMesasExamen().toString())){
                        comparar = false;
                    }

                    JSONArray notasArray = materiaJson.getJSONArray("notas");
                    int primerParcial = 0;
                    int segundoParcial = 0;

                    for (int j = 0; j < notasArray.length(); j++) {
                        JSONObject nota = notasArray.getJSONObject(j);

                        if (nota.has("primerParcial")) {
                            primerParcial = nota.getInt("primerParcial");
                        } else if (nota.has("segundoParcial")) {
                            segundoParcial = nota.getInt("segundoParcial");
                        }
                    }

                    if(primerParcial != usuario.getMaterias().get(i).getNotas().get("primerParcial")){
                        comparar = false;
                    } else if(segundoParcial != usuario.getMaterias().get(i).getNotas().get("segundoParcial")){
                        comparar = false;
                    }

                }


            }

        }

        return comparar;
    }

    /**
     * Obtiene las materias que puede cursar un estudiantes tomando en cuenta las correlatividades
     * para cursar y las correlativas que necesita tener aprobadas si o si
     * @return HashMap<String, Materia>
     */
    public HashMap<String, Materia> obtenerMateriasParaCursar() throws CamposVaciosException, DatosIncorrectosException {

        // Obtiene todas las materias de la carrera del estudiante
        HashMap<String, Materia> materias = manejoArchivosCarrera.retornarCarrera(pathCarreras, Data.getEstudiante().getCodigoCarrera()).getMaterias();

        // Recopila los códigos de materias que el estudiante ya ha cursado (cursadas, regularizadas, aprobadas)
        HashSet<String> materiasEstudiante = Data.getEstudiante().getMaterias().stream()
                .map(EstadoAlumnoMateria::getCodigoMateria)
                .collect(Collectors.toCollection(HashSet::new));

        // Obtiene las materias aprobadas y regularizadas por el estudiante
        HashSet<String> materiasAprobadas = obtenerMateriasSegunEstado(EstadoMateria.APROBADA);
        HashSet<String> materiasRegularizadas = obtenerMateriasSegunEstado(EstadoMateria.REGULARIZADA);

        // Remueve materias aprobadas, cursadas y regularizadas del conjunto de materias
        materiasAprobadas.forEach(materias::remove);
        materiasRegularizadas.forEach(materias::remove); // También eliminamos materias regularizadas

        // Itera sobre las materias restantes y verifica las correlativas
        Iterator<Map.Entry<String, Materia>> iterator = materias.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Materia> entry = iterator.next();
            HashSet<String> correlativasCursar = entry.getValue().getCodigoCorrelativasCursado();

            // Verifica si todas las correlativas están aprobadas o regularizadas
            boolean todasCorrelativasCumplidas = correlativasCursar.stream().allMatch(correlativa -> {
                // Verificar si la correlativa está regularizada o aprobada
                return materiasRegularizadas.contains(correlativa) || materiasAprobadas.contains(correlativa);
            });

            // Si alguna correlativa no está ni regularizada ni aprobada, remueve la materia del mapa
            if (!todasCorrelativasCumplidas) {
                iterator.remove();
            }
        }

        for(int i = 0 ; i < Data.getEstudiante().getMaterias().size() ; i++){

            if(Data.getEstudiante().getMaterias().get(i).getEstado().equals(EstadoMateria.NO_REGULARIZADA)){
                materias.remove(Data.getEstudiante().getMaterias().get(i).getCodigoMateria());
            }

        }

        return materias;
    }




    /**
     * Obtiene las materias que aprobo el estudiante
     *
     * @return HashMap<String>
     */
    public HashSet<String> obtenerMateriasSegunEstado(EstadoMateria estadoMateria) {
        HashSet<String> materiasAprobadas = new HashSet<>();
        for (EstadoAlumnoMateria materia : this.getMaterias()) {
            if (materia.getEstado().equals(estadoMateria)) {
                materiasAprobadas.add(materia.getCodigoMateria());
            }
        }
        return materiasAprobadas;
    }

    /**
     * Inscribe a un estudiante en una materia de una comision
     * @param comision
     * @return Estudiante
     */
    public Estudiante inscribirse(Comision comision){

        EstadoAlumnoMateria materia = new EstadoAlumnoMateria();

        materia.setCodigoMateria(comision.getCodigoMateria());
        materia.setCodigoComision(comision.getId());
        materia.setEstado(EstadoMateria.NO_REGULARIZADA);
        materia.setNotas(new HashMap<>());
        materia.setMesasExamen(new HashMap<String, EstadoAlumnoMesa>());

        this.materias.add(materia);

        return this;
    }

    public Map<String, List<Integer>> obtenerParcialesRendidos() {
        Map<String, List<Integer>> parcialesRendidos = new HashMap<>();

        for (EstadoAlumnoMateria materia : this.getMaterias()) {
            List<Integer> notasParciales = new ArrayList<>();
            Map<String, Integer> notas = materia.getNotas();

            if (notas.containsKey("primerParcial")) {
                notasParciales.add(notas.get("primerParcial"));
            }
            if (notas.containsKey("segundoParcial")) {
                notasParciales.add(notas.get("segundoParcial"));
            }

            if (!notasParciales.isEmpty()) {
                parcialesRendidos.put(materia.getCodigoMateria(), notasParciales);
            }
        }

        return parcialesRendidos;
    }

    public HashSet<MesaExamen> obtenerMesasExamenesParaAnotarse(HashSet<String> materiasAprobadas, ArrayList<MesaExamen> mesasExamen) {
        HashSet<MesaExamen> mesasExamenFiltradas = new HashSet<>();

        // Primer bucle: filtrar según materias aprobadas, regularizadas y otras condiciones
        for (MesaExamen mesaExamen : mesasExamen) {
            // Si la materia no está aprobada y cumple con las otras condiciones
            if (!(materiasAprobadas.contains(mesaExamen.getCodigoMateria()))) {
                if (mesaExamen.isActividad() && mesaExamen.isApertura() && mesaExamen.getCupos() > 0) {

                    // Verificar si la materia está regularizada
                    boolean materiaRegularizada = false;
                    for (EstadoAlumnoMateria materia : Data.getEstudiante().getMaterias()) {
                        if (materia.getCodigoMateria().equals(mesaExamen.getCodigoMateria())) {
                            // Si la materia está regularizada o tiene una nota aprobatoria
                            if (materia.getEstado().equals(EstadoMateria.REGULARIZADA)) {
                                materiaRegularizada = true;
                                break;
                            }
                        }
                    }

                    // Si la materia está regularizada te va permitir la inscripción
                    if (materiaRegularizada) {
                        mesasExamenFiltradas.add(mesaExamen);
                    }
                }
            }
        }

        // Segundo bucle: filtrar según EstadoMateria.NO_REGULARIZADA (eliminamos las mesas con materias no regularizadas)
        HashSet<MesaExamen> aEliminar = new HashSet<>();

        for (MesaExamen mesaExamen : mesasExamenFiltradas) {
            // Iterar a través de todas las materias del estudiante
            for (EstadoAlumnoMateria materia : Data.getEstudiante().getMaterias()) {
                if (materia.getCodigoMateria().equals(mesaExamen.getCodigoMateria())) {
                    if (materia.getEstado().equals(EstadoMateria.NO_REGULARIZADA)) {
                        // Si la materia está NO_REGULARIZADA, marcar la mesaExamen para eliminar
                        aEliminar.add(mesaExamen);
                    }
                }
            }
        }

        // Eliminar las mesasExamen que deben ser filtradas
        mesasExamenFiltradas.removeAll(aEliminar);

        return mesasExamenFiltradas;
    }
}


