import java.util.ArrayList;
import java.util.List;

public class Box {

    public static final int BOXHEIGHT = 150;

    private int capacity;

    private List<Object> objects;

    public Box() {

        this.capacity = BOXHEIGHT;
        this.objects = new ArrayList<>();

    }

    public int getCurrentFill() {

        int capacity = 0;
        for (Object obj: this.objects) {
            capacity += obj.getHeight();
        }
        return capacity;

    }

    public void addObject(Object obj) {

        if (this.canAddObject(obj)) {
            this.objects.add(obj);
        }

    }

    public boolean canAddObject(Object obj) {

        if (obj.getHeight() + this.getCurrentFill() <= BOXHEIGHT) {
            return true;
        }
        return false;

    }

    public int getRemainingCapacity() {

        return BOXHEIGHT - this.getCurrentFill();

    }


    public List<Object> getObjects() {
        List<Object> temp = new ArrayList<>();
        for (Object o: this.objects) {
            temp.add(new Object(o.getHeight(), o.getID()));
        }
        return temp;
    }
}
