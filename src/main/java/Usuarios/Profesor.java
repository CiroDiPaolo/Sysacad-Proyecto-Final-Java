package Usuarios;

import ControlArchivos.manejoArchivos;
import ControlArchivos.manejoArchivosComisiones;
import ControlArchivos.manejoArchivosProfesor;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import Modelo.Comision;
import Modelo.iCRUD;
import Path.Path;
import org.json.JSONObject;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;

/**
 * La clase Profesor no contiene atributos pero es para diferenciar sus funciones de los otros tipos de usuario.
 * Tiene los mismos atributos que un Usuario.
 */
public final class Profesor extends Usuario implements iCRUD {

    //CONSTRUCTORES

    public Profesor(String nombre, String dni, String legajo, String correo) {
        super(nombre, dni, legajo, correo);
    }

    public Profesor() {}

    public Profesor(String nombre, String apellido, String dni, String legajo, String contrasenia, String correo, LocalDate fechaDeAlta, boolean actividad) {
        super(nombre, apellido, dni, legajo, contrasenia, correo, fechaDeAlta, actividad);
    }

    /**
     * Crea un nuevo profesor y lo guarda en el archivo especificado.
     *
     * @param path La ruta del archivo donde se guardará el profesor.
     * @return true si el profesor fue creado y guardado exitosamente, false en caso contrario.
     * @throws EntidadYaExistente Si ya existe un profesor con el mismo DNI.
     * @throws CamposVaciosException Si alguno de los campos obligatorios está vacío.
     * @throws DatosIncorrectosException Si el DNI o el correo no son válidos.
     */
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

    /**
     * Actualiza la información de un profesor en el archivo especificado.
     *
     * @param path La ruta del archivo donde se actualizará el profesor.
     * @param jsonObject El objeto JSON con los datos actualizados del profesor.
     * @return true si el profesor fue actualizado exitosamente, false en caso contrario.
     * @throws CamposVaciosException Si alguno de los campos obligatorios está vacío.
     * @throws DatosIncorrectosException Si el DNI o el correo no son válidos.
     */
    @Override
    public boolean actualizar(String path, JSONObject jsonObject) throws CamposVaciosException, DatosIncorrectosException {
        if(!this.getDni().isEmpty() && !this.getNombre().isEmpty() && !this.getCorreo().isEmpty())
        {
            if(esDniValido(getDni())){

                if(esCorreoValido(getCorreo())) {

                    setLegajo(generarLegajo(Profesor.class, path));
                    setContrasenia(getDni());
                    if(manejoArchivosProfesor.actualizarProfesor(jsonObject,path)){
                        return true;
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

    /**
     * Obtiene las comisiones asignadas al profesor en el año actual.
     *
     * @param path La ruta del archivo donde se encuentran las comisiones.
     * @return Un conjunto de comisiones asignadas al profesor.
     */
    public HashSet<Comision> obtenerComisiones(String path){

        HashSet<Comision> comisiones = (manejoArchivosComisiones.obtenerComisionesPorAnio(Path.pathComisiones,String.valueOf(LocalDate.now().getYear())));

        Iterator<Comision> it = comisiones.iterator();

        while (it.hasNext()) {
            Comision comision = it.next();
            if (!comision.getCodigoProfesor().equals(getLegajo())) {
                it.remove();
            }
        }

        return comisiones;

    }

}
