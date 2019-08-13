/*
 * @Author Gabriel Arango
 * @Author Diego Timaná
 * @Version 1.0
 */
package poker;

import java.net.URL;
import java.util.Arrays;

import javax.swing.ImageIcon;
import java.awt.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class Carta.
 */
public class Carta implements Comparable<Carta> { // se implementa la interfaz comparable, para así poder organizar las
                                                  // cartas, cosa que nos ayuda a
    // determinar el tipo de jugada que tinene algun jugado
    /** daw. */
    public final static int treboles = 0, corazones = 1, diamantes = 2, picas = 3;

    /** The Constant king. */
    public final static int jack = 11, queen = 12, king = 13;

    /** The palo. */
    private final int palo;

    /** The costo. */
    private Integer valor, costo; // son de tipo integer para que no haya problemas en el compareTo

    /** The path. */
    private String path;

    /**
     * Instantiates a new carta.
     *
     * @param valor the valor
     * @param palo  the palo
     */
    public Carta(int valor, int palo) {
        this.valor = valor;
        this.palo = palo;
        costo = obtenerCosto();
        String path = "/cartas/" + valorCarta() + paloCarta() + ".png";
        path = path.toString();
    }

    /**
     * Obtener costo.
     *
     * @return the costo
     */
    // Obtiene el peso de cada carta (es diferente al valor)
    public int obtenerCosto() {
        int costoObtenido = 0;
        switch (valor) {
        case 1:
            costoObtenido = 13;
            return costoObtenido;

        case 2:
            costoObtenido = 1;
            return costoObtenido;

        case 3:
            costoObtenido = 2;
            return costoObtenido;

        case 4:
            costoObtenido = 3;
            return costoObtenido;

        case 5:
            costoObtenido = 4;
            return costoObtenido;

        case 6:
            costoObtenido = 5;
            return costoObtenido;

        case 7:
            costoObtenido = 6;
            return costoObtenido;

        case 8:
            costoObtenido = 7;
            return costoObtenido;

        case 9:
            costoObtenido = 8;
            return costoObtenido;

        case 10:
            costoObtenido = 9;
            return costoObtenido; // ee github

        case 11:
            costoObtenido = 10;
            return costoObtenido;

        case 12:
            costoObtenido = 11;
            return costoObtenido;

        case 13:
            costoObtenido = 12;
            return costoObtenido;

        default:
            break;
        }
        return costoObtenido;
    }

    public static int costoCarta(int i) {
        int costoObtenido = 0;
        switch (i) {
        case 1:
            costoObtenido = 13;
            return costoObtenido;

        case 2:
            costoObtenido = 1;
            return costoObtenido;

        case 3:
            costoObtenido = 2;
            return costoObtenido;

        case 4:
            costoObtenido = 3;
            return costoObtenido;

        case 5:
            costoObtenido = 4;
            return costoObtenido;

        case 6:
            costoObtenido = 5;
            return costoObtenido;

        case 7:
            costoObtenido = 6;
            return costoObtenido;

        case 8:
            costoObtenido = 7;
            return costoObtenido;

        case 9:
            costoObtenido = 8;
            return costoObtenido;

        case 10:
            costoObtenido = 9;
            return costoObtenido; // ee github

        case 11:
            costoObtenido = 10;
            return costoObtenido;

        case 12:
            costoObtenido = 11;
            return costoObtenido;

        case 13:
            costoObtenido = 12;
            return costoObtenido;

        default:
            break;
        }
        return costoObtenido;
    }

    /**
     * Gets the palo.
     *
     * @return the palo
     */
    public int getPalo() {
        return palo;
    }

    /**
     * Gets the valor.
     *
     * @return the valor
     */
    public int getValor() {
        return valor;
    }

    /**
     * Palo carta.
     *
     * @return the string
     */
    // nos devuelve el palo de la carta según que representación numérica de este.
    // Nos sirve para representar la carta en consola
    public String paloCarta() {
        switch (palo) {
        case treboles:
            return "T";
        case corazones:
            return "C";
        case diamantes:
            return "D";
        case picas:
            return "P";
        default:
            return "??";
        }
    }

    // nos devuelve el valor de la carta según que representación numérica de este.
    // Nos sirve para representar la carta en consola
    /**
     * Obtener valor carta.
     *
     * @param valor the valor
     * @return the string
     */
    // es estático
    public static String obtenerValorCarta(int valor) {
        switch (valor) {
        case 1:
            return "A";
        case 2:
            return "2";
        case 3:
            return "3";
        case 4:
            return "4";
        case 5:
            return "5";
        case 6:
            return "6";
        case 7:
            return "7";
        case 8:
            return "8";
        case 9:
            return "9";
        case 10:
            return "10";
        case 11:
            return "J";
        case 12:
            return "Q";
        case 13:
            return "K";
        default:
            return "??";
        }

    }

    /**
     * Valor carta.
     *
     * @return the string
     */
    // nos devuelve el valor de la carta según que representación numérica de este.
    // Nos sirve para representar la carta en consola
    public String valorCarta() {
        switch (valor) {
        case 1:
            return "A";
        case 2:
            return "2";
        case 3:
            return "3";
        case 4:
            return "4";
        case 5:
            return "5";
        case 6:
            return "6";
        case 7:
            return "7";
        case 8:
            return "8";
        case 9:
            return "9";
        case 10:
            return "10";
        case 11:
            return "J";
        case 12:
            return "Q";
        case 13:
            return "K";
        default:
            return "??";
        }
    }

    /**
     * Este método returna un objeto de tipo <code>ImageIcon</code> que aloja la
     * imagen de la carta ya lista para ser dibujada sobre los componentes.
     * 
     * @return imagen de carta
     */
    public ImageIcon imagenCarta() {
        Image imagen = new ImageIcon(getClass().getResource("/cartas/" + valorCarta() + paloCarta() + ".png"))
                .getImage();
        imagen = imagen.getScaledInstance(66, 90, Image.SCALE_FAST); // Escala la imagen de la carta a un tamaño
                                                                     // deseable
        ImageIcon imagencarta = new ImageIcon(imagen);
        return imagencarta;
    }

    /**
     * Este método returna un objeto de tipo <code>ImageIcon</code> que aloja la
     * imagen VOLTEADA de la carta ya lista para ser dibujada sobre los componentes.
     *
     * @return the image icon
     */
    public ImageIcon ocultarCarta() {
        Image imagen = new ImageIcon(getClass().getResource("/cartas/" + "trasero.png")).getImage();
        imagen = imagen.getScaledInstance(66, 90, Image.SCALE_FAST);
        ImageIcon imagencarta = new ImageIcon(imagen);
        return imagencarta;
    }

    /**
     * Mostrar carta.
     *
     * @return the string
     */
    // retorna un string que nos reresenta la carta
    public String mostrarCarta() {
        return valorCarta() + " de " + paloCarta();
    }

    /**
     * Path imagen.
     *
     * @return the url
     */
    // la dirección url donde se encuentra la imagen
    public URL pathImagen() {
        URL url = getClass().getResource("/src/cartas" + path);
        return url;
    }

    /**
     * Gets the path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Compare to.
     *
     * @param o the objeto carta
     * @return the int
     */
    @Override
    // este es el metodo que ayuda al Sort a comparar cartas según su costo.
    public int compareTo(Carta o) {
        // TODO Auto-generated method stub
        return this.costo.compareTo(o.obtenerCosto());
    }
}
