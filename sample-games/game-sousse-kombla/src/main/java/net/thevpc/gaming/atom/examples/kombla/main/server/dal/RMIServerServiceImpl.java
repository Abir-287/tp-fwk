package net.thevpc.gaming.atom.examples.kombla.main.server.dal;

import net.thevpc.gaming.atom.examples.kombla.main.shared.dal.RMIClientService;
import net.thevpc.gaming.atom.examples.kombla.main.shared.dal.RMIServerService;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

/**
 * RMI Server Service Implementation
 * 
 * @author abir
 */
public class RMIServerServiceImpl implements RMIServerService {
    private final MainServerDAOListener listener;
    private final Map<Integer, RMIClientService> clientCallbacks;

    public RMIServerServiceImpl(MainServerDAOListener listener) {
        this.listener = listener;
        this.clientCallbacks = new HashMap<>();
    }

    @Override
    public StartGameInfo connect(String playerName, RMIClientService cli) throws RemoteException {
        System.out.println("=== CONNECT METHOD CALLED ===");
        System.out.println("Player name: " + playerName);
        System.out.println("Client callback: " + cli);

        StartGameInfo startGameInfo = listener.onReceivePlayerJoined(playerName);
        clientCallbacks.put(startGameInfo.getPlayerId(), cli);

        System.out.println("Player " + playerName + " connected with ID: " + startGameInfo.getPlayerId());
        System.out.println("Total clients registered: " + clientCallbacks.size());
        System.out.println("Client callbacks map: " + clientCallbacks.keySet());

        return startGameInfo;
    }

    @Override
    public void moveLeft(int playerId) throws RemoteException {
        listener.onReceiveMoveLeft(playerId);
    }

    @Override
    public void moveRight(int playerId) throws RemoteException {
        listener.onReceiveMoveRight(playerId);
    }

    @Override
    public void moveUp(int playerId) throws RemoteException {
        listener.onReceiveMoveUp(playerId);
    }

    @Override
    public void moveDown(int playerId) throws RemoteException {
        listener.onReceiveMoveDown(playerId);
    }

    @Override
    public void fire(int playerId) throws RemoteException {
        listener.onReceiveReleaseBomb(playerId);
    }

    /**
     * Broadcast model updates to all connected clients
     */
    public void broadcastModelUpdate(net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel model) {
        System.out.println("Broadcasting to " + clientCallbacks.size() + " clients");
        java.util.Iterator<Map.Entry<Integer, RMIClientService>> iterator = clientCallbacks.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, RMIClientService> entry = iterator.next();
            try {
                System.out.println("Attempting to send to client " + entry.getKey());
                entry.getValue().onModelChanged(model);
                System.out.println("Successfully sent update to client " + entry.getKey());
            } catch (RemoteException e) {
                System.err.println("Failed to send model update to client " + entry.getKey());
                System.err.println("Exception type: " + e.getClass().getName());
                System.err.println("Exception message: " + e.getMessage());
                e.printStackTrace();
                // Remove disconnected clients safely
                iterator.remove();
            }
        }
    }
}
