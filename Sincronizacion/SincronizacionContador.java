package Sincronizacion;

public class SincronizacionContador {
    // ======= RECURSO COMPARTIDO =======
    static class Contador {
        private int valor = 0;

        // Versión SIN sincronizar (descomentar para probar)
        public void incrementar() {
            valor++; // NO es atómico
        }

        // Versión CON sincronización
        // public synchronized void incrementar() {
        // valor++; // ahora este incremento es atómico respecto a otros hilos
        // }

        public int getValor() {
            return valor;
        }
    }

    // ======= TAREA DEL HILO =======
    static class TareaIncremento implements Runnable {
        private final Contador contador;
        private final int repeticiones;

        public TareaIncremento(Contador contador, int repeticiones) {
            this.contador = contador;
            this.repeticiones = repeticiones;
        }

        @Override
        public void run() {
            for (int i = 0; i < repeticiones; i++) {
                contador.incrementar();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Contador contador = new Contador();
        int repeticiones = 1_000_000;
        Thread t1 = new Thread(new TareaIncremento(contador, repeticiones), "Hilo-1");
        Thread t2 = new Thread(new TareaIncremento(contador, repeticiones), "Hilo-2");
        long inicio = System.currentTimeMillis();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        long fin = System.currentTimeMillis();
        System.out.println("RGG- Valor esperado: " + (2 * repeticiones));
        System.out.println("RGG- Valor real:     " + contador.getValor());
        System.out.println("RGG- Tiempo: " + (fin - inicio) + " ms");
    }
}
