package Modelo;

public class EstadoMesa {
    private String codigoMesa;
    private int nota;
    private boolean presente;

    public EstadoMesa(String codigoMesa, int nota, boolean presente) {
        this.codigoMesa = codigoMesa;
        this.nota = nota;
        this.presente = presente;
    }

    public EstadoMesa() {
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
}
