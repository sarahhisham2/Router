import java.util.LinkedList;
import java.util.Queue;

public class Semaphore {
    private Queue<Device> devices;
    private int availableConnections;

    Semaphore() {
        availableConnections = 0;
        devices = null;
    }

    Semaphore(int availableConnections) {
        this.availableConnections = availableConnections;
        devices = new LinkedList<Device>();
    }

    public int getAvailableConnections() {
        return availableConnections;
    }

    public void connect(Device device) {
        synchronized (this) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            availableConnections--;
            if (availableConnections >= 0) {
                device.router.connect(device);
                return;
            }
        }

        if (availableConnections < 0) {
            devices.add(device);
            try {
                device.join();
            } catch (InterruptedException e) {
                synchronized (this) {
                    try {
                        device.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    device.router.connect(device);
                }
            }
        }
    }

    public synchronized void disconnect() {
        availableConnections++;
        if (!devices.isEmpty()) {
            Device d = devices.remove();
            d.interrupt();
        }
    }
}
