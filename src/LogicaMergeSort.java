import java.util.Arrays;
import java.util.HashMap;

public class LogicaMergeSort {

    private HashMap<Character, String> codigoMorse;

    public LogicaMergeSort(HashMap<Character, String> codigoMorse) {
        this.codigoMorse = codigoMorse;
    }

    public void mergeSortAndTranslate(char[] array, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSortAndTranslate(array, l, m);
            mergeSortAndTranslate(array, m + 1, r);
            merge(array, l, m, r);
            translateArray(array, l, r); // Traducir solo después de que el arreglo esté ordenado
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
    }

    private void translateArray(char[] array, int l, int r) {
        // Crear una copia del segmento a traducir
        char[] segment = Arrays.copyOfRange(array, l, r + 1);

        // Traducir cada carácter del segmento según el código Morse
        for (int i = 0; i < segment.length; i++) {
            char originalChar = segment[i];
            if (codigoMorse.containsKey(originalChar)) {
                // Obtener el código Morse correspondiente
                String morseCode = codigoMorse.get(originalChar);
                // Reemplazar en el arreglo original
                array[l + i] = morseCode.charAt(0); // Suponemos que el primer carácter del código Morse es suficiente
            }
        }
    }
}
