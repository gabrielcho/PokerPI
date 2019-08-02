package poker;

import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.IOException;

public class Carta {
    public final static int treboles = 0, corazones = 1, diamantes = 2, picas = 3;

    public final static int jack = 11, queen = 12, king = 13;

    private final int palo;
    private int valor, costo;
    private String path;

    public Carta(int val, int pal) {
        valor = val;
        palo = pal;
        costo = obtenerCosto();
        String path = "/cartas/" + valorCarta() + paloCarta() + ".png";
        path = path.toString();
    }

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
            return costoObtenido; //ee github

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

    public int getPalo() {
        return palo;
    }

    public int getValor() {
        return valor;
    }

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

    public ImageIcon imagenCarta() throws IOException {
        URL url = getClass().getResource("/cartas" + path);
        System.out.println(valorCarta() + paloCarta() + ".png"); 
        System.out.println(getClass().getResource("/cartas/" + valorCarta() + paloCarta() + ".png")); 
        Image imagen = new ImageIcon(getClass().getResource("/cartas/" + valorCarta() + paloCarta() + ".png"))
                .getImage();
        imagen = imagen.getScaledInstance(66, 90, Image.SCALE_FAST);
        ImageIcon imagencarta = new ImageIcon(imagen);
        return imagencarta;
    }

    public String mostrarCarta() {
        return valorCarta() + " de " + paloCarta();
    }

    public URL pathImagen() {
        URL url = getClass().getResource("/src/cartas" + path);
        return url;
    }

    public String getPath() {
        return path;
    }
}
