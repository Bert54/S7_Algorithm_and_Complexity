import graph.Graph;
import graph.GraphArrayList;
import graph.Vertice;

import java.util.ArrayList;

public class TestClass {


    public static void main(String[] args) {
        //questions1_3();
        //question4();
        question5();
    }

    public static void questions1_3() {
        System.out.println("Results with 125 Objects (Objects size are randomly selected between " + BinPacking.ITEMMINSIZE + " and " + BinPacking.ITEMMAXSIZE + ")");
        System.out.println("Fractional Packing (Inferior Limit) : " + BinPacking.getInstance().fractionalPacking("src/res/DSJC125.5.txt") + " boxes");
        System.out.println("First Fit Packing : " + BinPacking.getInstance().firstFitDecreasingPack("src/res/DSJC125.5.txt") + " boxes");
        System.out.println("Best Fit Packing : " + BinPacking.getInstance().bestFitDecreasingPacking("src/res/DSJC125.5.txt") + " boxes");
        System.out.println();

        System.out.println("Results with 250 Objects (Objects size are randomly selected between " + BinPacking.ITEMMINSIZE + " and " + BinPacking.ITEMMAXSIZE + ")");
        System.out.println("Fractional Packing (Inferior Limit) : " + BinPacking.getInstance().fractionalPacking("src/res/DSJC250.5.txt") + " boxes");
        System.out.println("First Fit Packing : " + BinPacking.getInstance().firstFitDecreasingPack("src/res/DSJC250.5.txt") + " boxes");
        System.out.println("Best Fit Packing : " + BinPacking.getInstance().bestFitDecreasingPacking("src/res/DSJC250.5.txt") + " boxes");
        System.out.println();

        System.out.println("Results with 500 Objects (Objects size are randomly selected between " + BinPacking.ITEMMINSIZE + " and " + BinPacking.ITEMMAXSIZE + ")");
        System.out.println("Fractional Packing (Inferior Limit) : " + BinPacking.getInstance().fractionalPacking("src/res/DSJC500.5.txt") + " boxes");
        System.out.println("First Fit Packing : " + BinPacking.getInstance().firstFitDecreasingPack("src/res/DSJC500.5.txt") + " boxes");
        System.out.println("Best Fit Packing : " + BinPacking.getInstance().bestFitDecreasingPacking("src/res/DSJC500.5.txt") + " boxes");
        System.out.println();

        System.out.println("Results with 1000 Objects (Objects size are randomly selected between " + BinPacking.ITEMMINSIZE + " and " + BinPacking.ITEMMAXSIZE + ")");
        System.out.println("Fractional Packing (Inferior Limit) : " + BinPacking.getInstance().fractionalPacking("src/res/DSJC1000.5.txt") + " boxes");
        System.out.println("First Fit Packing : " + BinPacking.getInstance().firstFitDecreasingPack("src/res/DSJC1000.5.txt") + " boxes");
        System.out.println("Best Fit Packing : " + BinPacking.getInstance().bestFitDecreasingPacking("src/res/DSJC1000.5.txt") + " boxes");
    }

    private static void question4() {
        System.out.println("DSatur graphe 3 3");
        affichageQ4("src/res/graphe3_3.txt");

        System.out.println("DSatur graphe 6 9");
        affichageQ4("src/res/graphe6_9.txt");
    }

    private static void affichageQ4(String filepath) {
        Graph g = BinPacking.getInstance().getGraphFromFile(filepath);
        ArrayList<Vertice> vertices = (ArrayList<Vertice>)BinPacking.getInstance().dSatur(((GraphArrayList)g).getVertices());

        ArrayList<Integer> an = new ArrayList<>();  // Numéro des sommets triés
        ArrayList<Integer> ac = new ArrayList<>();  // Couleur des sommets
        ArrayList<Integer> ad = new ArrayList<>();  // Degré des sommets
        for(Vertice v : vertices){
            an.add(v.getNumero());
            ac.add(v.getColor());
            ad.add(v.getDegre());
        }
        System.out.println("Numéros  : " + an);
        System.out.println("Couleurs : " + ac);
        System.out.println("Degrés   : " + ad + "\n");
    }

    private static void question5() {
        System.out.println("Results with 125 Objects (Objects size are randomly selected between " + BinPacking.ITEMMINSIZE + " and " + BinPacking.ITEMMAXSIZE + ")");
        System.out.println("First Fit Packing : " + BinPacking.getInstance().firstFitDecreasingPack("src/res/DSJC125.5.txt") + " boxes");
        System.out.println("Dsatur First Fit Packing : " + BinPacking.getInstance().dSaturWithFFDpacking("src/res/DSJC125.5.txt") + " boxes");
        System.out.println();

        System.out.println("Results with 250 Objects (Objects size are randomly selected between " + BinPacking.ITEMMINSIZE + " and " + BinPacking.ITEMMAXSIZE + ")");
        System.out.println("First Fit Packing : " + BinPacking.getInstance().firstFitDecreasingPack("src/res/DSJC250.5.txt") + " boxes");
        System.out.println("Dsatur First Fit Packing : " + BinPacking.getInstance().dSaturWithFFDpacking("src/res/DSJC250.5.txt") + " boxes");
        System.out.println();

        System.out.println("Results with 500 Objects (Objects size are randomly selected between " + BinPacking.ITEMMINSIZE + " and " + BinPacking.ITEMMAXSIZE + ")");
        System.out.println("First Fit Packing : " + BinPacking.getInstance().firstFitDecreasingPack("src/res/DSJC500.5.txt") + " boxes");
        System.out.println("Dsatur First Fit Packing : " + BinPacking.getInstance().dSaturWithFFDpacking("src/res/DSJC500.5.txt") + " boxes");
        System.out.println();

        System.out.println("Results with 1000 Objects (Objects size are randomly selected between " + BinPacking.ITEMMINSIZE + " and " + BinPacking.ITEMMAXSIZE + ")");
        System.out.println("First Fit Packing : " + BinPacking.getInstance().firstFitDecreasingPack("src/res/DSJC1000.5.txt") + " boxes");
        System.out.println("Dsatur First Fit Packing : " + BinPacking.getInstance().dSaturWithFFDpacking("src/res/DSJC1000.5.txt") + " boxes");
        System.out.println();
    }

}
