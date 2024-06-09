import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ImplementacionTraductor extends UnicastRemoteObject implements InterfazTraductor {
    private List<char[]> dataList;
    private int clientCount;
    private int totalClients;


    protected ImplementacionTraductor(int totalClients) throws RemoteException {
        dataList = new ArrayList<>();
        this.totalClients = totalClients;
        clientCount = 0;
    }

    @Override
    public void addArray(char[] data) throws RemoteException {
        dataList.add(data);
        clientCount++;
        if (clientCount == totalClients) {
            clientCount = 0;
            combineData();
        }
    }
    private void combineData() {
        int totalLength = dataList.stream().mapToInt(arr -> arr.length).sum();
        char[] combinedArray = new char[totalLength];
        int index = 0;
        for (char[] array : dataList) {
            System.arraycopy(array, 0, combinedArray, index, array.length);
            index += array.length;
        }
        dataList.clear();
        dataList.add(combinedArray);
    }

    @Override
    public char[] combineArrays() throws RemoteException {
        if (dataList.isEmpty()) return new char[0];
        int totalLength = dataList.stream().mapToInt(arr -> arr.length).sum();
        char[] combinedArray = new char[totalLength];
        int index = 0;
        for (char[] array : dataList) {
            System.arraycopy(array, 0, combinedArray, index, array.length);
            index += array.length;
        }
        return combinedArray;
    }

    @Override
    public void clearData() throws RemoteException {
        dataList.clear();
    }

    @Override
    public void clearCombinedArrays() throws RemoteException {
        dataList.clear();
    }

}