/* 
 * @Author Gabriel Arango
 * @Author Diego Timaná
 * @Version 1.0
 */
package poker;

import java.util.ArrayList;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 *  Clase encargada de generar y dar las cartas.
 */
public class Baraja {
    
    /** The baraja. */
    private ArrayList<Carta> baraja;

    /**
     *  Constructor de la baraja, genera las 52 cartas distintas del juego.
     */
    public Baraja() {
        baraja = new ArrayList<Carta>();
        crearBaraja();
        barajar();
    }

    /**
     *  Genera la baraja de 52 cartas diferentes poniéndolas en un ArrayList.
     */
    public void crearBaraja() {
    for (int i = 1; i < 14; i++) {
        
            for (int j = 0; j < 4; j++) {
                baraja.add(new Carta(i, j));
            }
        }

    }

    
    /**
     *  Aleatoriza el orden de las cartas contenidas en el arraylist de la baraja.
     */
    public void barajar() {
        ArrayList<Carta> barajaTemp = new ArrayList<Carta>();
        Random random = new Random(System.currentTimeMillis());
        int indicerandom = 0;
        int tamanoOriginal = this.baraja.size();
        for (int i = 0; i < tamanoOriginal; i++) {
            indicerandom = random.nextInt(this.baraja.size());
            barajaTemp.add(this.baraja.get(indicerandom));
            this.baraja.remove(indicerandom);
        }
        this.baraja = barajaTemp;
    }

    /**
     * Obtiene una carta de la baraja.
     * @param i  Idica la posicion de una carta en una determinada ArrayList de cartas.
     * @return carta  Es un objeto Carta con palo y valor.
     */
    public Carta getCarta(int i) {
        return this.baraja.get(i);
    }

    /**
     * Método usado para repartir cartas, se toma la ultima carta de la baraja, no hay que randomizar la selección
     * porque la baraja de por sí ya está randomizada.
     *
     * @return carta al azar de baraja
     */
    public Carta darCartaAlAzar() {
        Carta cartaObtenida = baraja.get(baraja.size() - 1);
        baraja.remove(cartaObtenida);
        return cartaObtenida;
    }

    /**
     * Quita una carta de una baraja.
     * @param i Indica la posicion de una carta en una determinada ArrayList de cartas.
     */
    public void quitarCarta(int i) {
        this.baraja.remove(i);
    }
    
    /**
     * Quita una carta de una baraja.
     *
     * @param carta the carta
     */
    public void quitarCarta(Carta carta) {
        this.baraja.remove(carta);
    }
    
    /**
     * Tamano baraja.
     *
     * @return the tamanño
     */
    public int tamanoBaraja() {
      return this.baraja.size();
    }
    
    /**
     * Adds the carta.
     *
     * @param carta the carta
     */
    public void addCarta(Carta carta) {
    	baraja.add(carta);
    }


}
