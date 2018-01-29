package mfra.callcenter.util.model;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
@Data
@ToString
public class Llamada extends Thread implements Serializable {

    private int duracion;
    private String asunto;
    
    @Override
    public void run() {
        try {
            Thread.sleep(duracion);
            super.run(); //To change body of generated methods, choose Tools | Templates.
        } catch (InterruptedException ex) {
            Logger.getLogger(Llamada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}
