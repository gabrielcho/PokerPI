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
  private Mano manoPrueba = new Mano();

  public Logica() throws IOException {
    setEntorno();
    baraja = new Baraja();
    humano = new Jugador("humano");
    pc = new Jugador("pc");
    repartirCartas();
    dibujarMano(humano);
    dibujarMano(pc);
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

  //////////////////////// PENDIENTES PARA IMPLEMENTAR LUEGO \\\\\\\\\\\\\\\\\\\\\\\\\\\\
  // Puede borrarse si es necesario
  public void analizarRepetidas(Mano mano) {
    int[] cartas = new int[14];
    //Con este for va contando cuantas cartas de cada valor tiene
    for (int i = 0; i < 7; i++) {
      int valor = mano.getCarta(i).getValor();
      cartas[valor] = cartas[valor] + 1;
    }
    // procedemos a construir el bucle que nos determina si es par, trio o cuatrupleta
    for (int pos = 1; pos < 14; pos++) {
      if (cartas[pos] == 2)
        System.out.println("Par");
      else if (cartas[pos] == 3)
        System.out.println("Trio");
      else if (cartas[pos] == 4)
        System.out.println("Cuatrupleta");
    }
  }

}