package Modelo;

import org.json.JSONObject;

/**
 * La clase EstadoMesa sirve para llevar un seguimiento de la mesa que rindió el alumno.
 * Tiene un codigo de la mesa en la que rindió, una nota y un boolean que sirve para saber si se presentó o no.
 */
public final class EstadoAlumnoMesa {

    //ATRIBUTOS

    private String codigoMesa;
    private int nota;
    private boolean presente;

    //CONSTRUCTORES

    public EstadoAlumnoMesa(String codigoMesa, int nota, boolean presente) {
        this.codigoMesa = codigoMesa;
        this.nota = nota;
        this.presente = presente;
    }

    public EstadoAlumnoMesa(EstadoAlumnoMesa original) {
        this.codigoMesa = original.codigoMesa;
        this.nota = original.nota;
        this.presente = original.presente;
    }

    public EstadoAlumnoMesa() {
        codigoMesa = "";
        nota = 0;
        presente = false;
    }

    //Getters

    public String getCodigoMesa() {
        return codigoMesa;
    }

    public int getNota() {
        return nota;
    }

    public boolean isPresente() {
        return presente;
    }

    //Setters

    public void setCodigoMesa(String codigoMesa) {
        this.codigoMesa = codigoMesa;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    /**
     * Convierte un JSONObject a una instancia de EstadoAlumnoMesa.
     * @param jsonObject El JSONObject que contiene los datos del EstadoAlumnoMesa.
     * @return Una nueva instancia de EstadoAlumnoMesa con los datos del JSONObject.
     */
    public static EstadoAlumnoMesa JSONObjectAEstadoAlumnoMesa(JSONObject jsonObject) {
        return new EstadoAlumnoMesa(
                jsonObject.getString("codigoAlumno"),
                jsonObject.getInt("nota"),
                jsonObject.getBoolean("estado")
        );
    }

    /**
     * Convierte la instancia de EstadoAlumnoMesa a JSONObject para almacenamiento.
     * @return El JSONObject con los datos del EstadoAlumnoMesa.
     */
    public JSONObject EstadoAlumnoMesaAJSONObjetc() {
        JSONObject json = new JSONObject();
        json.put("codigoAlumno", this.codigoMesa);
        json.put("estado", this.isPresente());
        json.put("nota", nota);

        return json;
    }

}
