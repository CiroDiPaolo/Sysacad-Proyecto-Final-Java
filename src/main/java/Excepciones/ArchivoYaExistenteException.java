package Excepciones;

public final class ArchivoYaExistenteException extends Exception {

    public ArchivoYaExistenteException(String message) {

        super(message);
        excepcionPersonalizada.excepcion(message);

    }
}
