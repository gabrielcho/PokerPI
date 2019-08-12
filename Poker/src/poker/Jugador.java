package poker;

public class Jugador {
  private int balance;
  private Mano mano;
  private String name;
  private int apuesta;
  private int[] jugada;
  // utlizo el nombre para diferenciar al pc del humano y as� crear un condicional
  // en la interfaz que me pinte las cartas
  // volteadas e caso de que sea el pc o normales en caso contrario

  /**
   * Inicializa el objeto de jugador, asignando un valor a su balance y a su mano
   * lista para alojar cartas
   */
  public Jugador(String name) {
    jugada = new int[2];
    this.name = name;
    apuesta = 0;
    mano = new Mano();
    balance = 10000;
  }

  public String getName() {
    return name;
  }

  public int getApuesta() {
    return apuesta;
  }

  public void setApuesta(int valor) {
    apuesta = valor;
  }

  public void reiniciarBalance() {
    balance = 10000;
  }

  public void reiniciarApuesta() {
    apuesta = 0;
  }

  /**
   * Resta dinero del balance total del jugador
   * 
   * @param suma
   */
  public void restarDinero(int suma) {
    balance -= suma;
  }

  /**
   * Suma dinero del balance total del jugador
   * 
   * @param suma
   */
  public void adicionarDinero(int suma) {
    balance += suma;
  }

  /**
   * Obtiene el balance del jugador
   * 
   * @return balance
   */
  public int getBalance() {
    return balance;
  }

  /**
   * Obtiene una carta del ArrayList de mano en el respectivo índice de parámetro
   * 
   * @param pos (índice en el arraylist 0-n)
   * @return Carta
   */
  public Carta getCartaMano(int pos) {
    return mano.getCarta(pos);
  }

  /** Obtiene el tamaño de la mano */
  public int getManoSize() {
    return mano.manoSize();
  }

  /**
   * Añade la carta que se le ingresa como parámetro a la mano
   * 
   * @param carta
   */
  public void tomarCarta(Carta carta) {
    mano.addCarta(carta);
  }

  public Mano getMano() {
    return mano;
  }

  /**
   * Extrae (elimina la carta de la ArrayList de origen) una carta de una baraja
   * de cartas y la añade a una nueva baraja.
   * 
   * @param barajaDeDondeViene the baraja de donde viene la carta
   */
  public void llamarCarta(Baraja barajaDeDondeViene, Baraja destino, Carta cartaParaAnadir) {
    destino.addCarta(cartaParaAnadir);
    barajaDeDondeViene.quitarCarta(cartaParaAnadir);
  }

  /**
   * Limpia la mano eliminando todos los objetos de carta de ella y restableciendo
   * su tamaño a 0
   */
  public void soltarCartas() {
    mano.limpiarMano();
  }

  public void setJugada(int[] arrayJugada) {
    jugada = arrayJugada;
  }

  public int[] getJugada() {
    return jugada;
  }
}