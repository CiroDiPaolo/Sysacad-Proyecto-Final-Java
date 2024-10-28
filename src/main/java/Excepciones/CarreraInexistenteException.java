package Excepciones;

public class CarreraInexistenteException extends Exception{

    public CarreraInexistenteException (String message){
        super(message);
        excepcionPersonalizada.excepcion(message);
    }
}
