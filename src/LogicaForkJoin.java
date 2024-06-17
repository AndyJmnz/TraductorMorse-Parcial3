import java.util.HashMap;
import java.util.concurrent.RecursiveAction;

public class LogicaForkJoin extends RecursiveAction {
    private static final int THRESHOLD = 10;
    private char[] arr;
    private int l, r;
    private HashMap<Character, String> codigoMorse;

    public LogicaForkJoin(char[] arr, int l, int r, HashMap<Character, String> codigoMorse) {
        this.arr = arr;
        this.l = l;
        this.r = r;
        this.codigoMorse = codigoMorse;
    }

    @Override
    protected void compute() {
        if (r - l <= THRESHOLD) {
            // Realizar ordenamiento y traducción directamente
            for (int i = l; i <= r; i++) {
                char c = arr[i];
                if (codigoMorse.containsKey(c)) {
                    arr[i] = codigoMorse.get(c).charAt(0); // Suponiendo que solo tomamos el primer carácter de Morse
                }
            }
        } else {
            int m = l + (r - l) / 2;
            LogicaForkJoin left = new LogicaForkJoin(arr, l, m, codigoMorse);
            LogicaForkJoin right = new LogicaForkJoin(arr, m + 1, r, codigoMorse);
            invokeAll(left, right);
        }
    }
}