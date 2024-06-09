public class LogicaMergeSort {
     public void mergeSort(char[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }
    public void justChecking(char[] array){
        char[] temp = array;
        if (temp.length >= 50000){
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else{
            String bandera = "Todo Okey";
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
