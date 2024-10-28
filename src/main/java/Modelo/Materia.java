package Modelo;

import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosCarrera;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import Excepciones.excepcionPersonalizada;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;

/**
 * La clase materia define una materia. La materia tiene un id que corresponde al id legal, un nombre, el año de la carrera en el que se cursa,
 * el cuatrimestre en el que se cursa, si se cursa, y si se rinde. A su vez guarda el codigo de las correlativas que se necesitan para cursarla
 * y el codigo de las correlativas que se necesitan para rendir el examen final.
 */
public final class Materia implements iCRUD{

    //Atributos

    private String id;
    private String nombre;
    private String anio;
    private String cuatrimestre;
    private boolean seCursa;
    private boolean seRinde;
    private HashSet<String> codigoCorrelativasCursado;
    private HashSet<String> codigoCorrelativasRendir;
    private boolean actividad;

    //Constructores
    public Materia(String id, String nombre, String anio, String cuatrimestre, boolean seCursa, boolean seRinde, HashSet<String> codigoCorrelativasCursado, HashSet<String> codigoCorrelativasRendir, boolean actividad) {
        this.id = id;
        this.nombre = nombre;
        this.anio = anio;
        this.cuatrimestre = cuatrimestre;
        this.seCursa = seCursa;
        this.seRinde = seRinde;
        this.codigoCorrelativasCursado = codigoCorrelativasCursado;
        this.codigoCorrelativasRendir = codigoCorrelativasRendir;
        this.actividad = actividad;
    }

    public Materia(String id, String nombre, String anio, String cuatrimestre, boolean seCursa, boolean seRinde, HashSet<String> codigoCorrelativasCursado, HashSet<String> codigoCorrelativasRendir) {
        this.id = id;
        this.nombre = nombre;
        this.anio = anio;
        this.cuatrimestre = cuatrimestre;
        this.seCursa = seCursa;
        this.seRinde = seRinde;
        this.codigoCorrelativasCursado = codigoCorrelativasCursado;
        this.codigoCorrelativasRendir = codigoCorrelativasRendir;
        this.actividad = true;
    }


    public Materia() {
        id = "";
        nombre ="";
        anio = "";
        cuatrimestre = "";
        seCursa = false;
        seRinde = false;
        actividad = false;
    }


    //Getters

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAnio() {
        return anio;
    }

    public String getCuatrimestre() {
        return cuatrimestre;
    }

    public boolean isSeCursa() {
        return seCursa;
    }

    public boolean isSeRinde() {
        return seRinde;
    }

    public HashSet<String> getCodigoCorrelativasCursado() {
        return codigoCorrelativasCursado;
    }

    public HashSet<String> getCodigoCorrelativasRendir() {
        return codigoCorrelativasRendir;
    }

    public boolean isActividad() {
        return actividad;
    }

    //Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public void setCuatrimestre(String cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public void setSeCursa(boolean seCursa) {
        this.seCursa = seCursa;
    }

    public void setSeRinde(boolean seRinde) {
        this.seRinde = seRinde;
    }

    public void setCodigoCorrelativasCursado(HashSet<String> codigoCorrelativasCursado) {
        this.codigoCorrelativasCursado = codigoCorrelativasCursado;
    }

    public void setCodigoCorrelativasRendir(HashSet<String> codigoCorrelativasRendir) {
        this.codigoCorrelativasRendir = codigoCorrelativasRendir;
    }

    public void setActividad(boolean actividad) {
        this.actividad = actividad;
    }

    //Metodos

    @Override
    public String toString() {
        return "Materia{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", anio='" + anio + '\'' +
                ", cuatrimestre='" + cuatrimestre + '\'' +
                ", seCursa=" + seCursa +
                ", seRinde=" + seRinde +
                ", codigoCorrelativasCursado=" + codigoCorrelativasCursado +
                ", codigoCorrelativasRendir=" + codigoCorrelativasRendir +
                '}';
    }

    @Override
    public boolean crear(String path) throws EntidadYaExistente, CamposVaciosException, DatosIncorrectosException {

        if(this.getAnio().matches("[1-9]") && this.getCuatrimestre().matches("[1-2]"))
        {
            if(!this.getId().isEmpty() && !this.getNombre().isEmpty())
            {

                try{
                    if(manejoArchivosCarrera.agregarMateria(path,this, Data.getCarrera().getId())){
                        excepcionPersonalizada.alertaConfirmacion("Materia cargada en la carrera exitosamente");
                    }


                }catch (EntidadYaExistente e)
                {
                    e.getMessage();
                }

            }else {
                throw new CamposVaciosException("Intentaste ingresar campos vacios. Volve a intentarlo");
            }

        }else{
            throw new DatosIncorrectosException("El año y/o cuatrimestre ingresados son incorrectos. Vuelva a intentarlo");
        }


        return false;
    }

    @Override
    public boolean actualizar(String path, JSONObject jsonObject) {
        return false;
    }

    @Override
    public boolean leer(String path, String legajo) {
        return false;
    }

    @Override
    public boolean borrar(String path) {
        return false;
    }

    public static Materia JSONObjectAMateria(JSONObject jsonObject) {

        String id = jsonObject.getString("id");
        String nombre = jsonObject.getString("nombre");
        String anio = jsonObject.getString("anio");
        String cuatrimestre = jsonObject.getString("cuatrimestre");
        boolean seCursa = jsonObject.getBoolean("seCursa");
        boolean seRinde = jsonObject.getBoolean("seRinde");
        boolean actividad = jsonObject.getBoolean("actividad");


        HashSet<String> codigoCorrelativasCursado = new HashSet<>();
        JSONArray cursadoArray = jsonObject.getJSONArray("codigoCorrelativasCursado");
        for (int i = 0; i < cursadoArray.length(); i++) {
            codigoCorrelativasCursado.add(cursadoArray.getString(i));
        }

        HashSet<String> codigoCorrelativasRendir = new HashSet<>();
        JSONArray rendirArray = jsonObject.getJSONArray("codigoCorrelativasRendir");
        for (int i = 0; i < rendirArray.length(); i++) {
            codigoCorrelativasRendir.add(rendirArray.getString(i));
        }

        // Creamos y devolvemos una instancia de Materia
        return new Materia(id, nombre, anio, cuatrimestre, seCursa, seRinde, codigoCorrelativasCursado, codigoCorrelativasRendir, actividad);
    }

    public JSONObject materiaAJSONObject() {
        JSONObject jsonObject = new JSONObject();

        // Agregamos cada atributo de Materia al JSONObject
        jsonObject.put("id", id);
        jsonObject.put("nombre", nombre);
        jsonObject.put("anio", anio);
        jsonObject.put("cuatrimestre", cuatrimestre);
        jsonObject.put("seCursa", seCursa);
        jsonObject.put("seRinde", seRinde);
        jsonObject.put("actividad", actividad);

        // Convertimos los HashSet de correlativas a JSONArray y los agregamos al JSONObject
        jsonObject.put("codigoCorrelativasCursado", new JSONArray(codigoCorrelativasCursado));
        jsonObject.put("codigoCorrelativasRendir", new JSONArray(codigoCorrelativasRendir));

        return jsonObject;
    }


}
