package Usuarios;

public final class Profesor extends Usuario {

    public Profesor(String name, String dni, String legajo,String contrasenia) {
        super(name, dni, legajo,contrasenia, contrasenia);
    }

    public Profesor() {}

    @Override
    public String toString() {
        return "| Profesor: " + getName();
    }
}
