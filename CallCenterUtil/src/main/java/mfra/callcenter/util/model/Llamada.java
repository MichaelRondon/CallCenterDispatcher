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
public class Llamada implements Serializable {

    private int duracion;
    private String asunto;

}
