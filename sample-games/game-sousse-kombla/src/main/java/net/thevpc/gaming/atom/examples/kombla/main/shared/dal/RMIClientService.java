
package net.thevpc.gaming.atom.examples.kombla.main.shared.dal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;

/**
 *
 * @author abir
 */
public interface RMIClientService extends Remote {
    void onModelChanged(DynamicGameModel model) throws RemoteException;
}
