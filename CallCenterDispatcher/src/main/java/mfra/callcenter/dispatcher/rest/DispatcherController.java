package mfra.callcenter.dispatcher.rest;

import java.net.URI;
import mfra.callcenter.dispatcher.service.DispatcherService;
import mfra.callcenter.util.model.FuncionarioDTO;
import mfra.callcenter.util.model.Llamada;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/app")
public class DispatcherController {

    @Autowired
    private DispatcherService dispatcherService;

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    public ResponseEntity<Void> registrar(@RequestBody FuncionarioDTO funcionarioDTO) {
        dispatcherService.registrar(funcionarioDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/app/registrar")
                .build().toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/despachar", method = RequestMethod.POST)
    public ResponseEntity<Void> despachar(@RequestBody Llamada llamada) {
        dispatcherService.despachar(llamada);
        return ResponseEntity.ok().build();
    }
}
