package Excepciones;

public class CamposVaciosException extends Exception {
    public CamposVaciosException(String message) {
        super(message);
        excepcionPersonalizada.excepcion(message);
    }
}
