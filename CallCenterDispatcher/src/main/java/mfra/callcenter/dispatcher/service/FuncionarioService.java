/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfra.callcenter.dispatcher.service;

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
public class FuncionarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioService.class);

    public void despachar(FuncionarioDTO funcionario, Llamada llamada) {
        String uri = String.format("http://%s:%s/funcionario/despachar", funcionario.getHost(), funcionario.getPuerto());
        LOGGER.info("Petición: {}", uri);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Llamada> request = new HttpEntity<>(llamada);
        try {
            ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, request, Object.class);
            if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                throw new IllegalStateException("Error en la consulta");
            }
        } catch (org.springframework.web.client.ResourceAccessException accessException) {
            LOGGER.error("Error de conexión: {}", accessException.getMessage());
        }
    }

    public boolean estaDisponible(FuncionarioDTO funcionario) {
        String uri = String.format("http://%s:%s/funcionario/estaDisponible", funcionario.getHost(), funcionario.getPuerto());
        LOGGER.info("Petición: {}", uri);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Boolean> response = restTemplate.getForEntity(uri, Boolean.class);
            if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                throw new IllegalStateException("Error en la consulta");
            }
            return response.getBody();
        } catch (org.springframework.web.client.ResourceAccessException accessException) {
            LOGGER.error("Error de conexión: {}", accessException.getMessage());
            return false;
        }
    }

}
