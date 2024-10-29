package Modelo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Esta clase sirve para la creacion de tablas en JavaFX, no interrumpe la logica del programa y sus datos.
 */
public final class MateriaFX {

    private String id;
    private String nombre;
    private BooleanProperty seCursa = new SimpleBooleanProperty(false);
    private BooleanProperty seRinde = new SimpleBooleanProperty(false);

    public MateriaFX(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isSeCursa() {
        return seCursa.get();
    }

    public void setSeCursa(boolean seCursa) {
        this.seCursa.set(seCursa);
    }

    public BooleanProperty seCursaProperty() {
        return seCursa;
    }

    public boolean isSeRinde() {
        return seRinde.get();
    }

    public void setSeRinde(boolean seRinde) {
        this.seRinde.set(seRinde);
    }

    public BooleanProperty seRindeProperty() {
        return seRinde;
    }
}
