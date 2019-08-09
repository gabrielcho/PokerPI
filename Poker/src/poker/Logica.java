package poker;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import java.lang.Exception;

/**
 * Por razones de estructuración necesitamos que la clase Lógica herede de
 * interfaz, esto permite que todo manejo de lógica pueda usar la clase de
 * interfaz sin problemas de acceso.
 * <h3>Las etiquetas HTML funcionan en el javadoc wtf</h3> y tambien en los
 * jlabels xd
 */
public class Logica extends Interfaz implements ActionListener {
	/** Esta es el objeto de baraja a usar en todo el juego */
	// Hay que tener en cuenta más adelante que esta baraja tiene que reiniciarse
	// Por lo que toca poner un método de reinicio en la baraja.
	private JButton pasar, subir, igualar, retirarse;
	private Baraja baraja;
	private Jugador humano, pc;
	private String fase;
	private int apuestaActual, boteNuevo, apuestaRonda;

	public Logica() {
		baraja = new Baraja();
		humano = new Jugador("humano");
		pc = new Jugador("pc");
		setBotones();
		apuestaRonda = 0;
	}

	/**
	 * Ajusta los botones que se van a usar para las acciones del poker. NOTA:::::
	 * Voy a dejarlo asi por ahora porque no se me ocurre como hacer un metodo que
	 * sea compatible con la implementacion de escuchas se puede pero al final
	 * quedaria igual y esa no es la gracia.
	 */
	public void setBotones() {
		igualar = new JButton();
		igualar.addActionListener(this);
		igualar.setIcon(imagenBoton("igual"));
		igualar.setPreferredSize(new Dimension(108, 36));
		igualar.setBorder(BorderFactory.createEmptyBorder());
		igualar.setContentAreaFilled(false);
		igualar.setFocusPainted(false);
		// escucha
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
		pasar.addActionListener(this);
		pasar.setIcon(imagenBoton("pasar"));
		pasar.setPreferredSize(new Dimension(108, 36));
		pasar.setBorder(BorderFactory.createEmptyBorder());
		pasar.setContentAreaFilled(false);
		pasar.setFocusPainted(false);
		// escucha
		super.addBoton(pasar);
		subir = new JButton();
		subir.addActionListener(this);
		subir.setIcon(imagenBoton("subir"));
		subir.setPreferredSize(new Dimension(108, 36));
		subir.setBorder(BorderFactory.createEmptyBorder());
		subir.setContentAreaFilled(false);
		subir.setFocusPainted(false);
		// escucha
		super.addBoton(subir);
	}

	/**
	 * Este método limpia la mano de los jugadores y les asigna dos nuevas cartas
	 * tomadas de la baraja al azar
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

	// metodo usado para asegurarnos de que el usuario digite un numero cuando se le
	// pide la apuesta
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
	 */
	public boolean mensajePedirApuesta() {
		boolean apuestaEfectuada = false;
		String auxBote = bote;
		String stringApuesta = JOptionPane.showInputDialog(null,
				"¿Cuánto quieres apostar? Tienes: " + humano.getBalance());
		if (stringApuesta != null) {
			apuestaActual = Integer.parseInt(stringApuesta);

			int boteNuevo = (Integer.parseInt(bote) + (apuestaActual * 2));

			if (isNumeric(stringApuesta) == true) {
				int valorObtenido = Integer.valueOf(apuestaActual);
				System.out.println("El valorObtenido es: " + valorObtenido);
				if (valorObtenido <= 0 || valorObtenido > humano.getBalance()) {
					System.out.println("no pasa el filtro");
					bote = auxBote;
					JOptionPane.showMessageDialog(null, "Digite un numero valido", "Alerta",
							JOptionPane.WARNING_MESSAGE);
					mensajePedirApuesta();
				}

				else {
					apuestaEfectuada = true;
					apuestaRonda += apuestaActual;
					bote = Integer.toString(boteNuevo);
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

	/*
	 * if (bote.equals("0"))
	 * 
	 * else { apuestaActual = Integer.valueOf(JOptionPane.showInputDialog(
	 * null,"¿Cuánto quieres apostar? Tienes: " + humano.getBalance())); boteNuevo =
	 * Integer.valueOf(bote) + apuestaActual; bote = Integer.toString(boteNuevo); }
	 */

	// Muestra un mensaje en pantalla pidiendo la apuesta del jugador, verifica que
	// el usuario digite un número, de lo contrario muestra un mensaje de error.
	public void mensajePedirPrimeraApuesta() {
		String auxBote = bote;
		bote = JOptionPane.showInputDialog(null, "¿Cuánto quieres apostar? Tienes: " + humano.getBalance());
		if (bote == null) {
			System.exit(0);
		} else {
			if (isNumeric(bote) == true) {
				int valorObtenido = Integer.valueOf(bote);
				if (valorObtenido <= 0 || valorObtenido > humano.getBalance()) {
					bote = auxBote;
					JOptionPane.showMessageDialog(null, "Digite un numero valido", "Alerta",
							JOptionPane.WARNING_MESSAGE);
					mensajePedirPrimeraApuesta();
				} else {
					bote = Integer.toString(Integer.parseInt(bote) * 2);
					apuestaRonda = Integer.parseInt(bote) / 2;
				}
			}

			else {
				JOptionPane.showMessageDialog(null, "Digite un numero valido", "Alerta", JOptionPane.WARNING_MESSAGE);
				mensajePedirPrimeraApuesta();
			}

		}
	}

	/** Pide apuestas y pinta el flop */
	public void primeraFase() {
		mensajePedirPrimeraApuesta();
		humano.setApuesta(Integer.parseInt(bote) / 2);
		System.out.println("El bote debe ser de 1 pero es de: " + bote);
		humano.restarDinero(humano.getApuesta());
		pc.setApuesta(Integer.parseInt(bote));// al principio el pc siempre iguala la apuesta del jugador
		pc.restarDinero(Integer.parseInt(bote) / 2);
		bote = Integer.toString((Integer.valueOf(bote) + pc.getApuesta()));
		pintarInfo(pc); // para que actualicen las infos
		pintarInfo(humano);
		actualizarPantalla();
		pintarFlop();
		fase = "Flop";
	}

	/** Empieza la fase turn poniendo la cuarta carta */
	public void faseTurn() {
		pintarProximaFase();

		fase = "Turn"; // Cambia el estado de la fase a "Turn"
	}

	public void faseRiver() {
		pintarProximaFase();
		fase = "River";
	}

	/**
	 * Añade la carta del Turn a la baraja "invisible" de los jugadores, además
	 * pinta el componente en el tablero y actualiza
	 */
	public void pintarProximaFase() {
		Carta cartaTurn = baraja.darCartaAlAzar();
		JButton turn = crearComponenteDeMano(cartaTurn);
		areaTableroCartas.add(turn);
		humano.tomarCarta(cartaTurn);
		pc.tomarCarta(cartaTurn);
		revalidate();
		repaint();
	}

	/**
	 * Inicia el juego (Queda pendiente decidir si al ejecutar este método el juego
	 * se reinicia de cero o sólo permite otra mano más) Esta vez no tenemos bucle
	 * de juego porque se supone que por el caracter de botones el juego tiene que
	 * hacerse con una estructura orientada a objetos
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

		// PRIMERA RONDA DE APUESTAS, pide apuestas y pinta el flop
		primeraFase();
		// Después de esta primera fase y ya pintado el flop, queda el flujo del juego a
		// manos de los botones

		// humano.tomarCarta(new Carta(1,2));
		// humano.tomarCarta(new Carta(1,2));
		// pc.tomarCarta(new Carta(3,3));
		// pc.tomarCarta(new Carta(3,3)); // para hacer pruebas del método Analizar
		// Repetidas
		//
		// pintarFlop();
		//
		// analizarRepetidas(humano.getMano());
		// analizarRepetidas(pc.getMano());
		// SEGUNDA RONDA DE APUESTAS

		// pierde y sale del bucle de juego esto debe ser un metodo que verifique las
		// manos xd
		/*
		 * if(humano.getBalance()>0 & pc.getBalance()>0) { // pierde o gana la mano, mas
		 * no el juego. el jugo se gana cuando alguno de los dos quede sin dinero
		 * LimpiarInterfaz(); jugar(); } else { //preguntar si quiere iniciar otra
		 * partida desde 0 JugarDeNuevo(); }
		 */

	}

	// Dar el flop (tres cartas a la mesa), pone los componentes sobre la mesa y se
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

	/** Inicializa la próxima fase dependiendo de la fase en la que se esté00 */
	public void proximaFase() {
		apuestaRonda = 0;
		humano.setApuesta(0);
		pc.setApuesta(0);
		pintarInfo(humano);
		switch (fase) {
		case "Flop": // Pasa a la fase "Turn"
			faseTurn();
			break;

		case "Turn": // Pasa a la fase "River"
			faseRiver();
			break;

		case "River": // Termina la mano y determina al ganador con alguna funcion que envuelva todo

			break;
		}
		actualizarPantalla();

	}

	// Muestra un mensaje en pantalla preguntando si el usuario quiere volver a
	// jugar
	//////////////////////////////////// verificar si esta bien y cuando
	// usarlo\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public void JugarDeNuevo() {
		if (humano.getBalance() > 0) {
			int resp = JOptionPane.showConfirmDialog(null, "�quieres volver a Jugar?");
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

	//////////////////////// PENDIENTES PARA IMPLEMENTAR LUEGO
	//////////////////////// \\\\\\\\\\\\\\\\\\\\\\\\\\\\
	// Puede borrarse si es necesario
	// verifica si hay pares(pair), tríos(three of a kind), cuatrupletas(Poker), par
	//////////////////////// doble, o un full house (trio + par)
	public void analizarRepetidas(Mano mano) {
		int[] cartas = new int[14];
		int pares = 0;
		boolean trio = false;
		// Llena de ceros el array
		for (int i = 0; i < 14; i++) {

			cartas[i] = 0;
		}
		// Con este for va contando cuantas cartas de cada valor tiene
		for (int i = 0; i < 7; i++) {
			int valor = mano.getCarta(i).getValor();
			cartas[valor] = cartas[valor] + 1;
		}

		// procedemos a construir el bucle que nos determina si es par, trio o
		// cuatrupleta
		for (int pos = 1; pos < 14; pos++) {
			System.out.println(cartas[pos]);
			if (cartas[pos] == 2)
				pares = +1;
			else if (cartas[pos] == 3)
				trio = true;
			else if (cartas[pos] == 4)
				System.out.println("Poker");
		}
		System.out.println("Fin bucle");
		// verifica si hay un FULL HOUSE
		if (pares == 1 & trio == true) {
			System.out.println("Full House");
		}

		else {
			if (trio == true) {// imprime el trio, si lo hay
				System.out.println("Trío");
			}

			// verifica cuantos pares hay
			else {
				switch (pares) {
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

	/** Escuchas que determinan el comportamiento del juego */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == pasar) {
			// cambiar turno?? hace algo el cumputador dependiendo de su jugada??
			System.out.println("Pasa");
			// Debe checar si se está en fase de flop, turn o river
			proximaFase();
		}
		if (e.getSource() == subir) {
			// igualar la apuesta que haya e incrementar lo que se quiera
			System.out.println("Sube");
			if (fase.equals("Turn") || fase.equals("River") || fase.equals("Flop")) {
				if (mensajePedirApuesta()) {
					humano.setApuesta(apuestaRonda);
					humano.restarDinero(humano.getApuesta());
					pc.setApuesta(apuestaRonda);// al principio el pc siempre iguala la apuesta del jugador
					pc.restarDinero(pc.getApuesta());
					pintarInfo(pc); // para que actualicen las infos
					pintarInfo(humano);
					actualizarPantalla();
				}
			}
		}
		if (e.getSource() == retirarse) {
			// gana el computador y se reinicia el juego, el juego acaba cuando alguno se
			// quede sin dinero
			System.out.println("Se retira");

			// El computador gana automáticamente y muestra sus cartas

			// Luego debe preguntar si se quiere volver a jugar
		}
		if (e.getSource() == igualar) {
			System.out.println("Iguala");

			// Verifica cuánto tiene apostado el pc en la mesa e iguala esa cantidad en
			// función de la apuesta del jugador
			// Algo tipo (apuestaPC)-(apuestaJugador) = Cantidad a igualar
		}
	}

}