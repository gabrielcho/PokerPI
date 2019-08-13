/*
 * @Author Gabriel Arango
 * @Author Diego Timaná
 * @Version 1.0
 */
package poker;

// TODO: Auto-generated Javadoc
/**
 * The Class Jugador.
 */
public class Jugador {
  
  /** The balance. */
  private int balance;  // balance es el dinero que tiene el jugador 
  
  /** The mano. */
  private Mano mano;
  
  /** The name. */
  private String name;
  
  /** The apuesta. */
  private int apuesta;
  
  /** The jugada. */
  private int[] jugada;  // es un array de posiciones, el la primera posición tiene el tipo de jugada que se tinene, y en la segunda el peso de la 
  //jugada con respecto a las mismas jugadas (se utiliza para desempatar)
  // utlizo el nombre para diferenciar al pc del humano y as� crear un condicional
  // en la interfaz que me pinte las cartas
  // volteadas e caso de que sea el pc o normales en caso contrario

  /**
   * Inicializa el objeto de jugador, asignando un valor a su balance y a su mano
   * lista para alojar cartas.
   *
   * @param name the name
   */
  public Jugador(String name) {
    jugada = new int[2];
    this.name = name;
    apuesta = 0;
    mano = new Mano();
    balance = 10000;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the apuesta.
   *
   * @return the apuesta
   */
  public int getApuesta() {
    return apuesta;
  }

  /**
   * Sets the apuesta.
   *
   * @param valor the new apuesta
   */
  public void setApuesta(int valor) {
    apuesta = valor;
  }

  /**
   * Reiniciar balance.
   */
  public void reiniciarBalance() {
    balance = 10000;
  }

  /**
   * Reiniciar apuesta.
   */
  public void reiniciarApuesta() {
    apuesta = 0;
  }

  /**
   * Resta dinero del balance total del jugador.
   *
   * @param suma the suma
   */
  public void restarDinero(int suma) {
    balance -= suma;
  }

  /**
   * Suma dinero del balance total del jugador.
   *
   * @param suma the suma
   */
  public void adicionarDinero(int suma) {
    balance += suma;
  }

  /**
   * Obtiene el balance del jugador.
   *
   * @return balance
   */
  public int getBalance() {
    return balance;
  }

  /**
   * Obtiene una carta del ArrayList de mano en el respectivo índice de parámetro.
   *
   * @param pos (índice en el arraylist 0-n)
   * @return Carta
   */
  public Carta getCartaMano(int pos) {
    return mano.getCarta(pos);
  }

  /**
   *  Obtiene el tamaño de la mano.
   *
   * @return the mano size
   */
  public int getManoSize() {
    return mano.manoSize();
  }

  /**
   * Añade la carta que se le ingresa como parámetro a la mano.
   *
   * @param carta the carta
   */
  public void tomarCarta(Carta carta) {
    mano.addCarta(carta);
  }

  /**
   * Gets the mano.
   *
   * @return the mano
   */
  public Mano getMano() {
    return mano;
  }

  /**
   * Extrae (elimina la carta de la ArrayList de origen) una carta de una baraja
   * de cartas y la añade a una nueva baraja.
   *
   * @param barajaDeDondeViene the baraja de donde viene la carta
   * @param destino the destino
   * @param cartaParaAnadir the carta para anadir
   */
  public void llamarCarta(Baraja barajaDeDondeViene, Baraja destino, Carta cartaParaAnadir) {
    destino.addCarta(cartaParaAnadir);
    barajaDeDondeViene.quitarCarta(cartaParaAnadir);
  }

  /**
   * Limpia la mano eliminando todos los objetos de carta de ella y restableciendo
   * su tamaño a 0.
   */
  public void soltarCartas() {
    mano.limpiarMano();
  }

  /**
   * Sets the jugada.
   *
   * @param arrayJugada the new jugada
   */
  public void setJugada(int[] arrayJugada) {
    jugada = arrayJugada;
  }

  /**
   * Gets the jugada.
   *
   * @return the jugada
   */
  public int[] getJugada() {
    return jugada;
  }
}