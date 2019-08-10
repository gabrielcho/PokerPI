package poker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Esta clase se encarga de gestionar todo lo gráfico, dibuja paneles y grafica
 * otros componentes necesarios para el juego.
 */
public class Interfaz extends JFrame {
    private Color colorCasino = new Color(18, 84, 8);
    protected JPanel areaJuego = new JPanel(new BorderLayout());
    private JPanel areaTablero = new JPanel(new BorderLayout()); // en la documentación se explica por qué BorderL.
    private JPanel areaTableroNORTH = new JPanel(new FlowLayout());
    protected JPanel areaTableroCartas = new JPanel(new FlowLayout());
    private JPanel areamano = new JPanel(new BorderLayout());
    private JPanel areaManoCartas = new JPanel(new FlowLayout());
    protected JPanel areaBote = new JPanel(new GridLayout());
    private JPanel areaManoBotones = new JPanel(new GridLayout(2, 1, 5, 5));
    private JPanel areaManoInfoYGlue = new JPanel(new FlowLayout());
    protected JPanel areaManoInfo = new JPanel(new GridLayout(2, 1, 5, 5));
    private JPanel areaPC = new JPanel(new BorderLayout());
    private JPanel areaPcCartas = new JPanel(new FlowLayout());
    private JPanel areaPcInfo = new JPanel(new GridLayout());
    protected String bote = "0";

    public Interfaz() {

    }

    /** Prepara la interfaz gráfica a su estado inicial */
    public void setEntorno() {
        setVisible(true);
        setTitle("Poker Texas Hold'em");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setResizable(false);
        areaJuego.add(areamano, BorderLayout.SOUTH);
        areaJuego.add(areaTablero, BorderLayout.CENTER); // añade el panel de las cartas comunitarias

        // ** Voy a explicar todo esto en la documentacion cuando tenga tiempo sorry :(
        // */
        areaJuego.add(areaPC, BorderLayout.NORTH);
        /** Esto tiene que ser explicado también */
        areaTablero.add(areaTableroNORTH, BorderLayout.NORTH);
        areaTableroNORTH.add(areaBote);
        areaTableroNORTH.add(Box.createRigidArea(new Dimension(0, 90)));

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
        /*
         * //Crear botones de accion igualar = new JButton ("Igualar");
         * igualar.addActionListener(escucha); //escucha areaManoBotones.add(igualar);
         * retirarse = new JButton ("Retirarse"); retirarse.addActionListener(escucha);
         * //escucha areaManoBotones.add(retirarse); pasar = new JButton ("Pasar");
         * pasar.addActionListener(escucha); //escucha areaManoBotones.add(pasar); subir
         * = new JButton ("Subir"); subir.addActionListener(escucha); //escucha
         * areaManoBotones.add(subir);
         */
        // Se repinta areaJuego (queda pendiente saber si basta con repintar)
        areaJuego.repaint();

        add(areaJuego);
        revalidate();
        repaint();
        colorearTodo();

    }

    public ImageIcon imagenBoton(String nombreBoton) {
        Image imagen = new ImageIcon(getClass().getResource("/cartas/" + nombreBoton + ".png")).getImage();
        imagen = imagen.getScaledInstance(108, 36, Image.SCALE_FAST);
        ImageIcon imagenBoton = new ImageIcon(imagen);
        return imagenBoton;
    }

    // Pinta el dinero que tienen los jugadores, queda pendiente la apuesta. este
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
     * 
     * @param carta
     * @return boton de mano
     * 
     */
    public JButton crearComponenteDeMano(Carta carta) {
        JButton boton = new JButton();
        boton.setIcon(carta.imagenCarta());
        boton.setBorder(BorderFactory.createEmptyBorder());
        boton.setPreferredSize(new Dimension(66, 90));
        return boton;
    }

    public JButton crearBotonManoPc(Carta carta) {
        JButton boton = new JButton();
        boton.setIcon(carta.ocultarCarta());
        boton.setBorder(BorderFactory.createEmptyBorder());
        boton.setPreferredSize(new Dimension(66, 90));
        return boton;
    }

    public void actualizarPantalla() {
        // SwingUtilities.updateComponentTreeUI(this);
        // this.validateTree();
        // this.removeAll();
        SwingUtilities.updateComponentTreeUI(this);
    }

    /** */
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

        revalidate();
        repaint();
    }

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
     * Colorea todos los paneles de la interfaz, por alguna razón no sirvió pintar
     * el JFrame
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

    public void addBoton(JButton boton) {
        areaManoBotones.add(boton);
    }

}