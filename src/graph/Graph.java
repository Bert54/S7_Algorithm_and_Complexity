package graph;

import java.io.*;

public abstract class Graph {

   public abstract int vertices();	// Nombre de sommets
   

   public abstract Iterable<Edge> next(int v);	// Sommet suivant
   public abstract Iterable<Edge> prev(int v);	// Sommet précédent

	/**
	 * Ecriture d'un graphe dans un fichier
	 * @param s nom du fichier
	 */
    public void writeFile(String s) {
		try {
			PrintWriter writer = new PrintWriter(s, "UTF-8");
			writer.println("digraph G{");
			int u;
			int n = vertices();
			for (u = 0; u < n;  u++) {
				for (Edge e : next(u)) {
					writer.println(e.getVerticeOrigin() + "->" + e.getVerticeDestination() + ";");
				}
			}
			writer.println("}");
			writer.close();
		}
		catch (IOException e) {
		}
    }
  
}
