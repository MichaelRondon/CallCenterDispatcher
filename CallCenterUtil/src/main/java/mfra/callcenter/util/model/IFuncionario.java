/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfra.callcenter.util.model;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
public interface IFuncionario {

    public void despachar(Llamada llamada);
    public boolean estaDisponible();
    
}
