package poker;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**Por razones de estructuración necesitamos que la clase Lógica herede de interfaz, esto permite que todo manejo
 * de lógica pueda usar la clase de interfaz sin problemas de acceso.
 * <h3> Las etiquetas HTML funcionan en el javadoc wtf</h3> y tambien en los jlabels xd
 */
public class Logica extends Interfaz implements ActionListener {
  /**Esta es el objeto de baraja a usar en todo el juego */
  //Hay que tener en cuenta más adelante que esta baraja tiene que reiniciarse
  //Por lo que toca poner un método de reinicio en la baraja.
  private JButton pasar, subir, igualar, retirarse;
  private Baraja baraja;
  private Jugador humano, pc;
  private String fase;

  public Logica() {
    baraja = new Baraja();
    humano = new Jugador("humano");
    pc = new Jugador("pc");
	setBotones();
  }
	public void setBotones(){
		igualar = new JButton ("Igualar"); 
        igualar.addActionListener(this);           //escucha
        super.addBoton(igualar);
        retirarse = new JButton ("Retirarse"); 
        retirarse.addActionListener(this);   	 //escucha
        super.addBoton(retirarse);
        pasar = new JButton ("Pasar"); 
        pasar.addActionListener(this);    		//escucha
        super.addBoton(pasar); 
        subir = new JButton ("Subir"); 
        subir.addActionListener(this);    		//escucha
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
  
  //metodo usado para asegurarnos de que el usuario digite un numero cuando se le pide la apuesta
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
  
  
  //Muestra un mensaje en pantalla pidiendo la apuesta del jugador, verifica que el usuario digite un número, de lo contrario muestra un mensaje de error.
  public void mensajePedirApuesta() {
	  bote = JOptionPane.showInputDialog(null,"¿Cuánto quieres apostar? Tienes: " + humano.getBalance()); 	  
	  if(bote==null) {
		  System.exit(0);
	  }
	  else { if (isNumeric(bote) == true) {
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
/**Pide apuestas y pinta el flop */
   public void primeraFase() {
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
	fase = "Flop";
   }
   /** Empieza la fase turn poniendo la cuarta carta  */
   public void faseTurn(){
	pintarTurn();
	
	fase = "Turn"; //Cambia el estado de la fase a "Turn"
   }

   public void faseRiver(){
	   pintarTurn(); //solo para probar
	   fase = "River";
   }
   /** Añade la carta del Turn a la baraja "invisible" de los jugadores, además pinta el componente en 
	* el tablero y actualiza 
    */
   public void pintarTurn(){
	Carta cartaTurn = baraja.darCartaAlAzar();
	JButton turn = crearComponenteDeMano(cartaTurn);
	areaTableroCartas.add(turn);
	humano.tomarCarta(cartaTurn);
	pc.tomarCarta(cartaTurn);
	revalidate();
	repaint();
   }

  /** Inicia el juego
  * (Queda pendiente decidir si al ejecutar este método el juego se reinicia de cero o sólo permite otra mano más)
  * Esta vez no tenemos bucle de juego porque se supone que por el caracter de botones el juego tiene que hacerse con una
  * estructura orientada a objetos
   */
  public void jugar() { 
 
	    setEntorno();
	    baraja = new Baraja();
	    repartirCartas();
	    dibujarMano(humano);
	    dibujarMano(pc);
	    pintarInfo(humano);
	    pintarInfo(pc);
	    revalidate();
		actualizarPantalla();
		
		//PRIMERA RONDA DE APUESTAS, pide apuestas y pinta el flop 
	  	primeraFase();
	   // Después de esta primera fase y ya pintado el flop, queda el flujo del juego a manos de los botones
	   
//	   humano.tomarCarta(new Carta(1,2));
//	   humano.tomarCarta(new Carta(1,2));
//	   pc.tomarCarta(new Carta(3,3));
//	   pc.tomarCarta(new Carta(3,3));                  // para hacer pruebas del método Analizar Repetidas
//	   
//	   pintarFlop();
//	   
//	   analizarRepetidas(humano.getMano());
//	   analizarRepetidas(pc.getMano());
	   //SEGUNDA RONDA DE APUESTAS
	   
	   
	   
	   
	 //pierde y sale del bucle de juego  esto debe ser un metodo que verifique las manos xd 
	   /*
	   if(humano.getBalance()>0 & pc.getBalance()>0) { // pierde o gana la mano, mas no el juego. el jugo se gana cuando alguno de los dos quede sin dinero
	   LimpiarInterfaz();
	   jugar();
	   }
	   else { //preguntar si quiere iniciar otra partida desde 0
		   JugarDeNuevo();
	   }*/
	   
  }

  
  //Dar el flop (tres cartas a la mesa), pone los componentes sobre la mesa y se agregan esas cartas a las manos de cada jugador.
  public void pintarFlop() {
	  for(int i=0; i<3; i++) {
		  Carta cartaFlop = baraja.darCartaAlAzar();
		  JButton flop = crearComponenteDeMano(cartaFlop);
		  areaTableroCartas.add(flop);
		  humano.tomarCarta(cartaFlop);
		  pc.tomarCarta(cartaFlop);
	  }
	  actualizarPantalla();
  }
  
  /**Inicializa la próxima fase dependiendo de la fase en la que se esté00 */
  public void proximaFase(){
	  switch(fase) {
		case "Flop": //Pasa a la fase "Turn"
		faseTurn();
		break;
		
		case "Turn": // Pasa a la fase "River"
		faseRiver();
		break;
		

		case "River":	// Termina la mano y determina al ganador con alguna funcion que envuelva todo
		break;
		
	  }
  }
  //Muestra un mensaje en pantalla preguntando si el usuario quiere volver a jugar
  ////////////////////////////////////verificar si esta bien y cuando usarlo\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  public void JugarDeNuevo() {
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
  //verifica si hay pares(pair), tríos(three of a kind), cuatrupletas(Poker), par doble, o un full house (trio + par)
  public void analizarRepetidas(Mano mano) {
    int[] cartas = new int[14];
	 int pares = 0;
	 boolean trio = false;
    //Llena de ceros el array
    for (int i = 0; i < 14; i++) {
       
        cartas[i] =0;
      }
    //Con este for va contando cuantas cartas de cada valor tiene
    for (int i = 0; i < 7; i++) {
      int valor = mano.getCarta(i).getValor();
      cartas[valor] = cartas[valor] + 1;
    }
   
    // procedemos a construir el bucle que nos determina si es par, trio o cuatrupleta
    for (int pos = 1; pos < 14; pos++) {
    	System.out.println(cartas[pos]);
      if (cartas[pos] == 2)
        pares=+1;
      else if (cartas[pos] == 3)
        trio = true;
      else if (cartas[pos] == 4)
        System.out.println("Poker");
    }
    System.out.println("Fin bucle");
    //verifica si hay un FULL HOUSE
    if(pares==1 & trio==true) {
    	System.out.println("Full House");
    }
    
    else {
	    	if(trio==true) {//imprime el trio, si lo hay
	    	 System.out.println("Trío");
	    	}
	    	 
	    //verifica cuantos pares hay
	  else { 	
	    switch(pares) {
	    case 0:
	    	break;
	    case 1:
	    	 System.out.println("Par");
	    	 break;
	    case 2: 
	    	 System.out.println("Doble Par");
	    	 break;
	    } 
      }
    }
  
  }
/**Escuchas que determinan el comportamiento del juego */
  @Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource()==pasar) {
				//cambiar turno?? hace algo el cumputador dependiendo de su jugada??
				System.out.println("Pasa");
				//Debe checar si se está en fase de flop, turn o river
				proximaFase();
			}
			if(e.getSource()==subir) {
				//igualar la apuesta que haya e incrementar lo que se quiera
				System.out.println("Sube");
			}
			if(e.getSource()==retirarse) {
//				gana el computador y se reinicia el juego, el juego acaba cuando alguno se quede sin dinero
				System.out.println("Se retira");

				//El computador gana automáticamente y muestra sus cartas

				//Luego debe preguntar si se quiere volver a jugar
			}
			if(e.getSource()==igualar) {
				System.out.println("Iguala");

				//Verifica cuánto tiene apostado el pc en la mesa e iguala esa cantidad en función de la apuesta del jugador
				//Algo tipo (apuestaPC)-(apuestaJugador) = Cantidad a igualar
			}		
		}

		

}