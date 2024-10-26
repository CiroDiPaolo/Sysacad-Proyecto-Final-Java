package Excepciones;

public class EntidadYaExistente extends Exception {
    public EntidadYaExistente(String message) {
        super(message);
        excepcionPersonalizada.excepcion(message);
    }
}
