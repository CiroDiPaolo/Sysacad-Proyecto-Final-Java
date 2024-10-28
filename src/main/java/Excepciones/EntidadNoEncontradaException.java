package Excepciones;

public class EntidadNoEncontradaException extends Exception {
    public EntidadNoEncontradaException(String message) {
        super(message);
        excepcionPersonalizada.excepcion(message);
    }
}
