package poker;

public class Baraja {
    private Carta[] baraja;

    public Baraja() {
        baraja = new Carta[52];
        crearBaraja();
        try {
            for (int i = 0; i < 52; i++)
                System.out.println(baraja[i].paloCarta() + baraja[i].valorCarta() + "            " + i);

        } catch (Exception e) {
            System.out.println("Se salio del array");
        }
    }

    public void crearBaraja() {
        int c = 0;
        for (int i = 1; i < 14; i++) {
            for (int j = 0; j < 4; j++) {
                baraja[c] = new Carta(i, j);
                c++;
            }
        }

    }

}
