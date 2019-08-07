package poker;

import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**Por razones de estructuración necesitamos que la clase Lógica herede de interfaz, esto permite que todo manejo
 * de lógica pueda usar la clase de interfaz sin problemas de acceso.
 * <h3> Las etiquetas HTML funcionan en el javadoc wtf</h3> y tambien en los jlabels xd
 */
public class Logica extends Interfaz {
  /**Esta es el objeto de baraja a usar en todo el juego */
  //Hay que tener en cuenta más adelante que esta baraja tiene que reiniciarse
  //Por lo que toca poner un método de reinicio en la baraja.
  private JButton pasar, subir, igualar, retirarse;
  private Baraja baraja;
  private Jugador humano, pc;
	private Escuchas escucha;
  private Mano manoPrueba = new Mano();

  public Logica() throws IOException {
    baraja = new Baraja();
    humano = new Jugador("humano");
    pc = new Jugador("pc");
		setBotones();
  }
	public void setBotones(){
		igualar = new JButton ("Igualar"); 
        igualar.addActionListener(escucha);           //escucha
        super.addBoton(igualar);
        retirarse = new JButton ("Retirarse"); 
        retirarse.addActionListener(escucha);   	 //escucha
        super.addBoton(retirarse);
        pasar = new JButton ("Pasar"); 
        pasar.addActionListener(escucha);    		//escucha
        super.addBoton(pasar); 
        subir = new JButton ("Subir"); 
        subir.addActionListener(escucha);    		//escucha
        super.addBoton(subir);
	}
  /**Este método limpia la mano de los jugadores y les asigna dos nuevas cartas tomadas de la baraja al azar */
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
	  bote = JOptionPane.showInputDialog(null,"�Cu�nto quieres apostar? Tienes: " + humano.getBalance()); 	  
	  if(bote==null) {
		  System.exit(0);
	  }
	  else {	   if (isNumeric(bote) == true) {
		  		   long valorObtenido = Long.valueOf(bote);	 	 
		  		   while(valorObtenido<=0 ||  valorObtenido > humano.getBalance()) {
		  			 JOptionPane.showMessageDialog(null, "Digite un numero v�lido","Alerta",JOptionPane.WARNING_MESSAGE);
		  	     	 mensajePedirApuesta();	
		  }
	    }  
   
	    else {
	    		JOptionPane.showMessageDialog(null, "Digite un numero v�lido","Alerta",JOptionPane.WARNING_MESSAGE);
		    	mensajePedirApuesta();	    
    }
  }   
}
  
  public void jugar() throws IOException { //bucle de juego???
 
	    setEntorno();
	    baraja = new Baraja();
	    repartirCartas();
	    dibujarMano(humano);
	    dibujarMano(pc);
	    pintarInfo(humano);
	    pintarInfo(pc);
	    revalidate();
	    actualizarPantalla();
	    
	   boolean noHaPerdido=true; 
	   
		//PRIMERA RONDA DE APUESTAS   
	   mensajePedirApuesta();
	   humano.setApuesta(Integer.valueOf(bote));
	   humano.restarDinero(humano.getApuesta());
	   pc.setApuesta(Integer.valueOf(bote));// al principio el pc siempre iguala la apuesta del jugador 
	   pc.restarDinero(pc.getApuesta());
	   bote = Integer.toString((Integer.valueOf(bote) + pc.getApuesta()));
	   pintarInfo(pc); //para que actualicen las infos
	   pintarInfo(humano);
	   actualizarPantalla();
	   pintarFlop();
	   
	   //SEGUNDA RONDA DE APUESTAS
	   
	   
	   
	   
	   noHaPerdido=false;//pierde y sale del bucle de juego  esto debe ser un metodo que verifique las manos xd 
	   /*
	   if(humano.getBalance()>0 & pc.getBalance()>0) { // pierde o gana la mano, mas no el juego. el jugo se gana cuando alguno de los dos quede sin dinero
	   LimpiarInterfaz();
	   jugar();
	   }
	   else { //preguntar si quiere iniciar otra partida desde 0
		   JugarDeNuevo();
	   }*/
	   
  }

  
  //Dar el flop (tres cartas a la mesa)
  public void pintarFlop() throws IOException {
	  for(int i=0; i<3; i++) {
		  Carta cartaFlop = baraja.darCartaAlAzar();
		  JButton flop = crearComponenteDeMano(cartaFlop);
		  areaTableroCartas.add(flop);
		  humano.tomarCarta(cartaFlop);
		  pc.tomarCarta(cartaFlop);
		 
	  }
	  actualizarPantalla();
	  for(int i=0; i<humano.getManoSize();i++) {
		  System.out.println(i+"   "+humano.getCartaMano(i).mostrarCarta()+" Carta humano");
		  System.out.println(i+"   "+pc.getCartaMano(i).mostrarCarta()+" Carta pc");
	  }
	  System.out.println( baraja.tamanoBaraja());
  }
  
  public void JugarDeNuevo() throws IOException {
	  if (humano.getBalance()>0) {
	  int resp = JOptionPane.showConfirmDialog(null, "�quieres volver a Jugar?");
	  if(resp==0) {
		  humano.reiniciarBalance();
		  pc.reiniciarBalance();
		  LimpiarInterfaz();
		  jugar();
	  }
	  else {
		  System.exit(0);
	  }	  
	 }
	  else {
		  JOptionPane.showMessageDialog(null, "Ya no tienes dinero para seguir jugando", "Mensaje", JOptionPane.WARNING_MESSAGE);
		  System.exit(0);
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


		public class Escuchas implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource()==pasar) {
				//cambiar turno?? hace algo el cumputador dependiendo de su jugada??
			}
			if(e.getSource()==subir) {
				//igualar la apuesta que haya e incrementar lo que se quiera
			}
			if(e.getSource()==retirarse) {
//				gana el computador y se reinicia el juego, el juego acaba cuando alguno se quede sin dinero

			}
			if(e.getSource()==igualar) {
//				ver cuanto hay apostado en la mesa y apostar esa misma cantidad
				
			}		
		}
    }

}