package graph;

// Arête
public class Edge {

   private int from;	// Origine
   private int to;	// Arrivée

	/**
	 * Constructeur d'une arête
	 * @param x origine de l'arete
	 * @param y extremite de l'arete
	 */
   public Edge(int x, int y) {
		this.from = x;
		this.to = y;
	 }
    /**
     * Retourne le sommet source de l'arete
     */
    public int getVerticeOrigin() {
	return this.from;
    }

    /**
     * Retourne le sommet de destination de l'arete
     */
    public int getVerticeDestination() {
	return this.to;
    }

}
