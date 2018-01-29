
package mfra.callcenter.dispatcher.rest;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Felipe
 */
@RestController
@RequestMapping("/app")
public class DispatcherController {

    @RequestMapping(value = "/agregar", method = RequestMethod.POST)
    public ResponseEntity<Frecuencia> agregar(@RequestBody Invernadero invernadero) {
        return new ResponseEntity(new Frecuencia(ThreadLocalRandom.current().nextInt(5, 60)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/eliminar", method = RequestMethod.POST)
    public ResponseEntity<Void> eliminar(@RequestBody Invernadero invernadero) {
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/actualizaFrecuencia", method = RequestMethod.POST)
    public ResponseEntity<Void> actualizaFrecuencia(@RequestBody Frecuencia frecuencia) {
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/listarNodos", method = RequestMethod.POST)
    public ResponseEntity<List<Invernadero>> listarNodos() {
        List<Invernadero> invernaderos = new LinkedList<>();
        Invernadero invernadero = new Invernadero();
        invernadero.setHost("localhost");
        invernadero.setNombre("Invernadero1");
        invernadero.setPuerto(8081);
        Invernadero invernadero2 = new Invernadero();
        invernadero2.setHost("localhost");
        invernadero2.setNombre("Invernadero2");
        invernadero2.setPuerto(8082);
        Invernadero invernadero3 = new Invernadero();
        invernadero3.setHost("localhost");
        invernadero3.setNombre("Invernadero3");
        invernadero3.setPuerto(8083);
        invernaderos.add(invernadero);
        invernaderos.add(invernadero2);
        invernaderos.add(invernadero3);
        return new ResponseEntity(invernaderos, HttpStatus.OK);
    }
}
