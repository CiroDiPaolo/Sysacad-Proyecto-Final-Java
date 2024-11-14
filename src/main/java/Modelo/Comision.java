package Modelo;

import Control.InicioSesion.Data;
import Excepciones.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;

import static ControlArchivos.manejoArchivos.leerArchivoJSON;
import static ControlArchivos.manejoArchivosComisiones.*;
import static Path.Path.pathComisiones;

/**
 * La clase Comision esta diseñada para identificar cada comision segun la carrera, el año, la materia, el nombre y el turno.
 * A traves de ella se le puede poner cupos y saber en qué aula y que profesor está asignado.
 */
public final class Comision implements iCRUD{

    //Atributos

    private String id;
    private Turno turno;
    private String nombre;
    private String codigoMateria;
    private String codigoCarrera;
    private String codigoProfesor;
    private String descripcion;
    private int anio;
    private String aula;
    private int cupos;
    private boolean apertura;
    private boolean actividad;
    private HashSet<EstadoAlumnoComision> estadoAlumnoComisionHashSet;

    //Constructores


    public Comision(String id, Turno turno, String nombre, String codigoMateria, String codigoCarrera, String codigoProfesor, String descripcion, int anio, String aula, int cupos, boolean apertura, boolean actividad, HashSet<EstadoAlumnoComision> estadoAlumnoComisionHashSet) {
        this.id = id;
        this.turno = turno;
        this.nombre = nombre;
        this.codigoMateria = codigoMateria;
        this.codigoCarrera = codigoCarrera;
        this.codigoProfesor = codigoProfesor;
        this.descripcion = descripcion;
        this.anio = anio;
        this.aula = aula;
        this.cupos = cupos;
        this.apertura = apertura;
        this.actividad = actividad;
        this.estadoAlumnoComisionHashSet = estadoAlumnoComisionHashSet;
    }

    public Comision() {
        turno = null;
        nombre = "";
        codigoMateria = "";
        codigoCarrera = "";
        codigoProfesor = "";
        descripcion = "";
        anio = 0;
        aula = "";
        cupos = 0;
        apertura = false;
        actividad = false;
    }

    //Getters

    public String getId() {
        return id;
    }

    public Turno getTurno() { return turno; }

    public String getNombre() { return nombre; }

    public String getCodigoMateria() {
        return codigoMateria;
    }

    public String getCodigoCarrera() {
        return codigoCarrera;
    }

    public String getCodigoProfesor() {
        return codigoProfesor;
    }

    public int getAnio() {
        return anio;
    }

    public String getAula() {
        return aula;
    }

    public int getCupos() {
        return cupos;
    }

    public boolean isActividad() {
        return actividad;
    }

    public HashSet<EstadoAlumnoComision> getEstadoAlumnoComisionHashSet() { return estadoAlumnoComisionHashSet; }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean getApertura() {
        return apertura;
    }

    //Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigoMateria(String codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public void setCodigoCarrera(String codigoCarrera) {
        this.codigoCarrera = codigoCarrera;
    }

    public void setCodigoProfesor(String codigoProfesor) {
        this.codigoProfesor = codigoProfesor;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    public void setActividad(boolean actividad) {
        this.actividad = actividad;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEstadoAlumnoComisionHashSet(HashSet<EstadoAlumnoComision> estadoAlumnoComisionHashSet) {
        this.estadoAlumnoComisionHashSet = estadoAlumnoComisionHashSet;
    }

    public void setApertura(boolean apertura) {
        this.apertura = apertura;
    }

    //Metodos


    @Override
    public String toString() {
        return "Comision{" +
                "id=" + id +
                ", turno=" + turno +
                ", nombre='" + nombre + '\'' +
                ", codigoMateria='" + codigoMateria + '\'' +
                ", codigoCarrera='" + codigoCarrera + '\'' +
                ", codigoProfesor='" + codigoProfesor + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", anio=" + anio +
                ", aula='" + aula + '\'' +
                ", cupos=" + cupos +
                ", apertura=" + apertura +
                ", actividad=" + actividad +
                ", estadoAlumnoComisionHashSet=" + estadoAlumnoComisionHashSet +
                '}';
    }

    public JSONObject ComisionAJSONObject() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", id);
        jsonObject.put("turno", turno.toString());
        jsonObject.put("nombre", nombre);
        jsonObject.put("codigoMateria", codigoMateria);
        jsonObject.put("codigoCarrera", codigoCarrera);
        jsonObject.put("codigoProfesor", codigoProfesor);
        jsonObject.put("descripcion", descripcion);
        jsonObject.put("anio", anio);
        jsonObject.put("aula", aula);
        jsonObject.put("cupos", cupos);
        jsonObject.put("apertura", apertura);
        jsonObject.put("actividad", actividad);

        JSONArray estadoArray = new JSONArray();
        for (EstadoAlumnoComision estado : estadoAlumnoComisionHashSet) {
            estadoArray.put(estado.AlumnoComisionAJSONObject());
        }
        jsonObject.put("estadoAlumnoComision", estadoArray);

        return jsonObject;
    }

    public static Comision JSONObjectAComision(JSONObject jsonObject) {
        Comision comision = new Comision();

        comision.id = jsonObject.getString("id");
        comision.turno = Turno.valueOf(jsonObject.getString("turno"));
        comision.nombre = jsonObject.getString("nombre");
        comision.codigoMateria = jsonObject.getString("codigoMateria");
        comision.codigoCarrera = jsonObject.getString("codigoCarrera");
        comision.codigoProfesor = jsonObject.getString("codigoProfesor");
        comision.descripcion = jsonObject.getString("descripcion");
        comision.anio = jsonObject.getInt("anio");
        comision.aula = jsonObject.getString("aula");
        comision.cupos = jsonObject.getInt("cupos");
        comision.apertura = jsonObject.getBoolean("apertura");
        comision.actividad = jsonObject.getBoolean("actividad");

        comision.estadoAlumnoComisionHashSet = new HashSet<>();
        JSONArray estadoArray = jsonObject.getJSONArray("estadoAlumnoComision");
        for (int i = 0; i < estadoArray.length(); i++) {
            JSONObject estadoJson = estadoArray.getJSONObject(i);
            EstadoAlumnoComision estado = EstadoAlumnoComision.JSONObjectAEstadoAlumnoComision(estadoJson);
            comision.estadoAlumnoComisionHashSet.add(estado);
        }

        return comision;
    }


    @Override
    public boolean crear(String path) throws EntidadYaExistente, CamposVaciosException, DatosIncorrectosException {
        if(this.getDescripcion().length() < 200) {
            if(!this.getNombre().isEmpty() && !this.getAula().isEmpty() && this.anio > 0 && this.codigoProfesor != null && this.codigoCarrera != null && this.codigoMateria != null)
            {
                if(cargarComisionAJSON(pathComisiones+generarNombreArchivoComision(this.getCodigoCarrera(), this.getAnio()), this.ComisionAJSONObject())){

                    return true;
                }
            }else{
                throw new CamposVaciosException("Dejaste campos vacios. Volve a intentar.");
            }
        } else{
            throw new DatosIncorrectosException("La descripcion excede el limite de caracteres. (Limite: 200).");
        }
        return false;
    }

    @Override
    public boolean actualizar(String path, JSONObject jsonObject) throws CamposVaciosException, DatosIncorrectosException {
        if(this.getDescripcion().length() < 200) {
            if(!this.getNombre().isEmpty() && !this.getAula().isEmpty() && this.anio > 0 && this.codigoProfesor != null && this.codigoCarrera != null && this.codigoMateria != null)
            {
                if(actualizarComisionAJSON(pathComisiones+generarNombreArchivoComision(this.getCodigoCarrera(), this.getAnio()), this.ComisionAJSONObject())){
                    excepcionPersonalizada.alertaConfirmacion("¡Comision actualizada exitosamente!");
                    return true;
                }
            }else{
                throw new CamposVaciosException("Dejaste campos vacios. Volve a intentar.");
            }
        } else{
            throw new DatosIncorrectosException("La descripcion excede el limite de caracteres. (Limite: 200).");
        }
        return false;
    }

    public static String generarIDComision(String codigoCarrera, String codigoMateria, String fileName) {
        JSONArray comisiones = new JSONArray(leerArchivoJSON(fileName));
        String ultimoCodigo = null;
        int ultNum = 0;

        for (int i = 0; i < comisiones.length(); i++) {
            JSONObject jsonObject = comisiones.getJSONObject(i);

            if (jsonObject.getString("codigoMateria").equals(codigoMateria)) {

                String id = jsonObject.getString("id");
                int currentNum = Integer.parseInt(id.substring(0, 3));
                if (currentNum > ultNum) {
                    ultimoCodigo = id;
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

        Data.setAux3(nuevoID);

        return nuevoID;
    }
}



