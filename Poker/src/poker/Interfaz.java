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
    private JPanel tablero = new JPanel(layout);

    public Interfaz() {
        setEntorno();
        crearBoton(new Carta(1, 1));
    }

    /**Prepara la interfaz gráfica a su estado inicial */
    public void setEntorno() {
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        JButton boton = new JButton("yeahsisa");
        tablero.add(boton);
        tablero.repaint();
        add(tablero);
        repaint();

    }

    public JButton crearBoton(Carta carta) {
        //   URL url = getClass().getResource("/cartas" + carta.getPath());
        JButton boton = new JButton();
        return boton;
    }

}