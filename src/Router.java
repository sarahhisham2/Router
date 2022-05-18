import java.util.Queue;
import java.util.Random;

public class Router {
    private int maxConnections;
    Semaphore semaphore;
    Device[] connections;
    Random rand;

    Router() {
        maxConnections = 0;
        semaphore = null;
        connections = null;
        rand = null;
    }

    Router(int maxConnections) {
        this.maxConnections = maxConnections;
        semaphore = new Semaphore(maxConnections);
        connections = new Device[maxConnections];
        rand = new Random();
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public synchronized void establishConnection(Device device) {
        System.out.println(device.name + " (" + device.type + ") is trying to connect.");
        if (semaphore.getAvailableConnections() <= 0) {
            System.out.println(device.name + "( " + device.type + ") is waiting.");
        }
    }

    public synchronized void connect(Device device) {
        boolean flag = false;
        for (int i = 0; i < maxConnections; i++) {
            if (connections[i] == null) {
                connections[i] = device;
                flag = true;
                System.out.println(device.name + " (" + device.type + ") connected to connection " + (i + 1));
                break;
            }
        }
        if (!flag) System.out.println("Router full. Please wait.");
    }

    public void doActivity(Device device) {
        System.out.println(device.name + " (" + device.type + ") is performing activity.");
        try {
            Thread.sleep(rand.nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void disconnect(Device device) {
        System.out.println(device.name + " (" + device.type + ") is disconnecting.");
        for (int i = 0; i < connections.length; i++) {
            if (connections[i] != null && connections[i].name == device.name) {
                connections[i] = null;
                break;
            }
        }
    }

}