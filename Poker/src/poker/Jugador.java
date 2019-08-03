package poker;

public class Jugador {

  private int balance;
  private Mano mano;

  /** Inicializa el objeto de jugador, asignando un valor a su balance y a su mano lista para alojar cartas */
  public Jugador() {
    mano = new Mano();
    balance = 10000;
  }

  /**
   * Resta dinero del balance total del jugador
   * @param suma
   */
  public void restarDinero(int suma) {
    balance -= suma;
  }

  /**
   * Suma dinero del balance total del jugador
   * @param suma
   */
  public void adicionarDinero(int suma) {
    balance += suma;
  }

  /**
   * Obtiene el balance del jugador
   * @return balance
   */
  public int getBalance() {
    return balance;
  }

  public Carta getCartaMano(int pos) {
    return mano.getCarta(pos);
  }

  public int getManoSize() {
    return mano.manoSize();
  }

  public void tomarCarta(Carta carta) {
    mano.addCarta(carta);
  }

  public void soltarCartas() {
    mano.limpiarMano();
  }
}