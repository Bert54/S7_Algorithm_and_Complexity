public class TestClass {

    public static void main(String[] args) {

        System.out.println("Results with 125 Objects (Objects size are randomly selected between " + BinPacking.ITEMMINSIZE + " and " + BinPacking.ITEMMAXSIZE + ")");
        System.out.println("Fractional Packing (Inferior Limit) : " + BinPacking.getInstance().fractionalPacking("src/res/DSJC125.5.txt") + " boxes");
        System.out.println("First Fit Packing : " + BinPacking.getInstance().firstFitDecreasingPack("src/res/DSJC125.5.txt") + " boxes");
        System.out.println("Best Fit Packing : " + BinPacking.getInstance().bestFitDecreasingPacking("src/res/DSJC125.5.txt") + " boxes");


    }

}
