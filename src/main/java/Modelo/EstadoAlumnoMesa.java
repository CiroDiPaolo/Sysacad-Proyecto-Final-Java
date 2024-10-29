package Modelo;

import org.json.JSONObject;

/**
 * La clase EstadoMesa sirve para llevar un seguimiento de la mesa que rindió el alumno.
 * Tiene un codigo de la mesa en la que rindió, una nota y un boolean que sirve para saber si se presentó o no.
 */
public final class EstadoAlumnoMesa {

    private String codigoMesa;
    private int nota;
    private boolean presente;

    public EstadoAlumnoMesa(String codigoMesa, int nota, boolean presente) {
        this.codigoMesa = codigoMesa;
        this.nota = nota;
        this.presente = presente;
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

    public static EstadoAlumnoMesa JSONObjectAEstadoAlumnoMesa(JSONObject jsonObject) {
        return new EstadoAlumnoMesa(
                (jsonObject.getString("key")),
                (jsonObject.getInt("value")),
                (jsonObject.getBoolean("presente"))
        );
    }
}
