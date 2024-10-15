package Modelo;

public final class Profesor extends Persona{

    private String contrasenia;

    public Profesor(String name, String dni, String contrasenia) {
        super(name, dni);
        this.contrasenia = contrasenia;
    }

    public Profesor() {}
    
    //GETTERS

    public String getContrasenia() { return contrasenia; }

    //SETTERS

    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }

    @Override
    public String toString() {
        return "| Profesor: " + getName();
    }
}
