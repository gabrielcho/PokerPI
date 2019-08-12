package poker;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

					/*
					 * } else JOptionPane.showMessageDialog(null, "Tienes que subir la apuesta!",
					 * "Alerta", JOptionPane.WARNING_MESSAGE);
					 */
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

	// Muestra un mensaje en pantalla pidiendo la apuesta del jugador, verifica que
	// el usuario digite un número, de lo contrario muestra un mensaje de error.
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

	/** Pide apuestas y pinta el flop */
	public void primeraFase() {
		mensajePedirPrimeraApuesta();
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
		setBotones();
		baraja = new Baraja();
		repartirCartas();
		dibujarMano(humano);
		dibujarMano(pc);
		pintarInfo(humano);
		pintarInfo(pc);
		revalidate();
		actualizarPantalla();
		// pruebaRepetidas();
		// PRIMERA RONDA DE APUESTAS, pide apuestas y pinta el flop
		primeraFase();
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
		// humano.setApuesta(0); //Las apeuestas se siguen acumulando no????
		// pc.setApuesta(0);
		pintarInfo(humano);
		switch (fase) {
		case "Flop": // Pasa a la fase "Turn"
			faseTurn();
			break;

		case "Turn": // Pasa a la fase "River"
			faseRiver();
			break;

		case "River": // Termina la mano y determina al ganador con alguna funcion que envuelva todo
			// analizarRepetidas(humano);
			// analizarRepetidas(pc);
			analizarRepetidas(humano);

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

	public void pruebaRepetidas() {
		int[] cartas = new int[14];
		int pares = 0;
		boolean trio = false;
		String manoEncontrada;
		String trioDe = "??";
		String parDe = "??";
		String pokerDe = "??";
		Mano mano = new Mano();
		mano.addCarta(new Carta(10, 1));
		mano.addCarta(new Carta(10, 2));
		mano.addCarta(new Carta(4, 1));
		mano.addCarta(new Carta(13, 3));
		mano.addCarta(new Carta(13, 2));
		mano.addCarta(new Carta(6, 1));
		mano.addCarta(new Carta(8, 3));

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
			if (cartas[pos] == 2) {
				pares += 1;
				parDe = Carta.obtenerValorCarta(pos);
			} else if (cartas[pos] == 3) {
				trioDe = Carta.obtenerValorCarta(pos);
				trio = true;
			} else if (cartas[pos] == 4)
				manoEncontrada = "Poker de " + Carta.obtenerValorCarta(pos);
		}
		// verifica si hay un FULL HOUSE
		if (pares == 1 & trio == true) {
			System.out.println("Full House");
		}

		else {
			if (trio == true) {// imprime el trio, si lo hay
				System.out.println("Trío de: " + trioDe);
			}

			// verifica cuantos pares hay
			else {
				switch (pares) {
				case 0:
					break;
				case 2:
					System.out.println("Doble Par de " + " y ");
					break;
				case 1:
					System.out.println("Par de " + parDe);
					break;

				}
			}

		}

		// return manoEncontrada;

	}

	//////////////////////// PENDIENTES PARA IMPLEMENTAR LUEGO
	//////////////////////// \\\\\\\\\\\\\\\\\\\\\\\\\\\\
	// Puede borrarse si es necesario
	// este metodo nos dice si la mano del jugador es una carta alta
	public void analizarCartaAlta(Mano mano) {
		List<Carta> cartas = new ArrayList<Carta>(); // crea una lista de las cartas porque hay un problema si le pasamos
												// directamente la mano al sort
		for (int i = 0; i < mano.manoSize(); i++) {
			cartas.add(mano.getCarta(i));
		}

		Collections.sort(cartas, Collections.reverseOrder()); // ordena todas las cartas con respecto a su valor de
																// forma descendente por lo que la carta más alta sería
																// la primera

		System.out.println(cartas.get(0).valorCarta() + " Alto/a");
	}

	public void analizarEscaleras(Mano mano) {
		if (listaOrdenada(mano) == null) {
			analizarColor(mano);
		} else {
			List<Carta> cartas = new ArrayList<Carta>();
			cartas = listaOrdenada(mano); // recibimos la lista de 5 cartas ordenadas a analizar
			// se verifica que no empiece en diez porque en ese caso seria una escalera real
			// de color
			if ((palosIguales(cartas) == true & cartas.get(0).obtenerCosto() != 9))
				// verifica si las 5
				// cartas que quedan son consecutivas, si lanza un true verificamos que sus
				// palos sean iguales si es así, tenemos una escalera de color(?)
				System.out.println("Escalera de color");

			else if (palosIguales(cartas) == false)
				System.out.println("Escalera normal xd");
			// caso en el que empieza en 10 pero no tinen el mismo palo, entonces es una
			// escalera normal
			else if (cartas.get(0).obtenerCosto() == 9 & palosIguales(cartas) == false)
				System.out.println("Escalera normal xd");
			// como las cartas estan ordenadas, y hay 5, si empieza en 10, seguirian
			// 11(J),12(Q),13(Q),14(As)?? si son del mismo palo entonces
			// es una escalera real de color
			else if (cartas.get(0).obtenerCosto() == 9 & palosIguales(cartas) == true)
				System.out.println("Escalera REAL DE COLOR");
			imprimeArrayPersonas(cartas);

		}
	}

	// recibe la mano del jugador, la ordena y verifica en que parte ese lista hay 5
	// valores consecutivos,
	// nos retorna esa lista ordenanda de valores
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
			return null; // en caso de que retorne null significa que no hay escaleras de ningun tipo
	}

	// vetifica que los palos de una lista de cartas sean todos iguales (solo para
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

	// verifica que en una lista ordenada de cartas ordenadas por valor, sus valores
	// sean consecutivos.
	public static boolean probarConsecutivos(List<Carta> arreglo) {

		for (int i = 1; i < arreglo.size(); i++) {
			if ((arreglo.get(i).obtenerCosto() - 1) != arreglo.get(i - 1).obtenerCosto()) {
				return false;
			}
		}
		return true;
	}

	// imprime un array para verificar xd borrar cuando ya no se necesite
	public void imprimeArrayPersonas(List<Carta> array) {
		for (int i = 0; i < array.size(); i++) {
			System.out.println((i + 1) + ". " + array.get(i).mostrarCarta());
		}
	}

	// hay que comprobar si hay color solo despues de que se compruebe de que no hay
	// escaleras.
	public void analizarColor(Mano mano) {
		int[] palos = new int[4];
		// Llena de ceros el array
		for (int i = 0; i < 4; i++) {
			palos[i] = 0;
		}
		// Con este for va contando cuantas cartas de cada palo tiene
		for (int i = 0; i < 7; i++) {
			int valor = mano.getCarta(i).getPalo();
			palos[valor] = palos[valor] + 1;
		}
		for (int pos = 0; pos < 4; pos++) {
			if (palos[pos] >= 5) {
				System.out.println("Color Flush");
			}
		}
	}

	// verifica si hay pares(pair), tríos(three of a kind), cuatrupletas(Poker), par
	//////////////////////// doble, o un full house (trio + par)
	public void analizarRepetidas(Jugador jugador) {
		int[] cartas = new int[14];
		int pares = 0;
		boolean trio = false;
		String manoEncontrada;
		String trioDe = "??";
		String parDe = "??";
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
				parDe = Carta.obtenerValorCarta(pos);
			} else if (cartas[pos] == 3) {
				trioDe = Carta.obtenerValorCarta(pos);
				trio = true;
			} else if (cartas[pos] == 4)
				manoEncontrada = "Poker de " + Carta.obtenerValorCarta(pos);
		}
		// verifica si hay un FULL HOUSE
		if (pares == 1 & trio == true) {
			System.out.println("Full House");
		}

		else {
			if (trio == true) {// imprime el trio, si lo hay
				System.out.println("Trío de: " + trioDe);
			}

			// verifica cuantos pares hay
			else {
				switch (pares) {
				case 0:
					break;
				case 1:
					System.out.println("Par de " + parDe);
					break;
				case 2:
					System.out.println("Doble Par de " + " y ");
					break;
				}
			}

		}

		// return manoEncontrada;

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
					pintarInfo(pc); // para que actualicen las infos
					pintarInfo(humano);
					actualizarPantalla();
				}
			}
		}
		if (e.getSource() == retirarse) {// se retira de la mano, mas no del juego, se le da todo lo del bote al pc y se
											// inicia una nueva mano.
			// gana el computador y se reinicia el juego, el juego acaba cuando alguno se
			// quede sin dinero
			System.out.println("Se retira");
			// no hay necesidad de mostrar las cartas, sea como sea gana esa mano, porque el
			// jugador se retiro.
			System.out.println("El pc antes del adicionarDinero() tiene " + +pc.getBalance() + " y el bote es de "
					+ Integer.parseInt(bote));
			pc.adicionarDinero(Integer.valueOf(bote));

			bote = "0";
			humano.reiniciarApuesta();
			pc.reiniciarApuesta();
			JOptionPane.showMessageDialog(null, "Tú oponente ganó porque te retiraste.");
			// Debe seguir jugando (se inicia una nueva mano), el juego no acaba hasta que
			// alguien se quede sin diner.
			LimpiarInterfaz();
			jugar();
		}
		if (e.getSource() == igualar) { // si el humano iguala, el pc no hace nada??
			if (pc.getApuesta() <= humano.getBalance()) {
				System.out.println("Iguala");
				humano.restarDinero(pc.getApuesta());
				bote = Integer.toString(Integer.valueOf(bote) + pc.getApuesta());// Verifica cuánto tiene apostado el pc
				// en la mesa e iguala esa cantidad
				// en
				// función de la apuesta del jugado // Algo tipo (apuestaPC)-(apuestaJugador) =
				// Cantidad a igualar
				humano.setApuesta(humano.getApuesta() + pc.getApuesta());
				pintarInfo(pc); // para que actualicen las infos
				pintarInfo(humano);
				actualizarPantalla();
				Mano mano = new Mano();
				mano.addCarta(new Carta(10, 1));
				mano.addCarta(new Carta(11, 1));
				mano.addCarta(new Carta(12, 1));
				mano.addCarta(new Carta(13, 1)); // para probar el metodo analizar escaleras
				mano.addCarta(new Carta(1, 1));
				mano.addCarta(new Carta(2, 1));
				mano.addCarta(new Carta(4, 1));
				analizarEscaleras(mano);
				analizarCartaAlta(mano);

			}

			else {
				JOptionPane.showMessageDialog(null,
						"No tienes suficiente dinero para igualar la apuesta de tu oponente", "Alerta",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

}