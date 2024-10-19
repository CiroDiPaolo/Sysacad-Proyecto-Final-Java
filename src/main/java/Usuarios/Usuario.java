package Usuarios;

import java.util.Objects;

public abstract class Usuario {

    private String name;
    private String apellido;
    private String dni;
    private String legajo;
    private String contrasenia;

    public Usuario(String name, String apellido, String dni, String legajo, String contrasenia) {
        this.name = name;
        this.apellido = apellido;
        this.dni = dni;
        this.legajo = legajo;
        this.contrasenia = contrasenia;
    }

    public Usuario() {
    }

    //GETTERS

    public String getName() { return name; }

    public String getApellido() { return apellido; }

    public String getDni() {
        return dni;
    }

    public String getLegajo() {
        return legajo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    //SETTERS

    public void setName(String name) {
        this.name = name;
    }

    public void setApellido(String apellido) { this.apellido = apellido; }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "name='" + name + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", legajo='" + legajo + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario that = (Usuario) o;
        return Objects.equals(dni, that.dni) && Objects.equals(legajo, that.legajo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni, legajo);
    }
}
