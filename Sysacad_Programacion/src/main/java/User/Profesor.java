package User;

public class Profesor extends Persona{

    private String catedra;
    private String contrasenia;

    public Profesor(String name, String dni, String catedra, String contrasenia) {
        super(name, dni);
        this.catedra = catedra;
        this.contrasenia = contrasenia;
    }

    public Profesor() {}

    public Profesor(String catedra, String contrasenia) {

        this.catedra = catedra;
        this.contrasenia = contrasenia;
    }

    //GETTERS

    public String getCatedra() { return catedra; }

    public String getContrasenia() { return contrasenia; }

    //SETTERS

    public void setCatedra(String catedra) { this.catedra = catedra; }

    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }

    @Override
    public String toString() {
        return "Profesor{" +
                "catedra='" + catedra + '\'' +
                '}';
    }

}
