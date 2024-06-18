import java.util.HashMap;
import java.util.concurrent.RecursiveAction;

public class LogicaForkJoin extends RecursiveAction {

    private static final int THRESHOLD = 10;
    private char[] array;
    private int left;
    private int right;
    private HashMap<Character, String> CodigoMorse;

    public LogicaForkJoin(char[] array, int left, int right, HashMap<Character, String> CodigoMorse) {
        this.array = array;
        this.left = left;
        this.right = right;
        this.CodigoMorse = CodigoMorse;
    }

    @Override
    protected void compute() {
        if (left < right) {
            int mid = left + (right - left) / 2;

            LogicaForkJoin leftTask = new LogicaForkJoin(array, left, mid, CodigoMorse);
            LogicaForkJoin rightTask = new LogicaForkJoin(array, mid + 1, right, CodigoMorse);

            invokeAll(leftTask, rightTask);

            merge(array, left, mid, right);
        }
    }

    private void merge(char[] array, int left, int mid, int right) {
        char[] temp = new char[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = array[i++];
        }

        while (j <= right) {
            temp[k++] = array[j++];
        }

        System.arraycopy(temp, 0, array, left, temp.length);
    }

    public void translateArray() {
        translateRange(array, left, right);
    }

    private void translateRange(char[] array, int left, int right) {
        for (int i = left; i <= right; i++) {
            if (CodigoMorse.containsKey(array[i])) {
                array[i] = CodigoMorse.get(array[i]).charAt(0); // Traducción a Morse simplificada
            }
        }
    }

    public char[] getTranslatedArrayCopy() {
        return array.clone(); // Devuelve una copia del arreglo traducido
    }
}
