package poker;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.*;

/**   Esta clase se encarga de gestionar todo lo gráfico, dibuja paneles y grafica otros componentes necesarios
 *  para el juego.
 */
public class Interfaz extends JFrame {

    private BorderLayout layout = new BorderLayout();
    private JPanel areaJuego = new JPanel(layout);
    private JPanel areamano = new JPanel(new BorderLayout());
    private JPanel areamanoPc = new JPanel(new FlowLayout());
    private JPanel areaManoCartas = new JPanel(new FlowLayout());
    private JPanel areaManoBotones = new JPanel(new GridLayout());
    private Baraja barajainicial = new Baraja();

    public Interfaz() throws IOException {
    }

    /**Prepara la interfaz gráfica a su estado inicial */
    public void setEntorno() throws IOException {
        setVisible(true);
        setTitle("");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        
        Border bordejpanelPc = new TitledBorder(new EtchedBorder(), "Oponente");
        areamanoPc.setBorder(bordejpanelPc);
        
        Border bordejpanel = new TitledBorder(new EtchedBorder(), "Jugador");
        areamano.setBorder(bordejpanel);
        areamano.add(areaManoCartas, BorderLayout.CENTER);
        areamano.add(areaManoBotones, BorderLayout.WEST);
        areaManoBotones.add(new JButton("Aca van los botones de accion"));

        //Se repinta areaJuego (queda pendiente saber si basta con repintar)
        areaJuego.add(areamano, BorderLayout.SOUTH);
        areaJuego.add(areamanoPc, BorderLayout.NORTH);
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
    
    public JButton crearBotonManoPc(Carta carta) throws IOException {
        JButton boton = new JButton();
        boton.setIcon(carta.ocultarCarta());
        boton.setBorder(BorderFactory.createEmptyBorder());
        boton.setPreferredSize(new Dimension(66, 90));
        return boton;
    }

    /** */
    public void dibujarMano(Jugador jugador) throws IOException {
    	if(jugador.getName()=="humano") {
        int tamano = jugador.getManoSize();
        for (int i = 0; i < tamano; i++) {
            Carta cartaDeMano = jugador.getCartaMano(i);
            JButton cartaMano = crearComponenteDeMano(cartaDeMano);
            areaManoCartas.add(cartaMano);
            System.out.println("Carta " + i + " creada");
        }
    }
    	else {
    		 int tamano = jugador.getManoSize();
    	        for (int i = 0; i < tamano; i++) {
    	            Carta cartaDeMano = jugador.getCartaMano(i);
    	            JButton cartaMano = crearBotonManoPc(cartaDeMano);
    	            areamanoPc.add(cartaMano);
    	                   System.out.println("Carta " + i + " del pc creada");
    	        }
    	}

        revalidate();
        repaint();
    }

}