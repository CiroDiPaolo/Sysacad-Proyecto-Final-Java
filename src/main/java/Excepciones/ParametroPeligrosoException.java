package Excepciones;

public class ParametroPeligrosoException extends Exception {
    public ParametroPeligrosoException(String message) {
      super(message);
      excepcionPersonalizada.excepcion(message);
    }
}
