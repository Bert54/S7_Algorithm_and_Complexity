package graph;

// Sommet
public class Vertice {

    private int numero;
    private int color;
    private int degre;  // Nombre d'arêtes reliant ce sommet

    public Vertice(int num, int degre) {
        this.numero = num;
        this.color = 0;
        this.degre = degre;
    }

    public void setColor(int c) {
        this.color = c;
    }

    public void addEdge() {
        this.degre++;
    }

    public boolean estColore() {
        return color > -1;
    }


    public int getNumero() {
        return numero;
    }

    public int getColor() {
        return this.color;
    }

    public int getDegre() {
        return degre;
    }
}
