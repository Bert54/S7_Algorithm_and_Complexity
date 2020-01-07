package graph;

import java.util.ArrayList;
import java.util.List;

// Sommet
public class Vertice {

    private int numero;
    private int color;
    private List<Vertice> adjacents;    // Liste des sommets adjacents

    public Vertice(int num) {
        this.numero = num;
        this.color = 0;
        this.adjacents = new ArrayList<>();
    }

    public void setColor(int c) {
        this.color = c;
    }

    /**
     * Ajoute une liaison au sommet
     * @param v sommet a l'autre bout de la liaison
     */
    public void addEdge(Vertice v) {
        this.adjacents.add(v);
    }

    /**
     * Supprime une liaison au sommet
     * @param v sommet a l'autre bout de la liaison
     */
    public void deleteEdge(Vertice v){
        this.adjacents.remove(v);
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
        return this.adjacents.size();
    }

    public List<Vertice> getAdjacents() {
        return adjacents;
    }
}
