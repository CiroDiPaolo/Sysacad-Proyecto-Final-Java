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

    private String nombre;
    private String apellido;
    private String dni;
    private String legajo;
    private String contrasenia;
    private String correo;
    private LocalDate fechaDeAlta;
    private boolean actividad;

    public Usuario(String nombre, String apellido, String dni, String legajo, String contrasenia, String correo, LocalDate fechaDeAlta, boolean actividad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.legajo = legajo;
        this.contrasenia = contrasenia;
        this.correo = correo;
        this.fechaDeAlta = fechaDeAlta;
        this.actividad = actividad;
    }

    public Usuario(String nombre, String apellido, String dni, String legajo, String contrasenia, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.legajo = legajo;
        this.contrasenia = contrasenia;
        this.correo = correo;
        this.actividad = true;
        fechaDeAlta = LocalDate.now();
    }

    public Usuario(String nombre, String apellido, String dni, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.correo = correo;
        fechaDeAlta = LocalDate.now();
        this.actividad = true;
    }

    public Usuario() {
        actividad = false;
        nombre = "";
        apellido = "";
        dni = "";
        legajo = "";
        contrasenia = "";
        correo = "";
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

    public String getNombre() { return nombre; }

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

    public boolean getActividad() {
        return actividad;
    }

    public String getCorreo() {
        return correo;
    }

    //SETTERS

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", legajo='" + legajo + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }


    /**
     * Retorna el legajo siguiente correspondiente segun la clase
     * @param clase
     * @param fileName
     * @return String
     */
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

    public static boolean compararJSONObjectConUsuario(JSONObject jsonObject, Usuario usuario)
    {

        boolean comparar = true;

        if(!jsonObject.getString("nombre").equals(usuario.getNombre()))
        {
            comparar = false;
        } else if(!jsonObject.getString("apellido").equals(usuario.getApellido())) {
            comparar = false;
        } else if (!jsonObject.getString("dni").equals(usuario.getDni())) {
            comparar = false;
        } else if (!jsonObject.getString("contrasenia").equals(usuario.getContrasenia())) {
            comparar = false;
        } else if (jsonObject.getBoolean("actividad")!=usuario.getActividad()) {
            comparar = false;
        }
        return comparar;
    }

    public static boolean esCorreoValido(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    public static boolean esDniValido(String dni) {
        return dni.matches("\\d{8}");
    }
}
