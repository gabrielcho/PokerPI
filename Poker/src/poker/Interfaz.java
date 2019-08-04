package poker;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**   Esta clase se encarga de gestionar todo lo gráfico, dibuja paneles y grafica otros componentes necesarios
 *  para el juego.
 */
public class Interfaz extends JFrame {
    private Color colorCasino = new Color(18, 84, 8);
    private JButton pasar, subir, igualar, retirarse;
    private JPanel areaJuego = new JPanel(new BorderLayout());
    private JPanel areaTablero = new JPanel(new BorderLayout()); // en la documentación se explica por qué BorderL.
    private JPanel areaTableroCartas = new JPanel(new FlowLayout());
    private JPanel areamano = new JPanel(new BorderLayout());
    private JPanel areaManoCartas = new JPanel(new FlowLayout());
    private JPanel areaManoBotones = new JPanel(new GridLayout(2,2,5, 5));
    private JPanel areaManoInfo = new JPanel(new GridLayout());
    private JPanel areaPC = new JPanel(new BorderLayout());
    private JPanel areaPcCartas = new JPanel(new FlowLayout());
    private JPanel areaPcInfo = new JPanel(new GridLayout());

    public Interfaz() throws IOException {
    }

    /**Prepara la interfaz gráfica a su estado inicial */
    public void setEntorno() throws IOException {
        setVisible(true);
        setTitle("Poker Texas Hold'em");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setResizable(false);

        areaJuego.add(areamano, BorderLayout.SOUTH);
        areaJuego.add(areaTablero, BorderLayout.CENTER); // añade el panel de las cartas comunitarias

        //** Voy a explicar todo esto en la documentacion cuando tenga tiempo sorry :( */
        areaJuego.add(areaPC, BorderLayout.NORTH);

        /**Esto tiene que ser explicado también */
        areaTablero.add(areaTableroCartas, BorderLayout.CENTER);
        areaTablero.add(Box.createRigidArea(new Dimension(200, 90)), BorderLayout.NORTH);
//        areaTableroCartas.add(crearComponenteDeMano(new Carta(1, 1)));
        areaTablero.add(Box.createRigidArea(new Dimension(200, 110)), BorderLayout.SOUTH);

        areamano.add(areaManoInfo, BorderLayout.EAST);
        areamano.add(areaManoCartas, BorderLayout.CENTER);
        areamano.add(areaManoBotones, BorderLayout.WEST);

        areaPC.add(areaPcInfo, BorderLayout.WEST);
        areaPC.add(areaPcCartas, BorderLayout.CENTER);
        areaPC.add(Box.createRigidArea(new Dimension(95, 90)), BorderLayout.EAST);
        
        //Crear botones de accion
        igualar = new JButton ("Igualar"); 
                     //escucha
        areaManoBotones.add(igualar);
        retirarse = new JButton ("Retirarse"); 
    	//escucha
        areaManoBotones.add(retirarse);
        pasar = new JButton ("Pasar"); 
        	//escucha
        areaManoBotones.add(pasar); 
        subir = new JButton ("Subir"); 
        	//escucha
        areaManoBotones.add(subir);
      
        //Se repinta areaJuego (queda pendiente saber si basta con repintar)
        areaJuego.repaint();

        add(areaJuego);
        revalidate();
        repaint();
        colorearTodo();

    }
    
    
    //Pinta el dinero que tienen los jugadores, queda pendiente la apuesta.
    public void pintarInfoJugadores(Jugador jugador) {
    	if(jugador.getName()=="humano") {
    	JLabel infoHumano=new JLabel("Dinero: $" + Integer.toString(jugador.getBalance()));
    	infoHumano.setFont(new Font("Broadway",Font.BOLD,15));
    	infoHumano.setForeground(Color.black);
    	areaManoInfo.add(infoHumano);
    	}
    	else {
    		JLabel infoPc=new JLabel("Dinero: $" + Integer.toString(jugador.getBalance()));
    		infoPc.setFont(new Font("Broadway",Font.BOLD,15));
        	infoPc.setForeground(Color.black);
    		areaPcInfo.add(infoPc);
    	}
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
        if (jugador.getName() == "humano") {
            int tamano = jugador.getManoSize();
            for (int i = 0; i < 2; i++) {
                Carta cartaDeMano = jugador.getCartaMano(i);
                JButton cartaMano = crearComponenteDeMano(cartaDeMano);
                areaManoCartas.add(cartaMano);
//                System.out.println("Carta " + i + " creada");
            }
        } else {
            int tamano = jugador.getManoSize();
            for (int i = 0; i < tamano; i++) {
                Carta cartaDeMano = jugador.getCartaMano(i);
                JButton cartaMano = crearBotonManoPc(cartaDeMano);
                areaPcCartas.add(cartaMano, BorderLayout.CENTER);
//                System.out.println("Carta " + i + " del pc creada");
            }
        }

        revalidate();
        repaint();
    }

    /** Colorea todos los paneles de la interfaz, por alguna razón no sirvió pintar el JFrame */
    public void colorearTodo() {
        areamano.setBackground(colorCasino);
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
    }

}