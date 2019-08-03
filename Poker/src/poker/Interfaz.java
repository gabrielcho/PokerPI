package poker;

import javax.swing.*;
import java.awt.*;
import java.awt.Image.*;
import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.image.*;
import java.io.*;

/**   Esta clase se encarga de gestionar todo lo gráfico, dibuja paneles y grafica otros componentes necesarios
 *  para el juego.
 */
public class Interfaz extends JFrame {

    private BorderLayout layout = new BorderLayout();
    private JPanel areaJuego = new JPanel(layout);
    private JPanel areamano = new JPanel(new FlowLayout());
    private Baraja barajainicial = new Baraja();

    public Interfaz() throws IOException {
    }

    /**Prepara la interfaz gráfica a su estado inicial */
    public void setEntorno() throws IOException {
        setVisible(true);
        setTitle("【Ｐ ｏ ｋ ｅ ｒ  　ｍ　ｅ　ｌ　ｏ】");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        //Se repinta areaJuego (queda pendiente saber si basta con repintar)
        areaJuego.add(areamano, BorderLayout.SOUTH);
        areaJuego.repaint();

        add(areaJuego);
        revalidate();
        repaint();

    }

    /**
     * Se crea un botón para el panel de mano, estos botones no son clickeables porque las jugadas se hacen desde otros botones.
     * @param carta
     * @return boton de mano
     * @throws IOException
     */
    public JButton crearComponenteDeMano(Carta carta) throws IOException {
        JButton boton = new JButton();
        boton.setIcon(carta.imagenCarta());
        boton.setBorder(BorderFactory.createEmptyBorder());
        boton.setPreferredSize(new Dimension(66, 90));
        return boton;
    }

    public void dibujarMano(Jugador jugador) throws IOException {
        int tamano = jugador.getManoSize();
        for (int i = 0; i < tamano; i++) {
            Carta cartaDeMano = jugador.getCartaMano(i);
            JButton cartaMano = crearComponenteDeMano(cartaDeMano);
            areamano.add(cartaMano);
            System.out.println("Carta " + i + " creada");
        }
        areaJuego.revalidate();
        areaJuego.repaint();
        areamano.revalidate();
        areamano.repaint();
        revalidate();
        repaint();
    }

}