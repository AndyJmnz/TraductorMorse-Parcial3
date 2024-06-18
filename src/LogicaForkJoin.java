
import java.util.concurrent.RecursiveAction;

public class LogicaForkJoin extends RecursiveAction {
    private char[] array;
    private int left;
    private int right;

    public LogicaForkJoin(char[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (left < right) {
            int mid = left + (right - left) / 2;

            LogicaForkJoin leftTask = new LogicaForkJoin(array, left, mid);
            LogicaForkJoin rightTask = new LogicaForkJoin(array, mid + 1, right);

            invokeAll(leftTask, rightTask);

            merge(array, left, mid, right);


        }

    }

    private void merge(char[] array, int left, int mid, int right) {
        char[] temp = new char[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid) {
            temp[k++] = array[i++];
        }

        while (j <= right) {
            temp[k++] = array[j++];
        }

        System.arraycopy(temp, 0, array, left, temp.length);
    }
}