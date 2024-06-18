import java.util.Random;

public class LogicaMergeSort {
    public void mergeSort(char[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
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




    public void Time(char[] array) {
        if (array.length >= 5000) {
            Random rand = new Random();
            int sleepTime = 50 + rand.nextInt(4);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
