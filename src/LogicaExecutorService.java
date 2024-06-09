import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogicaExecutorService {
    
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
