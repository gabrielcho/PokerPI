package poker;

import java.io.IOException;

/**Por razones de estructuración necesitamos que la clase Lógica herede de interfaz, esto permite que todo manejo
 * de lógica pueda usar la clase de interfaz sin problemas de acceso.
 * <h3> Las etiquetas HTML funcionan en el javadoc wtf</h3>
 */
public class Logica extends Interfaz {
  /**Esta es el objeto de baraja a usar en todo el juego */
  //Hay que tener en cuenta más adelante que esta baraja tiene que reiniciarse
  //Por lo que toca poner un método de reinicio en la baraja.
  private Baraja baraja;
  private Jugador humano, pc;

  public Logica() throws IOException {
    setEntorno();
    baraja = new Baraja();
    humano = new Jugador();
    pc = new Jugador();
    repartirCartas();
    dibujarMano(humano);
    revalidate();
    repaint();
  }

  /**Este método limpia la mano de los jugadores y les asigna dos nuevas cartas tomadas de la baraja al azar */
  public void repartirCartas() {
    humano.soltarCartas();
    pc.soltarCartas();
    for (int i = 0; i < 2; i++) {
      humano.tomarCarta(baraja.darCartaAlAzar());
      pc.tomarCarta(baraja.darCartaAlAzar());
    }
  }
}