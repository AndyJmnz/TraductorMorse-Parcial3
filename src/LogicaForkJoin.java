import java.util.HashMap;
import java.util.concurrent.RecursiveAction;

public class LogicaForkJoin extends RecursiveAction {

    private static final int THRESHOLD = 10;

    private char[] array;
    private int l;
    private int r;
    private HashMap<Character, String> codigoMorse;

    public LogicaForkJoin(char[] array, int l, int r, HashMap<Character, String> codigoMorse) {
        this.array = array;
        this.l = l;
        this.r = r;
        this.codigoMorse = codigoMorse;
    }

    @Override
    protected void compute() {
        if (r - l <= THRESHOLD) {
            // Realizar ordenamiento y traducción secuencialmente
            mergeSortAndTranslate(array, l, r);
        } else {
            // Dividir el trabajo en dos sub-tareas
            int m = l + (r - l) / 2;
            LogicaForkJoin leftTask = new LogicaForkJoin(array, l, m, codigoMorse);
            LogicaForkJoin rightTask = new LogicaForkJoin(array, m + 1, r, codigoMorse);

            // Invocar sub-tareas de manera asíncrona
            leftTask.fork();
            rightTask.compute();

            // Esperar a que las sub-tareas completen
            leftTask.join();
        }
    }

    private void mergeSortAndTranslate(char[] array, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSortAndTranslate(array, l, m);
            mergeSortAndTranslate(array, m + 1, r);
            merge(array, l, m, r);
        }
    }

    private void merge(char[] array, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        char[] L = new char[n1];
        char[] R = new char[n2];

        System.arraycopy(array, l, L, 0, n1);
        System.arraycopy(array, m + 1, R, 0, n2);

        int i = 0, j = 0;
        int k = l;

        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }

        translateArray(array, l, r);
    }

    private void translateArray(char[] array, int l, int r) {
        for (int i = l; i <= r; i++) {
            if (codigoMorse.containsKey(array[i])) {
                array[i] = codigoMorse.get(array[i]).charAt(0); // Traducción a Morse simplificada
            }
        }
    }
}