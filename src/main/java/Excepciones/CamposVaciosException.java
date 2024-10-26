package Excepciones;

public class CamposVaciosException extends RuntimeException {
    public CamposVaciosException(String message) {
        super(message);
        excepcionPersonalizada.excepcion(message);
    }
}
