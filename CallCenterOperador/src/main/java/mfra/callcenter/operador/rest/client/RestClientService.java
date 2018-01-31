/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfra.callcenter.operador.rest.client;

import mfra.callcenter.util.model.FuncionarioDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author acost
 */
@Service
public class RestClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestClientService.class);
    
    public void registrar (FuncionarioDTO funcionarioDTO, String dispatcherHost, String dispatcherPort){
        String uri = String.format("http://%s:%s/app/registrar", dispatcherHost, dispatcherPort);
        LOGGER.info("Petición: {}", uri);        
        LOGGER.info("funcionarioDTO: {}", funcionarioDTO);        
        RestTemplate restTemplate = new RestTemplate();
    }
    
    
}
