package Usuarios;

/**
 * La clase Profesor no contiene atributos pero es para diferenciar sus funciones de los otros tipos de usuario.
 * Tiene los mismos atributos que un Usuario.
 */
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
