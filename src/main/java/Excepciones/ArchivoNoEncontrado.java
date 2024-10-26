package Excepciones;

public class ArchivoNoEncontrado extends Exception {

    public ArchivoNoEncontrado(String message) {
        super(message);
        excepcionPersonalizada.excepcion(message);
    }
}
