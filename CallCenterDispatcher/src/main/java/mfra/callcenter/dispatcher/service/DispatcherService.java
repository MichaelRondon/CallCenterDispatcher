package mfra.callcenter.dispatcher.service;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import lombok.Setter;
import mfra.callcenter.dispatcher.rest.CallCenterDispatcher;
import mfra.callcenter.util.model.FuncionarioDTO;
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

    private static final LinkedBlockingQueue<Llamada> LLAMADAS = new LinkedBlockingQueue<>();
    private static final ConcurrentLinkedQueue<FuncionarioDTO> OPERADORES = new ConcurrentLinkedQueue<>();
    private static final ConcurrentLinkedQueue<FuncionarioDTO> SUPERVISORES = new ConcurrentLinkedQueue<>();
    private static final ConcurrentLinkedQueue<FuncionarioDTO> DIRECTORES = new ConcurrentLinkedQueue<>();

    static {
        Worker worker = new Worker();
        (new Thread(worker)).start();
    }

    @Override
    public void registrar(FuncionarioDTO funcionario) {
        LOGGER.info("Se registtra el funcionario: {}", funcionario);
        if (funcionario == null && funcionario.getRol() == null) {
            throw new IllegalStateException("El funcionario es nulo o no tiene rol");
        }
        switch (funcionario.getRol()) {
            case OPERADOR:
                OPERADORES.offer(funcionario);
                break;
            case SUPERVISOR:
                SUPERVISORES.offer(funcionario);
                break;
            case DIRECTOR:
                DIRECTORES.offer(funcionario);
                break;
        }
    }

    @Override
    public void despachar(Llamada llamada
    ) {
        LOGGER.info("Se procesará la llamada: {}", llamada);
        LLAMADAS.offer(llamada);
    }

    private static class Worker implements Runnable {

        @Setter
        private boolean procesar = true;

        @Override
        public void run() {
            LOGGER.info("Inicia ejecución del consumidor");
            FuncionarioService funcionarioService = new FuncionarioService();
            while (procesar) {
                try {
                    Llamada llamada = LLAMADAS.take();
                    if (procesar(OPERADORES, llamada, funcionarioService)) {
                        continue;
                    }
                    LOGGER.warn("No se encuentran OPERADORES disponibles");
                    if (procesar(SUPERVISORES, llamada, funcionarioService)) {
                        continue;
                    }
                    LOGGER.warn("No se encuentran SUPERVISORES disponibles");
                    if (procesar(DIRECTORES, llamada, funcionarioService)) {
                        continue;
                    }
                    LOGGER.warn("No se encuentran DIRECTORES disponibles");
                    LOGGER.warn("No se encuentra quien atienda la llamada, se dejará en la cola");
                    LLAMADAS.offer(llamada);

                } catch (InterruptedException ex) {
                    java.util.logging.Logger.getLogger(DispatcherService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private synchronized boolean procesar(ConcurrentLinkedQueue<FuncionarioDTO> funcionarios,
                Llamada llamada, FuncionarioService funcionarioService) {
            try {
                FuncionarioDTO funcionarioDTO = funcionarios.parallelStream()
                        .filter(operador -> funcionarioService.estaDisponible(operador))
                        .findAny().get();
                funcionarioService.despachar(funcionarioDTO, llamada);
                return true;
            } catch (NoSuchElementException nsee) {
                return false;
            }
        }

    }

}
