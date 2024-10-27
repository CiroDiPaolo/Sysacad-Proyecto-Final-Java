package Usuarios;

import ControlArchivos.manejoArchivosEstudiante;
import Excepciones.CamposVaciosException;
import Excepciones.EntidadYaExistente;
import Modelo.iCRUD;
import org.json.JSONObject;


/**
 * La clase Profesor no contiene atributos pero es para diferenciar sus funciones de los otros tipos de usuario.
 * Tiene los mismos atributos que un Usuario.
 */
public final class Profesor extends Usuario implements iCRUD {

    public Profesor(String nombre, String dni, String legajo,String contrasenia) {
        super(nombre, dni, legajo,contrasenia, contrasenia);
    }

    public Profesor() {}

    @Override
    public String toString() {
        return "| Profesor: " + getNombre();
    }

    @Override
    public boolean crear(String path) throws EntidadYaExistente, CamposVaciosException {

        if(!this.getDni().isEmpty() && !this.getNombre().isEmpty())
        {
            setLegajo(generarLegajo(Profesor.class, path));
            setContrasenia(getDni());

            JSONObject profesor = profesorAJSONObject();

            if(!Consultas.consultaArchivo.buscarClave(path,"dni",getDni())){

                manejoArchivosEstudiante.guardarEstudianteJSON(path,profesor);

            }else{

                System.out.println("El profesor ya existe");

            }

        }
        else
        {
            throw new CamposVaciosException("Faltan campos obligatorios");
        }

        return false;
    }

    @Override
    public boolean actualizar(String path) {
        return false;
    }

    @Override
    public boolean leer(String path) {
        return false;
    }

    @Override
    public boolean borrar(String path) {
        return false;
    }

    public JSONObject profesorAJSONObject(){

        JSONObject profesor = new JSONObject();

        profesor.put("nombre", getNombre());
        profesor.put("dni",getDni());
        profesor.put("legajo",getLegajo());
        profesor.put("contrasenia",getContrasenia());

        profesor.put("fechaDeAlta",getFechaDeAlta().toString());
        profesor.put("actividad",isActividad());

        return profesor;

    }

}
