package mfra.callcenter.operador;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import mfra.callcenter.operador.rest.client.RestClientService;
import mfra.callcenter.util.model.FuncionarioDTO;
import mfra.callcenter.util.model.Rol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 *
 * @author Felipe
 */
@SpringBootApplication
public class Application implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    private static final FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
    private static String dispatcherHost;
    private static String dispatcherPort;

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Application.class, args);
        Environment environment = configurableApplicationContext.getEnvironment();
        try {
            LOGGER.info("\n----------------------------------------------------------\n\t"
                    + "Application '{}' is running! Access URLs:\n\t"
                    + "Local: \t\thttp://127.0.0.1:{}\n\t"
                    + "External: \thttp://{}:{}\n----------------------------------------------------------",
                    environment.getProperty("spring.application.name"),
                    environment.getProperty("server.port"),
                    InetAddress.getLocalHost().getHostAddress(),
                    environment.getProperty("server.port"));

            funcionarioDTO.setHost(InetAddress.getLocalHost().getHostAddress());
            funcionarioDTO.setPuerto(getInt(environment.getProperty("server.port"), 8080));

            if (dispatcherHost == null) {
                dispatcherHost = environment.getProperty("dispatcher.host");
            }

            if (dispatcherPort == null) {
                dispatcherPort = environment.getProperty("dispatcher.port");
            }

            RestClientService restClientService = new RestClientService();
            restClientService.registrar(funcionarioDTO, dispatcherHost, dispatcherPort);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
        LOGGER.info("NonOptionArgs: {}", args.getNonOptionArgs());
        LOGGER.info("OptionNames: {}", args.getOptionNames());

        dispatcherHost = args.containsOption("dispatcher.host")
                ? args.getOptionValues("dispatcher.host").get(0) : null;
        dispatcherPort = args.containsOption("dispatcher.port")
                ? args.getOptionValues("dispatcher.port").get(0) : null;
        funcionarioDTO.setNombre(args.containsOption("nombre") ? args.getOptionValues("nombre").get(0) : "anonimo");
        funcionarioDTO.setNumero(args.containsOption("numero") ? args.getOptionValues("numero").get(0) : "000");
        funcionarioDTO.setRol(getRol(args.containsOption("rol") ? args.getOptionValues("rol").get(0) : "OPERADOR"));

    }

    private static int getInt(String intValue, int defaultValue) {
        if (intValue == null) {
            return defaultValue;
        }
        try {
            return (new BigDecimal(intValue)).intValue();
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    private Rol getRol(String rolParameter) {
        try {
            if (rolParameter != null) {
                return Rol.valueOf(rolParameter.trim().toUpperCase());
            }
        } catch (IllegalArgumentException iae) {
        }
        return Rol.OPERADOR;
    }
}
