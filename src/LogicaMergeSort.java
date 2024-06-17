import java.util.HashMap;

public class LogicaMergeSort {

    public void mergeSortAndTranslate(char[] arr, int l, int r, HashMap<Character, String> codigoMorse) {
        if (l < r) {
            int m = l + (r - l) / 2;

            mergeSortAndTranslate(arr, l, m, codigoMorse);
            mergeSortAndTranslate(arr, m + 1, r, codigoMorse);

            merge(arr, l, m, r, codigoMorse);
        }
    }

    private void merge(char[] arr, int l, int m, int r, HashMap<Character, String> codigoMorse) {
        // Realizar el merge sort
        // Aquí implementa la lógica del merge sort
        // ...

        // Traducir a código Morse
        for (int i = l; i <= r; i++) {
            char c = arr[i];
            if (codigoMorse.containsKey(c)) {
                arr[i] = codigoMorse.get(c).charAt(0); // Suponiendo que solo tomamos el primer carácter de Morse
            }
        }
    }
}