package Usuarios;

import ControlArchivos.manejoArchivos;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import Modelo.iCRUD;
import org.json.JSONObject;

import java.time.LocalDate;

/**
 * La clase Profesor no contiene atributos pero es para diferenciar sus funciones de los otros tipos de usuario.
 * Tiene los mismos atributos que un Usuario.
 */
public final class Profesor extends Usuario implements iCRUD {

    public Profesor(String nombre, String dni, String legajo, String correo) {
        super(nombre, dni, legajo, correo);
    }

    public Profesor() {}

    public Profesor(String nombre, String apellido, String dni, String legajo, String contrasenia, String correo, LocalDate fechaDeAlta, boolean actividad) {
    }

    @Override
    public String toString() {
        return "| Profesor: " + getNombre();
    }

    @Override
    public boolean crear(String path) throws EntidadYaExistente, CamposVaciosException, DatosIncorrectosException {

        boolean creado = false;

        if(!this.getDni().isEmpty() && !this.getNombre().isEmpty() && !this.getCorreo().isEmpty())
        {

            if(esDniValido(getDni())){

                if(esCorreoValido(getCorreo())) {

                    setLegajo(generarLegajo(Profesor.class, path));
                    setContrasenia(getDni());

                    JSONObject profesor = profesorAJSONObject();

                    if (!Consultas.consultaArchivo.buscarClave(path, getDni(), "dni")) {

                        manejoArchivos.guardarObjetoJSON(path, profesor);
                        creado = true;

                    } else {

                        throw new EntidadYaExistente("Ya existe un profesor cargado con ese DNI");

                    }

                }else{

                    throw new DatosIncorrectosException("El correo ingresado no es valido");

                }

            }else{

                throw new DatosIncorrectosException("El DNI ingresado no es valido");

            }

        }
        else
        {
            throw new CamposVaciosException("Faltan campos obligatorios");
        }

        return creado;
    }

    @Override
    public boolean actualizar(String path, JSONObject jsonObject) {
        return false;
    }

    @Override
    public boolean leer(String path, String legajo) {
        return false;
    }

    @Override
    public boolean borrar(String path) {
        return false;
    }

    /**
     * Convierte un profesor a un JSONObject y lo retorna
     * @return
     */
    public JSONObject profesorAJSONObject(){

        JSONObject profesor = new JSONObject();

        profesor.put("nombre", getNombre());
        profesor.put("apellido", getApellido());
        profesor.put("dni",getDni());
        profesor.put("correo",getCorreo());
        profesor.put("legajo",getLegajo());
        profesor.put("contrasenia",getContrasenia());
        profesor.put("fechaDeAlta",getFechaDeAlta().toString());
        profesor.put("actividad", getActividad());

        return profesor;

    }

    /**
     * Convierte un JSONObject a un profesor y lo retorna
     * @param profesor
     * @return
     */
    public static Profesor JSONObjectAProfesor(JSONObject profesor){

        String nombre = profesor.getString("nombre");
        String apellido = profesor.getString("apellido");
        String dni = profesor.getString("dni");
        String legajo = profesor.getString("legajo");
        String contrasenia = profesor.getString("contrasenia");
        String correo = profesor.getString("correo");
        LocalDate fechaDeAlta = LocalDate.parse(profesor.getString("fechaDeAlta"));
        boolean actividad = profesor.getBoolean("actividad");

        return new Profesor(nombre,apellido,dni,legajo,contrasenia,correo,fechaDeAlta,actividad);

    }

}
