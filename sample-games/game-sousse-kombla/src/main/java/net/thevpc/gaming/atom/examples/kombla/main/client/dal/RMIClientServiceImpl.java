package net.thevpc.gaming.atom.examples.kombla.main.client.dal;

import net.thevpc.gaming.atom.examples.kombla.main.shared.dal.RMIClientService;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;

import java.rmi.RemoteException;

/**
 * 
 * @author abir
 */
public class RMIClientServiceImpl implements RMIClientService {
    private final MainClientDAOListener listener;

    public RMIClientServiceImpl(MainClientDAOListener listener) {
        this.listener = listener;
    }

    @Override
    public void onModelChanged(DynamicGameModel model) throws RemoteException {
        if (listener != null) {
            listener.onModelChanged(model);
        }
    }
}
