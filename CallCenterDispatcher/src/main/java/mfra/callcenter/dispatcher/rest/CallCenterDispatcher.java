package mfra.callcenter.dispatcher.rest;

import mfra.callcenter.util.model.Funcionario;
import mfra.callcenter.util.model.Llamada;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
public interface CallCenterDispatcher {
    
    public void registrar(Funcionario funcionario);    
    public void despachar(Llamada llamada);    
}
