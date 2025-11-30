/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.thevpc.gaming.atom.examples.kombla.main.server.dal;

import net.thevpc.gaming.atom.examples.kombla.main.shared.dal.RMIServerService;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;
import net.thevpc.gaming.atom.examples.kombla.main.shared.engine.AppConfig;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * RMI Server DAO implementation
 * // Staaml RMIServerServiceImpl to handle RMI operations
 * 
 * @author abir
 */
public class RMIMainServerDAO implements MainServerDAO {
    private RMIServerServiceImpl serviceImpl;
    private AppConfig config;

    public RMIMainServerDAO() {
    }

    @Override
    public void start(MainServerDAOListener listener, AppConfig config) {
        this.config = config;

        try {
            serviceImpl = new RMIServerServiceImpl(listener);
            RMIServerService serverStub = (RMIServerService) UnicastRemoteObject.exportObject(serviceImpl, 0);

            Registry registry = LocateRegistry.createRegistry(config.getServerPort());
            registry.rebind("BombermanServer", serverStub);
            System.out.println("RMI Server started on port " + config.getServerPort());
        } catch (Exception e) {
            throw new RuntimeException("Failed to start RMI server", e);
        }
    }

    @Override
    public void sendModelChanged(DynamicGameModel model) {
        if (serviceImpl != null) {
            serviceImpl.broadcastModelUpdate(model);
        }
    }
}
