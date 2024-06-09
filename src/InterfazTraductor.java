import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfazTraductor extends Remote {
    void addArray(char[] data) throws RemoteException;

    char[] combineArrays() throws RemoteException;

    void clearData() throws RemoteException;

    void clearCombinedArrays() throws RemoteException;
}