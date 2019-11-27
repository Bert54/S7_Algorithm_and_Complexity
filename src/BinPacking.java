import graph.Edge;
import graph.Graph;
import graph.GraphArrayList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    public List<Object> generateObjectsFromGraph(Graph graph) {

        int numObjects = graph.vertices();
        List<Object> objects = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= numObjects; i++) {
            objects.add(new Object(random.nextInt(ITEMMAXSIZE - 9) + ITEMMINSIZE, i));
        }
        return objects;

    }

    public Graph getGraphFromFile(String path) {

        FileReader fileReader = null;
        Graph g = null;
        try {

            fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
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
            g.writeFile("test");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return g;

    }

}
