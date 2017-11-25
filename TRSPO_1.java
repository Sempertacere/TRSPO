package trspo;

abstract class Aperture {
    private double height;
    private double width;
    public Aperture(double h, double w) {
        this.height = h;
        this.width = w;
    }
    public double getHeight() {
        return height;
    }
    public double getWidth() {
        return width;
    }
    public void setHeight(double newHeight) {
        height = newHeight;
    }
    public void setWidth(double newWidth) {
        width = newWidth;
    }
}

class Window extends Aperture implements Closable {
    private String material;
    private boolean closed;
    public Window(double h, double w, String m, boolean c) {
        super(h, w);
        this.material = m;
        this.closed = c;
    }
    public void close() {
        closed = true;
    }
    public void open() {
        closed = false;
    }
    public boolean getState() {
        return closed;
    }
}

class Door extends Aperture implements Closable, Lockable {
    private String material;
    private boolean closed;
    private boolean[] locks;
    public Door(double h, double w, String m, boolean c, boolean[] l) {
        super(h, w);
        this.material = m;
        this.closed = c;
        this.locks = l;
    }
    public void close() {
        boolean canClose = true;
        for (int i = 0; i < locks.length; i++) {
            if(locks[i]) {
                canClose = false;
                break;
            }
        }
        closed = canClose;
    }
    public void open() {
        boolean isLocked = false;
        for (int i = 0; i < locks.length; i++) {
            if(locks[i]) {
                isLocked = true;
                break;
            }
        }
        closed = isLocked;
    }
    public void lock(int index) {
        locks[index] = true;
    }
    public void unlock(int index) {
        locks[index] = false;
    }
    public boolean getState() {
        return closed;
    }
}

interface Closable {
    public void close();
    public void open();
}

interface Lockable {
    public void lock(int index);
    public void unlock(int index);
}

public class TRSPO_1 {
    public static void main(String[] args) {
        Window winInKitchen = new Window(140, 42.5, "metal-plastic", false), winInLounge = new Window(150, 45, "metal-plastic", true);
        Door doorInKitchen = new Door(185, 60, "wood", true, new boolean[]{false}), doorInApartment = new Door(190, 65, "wood", true, new boolean[]{true, false, false});
        winInLounge.close();
        winInKitchen.setWidth(42);
        doorInApartment.open();
        System.out.println(doorInApartment.getState() ? "The door is still closed" : "Now the door is opened");
        doorInApartment.unlock(0);
        doorInApartment.open();
        System.out.println(doorInApartment.getState() ? "The door is still closed" : "Now the door is opened");
        doorInApartment.close();
        doorInApartment.lock(0);
    }
}