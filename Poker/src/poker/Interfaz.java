/* 
 * @Author Gabriel Arango
 * @Author Diego Timaná
 * @Version 1.0
 */
package poker;

import javax.swing.*;
import java.awt.*;

// TODO: Auto-generated Javadoc
/**
 * Esta clase se encarga de gestionar todo lo gráfico, dibuja paneles y grafica
 * otros componentes necesarios para el juego.
 */
public class Interfaz extends JFrame {
    
    /** The color casino. */
    private Color colorCasino = new Color(18, 84, 8);
    
    /** The area juego. */
    protected JPanel areaJuego = new JPanel(new BorderLayout());
    
    /** The area tablero. */
    private JPanel areaTablero = new JPanel(new BorderLayout()); // en la documentación se explica por qué BorderL.
    
    /** The area tablero NORTH. */
    private JPanel areaTableroNORTH = new JPanel(new FlowLayout());
    
    /** The area tablero cartas. */
    protected JPanel areaTableroCartas = new JPanel(new FlowLayout());
    
    /** The areamano. */
    private JPanel areamano = new JPanel(new BorderLayout());
    
    /** The area mano cartas. */
    private JPanel areaManoCartas = new JPanel(new FlowLayout());
    
    /** The area bote. */
    protected JPanel areaBote = new JPanel(new GridLayout());
    
    /** The area mano botones. */
    private JPanel areaManoBotones = new JPanel(new GridLayout(2, 1, 5, 5));
    
    /** The area mano info Y glue. */
    private JPanel areaManoInfoYGlue = new JPanel(new FlowLayout());
    
    /** The area mano info. */
    protected JPanel areaManoInfo = new JPanel(new GridLayout(2, 1, 5, 5));
    
    /** The area PC. */
    private JPanel areaPC = new JPanel(new BorderLayout());
    
    /** The area pc cartas. */
    protected JPanel areaPcCartas = new JPanel(new FlowLayout());
    
    /** The area pc info. */
    private JPanel areaPcInfo = new JPanel(new GridLayout());
    
    /** The bote. */
    protected String bote = "0";

    /**
     * Instantiates a new interfaz.
     */
    public Interfaz() {

    }

    /**
     *  Prepara la interfaz gráfica a su estado inicial.
     */
    public void setEntorno() {
        setVisible(true);
        setTitle("Poker Texas Hold'em");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setResizable(false);
        areaJuego.add(areamano, BorderLayout.SOUTH);
        areaJuego.add(areaTablero, BorderLayout.CENTER); // añade el panel de las cartas comunitarias
        areaJuego.add(areaPC, BorderLayout.NORTH);
       
        areaTablero.add(areaTableroNORTH, BorderLayout.NORTH);
        areaTableroNORTH.add(areaBote);
        areaTableroNORTH.add(Box.createRigidArea(new Dimension(0, 90))); //ponemos Rigid Areas para centrar los paneles

        areaTablero.add(areaTableroCartas, BorderLayout.CENTER);
        areaTablero.add(Box.createRigidArea(new Dimension(200, 110)), BorderLayout.SOUTH);

        areaManoInfoYGlue.add(areaManoInfo);
        areaManoInfoYGlue.add(Box.createRigidArea(new Dimension(72, 0)));
        areamano.add(areaManoInfoYGlue, BorderLayout.EAST);
        areamano.add(areaManoCartas, BorderLayout.CENTER);
        areamano.add(areaManoBotones, BorderLayout.WEST);

        areaPC.add(areaPcInfo, BorderLayout.WEST);
        areaPC.add(areaPcCartas, BorderLayout.CENTER);
        areaPC.add(Box.createRigidArea(new Dimension(132, 0)), BorderLayout.EAST);

        // Se repinta areaJuego (queda pendiente saber si basta con repintar)
        areaJuego.repaint();

        add(areaJuego);
        revalidate();
        repaint();
        colorearTodo();

    }

    /**
     * Imagen boton.
     *
     * @param nombreBoton the nombre boton
     * @return the image icon
     */
    /* el botón tiene una imagen, es metodo se la pone  */
    public ImageIcon imagenBoton(String nombreBoton) {
        Image imagen = new ImageIcon(getClass().getResource("/cartas/" + nombreBoton + ".png")).getImage();
        imagen = imagen.getScaledInstance(108, 36, Image.SCALE_FAST);
        ImageIcon imagenBoton = new ImageIcon(imagen);
        return imagenBoton;
    }

    // Pinta el dinero que tienen los jugadores. Este
    /**
     * Pintar info.
     *
     * @param jugador the jugador
     */
    // metodo tambien sirve para actualizar toda la info en la pantalla
    public void pintarInfo(Jugador jugador) {
        if (jugador.getName() == "humano") {
            areaManoInfo.removeAll();

            JLabel infoApuesta = new JLabel("Tu apuesta: $" + jugador.getApuesta());
            infoApuesta.setFont(new Font("SansSerif.bolditalic", Font.BOLD, 15));
            infoApuesta.setForeground(Color.black);
            areaManoInfo.add(infoApuesta);

            JLabel infoHumano = new JLabel("Tu Dinero: $" + Integer.toString(jugador.getBalance()));
            infoHumano.setFont(new Font("SansSerif.bolditalic", Font.BOLD, 15));
            infoHumano.setForeground(Color.white);
            areaManoInfo.add(infoHumano);

        } else {
            areaPcInfo.removeAll();
            areaBote.removeAll();
            JLabel infoPc = new JLabel("           Dinero: $" + Integer.toString(jugador.getBalance()));
            infoPc.setFont(new Font("SansSerif.bolditalic", Font.BOLD, 15));
            infoPc.setForeground(Color.white);
            areaPcInfo.add(infoPc);

            JLabel infoBote = new JLabel("Bote: $" + bote);
            infoBote.setFont(new Font("SansSerif.bolditalic", Font.BOLD, 15));
            infoBote.setForeground(Color.white);
            areaBote.add(infoBote);
        }
    }

    /**
     * Se crea un botón para el panel de mano, estos botones no son clickeables
     * porque las jugadas se hacen desde otros botones.
     * Las cartas se representan graficamente mediante botones
     *
     * @param carta the carta
     * @return boton de mano
     */
    public JButton crearComponenteDeMano(Carta carta) {
        JButton boton = new JButton();
        boton.setIcon(carta.imagenCarta());
        boton.setBorder(BorderFactory.createEmptyBorder());
        boton.setPreferredSize(new Dimension(66, 90));
        return boton;
    }

    /**
     * Crear boton mano pc.
     *
     * @param carta the carta
     * @return the j button
     */
    /*Crea el boton que representa las cartas del pc, que están volteadas*/
    public JButton crearBotonManoPc(Carta carta) {
        JButton boton = new JButton();
        boton.setIcon(carta.ocultarCarta());
        boton.setBorder(BorderFactory.createEmptyBorder());
        boton.setPreferredSize(new Dimension(66, 90));
        return boton;
    }

    /**
     * Actualizar pantalla.
     */
    /*Actualiza la pantalla*/
    public void actualizarPantalla() {
    	revalidate();
		repaint();
    }

    /**
     * Dibujar mano.
     *
     * @param jugador the jugador
     */
    /*Este metodo añade las cartas a los paneles para ser graficadas, dependiendo del jugador que se le ingrese
     * las pinta ocultas o no
     */
    public void dibujarMano(Jugador jugador) {
        if (jugador.getName() == "humano") {
            for (int i = 0; i < 2; i++) {
                Carta cartaDeMano = jugador.getCartaMano(i);
                JButton cartaMano = crearComponenteDeMano(cartaDeMano);
                areaManoCartas.add(cartaMano);
                // System.out.println("Carta " + i + " creada");
            }
        } else {
            int tamano = jugador.getManoSize();
            for (int i = 0; i < tamano; i++) {
                Carta cartaDeMano = jugador.getCartaMano(i);
                JButton cartaMano = crearBotonManoPc(cartaDeMano);
                areaPcCartas.add(cartaMano, BorderLayout.CENTER);
                // System.out.println("Carta " + i + " del pc creada");
            }
        }

        actualizarPantalla();
    }
   
    /**
     * Limpiar interfaz.
     */
    /*Limpia todos los paneles de la interfaz, dejándolos sin ningun componente. Se utiliza cuando se quiere reiniciar el juego
     * */
    public void LimpiarInterfaz() {
        areamano.removeAll();
        areaBote.removeAll();
        areaJuego.removeAll();
        areaPC.removeAll();
        areaPcCartas.removeAll();
        areaPcInfo.removeAll();
        areaManoCartas.removeAll();
        areaTableroCartas.removeAll();
        areaTablero.removeAll();
        areaManoCartas.removeAll();
        areaManoBotones.removeAll();
        areaManoInfo.removeAll();
        areaManoInfoYGlue.removeAll();
        areaTableroNORTH.removeAll();

        this.repaint();

    }

    /**
     * Colorea todos los paneles de la interfaz de un mismo color.
     * 
     */
    public void colorearTodo() {
        areamano.setBackground(colorCasino);
        areaBote.setBackground(colorCasino);
        areaJuego.setBackground(colorCasino);
        areaPC.setBackground(colorCasino);
        areaPcCartas.setBackground(colorCasino);
        areaPcInfo.setBackground(colorCasino);
        areaManoCartas.setBackground(colorCasino);
        areaTableroCartas.setBackground(colorCasino);
        areaTablero.setBackground(colorCasino);
        areaManoCartas.setBackground(colorCasino);
        areaManoBotones.setBackground(colorCasino);
        areaManoInfo.setBackground(colorCasino);
        areaManoInfoYGlue.setBackground(colorCasino);
        areaTableroNORTH.setBackground(colorCasino);
    }

    /**
     * Adds the boton.
     *
     * @param boton the boton
     */
    /*Añade un boton al panel areaManoBotones, este metodo se ultiliza en la Lógica*/
    public void addBoton(JButton boton) {
        areaManoBotones.add(boton);
    }

}