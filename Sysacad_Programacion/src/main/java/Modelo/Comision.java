package Modelo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class Comision {

    //Atributos

    private String id;
    private Turno turno;
    private HashSet<Materia> materias;

    //Constructores

    public Comision(String id, Turno turno) {

        this.id = id;
        this.turno = turno;
        this.materias = new HashSet<>();

    }

    public Comision() {
    }

    //Getters

    public Turno getTurno() {
        return turno;
    }

    public String getId() {
        return id;
    }

    //Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    //metodos

    public String agregarMateria(Materia m){

        String mensaje = "";

        if(!materias.contains(m)){

            materias.add(m);

            mensaje = "La materia " + m.getNombre() + "se añadio con exito";

        }else{

            mensaje = "La materia ya esta cargada";

        }

        return mensaje;

    }

    @Override
    public String toString() {
        return "Comision " + id + " Turno " + turno + "\n" + materias + "\n";
    }
}
