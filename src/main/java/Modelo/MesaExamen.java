package Modelo;

import ControlArchivos.manejoArchivos;
import ControlArchivos.manejoArchivosMesaExamen;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;

import static ControlArchivos.manejoArchivos.leerArchivoJSON;

public final class MesaExamen implements iCRUD{

    private String id;
    private Turno turno;
    private String codigoCarrera;
    private String codigoMateria;
    private String codigoPresidente;
    private HashSet<String> vocales;
    private LocalDate fecha;
    private LocalTime hora;
    private int cupos;
    private String aula;
    private boolean apertura;
    private boolean actividad;
    private HashSet<EstadoAlumnoMesa> alumnosInscriptos;

    public MesaExamen(String id, Turno turno, String codigoCarrera, String codigoMateria, String codigoPresidente, HashSet<String> vocales, LocalDate fecha, LocalTime hora, int cupos, String aula, boolean apertura, boolean actividad, HashSet<EstadoAlumnoMesa> alumnosInscriptos) {
        this.id = id;
        this.turno = turno;
        this.codigoCarrera = codigoCarrera;
        this.codigoMateria = codigoMateria;
        this.codigoPresidente = codigoPresidente;
        this.vocales = vocales;
        this.fecha = fecha;
        this.hora = hora;
        this.cupos = cupos;
        this.aula = aula;
        this.apertura = apertura;
        this.actividad = actividad;
        this.alumnosInscriptos = alumnosInscriptos;
    }

    public MesaExamen() {
        turno = null;
        codigoCarrera = "";
        codigoPresidente = "";
        codigoMateria = "";
        cupos = 0;
        aula = "";
        actividad = false;
    }

public MesaExamen(MesaExamen mesa) {
    this.id = mesa.id;
    this.turno = mesa.turno;
    this.codigoCarrera = mesa.codigoCarrera;
    this.codigoMateria = mesa.codigoMateria;
    this.codigoPresidente = mesa.codigoPresidente;
    this.vocales = new HashSet<>(mesa.vocales);
    this.fecha = mesa.fecha;
    this.hora = mesa.hora;
    this.cupos = mesa.cupos;
    this.aula = mesa.aula;
    this.apertura = mesa.apertura;
    this.actividad = mesa.actividad;
    this.alumnosInscriptos = new HashSet<>(mesa.alumnosInscriptos);
}
    //Getters

    public String getId() {
        return id;
    }

    public Turno getTurno() {
        return turno;
    }

    public String getCodigoCarrera() {
        return codigoCarrera;
    }

    public String getCodigoMateria() {
        return codigoMateria;
    }

    public String getCodigoPresidente() {
        return codigoPresidente;
    }

    public HashSet<String> getVocales() {
        return vocales;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public int getCupos() {
        return cupos;
    }

    public String getAula() {
        return aula;
    }

    public boolean isActividad() {
        return actividad;
    }

    public boolean isApertura() {
        return apertura;
    }

    public HashSet<EstadoAlumnoMesa> getAlumnosInscriptos() { return alumnosInscriptos; }

    //Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public void setCodigoCarrera(String codigoCarrera) {
        this.codigoCarrera = codigoCarrera;
    }

    public void setCodigoMateria(String codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public void setCodigoPresidente(String codigoPresidente) {
        this.codigoPresidente = codigoPresidente;
    }

    public void setVocales(HashSet<String> vocales) {
        this.vocales = vocales;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public void setActividad(boolean actividad) {
        this.actividad = actividad;
    }

    public void setApertura(boolean apertura) {
        this.apertura = apertura;
    }

    public void setAlumnosInscriptos(HashSet<EstadoAlumnoMesa> alumnosInscriptos) { this.alumnosInscriptos = alumnosInscriptos; }

    // Conversion to JSON
    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("turno", turno.toString());
        json.put("codigoCarrera", codigoCarrera);
        json.put("codigoMateria", codigoMateria);
        json.put("codigoPresidente", codigoPresidente);
        json.put("vocales", new JSONArray(vocales));
        json.put("fecha", fecha.toString());
        json.put("hora", hora.toString());
        json.put("cupos", cupos);
        json.put("aula", aula);
        json.put("apertura", apertura);
        json.put("actividad", actividad);

        JSONArray alumnosArray = new JSONArray();
        for (EstadoAlumnoMesa alumno : alumnosInscriptos) {
            alumnosArray.put(alumno.EstadoAlumnoMesaAJSONObjetc());
        }
        json.put("alumnosInscriptos", alumnosArray);

        return json;
    }

    public static MesaExamen fromJSONObject(JSONObject json) {
        MesaExamen instancia = new MesaExamen();
        instancia.id = json.getString("id");
        instancia.turno = Turno.valueOf(json.getString("turno"));
        instancia.codigoCarrera = json.getString("codigoCarrera");
        instancia.codigoMateria = json.getString("codigoMateria");
        instancia.codigoPresidente = json.getString("codigoPresidente");

        JSONArray vocalesArray = json.getJSONArray("vocales");
        instancia.vocales = new HashSet<>();
        for (int i = 0; i < vocalesArray.length(); i++) {
            instancia.vocales.add(vocalesArray.getString(i));
        }

        instancia.fecha = LocalDate.parse(json.getString("fecha"));
        instancia.hora = LocalTime.parse(json.getString("hora"));
        instancia.cupos = json.getInt("cupos");
        instancia.aula = json.getString("aula");
        instancia.apertura = json.getBoolean("apertura");
        instancia.actividad = json.getBoolean("actividad");

        // Check if "alumnosInscriptos" exists before accessing it
        if (json.has("alumnosInscriptos")) {
            JSONArray alumnosArray = json.getJSONArray("alumnosInscriptos");
            instancia.alumnosInscriptos = new HashSet<>();
            for (int i = 0; i < alumnosArray.length(); i++) {
                JSONObject alumnoJson = alumnosArray.getJSONObject(i);
                EstadoAlumnoMesa alumno = EstadoAlumnoMesa.JSONObjectAEstadoAlumnoMesa(alumnoJson);
                instancia.alumnosInscriptos.add(alumno);
            }
        } else {
            // Handle case where "alumnosInscriptos" is missing
            instancia.alumnosInscriptos = new HashSet<>();
        }

        return instancia;
    }


    public static String generarIDMesaExamen(String codigoCarrera, String codigoMateria, String fileName) {
        JSONArray mesaExamen = new JSONArray(leerArchivoJSON(fileName));
        String ultimoCodigo = null;
        int ultNum = 0;

        for (int i = 0; i < mesaExamen.length(); i++) {
            JSONObject jsonObject = mesaExamen.getJSONObject(i);

            if (jsonObject.getString("codigoCarrera").equals(codigoCarrera) &&
                    jsonObject.getString("codigoMateria").equals(codigoMateria)) {

                int currentNum = Integer.parseInt(jsonObject.getString("id").substring(0, 3));

                if (currentNum > ultNum) {
                    ultimoCodigo = jsonObject.getString("id");
                    ultNum = currentNum;
                }
            }
        }

        String nuevoID;

        if (ultimoCodigo != null) {

            String auxiliar = ultimoCodigo.substring(0, 3);
            int num = Integer.parseInt(auxiliar);
            num++;
            nuevoID = String.format("%03d", num) + "-" + codigoMateria;
        } else {
            nuevoID = "001" + "-" + codigoMateria;
        }

        return nuevoID;
    }

    @Override
    public boolean crear(String path) throws EntidadYaExistente, CamposVaciosException, DatosIncorrectosException {

        if(manejoArchivos.esFormatoFechaValida(this.getFecha().toString()) && manejoArchivos.esFormatoHoraValida(this.getHora().toString())&& manejoArchivos.esFechaValidaEnRango(fecha) && manejoArchivos.esHoraValidaEnRango(hora))
        {
            if(codigoMateria == null || vocales == null || codigoPresidente == null || aula == null)
            {
                throw new CamposVaciosException("Debes completar todos los campos.");
            }

            if(!codigoMateria.isEmpty() && !vocales.isEmpty() && !codigoPresidente.isEmpty() && !aula.isEmpty())
            {
                if(manejoArchivosMesaExamen.cargarMesaExamenAJSON(path,this.toJSONObject()))
                {
                    return true;
                }

            }else {
                throw new CamposVaciosException("Debes completar todos los campos.");
            }
        }else {
            throw new DatosIncorrectosException("Ingresaste campos de fecha/hora en formato incorrecto. Verifica que estén en su formato correcto. Hora: formato 24hs \n Fecha: aaaa/mm/dd con fecha actual hasta el último dia del próximo año");
        }
        return false;
    }

    @Override
    public boolean actualizar(String path, JSONObject jsonObject) throws CamposVaciosException, DatosIncorrectosException {
        if(manejoArchivos.esFormatoFechaValida(this.getFecha().toString()) && manejoArchivos.esFormatoHoraValida(this.getHora().toString())&& manejoArchivos.esFechaValidaEnRango(fecha) && manejoArchivos.esHoraValidaEnRango(hora))
        {
            if(!codigoMateria.isEmpty() && !vocales.isEmpty() && !codigoPresidente.isEmpty() && !aula.isEmpty())
            {
                try{
                    if(manejoArchivosMesaExamen.actualizarMesaExamenAJSON(path,this.toJSONObject())){
                        return true;
                    }
                }catch (EntidadYaExistente e)
                {
                    e.getMessage();
                }

            }else {
                throw new CamposVaciosException("Debes completar todos los campos.");
            }
        }else {
            throw new DatosIncorrectosException("Ingresaste campos de fecha/hora en formato incorrecto. Verifica que estén en su formato correcto. Hora: formato 24hs \n Fecha: aaaa/mm/dd con fecha actual hasta el último dia del próximo año");
        }
        return false;
    }

    @Override
    public boolean leer(String path, String id) {
        return false;
    }

    @Override
    public boolean borrar(String path) {
        return false;
    }
}
