package Usuarios;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Usuario {

    private String name;
    private String apellido;
    private String dni;
    private String legajo;
    private String contrasenia;
    private LocalDate fechaDeAlta;

    public Usuario(String name, String apellido, String dni, String legajo, String contrasenia) {
        this.name = name;
        this.apellido = apellido;
        this.dni = dni;
        this.legajo = legajo;
        this.contrasenia = contrasenia;
        fechaDeAlta = LocalDate.now();
    }

    public Usuario() {
        name = "";
        apellido = "";
        dni = "";
        legajo = "";
        contrasenia = "";
        fechaDeAlta = LocalDate.now();
    }

    /**
     * Este enum representa los tipos de usuario que existen(las clases que heredan de usuario).
     * Se utiliza en la funcion de generarLegajoRandom
     */
    public enum ETipoUsuario{
        ALUMNO,
        PROFESOR,
        ADMINISTRADOR
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

    public LocalDate getFechaDeAlta() {return fechaDeAlta;}

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

    public void setFechaDeAlta(LocalDate fechaDeAlta) {this.fechaDeAlta = fechaDeAlta;}

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

    /**
     * Genera un legajo random que comienza con una letra segun el tipo de usuario("E" para estudiante, "P" para profesor, "A" para administrador)
     * Y lo concatena con 6 numeros random que van del 0 al 9 cada numero
     * @param tipoUsuario
     * @return String de un ID legajo
     */
    public static String generarLegajoRandom(ETipoUsuario tipoUsuario)
    {
        String id = "";
        if(tipoUsuario == ETipoUsuario.ALUMNO)
        {
            id += "E";
        } else if (tipoUsuario == ETipoUsuario.PROFESOR) {
            id += "P";
        }else if (tipoUsuario == ETipoUsuario.ADMINISTRADOR) {
            id += "A";
        }

        for(int i=0; i<6; i++)
        {
            int num = (int)Math.floor(Math.random() * 9);

            id += Integer.toString(num);
        }

        return id;
    }

}
