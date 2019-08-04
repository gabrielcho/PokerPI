package poker;

import java.io.IOException;

import javax.swing.JOptionPane;

/**Por razones de estructuraci√≥n necesitamos que la clase L√≥gica herede de interfaz, esto permite que todo manejo
 * de l√≥gica pueda usar la clase de interfaz sin problemas de acceso.
 * <h3> Las etiquetas HTML funcionan en el javadoc wtf</h3> y tambien en los jlabels xd
 */
public class Logica extends Interfaz {
  /**Esta es el objeto de baraja a usar en todo el juego */
  //Hay que tener en cuenta m√°s adelante que esta baraja tiene que reiniciarse
  //Por lo que toca poner un m√©todo de reinicio en la baraja.
  private Baraja baraja;
  private Jugador humano, pc;
  private Mano manoPrueba = new Mano();
   // el dinero que hay apostado por ambos jugadores en la mesa

  public Logica() throws IOException {
    setEntorno();
    baraja = new Baraja();
    humano = new Jugador("humano");
    pc = new Jugador("pc");
    bote = "0" ;
  }

  /**Este m√©todo limpia la mano de los jugadores y les asigna dos nuevas cartas tomadas de la baraja al azar */
  public void repartirCartas() {
    humano.soltarCartas();
    pc.soltarCartas();
    for (int i = 0; i < 2; i++) {
      Carta cartaJugador = baraja.darCartaAlAzar();
      Carta cartaPc = baraja.darCartaAlAzar();
      humano.tomarCarta(cartaJugador);
      pc.tomarCarta(cartaPc);
      baraja.quitarCarta(cartaJugador);
      baraja.quitarCarta(cartaPc);
    }
    
  }
  
  //metodo usado para asegurarnos de que el usuario digite un numero cuando se le pide la apuesta, y la pone en la mesa
  public boolean isNumeric(String cadena) {

      boolean resultado;

      try {
          Integer.parseInt(cadena);
          resultado = true;
      } catch (NumberFormatException excepcion) {
          resultado = false;
      }

      return resultado;
  }
  
  
  public void mensajePedirApuesta() {
	  bote = JOptionPane.showInputDialog(null,"øCu·nto quieres apostar? Tienes: " + humano.getBalance());
	    if (isNumeric(bote) == true) {
	    while(Integer.valueOf(bote)<=0 ||  Integer.valueOf(bote)>= humano.getBalance()) {
	    	JOptionPane.showMessageDialog(null, "Digite un numero v·lido","Alerta",JOptionPane.WARNING_MESSAGE);
	    	bote = JOptionPane.showInputDialog(null,"øCu·nto quieres apostar? Tienes: " + humano.getBalance());
	    }  
   } 
	    else {
	    	while(isNumeric(bote) == false) {
	    		JOptionPane.showMessageDialog(null, "Digite un numero v·lido","Alerta",JOptionPane.WARNING_MESSAGE);
		    	mensajePedirApuesta();
	    } 
	  }
}
  
  public void jugar() throws IOException { //bucle de juego???
	  baraja.barajar();
	  repartirCartas();
	    dibujarMano(humano);
	    dibujarMano(pc);
	    pintarInfoJugadores(humano);
	    pintarInfoJugadores(pc);
	    revalidate();
	    repaint();
	    
	   mensajePedirApuesta();
	   humano.setApuesta(Integer.valueOf(bote));
	   humano.restarDinero(humano.getApuesta());
	   areaManoInfo.removeAll();
	   pintarInfoJugadores(humano);
	    actualizarPantalla(areaManoInfo);
	  // que hace el computador?? que clase de inteligencia artificial aplicamos D:
	   
	   
	   
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