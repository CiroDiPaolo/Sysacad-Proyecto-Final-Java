package Usuarios;

public final class Administrador extends Usuario {

    public Administrador(String name, String apellido, String dni, String legajo, String contrasenia) {
        super(name, apellido, dni, legajo, contrasenia);
    }

    public Administrador() {
    }

    @Override
    public String toString() {
        return "| Administrador: " + getName();
    }
}
