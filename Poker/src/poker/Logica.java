/*
 * @Author Gabriel Arango
 * @Author Diego Timaná
 * @Version 1.0
 */
package poker;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Por razones de estructuración necesitamos que la clase Lógica herede de
 * interfaz, esto permite que todo manejo de lógica pueda usar la clase de
 * interfaz sin problemas de acceso.
 */
public class Logica extends Interfaz implements ActionListener {
	
	/** The retirarse. */
	private JButton pasar, subir, igualar, retirarse;
	
	/** The baraja. */
	private Baraja baraja; 
 /**  Esta es el objeto de baraja a usar en todo el juego. */
	private Jugador humano, pc;
	
	/** The fase. */
	private String fase;
	
	/** The apuesta ronda. */
	private int apuestaActual, apuestaRonda;
	
	/** The Constant CARTAALTA. */
	public static final int CARTAALTA = 1;
	
	/** The Constant PAR. */
	public static final int PAR = 2;
	
	/** The Constant DOBLEPAR. */
	public static final int DOBLEPAR = 3;
	
	/** The Constant TRIO. */
	public static final int TRIO = 4;
	
	/** The Constant ESCALERA. */
	public static final int ESCALERA = 5;
	
	/** The Constant COLOR. */
	public static final int COLOR = 6;
	
	/** The Constant FULLHOUSE. */
	public static final int FULLHOUSE = 7;
	
	/** The Constant CUATRUPLETA. */
	public static final int CUATRUPLETA = 8;
	
	/** The Constant ESCALERACOLOR. */
	public static final int ESCALERACOLOR = 9;
	
	/** The Constant ESCALERAREAL. */
	public static final int ESCALERAREAL = 10;

	/**
	 * Instantiates a new logica.
	 */
	/*
	 * El constructor de la clase nos inicia algunas variables y nos crea objetos
	 *  que vamos a necesitar durante todo el juego
	*/
	public Logica() {
		baraja = new Baraja();
		humano = new Jugador("humano");
		pc = new Jugador("pc");
		apuestaRonda = 0;
	}

	/**
	 * Ajusta los botones que se van a usar para las acciones del poker. 
	 * se inicializan en esta clase porque aquí vamos a manejar las acciones de estos.
	 */
	public void setBotones() {
		igualar = new JButton();
		igualar.addActionListener(this);// escucha
		igualar.setIcon(imagenBoton("igual"));
		igualar.setPreferredSize(new Dimension(108, 36));
		igualar.setBorder(BorderFactory.createEmptyBorder());
		igualar.setContentAreaFilled(false);
		igualar.setFocusPainted(false);
		
		super.addBoton(igualar);
		retirarse = new JButton();
		retirarse.addActionListener(this); // escucha
		retirarse.setIcon(imagenBoton("fold"));
		retirarse.setPreferredSize(new Dimension(108, 36));
		retirarse.setBorder(BorderFactory.createEmptyBorder());
		retirarse.setContentAreaFilled(false);
		retirarse.setFocusPainted(false);
		super.addBoton(retirarse);
		
		pasar = new JButton();
		pasar.addActionListener(this);// escucha
		pasar.setIcon(imagenBoton("pasar"));
		pasar.setPreferredSize(new Dimension(108, 36));
		pasar.setBorder(BorderFactory.createEmptyBorder());
		pasar.setContentAreaFilled(false);
		pasar.setFocusPainted(false);
		
		super.addBoton(pasar);
		subir = new JButton();
		subir.addActionListener(this);// escucha
		subir.setIcon(imagenBoton("subir"));
		subir.setPreferredSize(new Dimension(108, 36));
		subir.setBorder(BorderFactory.createEmptyBorder());
		subir.setContentAreaFilled(false);
		subir.setFocusPainted(false);
		super.addBoton(subir);
	}

	/**
	 * Este método limpia la mano de los jugadores y les asigna dos nuevas cartas
	 * tomadas de la baraja al azar.
	 */
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

	/**
	 * Checks if is numeric.
	 *
	 * @param cadena the cadena
	 * @return true, if is numeric
	 */
	/*
	 *  método usado para asegurarnos de que el usuario digite un número y no
	 *  un String en el JOptionPane  
	 */
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

	/**
	 * Muestra un InputDialog de JOptionPane para preguntar por la apuesta Este
	 * metodo retorna booleano porque esa es la manera en la que sabremos si se
	 * ingresaron datos validos para poder realizar las jugadas que dependen de que
	 * la apuesta se haya hecho.
	 *
	 * @return true, if successful
	 */
	public boolean mensajePedirApuesta() {
		boolean apuestaEfectuada = false; // Por ahora no se ha efectuado la apuesta
		// String auxBote = bote;
		String stringApuesta = JOptionPane.showInputDialog(null,
				"¿Cuánto quieres apostar? Tienes: " + humano.getBalance());
		if (stringApuesta != null) {
			apuestaActual = Integer.parseInt(stringApuesta);

			if (isNumeric(stringApuesta) == true) {
				int valorObtenido = Integer.valueOf(apuestaActual);

				if (valorObtenido <= 0 || valorObtenido > humano.getBalance()) {

					JOptionPane.showMessageDialog(null, "Digite un numero valido", "Alerta",
							JOptionPane.WARNING_MESSAGE);
					mensajePedirApuesta();
				}

				else {
					int boteNuevo = (Integer.parseInt(bote) + (apuestaActual * 2));
					apuestaEfectuada = true;
					apuestaRonda += apuestaActual;
					bote = Integer.toString(boteNuevo);

					humano.setApuesta(humano.getApuesta() + apuestaRonda);

					humano.restarDinero(Integer.valueOf(valorObtenido));
					pc.setApuesta(pc.getApuesta() + apuestaRonda);
					pc.restarDinero(Integer.valueOf(valorObtenido));
				}
			}

			else {
				JOptionPane.showMessageDialog(null, "Digite un numero valido", "Alerta", JOptionPane.WARNING_MESSAGE);
				mensajePedirApuesta();
			}
		} else {
			JOptionPane.showMessageDialog(null, "No ingresaste apuesta", "Alerta", JOptionPane.WARNING_MESSAGE);
		}
		return apuestaEfectuada;
	}

	/**
	 * Mensaje pedir primera apuesta.
	 */
	/* Muestra un mensaje en pantalla pidiendo la apuesta del jugador, verifica que
	*  el usuario digite un número válido, de lo contrario muestra un mensaje de error y si da en el boton
	*  cancelar del ShowInputDialog el programa termina
	*/
	public void mensajePedirPrimeraApuesta() {
		String entrada = JOptionPane.showInputDialog(null, "¿Cuánto quieres apostar? Tienes: " + humano.getBalance());
		if (entrada == null) {
			System.exit(0);
		} else {
			if (isNumeric(entrada) == true) {
				int valorObtenido = Integer.valueOf(entrada);
				if (valorObtenido <= 0 || valorObtenido > humano.getBalance()) {

					JOptionPane.showMessageDialog(null, "Digite un numero valido", "Alerta",
							JOptionPane.WARNING_MESSAGE);
					mensajePedirPrimeraApuesta();
				} else {
					bote = Integer.toString(Integer.parseInt(bote) + (Integer.parseInt(entrada) * 2));
					apuestaRonda = Integer.parseInt(bote) / 2;
					humano.setApuesta(humano.getApuesta() + apuestaRonda);

					humano.restarDinero(Integer.valueOf(valorObtenido));
					pc.setApuesta(pc.getApuesta() + apuestaRonda);

					pc.restarDinero(Integer.valueOf(valorObtenido));

				}
			}

			else {
				JOptionPane.showMessageDialog(null, "Digite un numero valido", "Alerta", JOptionPane.WARNING_MESSAGE);
				mensajePedirPrimeraApuesta();
			}

		}
	}

	/**
	 *  Pide apuestas y pinta el flop.
	 */
	public void primeraFase() {
		mensajePedirPrimeraApuesta();
		pintarInfo(pc); // para que actualicen las infos
		pintarInfo(humano);
		actualizarPantalla();
		pintarFlop();
		fase = "Flop";
	}

	/**
	 *  Empieza la fase turn poniendo la cuarta carta.
	 */
	public void faseTurn() {
		pintarProximaFase();

		fase = "Turn"; // Cambia el estado de la fase a "Turn"
	}

	/**
	 * Fase river.
	 */
	public void faseRiver() {
		pintarProximaFase();
		fase = "River";
	}

	/**
	 * Añade la carta del Turn a la baraja "invisible" de los jugadores, además
	 * pinta el componente en el tablero y actualiza.
	 */
	public void pintarProximaFase() {
		Carta cartaTurn = baraja.darCartaAlAzar();
		JButton turn = crearComponenteDeMano(cartaTurn);
		areaTableroCartas.add(turn);
		humano.tomarCarta(cartaTurn);
		pc.tomarCarta(cartaTurn);
		actualizarPantalla();
	}

	/**
	 * Inicia el juego.
	 */
	public void jugar() {

		setEntorno();
		setBotones();
		baraja = new Baraja();
		repartirCartas();
		dibujarMano(humano);
		dibujarMano(pc);
		pintarInfo(humano);
		pintarInfo(pc);
		actualizarPantalla();
		// PRIMERA RONDA DE APUESTAS, pide apuestas y pinta el flop
		primeraFase();
	}

	// Dar el flop (tres cartas a la mesa), pone los componentes sobre la mesa y se
	/**
	 * Pintar flop.
	 */
	// agregan esas cartas a las manos de cada jugador.
	public void pintarFlop() {
		for (int i = 0; i < 3; i++) {
			Carta cartaFlop = baraja.darCartaAlAzar();
			JButton flop = crearComponenteDeMano(cartaFlop);
			areaTableroCartas.add(flop);
			humano.tomarCarta(cartaFlop);
			pc.tomarCarta(cartaFlop);
		}
		actualizarPantalla();
	}

	/**
	 *  Inicializa la próxima fase dependiendo de la fase en la que se esté.
	 */
	public void proximaFase() {
		apuestaRonda = 0;
		pintarInfo(humano);
		switch (fase) {
		case "Flop": // Pasa a la fase "Turn"
			faseTurn();
			break;

		case "Turn": // Pasa a la fase "River"
			faseRiver();
			break;

		case "River": // Termina la mano, muestra las cartas del pc y determina al ganador

			analizarJugadas(humano);
			analizarJugadas(pc);
			mostrarCartasPc();
			determinarGanador();

			break;
		}
		actualizarPantalla();

	}

	/**
	 * Método usado para mostrar las cartas del pc que están volteadas 
	 * durante el juego, se utiliza cuando el juego termina.
	 */
	public void mostrarCartasPc() {
		JButton carta1 = crearComponenteDeMano(pc.getCartaMano(0));
		JButton carta2 = crearComponenteDeMano(pc.getCartaMano(1));
		areaPcCartas.removeAll();
		areaPcCartas.add(carta1, BorderLayout.CENTER);
		areaPcCartas.add(carta2, BorderLayout.CENTER);
		actualizarPantalla();
	}

	/** Verifica que el usuario todiavía tenga dinero para jugar y entonces
	 * muestra un mensaje en pantalla preguntando si el usuario quiere volver a
	 * jugar, en caso de que no tenga dinero o presione el boton "no" 
	 * de el ShowComfirmDialog el programa acaba.
	 */
	public void JugarDeNuevo() {
		if (humano.getBalance() > 0) {
			int resp = JOptionPane.showConfirmDialog(null, "quieres volver a Jugar?");
			if (resp == 0) {
				humano.reiniciarBalance();
				pc.reiniciarBalance();
				LimpiarInterfaz();
				jugar();
			} else {
				System.exit(0);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Ya no tienes dinero para seguir jugando", "Mensaje",
					JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
	}


	/**
	 * Este método verifica cual es la carta más alta de la mano del jugador y se la agrega a su jugada.
	 *
	 * @param jugador the jugador
	 */
	public void analizarCartaAlta(Jugador jugador) {
		List<Carta> cartas = new ArrayList<Carta>(); // crea una lista de las cartas porque hay un problema si le
														// pasamos
														// directamente la mano al sort
		for (int i = 0; i < jugador.getMano().manoSize(); i++) {
			cartas.add(jugador.getMano().getCarta(i));
		}

		Collections.sort(cartas, Collections.reverseOrder()); // ordena todas las cartas con respecto a su valor de
																// forma descendente por lo que la carta más alta sería
																// la primera

		System.out.println(cartas.get(0).valorCarta() + " Alto/a");
		int jugada[] = { CARTAALTA, 1 };
		jugador.setJugada(jugada);
	}

	
	/**
	 * este método nos analiza las posibles escaleras que puede llegar a tener un jugador tales como
	 * Escalera, Escalera Real De Color, además tambien nos sirve para ayudarnos a ver si hay un Color.
	 * la jugada que se obtenga se agrega a la jugada del jugador.
	 *
	 * @param jugador the jugador
	 */
	public void analizarEscaleras(Jugador jugador) {
		if (listaOrdenada(jugador.getMano()) == null) {
			analizarColor(jugador);  /* cuando la lista ordenada nos retorna un null significa que en la mano del jugador 
			no hay 5 cartas consecutivas por lo que se procede a analizar si hay un color el cual no necesita que haya cartas cosecutivas, solo que
			los palos de las cartas sean iguales*/
		} else {
			List<Carta> cartas = new ArrayList<Carta>();
			cartas = listaOrdenada(jugador.getMano()); // recibimos la lista de 5 cartas ordenadas y consecutivas a analizar
			// se verifica que no empiece en 9 porque en ese caso seria una escalera real de color
			if ((palosIguales(cartas) == true & cartas.get(0).obtenerCosto() != 9)) {
				System.out.println("Escalera de color");
				int jugada[] = { ESCALERACOLOR, 1 };
				jugador.setJugada(jugada);
			}
			/*como la lista que estamos analizando es consecutiva, si no tiene los palos iguales entonces es una escalera normal*/
			else if (palosIguales(cartas) == false) {
				System.out.println("Escalera normal xd");
				int jugada[] = { ESCALERA, 1 };
				jugador.setJugada(jugada);
			}
			// caso en el que empieza en 9 pero no tinen el mismo palo, entonces es una
			// escalera normal
			else if (cartas.get(0).obtenerCosto() == 9 & palosIguales(cartas) == false) {
				System.out.println("Escalera normal xd");
				int jugada[] = { ESCALERA, 1 };
				jugador.setJugada(jugada);
			}
			// como las cartas estan ordenadas, y hay 5, si empieza en 10, seguirian
			// 11(J),12(Q),13(Q),14(As) si son del mismo palo entonces
			// es una escalera real de color
			else if (cartas.get(0).obtenerCosto() == 9 & palosIguales(cartas) == true) {
				int jugada[] = { ESCALERAREAL, 1 };
				jugador.setJugada(jugada);
				System.out.println("Escalera REAL DE COLOR");
				imprimeArrayPersonas(cartas);
			}

		}
	}

	// recibe la mano del jugador, la ordena y verifica en que parte ese lista hay 5
	// valores consecutivos,
	// nos retorna esa lista ordenanda de valores
	/**
	 * Lista ordenada.
	 *
	 * @param mano the mano
	 * @return the list ordenada y consecutiva
	 */
	// consecutivos
	public List<Carta> listaOrdenada(Mano mano) { // mirar caso en el que las dos o tres listas tienen consecutivos??
		// primero llena tres listas, con la misma mano
		List<Carta> cartas = new ArrayList<Carta>(); // crea una lista de las cartas por
		// que hay un problema si le pasamos directamente la mano al sort
		for (int i = 0; i < mano.manoSize(); i++) {
			cartas.add(mano.getCarta(i));
		}
		Collections.sort(cartas); // Ordena las cartas

		List<Carta> cartasSinPrimeras = new ArrayList<Carta>();
		for (int i = 0; i < 5; i++) {
			cartasSinPrimeras.add(cartas.get(2 + i));
		}

		List<Carta> cartasSinUltimas = new ArrayList<Carta>();
		for (int i = 0; i < 5; i++) {
			cartasSinUltimas.add(cartas.get(i));
		}
		List<Carta> cartasSinLaterales = new ArrayList<Carta>();
		for (int i = 0; i < 5; i++) {
			cartasSinLaterales.add(cartas.get(1 + i));
		}
		if (probarConsecutivos(cartasSinPrimeras) == true)
			return cartasSinPrimeras;
		else if (probarConsecutivos(cartasSinLaterales) == true)
			return cartasSinLaterales;
		else if (probarConsecutivos(cartasSinUltimas) == true)
			return cartasSinUltimas;
		else
			return null; // en caso de que retorne null significa que no hay escaleras de ningun tipo pero puede haber un color 
	}

	// vetifica que los palos de una lista de cartas sean todos iguales (solo para
	/**
	 * Palos iguales.
	 *
	 * @param cartas the cartas
	 * @return true, if successful
	 */
	// una lista de 5)
	public boolean palosIguales(List<Carta> cartas) {
		if (cartas.get(0).getPalo() == cartas.get(1).getPalo() & cartas.get(1).getPalo() == cartas.get(2).getPalo()
				& cartas.get(2).getPalo() == cartas.get(3).getPalo()
				& cartas.get(3).getPalo() == cartas.get(4).getPalo()) {
			return true;
		} else {
			return false;
		}
	}

	// verifica, en una lista ordenada de cartas ordenadas por valor que sus valores
	/**
	 * Probar consecutivos.
	 *
	 * @param arreglo the arreglo
	 * @return true, if successful
	 */
	// sean consecutivos.
	public static boolean probarConsecutivos(List<Carta> arreglo) {

		for (int i = 1; i < arreglo.size(); i++) {
			if ((arreglo.get(i).obtenerCosto() - 1) != arreglo.get(i - 1).obtenerCosto()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Imprime array personas.
	 *
	 * @param array the array
	 */
	// imprime un array para verificar xd borrar cuando ya no se necesite
	public void imprimeArrayPersonas(List<Carta> array) {
		for (int i = 0; i < array.size(); i++) {
			System.out.println((i + 1) + ". " + array.get(i).mostrarCarta());
		}
	}

	// hay que comprobar si hay color solo despues de que se compruebe de que no hay
	/**
	 * Analizar color.
	 *
	 * @param jugador the jugador
	 */
	// escaleras.
	public void analizarColor(Jugador jugador) {
		int[] palos = new int[4]; /*se crea un array de 4 que nos representaria todos los palos posibles*/
		// Llena de ceros el array
		for (int i = 0; i < 4; i++) {
			palos[i] = 0;
		}
		// Con este for va contando cuantas cartas de cada palo tiene
		for (int i = 0; i < 7; i++) {
			int valor = jugador.getMano().getCarta(i).getPalo();
			palos[valor] = palos[valor] + 1; /*Se suma uno a cada posición del array que representa un palo por cada carta de ese palo que haya
			en la mano del jugador por eso el for va a hasta 7*/
		}
		/*Este for nos verifica en que palo hay más de 5 cartas, y si lo hay significa que el jugador tiene un color y lo
		 * agrega a su jugada*/
		for (int pos = 0; pos < 4; pos++) {
			if (palos[pos] >= 5) {
				System.out.println("Color");
				int jugada[] = { COLOR, 1 };
				jugador.setJugada(jugada);
			}
		}
	}

	// verifica si hay pares(pair), tríos(three of a kind), cuatrupletas(Poker), par
	/**
	 * Analizar repetidas.
	 *
	 * @param jugador the jugador
	 */
	// doble, o un full house (trio + par)
	public void analizarRepetidas(Jugador jugador) {
		int[] cartas = new int[14]; /*se crea un array de 14 que nos representaria todos los valores de cartas posibles*/
		int pares = 0;
		boolean trio = false;
		String manoEncontrada;
		String trioDe = "??";
		String par = "??";
		int par1 = 0;
		int par2 = 0;
		int cuatrupleta;
		String pokerDe = "??";
		// Llena de ceros el array
		for (int i = 0; i < 14; i++) {

			cartas[i] = 0;
		}
		// Con este for va contando cuantas cartas de cada valor tiene
		for (int i = 0; i < 7; i++) {
			int valor = jugador.getMano().getCarta(i).getValor();
			cartas[valor] = cartas[valor] + 1;
		}

		// procedemos a construir el bucle que nos determina si es par, trio o
		// cuatrupleta
		for (int pos = 1; pos < 14; pos++) {
			if (cartas[pos] == 2) {
				pares = +1;
				if (par1 == 0) {
					par1 = pos;
				} else
					par2 = pos;

			} else if (cartas[pos] == 3) {
				trioDe = Carta.obtenerValorCarta(pos);
				trio = true;
			} else if (cartas[pos] == 4) {
				manoEncontrada = "Cuatrupleta de " + Carta.obtenerValorCarta(pos);
				int jugada[] = { CUATRUPLETA, pos };
				jugador.setJugada(jugada);
			}
		}
		// verifica si hay un FULL HOUSE
		if (pares == 1 & trio == true) {
			System.out.println("Full House");
			int jugada[] = { FULLHOUSE, 1 };
			jugador.setJugada(jugada);
		}

		else {
			if (trio == true) {// imprime el trio, si lo hay
				System.out.println("Trío de: " + trioDe);
				int jugada[] = { TRIO, 1 };
				jugador.setJugada(jugada);
			}

			// verifica cuantos pares hay
			else {
				switch (pares) {
				case 0:
					break;
				case 1:
					System.out.println("Par de " + Carta.obtenerValorCarta(par1));
					int jugada[] = { PAR, par1 };
					jugador.setJugada(jugada);
					break;
				case 2:
					System.out.println(
							"Doble Par de " + Carta.obtenerValorCarta(par1) + " y " + Carta.obtenerValorCarta(par2));
					int jugadaDoblePar[] = { DOBLEPAR, (par2 + par1) };
					jugador.setJugada(jugadaDoblePar);
					break;
				}
			}

		}

	}

	/**
	 *  Escuchas que determinan el comportamiento del juego.
	 *
	 * @param e the evento
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == pasar) {
			// cambia de fase el juego, no pasa nada más
			System.out.println("Pasa");
			// Debe checar si se está en fase de flop, turn o river
			proximaFase();
		}
		if (e.getSource() == subir) {
			// igualar la apuesta que haya e incrementar lo que se quiera
			System.out.println("Sube");
			if (fase.equals("Turn") || fase.equals("River") || fase.equals("Flop")) {
				if (mensajePedirApuesta()) {
					pintarInfo(pc); // para que actualicen las infos
					pintarInfo(humano);
					proximaFase();
					actualizarPantalla();					
				}
			}
		}
		if (e.getSource() == retirarse) {// se retira de la mano, mas no del juego, se le da todo lo del bote al pc y se
											// inicia una nueva mano.
											// gana el computador y se reinicia el juego, el juego acaba cuando alguno se
											// quede sin dinero
			System.out.println("Se retira");
			System.out.println("El pc antes del adicionarDinero() tiene " + +pc.getBalance() + " y el bote es de "
					+ Integer.parseInt(bote));
			pc.adicionarDinero(Integer.valueOf(bote));
			bote = "0"; //se reincia el bote ya que lo que había antes se lo lleva el pc
			humano.reiniciarApuesta();
			pc.reiniciarApuesta();
			JOptionPane.showMessageDialog(null, "Tú oponente ganó porque te retiraste.");
			// Debe seguir jugando (se inicia una nueva mano), el juego no acaba hasta que
			// alguien se quede sin diner.
			LimpiarInterfaz();
			jugar();
		}
		if (e.getSource() == igualar) { /*El jugador pone en la mesa lo que el pc haya apostado anteriormente, mientras que el pc no hace n*/
			if (pc.getApuesta() <= humano.getBalance()) { /*Verifica que el jugador tenga el suficiente dinero para igualar, en caso de que no
			se muestra un mensaje advirtiéndole */
				System.out.println("Iguala");
				humano.restarDinero(pc.getApuesta());
				bote = Integer.toString(Integer.valueOf(bote) + pc.getApuesta());
				humano.setApuesta(humano.getApuesta() + pc.getApuesta());
				pintarInfo(pc); // para que actualicen las infos
				pintarInfo(humano);
				proximaFase();
				actualizarPantalla();			
			}

			else {
				JOptionPane.showMessageDialog(null,
						"No tienes suficiente dinero para igualar la apuesta de tu oponente", "Alerta",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Analiza todas las posibles jugadas de los jugadores.
	 *
	 * @param jugador the jugador
	 */
	public void analizarJugadas(Jugador jugador) {
		analizarCartaAlta(jugador);
		analizarRepetidas(jugador);
		analizarEscaleras(jugador);

	}

	/**
	 * Obtiene la jugada que el jugador tenga en su atributo "Jugada".
	 *
	 * @param jugador the jugador
	 * @return la jugada
	 */
	public String sacarJugada(Jugador jugador) {
		int jugada[] = jugador.getJugada();
		switch (jugada[0]) {
		case CARTAALTA:
			return "Carta alta";
		case PAR:
			return "Par";
		case DOBLEPAR:
			return "Doble par";
		case TRIO:
			return "Trío";
		case ESCALERA:
			return "Escalera";
		case COLOR:
			return "Color";
		case FULLHOUSE:
			return "Full house";
		case CUATRUPLETA:
			return "Cuatrupleta";
		case ESCALERACOLOR:
			return "Escalera de Color";
		case ESCALERAREAL:
			return "Escalera Real";
		default:
			return "???";
		}
	}

	/**
	 * Determina el ganador del juego basado en las reglas del Poker Texas Hold'em.
	 */
	public void determinarGanador() {
		for (int i = 0; i < 2; i++) {

			if (humano.getJugada()[i] > pc.getJugada()[i]) {
				JOptionPane.showMessageDialog(null, "Ganas con " + sacarJugada(humano), "Alerta",
						JOptionPane.WARNING_MESSAGE);
				humano.adicionarDinero(Integer.parseInt(bote));
				break;

			} else if (humano.getJugada()[i] < pc.getJugada()[i]) {
				JOptionPane.showMessageDialog(null, "El PC gana con: " + sacarJugada(pc), "Alerta",
						JOptionPane.WARNING_MESSAGE);
				pc.adicionarDinero(Integer.parseInt(bote));
				break;
			}

			else if (humano.getJugada()[1] == pc.getJugada()[1]) {
				JOptionPane.showMessageDialog(null, "EMPATE (POR AHORA) " + sacarJugada(pc), "Alerta",
						JOptionPane.WARNING_MESSAGE);
			}

		}
	}

}