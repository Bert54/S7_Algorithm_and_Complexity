package graph;

import java.util.ArrayList;
import java.util.List;

// Sommet
public class Vertice {

    private int numero;
    private int color;
    private List<Vertice> voisins;    // Liste des sommets adjacents

    public Vertice(int num) {
        this.numero = num;
        this.color = 0;
        this.voisins = new ArrayList<>();
    }

    public void setColor(int c) {
        this.color = c;
    }

    /**
     * Ajoute un sommet voisin au sommet (lie par une arete)
     * @param v sommet a l'autre bout de la liaison
     */
    public void addVoisin(Vertice v) {
        if(!this.voisins.contains(v))
            this.voisins.add(v);
    }

    /**
     * Supprime une liaison au sommet
     * @param v sommet a l'autre bout de la liaison
     */
    public void deleteEdge(Vertice v){
        this.voisins.remove(v);
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
        return this.voisins.size();
    }

    public List<Vertice> getAdjacents() {
        return voisins;
    }
}
