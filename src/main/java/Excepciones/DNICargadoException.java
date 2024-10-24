package Excepciones;

public final class DNICargadoException extends Exception{

    public DNICargadoException(String mensaje) {

        super(mensaje);
        excepcionPersonalizada.excepcion(mensaje);

    }
}
