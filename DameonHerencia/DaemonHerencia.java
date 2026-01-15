package DameonHerencia;

public class DaemonHerencia {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("RGG - [MAIN] isDaemon = " + Thread.currentThread().isDaemon()); // false
        Thread noDaemon = new Thread(() -> {
            System.out.println("RGG - [NO-DAEMON] isDaemon = " + Thread.currentThread().isDaemon());
            // Hilo creado desde un hilo normal
            Thread hijo = new Thread(() -> {
                System.out.println("RGG - [HIJO DE NO-DAEMON] isDaemon = " + Thread.currentThread().isDaemon());
            });
            hijo.start();
            try {
                hijo.join();
            } catch (InterruptedException e) {
            }
        });
        Thread daemon = new Thread(() -> {
            System.out.println("RGG - [DAEMON] isDaemon = " + Thread.currentThread().isDaemon());
            // Hilo creado desde un hilo daemon
            Thread hijo = new Thread(() -> {
                System.out.println("RGG - [HIJO DE DAEMON] isDaemon = " + Thread.currentThread().isDaemon());
            });
            hijo.start();
            try {
                hijo.join();
            } catch (InterruptedException e) {
            }
        });
        daemon.setDaemon(true);
        noDaemon.start();
        daemon.start();
        noDaemon.join();
        daemon.join();
        System.out.println("RGG - [MAIN] fin");
    }
}