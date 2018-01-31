# CallCenterDispatcher

Aplicación que simula el funcionamiento de un CallCenter implementando un dispatcher y varios servicios de funcionarios.

Requisitos:

- Maven
- Java 8

Método sugerido para probarlo:

1. Descargar el código.
2. Compilar ejecutando la sentencia <code>mvn clean install -DskipTests</code> en la ruta <b>CallCenterDispatcher</b>
3. Terminando la compilación dirigirse a la ruta <b>CallCenterDispatcher\CallCenterDispatcher</b> y ejecutar la sentencia <code>mvn</code>
4. En una consola alterna ejecutar  el comando <code>java -jar CallCenterOperador\target\callcenteroperador-1.0.jar --rol=OPERADOR --nombre=pepito2 --numero=4016599934 --server.port=8081</code> en la ruta <b>CallCenterDispatcher</b>
5. En una consola alterna ejecutar  el comando <code>java -jar CallCenterOperador\target\callcenteroperador-1.0.jar --rol=DIRECTOR --nombre=pepito3 --numero=5016599934 --server.port=8082</code> en la ruta <b>CallCenterDispatcher</b>
6. En una consola alterna ejecutar  el comando <code>java -jar CallCenterOperador\target\callcenteroperador-1.0.jar --rol=SUPERVISOR --nombre=pepito1 --numero=3016599934</code> en la ruta <b>CallCenterDispatcher</b>
7. Se pueden subir tantas instancias como se deseen del <b>callcenteroperador-1.0.jar</b> con diferentes puertos. Una vez suban podrá observarse comoo se registran en el dispatcher.
8. En una consola alterna ejecutar el comando <code>mvn test</code> en la ruta <b>CallCenterDispatcher</b> Esto hará 15 peticiones (llamadas) al dispatcher.


Algunas tecnologías implementadas:

- Servicios REST con Springboot.
- Implementación java LinkedBlockingQueue
- Implementación java ConcurrentLinkedQueue
- Sincronización de hilos
- Implementación java ExecutorService
- Implementación java Callable


Sugerencias de mejora:

- Implementación de un Middleware de colas MOM como JMS y RabbitMQ para garantizar confiabilidad.
- De no poder hacer lo anterior implementar la persistencia de la información en las colas así como la recuperación de esta información al arranque del servicio.
- Implementación de componentes WEB que permitan hacer seguimiento de las llamas en forma gráfica.

