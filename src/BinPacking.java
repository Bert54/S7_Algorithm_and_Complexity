import graph.Edge;
import graph.Graph;
import graph.GraphArrayList;
import graph.Vertice;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinPacking {

    private static BinPacking instance = new BinPacking();

    public static final int ITEMMINSIZE = 10;
    public static final int ITEMMAXSIZE = 50;

    public static BinPacking getInstance() {
        return instance;
    }

    private BinPacking() {
    }

    /**
     * La méthode qui effectue le packing en remplissant complètement les boites en divisant les objets
     * @param filePath le fichier depuis lequel charger le nombre d'objets (et le graphe)
     * @return nombre de boites nécessaire avec le fractionalPacking
     */
    public int fractionalPacking(String filePath) {
        Graph g = this.getGraphFromFile(filePath);
        List<Object> objectList = this.generateObjectsFromGraph(g);
        if (objectList.isEmpty()) {
            return 0;
        }
        List<Box> boxList = new ArrayList<>();
        boxList.add(new Box());
        for (Object obj: objectList) {
            Box box = boxList.get(boxList.size()-1);
            if (box.canAddObject(obj)) {
                box.addObject(obj);
            }
            else {
                int remainingCapa = box.getRemainingCapacity();
                if (remainingCapa != 0) {
                    box.addObject(new Object(remainingCapa));
                    obj = new Object(obj.getHeight() - remainingCapa);
                }
                while (obj.getHeight() > 0) {
                    box = new Box();
                    boxList.add(box);
                    if (obj.getHeight() > Box.BOXHEIGHT) {
                        box.addObject(new Object(Box.BOXHEIGHT));
                        obj = new Object(obj.getHeight() - Box.BOXHEIGHT);
                    }
                    else {
                        box.addObject(obj);
                        obj = new Object(0);
                    }
                }
            }
        }
        return boxList.size();
    }

    /**
     * La méthode qui effectue le packing en triant dans le sens inverse les objets, puis
     * en prenant la première boite dans laquelle on peut mettre l'objet, en tenant compte
     * d'un graphe de conflits entre les objets
     * @param filePath le fichier depuis lequel charger le nombre d'objets (et le graphe)
     * @return nombre de boites nécessaires avec le firstFitPacking
     */
    public int firstFitDecreasingPack(String filePath) {

        Graph g = this.getGraphFromFile(filePath);
        List<Object> objectList = this.generateObjectsFromGraph(g);
        if (objectList.isEmpty()) {
            return 0;
        }
        this.decreaseSort(objectList);
        List<Box> boxList = new ArrayList<>();
        boxList.add(new Box());
        for (Object obj: objectList) {
            // On récupère la boite selon l'algorithme du First Fit (première boite dans lequel on peut y mettre l'objet)
            Box box = this.firstBoxWithEnoughCapacity(obj, boxList);
            if (box.getCurrentFill() == 0) {
                box.addObject(obj);
            }
            else {
                if (!(box.getCurrentFill() == 0)) {
                    boolean conflict = false;
                    for (Object objBox: box.getObjects()) {
                        for (Edge e: g.next(obj.getID())) {
                            if (e.getVerticeOrigin() == obj.getID() && e.getVerticeDestination() == objBox.getID()) {
                                conflict = true;
                            }
                        }
                    }
                    if (!conflict) {
                        box.addObject(obj);
                    }
                    else {
                        box = new Box();
                        box.addObject(obj);
                        boxList.add(box);
                    }
                }
                else {
                    box = new Box();
                    box.addObject(obj);
                    boxList.add(box);
                }
            }
        }
        return boxList.size();
    }

    /**
     * La méthode qui effectue le packing en triant dans le sens inverse les objets, puis
     * en prenant la meilleure boite (la boite avec la plus grande capa restante après
     * ajout), en tenant compte d'un graphe de conflits entre les objets
     * @param filePath le fichier depuis lequel charger le nombre d'objets (et le graphe)
     * @return nombre de boites nécessaires avec le bestFitPacking
     */
    public int bestFitDecreasingPacking(String filePath) {

        Graph g = this.getGraphFromFile(filePath);
        List<Object> objectList = this.generateObjectsFromGraph(g);
        if (objectList.isEmpty()) {
            return 0;
        }
        this.decreaseSort(objectList);
        List<Box> boxList = new ArrayList<>();
        boxList.add(new Box());
        for (Object obj: objectList) {
            // On récupère la boite selon l'algorithme du Best Fit (boite avec meilleure capa restante après ajout)
            Box box = this.bestBoxWithEnoughCapacityAfterAddition(obj, boxList);
            if (box.getCurrentFill() == 0) {
                box.addObject(obj);
            }
            else {
                if (box.getRemainingCapacity() - obj.getHeight() >= 0) {
                    boolean conflict = false;
                    for (Object objBox: box.getObjects()) {
                        for (Edge e: g.next(obj.getID())) {
                            if (e.getVerticeOrigin() == obj.getID() && e.getVerticeDestination() == objBox.getID()) {
                                conflict = true;
                            }
                        }
                    }
                    if (!conflict) {
                        box.addObject(obj);
                    }
                    else {
                        box = new Box();
                        box.addObject(obj);
                        boxList.add(box);
                    }
                }
                else {
                    box = new Box();
                    box.addObject(obj);
                    boxList.add(box);
                }
            }
        }
        return boxList.size();

    }

    /**
     * Algorithme du Best Fit
     * @param obj objet à ajouter
     * @param boxList liste des boites
     * @return la boite sélectionnée
     */
    private Box bestBoxWithEnoughCapacityAfterAddition(Object obj, List<Box> boxList) {
        Box box = null;
        if (boxList.isEmpty()) {
            box = new Box();
            boxList.add(box);
        }
        else {
            int remainingCapaBestBox = 0;
            int tempCapa;
            for (Box b: boxList) {
                if (b.getRemainingCapacity() - obj.getHeight() >= 0) {
                    tempCapa = b.getRemainingCapacity() - obj.getHeight();
                    if (tempCapa > remainingCapaBestBox) {
                        box = b;
                        remainingCapaBestBox = tempCapa;
                    }
                }
            }
            if (box == null) {
                box = new Box();
                boxList.add(box);
            }
        }
        return box;
    }

    /**
     * Algorithme du First Fit
     * @param obj objet à ajouter
     * @param boxList liste des boites
     * @return la boite sélectionnée
     */
    private Box firstBoxWithEnoughCapacity(Object obj, List<Box> boxList) {
        Box box = null;
        if (boxList.isEmpty()) {
            box = new Box();
            boxList.add(box);
        }
        else {
            for (Box b: boxList) {
                if (box == null && b.getRemainingCapacity() - obj.getHeight() >= 0) {
                    box = b;
                }
            }
            if (box == null) {
                box = new Box();
                boxList.add(box);
            }
        }
        return box;
    }

    /**
     * Algorithme de tri inverse des objets (tri par insertion)
     * @param objectList la liste des objets triee (ordre decroissant)
     */
    public void decreaseSort(List<Object> objectList) {

        Object[] arr = new Object[objectList.size()-1];
        for (int i = 0 ; i < objectList.size()-1 ; i++) {
            arr[i] = objectList.get(i);
        }

        Object key;
        int j;

        for (int i = 1 ; i < objectList.size()-1 ; i++) {
            key = arr[i];
            j = i - 1;
            while (j >= 0 && arr[j].getHeight() > key.getHeight()) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }

        // On inverse la liste triée

        int beg = 0;
        int end = arr.length-1;
        while (beg < end) {
            key = arr[beg];
            arr[beg] = arr[end];
            arr[end] = key;
            beg++;
            end--;
        }

        objectList.clear();
        for (int i = 0 ; i < arr.length ; i++) {
            objectList.add(arr[i]);
        }

    }

    /**
     * La méthode qui lit un graphe et génère le nombre d'objets en fonction du nombre de sommets de
     * ce graphe
     * @param graph le graphe à lire
     * @return la liste des objets (non triée)
     */
    public List<Object> generateObjectsFromGraph(Graph graph) {

        int numObjects = graph.vertices();
        List<Object> objects = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= numObjects; i++) {
            objects.add(new Object(random.nextInt(ITEMMAXSIZE - 9) + ITEMMINSIZE, i));
        }
        return objects;

    }

    /**
     * Méthode qui construit un graphe non orienté depuis un fichier avec un format bien particulier (voir le
     * dossier res)
     * @param path chemin du fichier
     * @return Graphe généré
     */
    public Graph getGraphFromFile(String path) {

        FileReader fileReader = null;
        Graph g = null;
        try {

            InputStream in = getClass().getResourceAsStream("/" + path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line;
            do {
                line = bufferedReader.readLine();
            } while (line.charAt(0) != 'p');
            String[] decompose = line.split(" ");
            g = new GraphArrayList(Integer.parseInt(decompose[2]) + 1);
            while ((line = bufferedReader.readLine()) != null) {
                decompose = line.split(" ");
                ((GraphArrayList) g).addEdge(new Edge(Integer.parseInt(decompose[1]), Integer.parseInt(decompose[2])));
                ((GraphArrayList) g).addEdge(new Edge(Integer.parseInt(decompose[2]), Integer.parseInt(decompose[1])));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return g;

    }

    /**
     * Colorie les sommets d'un graphe donne en parametre
     * @param u ensemble des sommets du graphe
     * @return l'ensemble des sommets colorés
     */
    public List<Vertice> dSatur(List<Vertice> u) {
        ArrayList<Vertice> c = new ArrayList<>(u.size());   // Ensemble des sommets colorés
        ArrayList<Vertice> uTrie = triDecroissantDegres(u);     // Tri des sommets
        u = uTrie;  // On travaille toujours dans u
        // Le 1ier sommet de uTrie est de degré maximal. On lui associe la couleur 1
        Vertice vDegreMax = u.get(0);
        vDegreMax.setColor(1);
        // On l'ajoute à la liste des sommets colorés
        c.add(vDegreMax);
        // On le supprime de la liste de départ
        u.remove(vDegreMax);

        Vertice v;
        //while(!c.equals(uTrie)) {
         while(!u.isEmpty()){
            // Choisir sommet v de u avec degré de saturation max
            // (nombre max de couleurs auxquelles il est adjacent parmi les sommets de c)
            // Si égalité, choisir le sommet de degré max
            v = sommetSaturMax(u);
//            System.out.println("Numéro de v : " + v.getNumero());

            // Attribuer à v le numéro de couleur le plus petit
            // Ajouter v à c et supprimer v de u
            setCouleur(v);
            c.add(v);
            u.remove(v);

            // Si c = v on s'arrête
            // Sinon retour au choix du sommet avec degré de saturation max
        }
        return c;
    }

    /**
     * Trie les sommets par ordre decroissant de degres
     * @param u ensemble des sommets du graphe
     * @return ensemble des sommets triés
     */
    private ArrayList<Vertice> triDecroissantDegres(List<Vertice> u) {
        ArrayList<Vertice> t = new ArrayList<>(u.size());   // Liste des sommets triés
        int i;
        for(Vertice v : u){
            if(t.isEmpty()){    // 1ière entrée
                t.add(v);
            }
            else {
                i = 0;
                while (i < t.size() && v.getDegre() < t.get(i).getDegre()){
                    i++;
                }
                if(i == t.size()){
                    // La 2ème condition a arrêté la boucle, on ajoute à la fin
                    t.add(v);
                }
                else {
                    // Sinon on ajoute au bon emplacement
                    t.add(i, v);
                }
            }
        }
        return t;
    }

    /**
     * Cherche le sommet avec le degre de saturation maximal dans une liste de sommets
     * @param u liste des sommets
     * @return sommet de degre de saturation maximal
     */
    private Vertice sommetSaturMax(List<Vertice> u){
        int nbColor;    // Nombre de voisins colorés
        Vertice sommet = null;
//        System.out.println("u : " + u);
        int nbColorMax = 0;
        // On regarde tous les sommets de la liste
        for(Vertice v : u){
            nbColor = 0;
            for(Vertice voisin : v.getAdjacents()){
                if(voisin.estColore()){
                    nbColor++;
                }
            }
            if(nbColor > nbColorMax){
                sommet = v;
                nbColorMax = nbColor;
            }
            // En cas d'égalité, on garde le 1ier sommet
            // Comme la liste est triée, le 1ier sommet sera toujours celui de degré max
        }
        return sommet;
    }

    /**
     * Attribution d'une couleur a un sommet
     * @param v sommet a colorier
     */
    private void setCouleur(Vertice v) {
        int couleur;
//        System.out.println("Numéro : " + v.getNumero() + " | Degré : " + v.getDegre());
        ArrayList<Integer> couleurVoisins = new ArrayList<>(v.getDegre());
        int i;
        for(Vertice voisin : v.getAdjacents()){
            couleur = voisin.getColor();
            if(couleur != 0 && !couleurVoisins.contains(couleur)) {
                if(couleurVoisins.isEmpty()){    // 1ière entrée
                    couleurVoisins.add(voisin.getColor());
                }
                else {
                    // On range les couleurs par ordre croissant
                    i = 0;
                    while (i < couleurVoisins.size() && voisin.getColor() < couleurVoisins.get(i)) {
                        i++;
                    }
                    couleurVoisins.add(i, voisin.getColor());
                }
            }
        }
//        System.out.println("Liste des couleurs voisines : " + couleurVoisins);
        // Nombre max de couleurs = nombre de voisins
        // On choisit donc des couleurs de 1 à <nombre de voisins>
        i = 1;  // couleur à tester
        couleur = 0;    // couleur finale du sommet
        while(couleur == 0){
            if(i == couleurVoisins.size()){
                couleur = i + 1;    // Tous les voisins sont colorés
                v.setColor(couleur);
            }
            else {  // i < size, on peut alors faire un get
                if(i < couleurVoisins.get(couleurVoisins.size() - i)) {  // On regarde par la fin
                    couleur = i;
                    v.setColor(couleur);    // Attribution de la couleur, fin de la boucle
                }
                else{
                    i++;
                }
            }
        }
//        System.out.println("Couleur : " + v.getColor());
    }

    /**
     * Trouve un remplissage ou chaque element est ajoute a la premiere boite
     * capable de l'accueillir et ne comptenant pas d'autres objets en conflit avec
     * @param filepath fichier comportant le graphe
     * @return liste des sommets colores
     */
    public int dSaturWithFFDpacking(String filepath) {
        Graph g = getGraphFromFile(filepath);
        // c = ensemble des sommets colorés
        ArrayList<Vertice> c = (ArrayList<Vertice>)dSatur(((GraphArrayList)g).getVertices());
        // Liste des objets
        List<Object> objectList = this.generateObjectsFromGraph(g);
        if (objectList.isEmpty()) {
            return 0;
        }

//        System.out.println("Dsatur succès");

        int idObjet;
        for(Object o : objectList){
            // On donne la couleur des sommets aux objets
            idObjet = o.getID();
            for(Vertice v : c){
                if(v.getNumero() == idObjet){
                    o.setCouleur(v.getColor());
                    break;
                }
            }
        }
//        System.out.println("Les sommets sont coloriés");

        // Création des boîtes
        List<Box> boxList = new ArrayList<>();
        boxList.add(new Box(objectList.get(0).getCouleur()));   // 1ière boîte de couleur du 1ier objet
        Box box;
        boolean conflict;
        for (Object o: objectList) {
            conflict = true;
            // On récupère la boite selon l'algorithme du First Fit (première boite dans laquelle on peut y mettre l'objet)
            for(Box b : boxList) {
                if (conflict && o.getCouleur() == b.getCouleur() && b.getCurrentFill() >= o.getHeight()) {
                    // On ajoute l'objet à la 1ière boîte de sa couleur qui a assez de place
                    b.addObject(o);
                    conflict = false;
                }
            }
            if (conflict) { // Que des conflits, on recrée une boîte
                box = new Box(o.getCouleur());  // Boîte de couleur de l'objet
                box.addObject(o);
                boxList.add(box);
            }
        }
        return boxList.size();
    }

}