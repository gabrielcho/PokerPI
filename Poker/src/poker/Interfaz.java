package poker;

import javax.swing.*;
import java.awt.*;
import java.awt.Image.*;
import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.image.*;
import java.io.*;

public class Interfaz extends JFrame {

    private BorderLayout layout = new BorderLayout();
    private JPanel areaJuego = new JPanel(layout);
    private JPanel areamano = new JPanel(new FlowLayout());

    public Interfaz() throws IOException {
        setEntorno();
    }

    /**Prepara la interfaz gráfica a su estado inicial */
    public void setEntorno() throws IOException {
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        JButton boton = crearBotonMano(new Carta(1, 1));
        JButton boton1 = crearBotonMano(new Carta(6, 2));
        areaJuego.add(areamano, BorderLayout.SOUTH);
        // Se añaden botones a la parte inferior denominada areamano
        areamano.add(boton);
        areamano.add(boton1);
        //Se repinta areaJuego (queda pendiente saber si basta con repintar)
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
    public JButton crearBotonMano(Carta carta) throws IOException {
        JButton boton = new JButton();
        boton.setIcon(carta.imagenCarta());
        boton.setBorder(BorderFactory.createEmptyBorder());
        boton.setPreferredSize(new Dimension(66, 90));
        return boton;
    }

}