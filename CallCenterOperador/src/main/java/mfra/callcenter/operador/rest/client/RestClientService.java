/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfra.callcenter.operador.rest.client;

import mfra.callcenter.util.model.FuncionarioDTO;
import mfra.callcenter.util.model.Llamada;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author acost
 */
@Service
public class RestClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestClientService.class);

    public void registrar(FuncionarioDTO funcionarioDTO, String dispatcherHost, String dispatcherPort) {
        String uri = String.format("http://%s:%s/app/registrar", dispatcherHost, dispatcherPort);
        LOGGER.info("Petición: {}", uri);
        LOGGER.info("funcionarioDTO: {}", funcionarioDTO);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<FuncionarioDTO> request = new HttpEntity<>(funcionarioDTO);
        try {
            ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, request, Object.class);
            if (!response.getStatusCode().equals(HttpStatus.CREATED)) {
                LOGGER.info("response.getStatusCode(): {}", response.getStatusCode());
                throw new IllegalStateException("Error en la consulta");
            }
        } catch (org.springframework.web.client.ResourceAccessException accessException) {
            LOGGER.error("Error de conexión: {}", accessException.getMessage());
        }
    }

}
