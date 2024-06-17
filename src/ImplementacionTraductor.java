import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class ImplementacionTraductor extends UnicastRemoteObject implements InterfazTraductor {
    private List<char[]> dataList;
    private int clientCount;
    private int totalClients;
    private char[] combinedData;

    protected ImplementacionTraductor(int totalClients) throws RemoteException {
        dataList = new ArrayList<>();
        this.totalClients = totalClients;
        clientCount = 0;
        combinedData = new char[0]; // Inicializar el array combinado vacío
    }

    @Override
    public synchronized void addArray(char[] data) throws RemoteException {
        dataList.add(data);
        clientCount++;
        if (clientCount == totalClients) {
            clientCount = 0;
            combineData();
        }
    }

    private void combineData() {
        int totalLength = dataList.stream().mapToInt(arr -> arr.length).sum();
        combinedData = new char[totalLength];
        int index = 0;
        for (char[] array : dataList) {
            System.arraycopy(array, 0, combinedData, index, array.length);
            index += array.length;
        }
        dataList.clear();
    }

    @Override
    public synchronized char[] combineArrays() throws RemoteException {
        return combinedData;
    }

    @Override
    public synchronized void clearData() throws RemoteException {
        dataList.clear();
        combinedData = new char[0]; // Limpiar el array combinado
    }

    @Override
    public synchronized void clearCombinedArrays() throws RemoteException {
        combinedData = new char[0]; // Limpiar el array combinado
    }
}
