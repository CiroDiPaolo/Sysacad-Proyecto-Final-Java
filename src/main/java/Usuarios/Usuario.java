package Usuarios;

import ControlArchivos.manejoArchivos;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Objects;

/**
 * La clase Usuario es una clase abstracta ya que sirve como base para las clases Estudiantes, Administrador, Profesor
 * Tiene un nombre(name), apellido, dni, legajo, contraseña y una fecha de alta del usuario.
 */
public abstract class Usuario {

    private String name;
    private String apellido;
    private String dni;
    private String legajo;
    private String contrasenia;
    private LocalDate fechaDeAlta;
    private boolean actividad;

    public Usuario(String name, String apellido, String dni, String legajo, String contrasenia) {
        this.name = name;
        this.apellido = apellido;
        this.dni = dni;
        this.legajo = legajo;
        this.contrasenia = contrasenia;
        this.actividad = true;
        fechaDeAlta = LocalDate.now();
    }

    public Usuario(String name, String apellido, String dni) {
        this.name = name;
        this.apellido = apellido;
        this.dni = dni;
        fechaDeAlta = LocalDate.now();
        this.actividad = true;
    }

    public Usuario() {
        actividad = false;
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

    public boolean isActividad() {
        return actividad;
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

    public void setFechaDeAlta(LocalDate fechaDeAlta) {this.fechaDeAlta = fechaDeAlta;}

    public void setLegajo(String legajo) { this.legajo = legajo; }

    public void setActividad(boolean actividad) {
        this.actividad = actividad;
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

    public static String generarLegajo(Class<?> clase, String fileName){
        String ultimoLegajo = manejoArchivos.ultimoLegajo(fileName);
        String auxiliar = ultimoLegajo.substring(1);
        String auxiliar2 = "";
        String nuevoLegajo = null;
        int num = Integer.parseInt(auxiliar);
        auxiliar = Integer.toString((num+1));
        int cantCeros = 6- (auxiliar.length());

        for(int i = 0; i<cantCeros; i++)
        {
            auxiliar2 = auxiliar2.concat("0");
        }
        
        try{
            if(clase == Estudiante.class)
            {
                nuevoLegajo = ("E").concat(auxiliar2).concat(auxiliar);
                
            } else if (clase == Profesor.class){
                nuevoLegajo = ("P").concat(auxiliar2).concat(auxiliar);
            } else if (clase == Administrador.class)
            {
                nuevoLegajo = ("A").concat(auxiliar2).concat(auxiliar);
            }

            return nuevoLegajo;
        }catch (IllegalArgumentException e)
        {
            System.out.println("No ingresaste una clase correcta");
        }
        
        
        return null;
    }
}
