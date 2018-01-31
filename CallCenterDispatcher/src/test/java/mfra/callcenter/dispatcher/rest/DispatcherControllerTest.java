package mfra.callcenter.dispatcher.rest;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import mfra.callcenter.dispatcher.Application;
import mfra.callcenter.util.model.Llamada;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author acost
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class DispatcherControllerTest {

    @Test
    public void retrieveDetailsForCourse() throws Exception {
        Llamada llamada;
        List<Callable<HttpStatus>> list = new LinkedList<>();
        for (int i = 0; i < 15; i++) {
            llamada = new Llamada();
            llamada.setAsunto(String.format("Asunto%s", i));
            llamada.setDuracion(10000);
//            llamada.setDuracion(500 * i + 5000);
            list.add(getThread(llamada));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<HttpStatus>> invokeAll = executorService.invokeAll(list);
        for (Future<HttpStatus> future : invokeAll) {
            Assert.assertEquals(HttpStatus.OK, future.get());
        }
    }

    private Callable<HttpStatus> getThread(Llamada llamada) {
        String uri = String.format("http://%s:%s/app/despachar", "LAPTOP-MICHAEL", "8080");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Llamada> request = new HttpEntity<>(llamada);
        return new Callable<HttpStatus>() {

            @Override
            public HttpStatus call() throws Exception {
                try {
                    ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, request, Object.class);
                    return response.getStatusCode();
                } catch (org.springframework.web.client.ResourceAccessException accessException) {
                    accessException.printStackTrace();
                    return null;
                }
            }
        };
    }
}
