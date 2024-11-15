package Usuarios;

/**
 * La clase Administrador no contiene atributos pero sirve para diferenciar sus funcionalidades de otros tipos de Usuario.
 * Contiene los mismos atributos con un Usuario.
 */
public final class Administrador extends Usuario {

    public Administrador(String nombre, String apellido, String dni, String legajo, String contrasenia, String correo) {
        super(nombre, apellido, dni, legajo, contrasenia, correo);
    }

    public Administrador() {
    }

}
