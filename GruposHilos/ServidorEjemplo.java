package GruposHilos;

public class ServidorEjemplo {
    // Tarea genérica de un hilo
    static class TareaUsuario implements Runnable {
        private final String tipo;

        public TareaUsuario(String tipo) {
            this.tipo = tipo;
        }

        @Override
        public void run() {
            Thread hilo = Thread.currentThread();
            ThreadGroup grupo = hilo.getThreadGroup();
            for (int i = 1; i <= 7; i++) {
                System.out.println(
                        "[" + grupo.getName() + "] "
                                + hilo.getName()
                                + " (" + tipo + " - " + i + ")");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println(hilo.getName() + " detenido");
                    return;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // ===== Grupo padre: servidor =====
        ThreadGroup servidor = new ThreadGroup("Servidor");
        // ===== Subgrupos: usuarios =====
        ThreadGroup usuarioA = new ThreadGroup(servidor, "Usuario-A");
        ThreadGroup usuarioB = new ThreadGroup(servidor, "Usuario-B");
        // ===== Hilos del usuario A =====
        Thread aRecibir = new Thread(usuarioA,
                new TareaUsuario("recibir datos"),
                "A-Recibir");
        Thread aEnviar = new Thread(usuarioA,
                new TareaUsuario("enviar datos"),
                "A-Enviar");
        // ===== Hilos del usuario B =====
        Thread bRecibir = new Thread(usuarioB,
                new TareaUsuario("recibir datos"),
                "B-Recibir");
        Thread bEnviar = new Thread(usuarioB,
                new TareaUsuario("enviar datos"),
                "B-Enviar");
        // Arrancamos todos los hilos
        aRecibir.start();
        aEnviar.start();
        bRecibir.start();
        bEnviar.start();
        // Simulamos que el usuario A se desconecta
        Thread.sleep(1000);

        System.out.println("\n❌ Usuario-A se desconecta\n");
        usuarioA.interrupt(); // interrumpe todos los hilos del grupo
        // Esperamos a que termine todo
        aRecibir.join();
        aEnviar.join();
        bRecibir.join();
        bEnviar.join();
        System.out.println("\nServidor cerrado");
    }
}
