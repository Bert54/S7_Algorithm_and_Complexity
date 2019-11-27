public class Object {

    private final int height;
    private final int ID;

    public Object(int height) {

        this.height = height;
        this.ID = 0;

    }

    public Object(int height, int ID) {

        this.height = height;
        this.ID = ID;

    }

    public int getHeight() {

        return this.height;

    }

    public int getID() {

        return this.ID;

    }

}
