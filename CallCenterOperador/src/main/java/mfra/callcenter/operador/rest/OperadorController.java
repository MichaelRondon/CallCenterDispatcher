package mfra.callcenter.operador.rest;

import java.net.URI;
import java.util.logging.Level;
import mfra.callcenter.util.model.Llamada;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Felipe
 */
@RestController
@RequestMapping("/funcionario")
public class OperadorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperadorController.class);

    private Thread hilo = null;

    @RequestMapping(value = "/despachar", method = RequestMethod.POST)
    public ResponseEntity<Void> despachar(@RequestBody Llamada llamada) {
        LOGGER.info("Se procesara la llamada con asunto: {}. Y con duración: {}",
                llamada.getAsunto(), llamada.getDuracion());
        hilo = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(llamada.getDuracion());
                    LOGGER.info("Termina la llamada con asunto: {}.",
                            llamada.getAsunto(), llamada.getDuracion());
                } catch (InterruptedException ex) {
                    java.util.logging.Logger.getLogger(OperadorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        hilo.start();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/funcionario/despachar")
                .build().toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/estaDisponible", method = RequestMethod.GET)
    public ResponseEntity<Boolean> estaDisponible() {
        boolean resp = hilo == null ? true : !hilo.isAlive();
//        LOGGER.info("Se pregunta por la disponibilidad del operador. Disonible: {}", resp);
        return ResponseEntity.ok().body(resp);
    }
}
