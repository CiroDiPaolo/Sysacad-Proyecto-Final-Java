package User;

public class Persona {

    private String name;
    private String dni;

    public Persona(String name, String dni) {
        this.name = name;
        this.dni = dni;
    }

    public Persona() {
    }

    //GETTERS

    public String getName() { return name; }

    public String getDni() {
        return dni;
    }


    //SETTERS


    public void setName(String name) {
        this.name = name;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "name='" + name + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }

}
