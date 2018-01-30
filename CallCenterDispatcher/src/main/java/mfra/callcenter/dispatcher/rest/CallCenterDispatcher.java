package mfra.callcenter.dispatcher.rest;

import mfra.callcenter.util.model.FuncionarioDTO;
import mfra.callcenter.util.model.Llamada;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
public interface CallCenterDispatcher {
    
    public void registrar(FuncionarioDTO funcionario);    
    public void despachar(Llamada llamada);    
}
