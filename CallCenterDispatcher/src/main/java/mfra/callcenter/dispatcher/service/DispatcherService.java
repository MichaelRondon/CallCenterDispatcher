package mfra.callcenter.dispatcher.service;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import lombok.Setter;
import mfra.callcenter.dispatcher.rest.CallCenterDispatcher;
import mfra.callcenter.util.model.Funcionario;
import mfra.callcenter.util.model.Llamada;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
@Service
public class DispatcherService implements CallCenterDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherService.class);

    private static final LinkedBlockingQueue<Llamada> llamadas = new LinkedBlockingQueue<>();
    private static final ConcurrentLinkedQueue<Funcionario> operadores = new ConcurrentLinkedQueue<>();
    private static final ConcurrentLinkedQueue<Funcionario> supervisore = new ConcurrentLinkedQueue<>();
    private static final ConcurrentLinkedQueue<Funcionario> directores = new ConcurrentLinkedQueue<>();

    static {
        Worker worker = new Worker();
        (new Thread(worker)).start();
    }

    @Override
    public void registrar(Funcionario funcionario) {
        LOGGER.info("Se registtra el funcionario: {}", funcionario);
        if (funcionario == null && funcionario.getRol() == null) {
            throw new IllegalStateException("El funcionario es nulo o no tiene rol");
        }
        switch (funcionario.getRol()) {
            case OPERADOR:
                operadores.offer(funcionario);
                break;
            case SUPERVISOR:
                supervisore.offer(funcionario);
                break;
            case DIRECTOR:
                directores.offer(funcionario);
                break;
        }
    }

    @Override
    public void despachar(Llamada llamada
    ) {
        LOGGER.info("Se procesará la llamada: {}", llamada);
        llamadas.offer(llamada);
    }

    private static class Worker implements Runnable {

        @Setter
        private boolean procesar = true;

        @Override
        public void run() {
            while (procesar) {
                try {
                    Llamada llamada = llamadas.take();
                    if (procesar(operadores, llamada)) {
                        continue;
                    }
                    if (procesar(supervisore, llamada)) {
                        continue;
                    }
                    if (procesar(directores, llamada)) {
                        continue;
                    }
                    llamadas.offer(llamada);

                } catch (InterruptedException ex) {
                    java.util.logging.Logger.getLogger(DispatcherService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private synchronized boolean procesar(ConcurrentLinkedQueue<Funcionario> funcionarios, Llamada llamada) {
            try {
                funcionarios.parallelStream()
                        .filter(operador -> operador.estaDisponible())
                        .findAny().get().despachar(llamada);
                return true;
            } catch (NoSuchElementException nsee) {
                return false;
            }
        }

    }

}
