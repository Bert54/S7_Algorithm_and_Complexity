package graph;

import java.util.ArrayList;
import java.io.*;

public class GraphArrayList extends Graph {

   private ArrayList<Edge>[] adj;	// Liste des arêtes
   private final int V;				// Nombre de sommets
   private int E;					// Nombre d'arêtes

	/**
	 * Constructeur d'un graphe a partir d'une liste
	 * @param N nombre de sommets
	 */
	@SuppressWarnings("unchecked")
   public GraphArrayList(int N) {
		this.V = N;
		this.E = 0;
		adj = (ArrayList<Edge>[]) new ArrayList[N];
		for (int v= 0; v < N; v++)
		  adj[v] = new ArrayList<Edge>();
		
	 }

	/**
	 * Getter sur le nombre de sommets, c'est a dire la longueur de la liste
	 * @return nombre de sommets
	 */
	public int vertices() {
		return V;
	 }

	/**
	 * Ajout d'une arete
	 * @param e arete a ajouter
	 */
	public void addEdge(Edge e) {
		int v = e.getVerticeOrigin();
		int w = e.getVerticeDestination();
		adj[v].add(e);
		adj[w].add(e);
	 }

	/**
	 * Iterateur sur les aretes adjacentes
	 * @param v arete de depart
	 * @return aretes adjacentes
	 */
	public Iterable<Edge> adj(int v) {
		return adj[v];
	 }

	/**
	 * Iterateur sur l'arete suivante
	 * @param v arete de depart
	 * @return arete suivante
	 */
   public Iterable<Edge> next(int v) {
		ArrayList<Edge> n = new ArrayList<Edge>();
		for (Edge e: adj[v])
		  if (e.getVerticeDestination() != v)
			n.add(e);
		return n;
	 }

	/**
	 * Iterateur sur l'arete precedente
	 * @param v arete de depart
	 * @return arete precedente
	 */
	public Iterable<Edge> prev(int v) {
		ArrayList<Edge> n = new ArrayList<Edge>();
		for (Edge e: adj[v])
		  if (e.getVerticeOrigin() != v)
			n.add(e);
		return n;
	 }

	/**
	 * Iterateur sur toutes les aretes
	 * @return liste des aretes
	 */
	public Iterable<Edge> edges() {
	ArrayList<Edge> list = new ArrayList<Edge>();
        for (int v = 0; v < V; v++) {
			for (Edge e : adj(v)) {
				if (e.getVerticeDestination() != v)
					list.add(e);
			}
		}
        return list;
    }
    

}
