public class TestClass {


    public static void main(String[] args) {
        questions1_3();
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

}
