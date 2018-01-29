package mfra.callcenter.util.model;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
@Data
@ToString
public abstract class Funcionario implements Serializable, IFuncionario{
    
    private String numero;
    private int puerto;
    private String host;
    private String nombre;
    private Rol rol;
}
