package Modelo;

import ControlArchivos.manejoArchivosAvisos;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.util.HashSet;

import static Path.Path.pathAvisos;

public class Avisos implements iCRUD{

    //ATRIBUTOS
    private int id;
    private String legajoAutor;
    private String titulo;
    private String subtitulo;
    private String descripcion;
    private HashSet<String> legajos;
    private LocalDate fecha;
    private AccesoAviso accesoAviso;

    //CONSTRUCTORES

    public Avisos(int id, String legajoAutor, String titulo, String subtitulo, String descripcion, HashSet<String> legajos, LocalDate fecha, AccesoAviso accesoAviso) {
        this.id = id;
        this.legajoAutor = legajoAutor;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.descripcion = descripcion;
        this.legajos = legajos;
        this.fecha = fecha;
        this.accesoAviso = accesoAviso;
    }

    public Avisos(int id, String titulo, String subtitulo, String descripcion, HashSet<String> legajos, LocalDate fecha, AccesoAviso accesoAviso) {
        this.id = id;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.descripcion = descripcion;
        this.legajos = legajos;
        this.fecha = fecha;
        this.accesoAviso = accesoAviso;
    }

    public Avisos(String titulo, String subtitulo, String descripcion, HashSet<String> legajos, LocalDate fecha, AccesoAviso accesoAviso) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.descripcion = descripcion;
        this.legajos = legajos;
        this.fecha = fecha;
        this.accesoAviso = accesoAviso;
    }

    public Avisos() {
    }

    //GETTERS

    public int getId() {
        return id;
    }

    public String getLegajoAutor() {
        return legajoAutor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public HashSet<String> getLegajos() {
        return legajos;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public AccesoAviso getAccesoAviso() {
        return accesoAviso;
    }

    //SETTERS

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLegajos(HashSet<String> legajos) {
        this.legajos = legajos;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setAccesoAviso(AccesoAviso accesoAviso) {
        this.accesoAviso = accesoAviso;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLegajoAutor(String legajoAutor) {
        this.legajoAutor = legajoAutor;
    }

    /**
     * Convierte el objeto Avisos a un JSONObject.
     * @return JSONObject que representa el objeto Avisos.
     */
    public JSONObject AvisoAJSONObject() {
        JSONObject json = new JSONObject();
        json.put("id",id);
        json.put("legajoAutor",legajoAutor);
        json.put("titulo", titulo);
        json.put("subtitulo", subtitulo);
        json.put("descripcion", descripcion);
        json.put("fecha", fecha.toString());
        json.put("accesoAviso", accesoAviso.name());
        JSONArray legajosArray = new JSONArray(legajos);
        json.put("legajos", legajosArray);

        return json;
    }

    /**
     * Convierte un JSONObject a un objeto Avisos.
     * @param json JSONObject que representa el objeto Avisos.
     * @return Objeto Avisos.
     */
    public static Avisos JSONObjectAaviso(JSONObject json) {

        String titulo = json.getString("titulo");
        String subtitulo = json.getString("subtitulo");
        String descripcion = json.getString("descripcion");
        String legajoAutor = json.getString("legajoAutor");
        int id = json.getInt("id");
        HashSet<String> legajos = new HashSet<>();
        JSONArray legajosArray = json.getJSONArray("legajos");
        for (int i = 0; i < legajosArray.length(); i++) {
            legajos.add(legajosArray.getString(i));
        }
        LocalDate fecha = LocalDate.parse(json.getString("fecha"));
        AccesoAviso accesoAviso = AccesoAviso.valueOf(json.getString("accesoAviso"));

        Avisos aviso = new Avisos();
        aviso.id = id;
        aviso.legajoAutor = legajoAutor;
        aviso.titulo = titulo;
        aviso.subtitulo = subtitulo;
        aviso.descripcion = descripcion;
        aviso.legajos = legajos;
        aviso.fecha = fecha;
        aviso.accesoAviso = accesoAviso;

        return aviso;
    }

    /**
     * Crea un nuevo aviso y lo guarda en el archivo especificado.
     * @param path Ruta del archivo donde se guardará el aviso.
     * @return true si el aviso se creó y guardó correctamente, false en caso contrario.
     * @throws EntidadYaExistente Si el aviso ya existe.
     * @throws CamposVaciosException Si alguno de los campos obligatorios está vacío.
     * @throws DatosIncorrectosException Si alguno de los datos es incorrecto.
     */
    @Override
    public boolean crear(String path) throws EntidadYaExistente, CamposVaciosException, DatosIncorrectosException {
        if(!this.titulo.isEmpty() && !this.subtitulo.isEmpty() && !this.descripcion.isEmpty())
        {
            if(this.descripcion.length()<1000)
            {   if(this.getTitulo().length()<100){
                    if(this.getSubtitulo().length()<200) {
                        if (manejoArchivosAvisos.guardarAvisoAJSON(pathAvisos,this.AvisoAJSONObject()))
                        {
                            return true;
                        }
                    }else{
                        throw new DatosIncorrectosException("Ingresaste un subtitulo muy largo. Su subtitulo es de " + subtitulo.length() + " caracteres. Excede el limite de 200 caracteres");
                    }

                }else{
                    throw new DatosIncorrectosException("Ingresaste un titulo muy largo. Su titulo es de " + titulo.length() + " caracteres. Excede el limite de 100 caracteres");
                }

            }else{
                throw new DatosIncorrectosException("Ingresaste un mensaje muy largo. Su mensaje es de " + descripcion.length() + " caracteres. Excede el limite de 1000 caracteres");
            }
        }else {
            throw new CamposVaciosException("Intentaste ingresar campos vacíos");
        }
        return false;
    }

    /**
     * Actualiza un aviso existente en el archivo especificado.
     * @param path Ruta del archivo donde se actualizará el aviso.
     * @param jsonObject JSONObject que contiene los datos actualizados del aviso.
     * @return true si el aviso se actualizó correctamente, false en caso contrario.
     * @throws CamposVaciosException Si alguno de los campos obligatorios está vacío.
     * @throws DatosIncorrectosException Si alguno de los datos es incorrecto.
     */
    @Override
    public boolean actualizar(String path, JSONObject jsonObject) throws CamposVaciosException, DatosIncorrectosException {
        if(!this.titulo.isEmpty() && !this.subtitulo.isEmpty() && !this.descripcion.isEmpty())
        {
            if(this.descripcion.length()<1000)
            {
                if(this.getTitulo().length()<100){
                    if(this.getSubtitulo().length()<200) {
                        if (manejoArchivosAvisos.actualizarAvisoAJSON(pathAvisos,this.AvisoAJSONObject()))
                        {
                            return true;
                        }
                    }else{
                        throw new DatosIncorrectosException("Ingresaste un subtitulo muy largo. Su subtitulo es de " + subtitulo.length() + " caracteres. Excede el limite de 200 caracteres");
                    }

                }else{
                    throw new DatosIncorrectosException("Ingresaste un titulo muy largo. Su titulo es de " + titulo.length() + " caracteres. Excede el limite de 100 caracteres");
                }
            }else{
                throw new DatosIncorrectosException("Ingresaste un mensaje muy largo. Su mensaje es de " + descripcion.length() + " caracteres. Excede el limite de 1000 caracteres");
            }
        }else {
            throw new CamposVaciosException("Intentaste ingresar campos vacíos");
        }
        return false;
    }

}
