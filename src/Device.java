public class Device extends Thread {
    String name;
    String type;
    Router router;

    Device() {
        this.name = "";
        this.type = "";
        this.router = null;
    }

    Device(String name, String type, Router router) {
        this.name = name;
        this.type = type;
        this.router = router;
    }

    @Override
    public void run() {
        router.establishConnection(this);
        router.semaphore.connect(this);
        router.doActivity(this);
        router.disconnect(this);
        router.semaphore.disconnect();
    }
}
