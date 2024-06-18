import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogicaExecutorService {


    public static void Time(char[] array) {
        if (array.length <= 5000) {
            Random rand = new Random();
            int sleepTime = 1 + rand.nextInt(3);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static String traducirTextoConExecutorService(char[] charArray, HashMap<Character, String> codigoMorse) {
        StringBuilder morseCode = new StringBuilder();

        for (char c : charArray) {
            if (codigoMorse.containsKey(c)) {
                morseCode.append(codigoMorse.get(c)).append(" "); 
            } else if (c == ' ') {
                morseCode.append(" "); 
            }
        }

        return morseCode.toString();
    }
}
