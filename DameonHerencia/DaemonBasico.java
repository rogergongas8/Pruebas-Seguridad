package DameonHerencia;

public class DaemonBasico {
    public static void main(String[] args) throws InterruptedException {
        Thread daemon = new Thread(() -> {
            int i = 0;
            while (true) {
                System.out.println(" RGG- [DAEMON] trabajando... " + i++);
                try {
                    Thread.sleep(500); // medio segundo
                } catch (InterruptedException e) {
                    System.out.println(" RGG- [DAEMON] interrumpido");
                    break;
                }
            }
        });
        // Lo marcamos como hilo demonio ANTES de arrancarlo
        // daemon.setDaemon(true);
        System.out.println(" RGG- ¿daemon.isDaemon()? " + daemon.isDaemon()); // true
        daemon.start();
        daemon.setDaemon(true);
        // El hilo main sigue con su vida
        System.out.println(" RGG- [MAIN] voy a dormir 2 segundos");
        Thread.sleep(2000);
        System.out.println(" RGG- [MAIN] termino ahora. La JVM se apagará y matará al daemon.");
    }
}
