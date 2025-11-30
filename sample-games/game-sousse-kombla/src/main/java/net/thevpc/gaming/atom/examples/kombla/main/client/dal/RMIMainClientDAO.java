/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.thevpc.gaming.atom.examples.kombla.main.client.dal;

import net.thevpc.gaming.atom.examples.kombla.main.shared.dal.RMIClientService;
import net.thevpc.gaming.atom.examples.kombla.main.shared.dal.RMIServerService;
import net.thevpc.gaming.atom.examples.kombla.main.shared.engine.AppConfig;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author abir
 */
public class RMIMainClientDAO implements MainClientDAO {
    private MainClientDAOListener listener;
    private AppConfig config;
    private RMIServerService serverService;
    private RMIClientServiceImpl clientServiceImpl;
    private int playerId;

    public RMIMainClientDAO() {
    }

    @Override
    public void start(MainClientDAOListener listener, AppConfig config) {
        this.listener = listener;
        this.config = config;
    }

    @Override
    public StartGameInfo connect() {
        try {
            clientServiceImpl = new RMIClientServiceImpl(listener);

            RMIClientService clientStub = (RMIClientService) UnicastRemoteObject.exportObject(clientServiceImpl, 0);

            Registry registry = LocateRegistry.getRegistry(
                    config.getServerAddress(),
                    config.getServerPort());

            serverService = (RMIServerService) registry.lookup("BombermanServer");

            StartGameInfo startGameInfo = serverService.connect(config.getPlayerName(), clientStub);
            this.playerId = startGameInfo.getPlayerId();

            System.out.println("Connected to server. Player ID: " + playerId);
            return startGameInfo;

        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to RMI server: " + e.getMessage(), e);
        }
    }

    @Override
    public void sendMoveLeft() {
        try {
            serverService.moveLeft(playerId);
        } catch (RemoteException e) {
            System.err.println("Failed to send move left: " + e.getMessage());
        }
    }

    @Override
    public void sendMoveRight() {
        try {
            serverService.moveRight(playerId);
        } catch (RemoteException e) {
            System.err.println("Failed to send move right: " + e.getMessage());
        }
    }

    @Override
    public void sendMoveUp() {
        try {
            serverService.moveUp(playerId);
        } catch (RemoteException e) {
            System.err.println("Failed to send move up: " + e.getMessage());
        }
    }

    @Override
    public void sendMoveDown() {
        try {
            serverService.moveDown(playerId);
        } catch (RemoteException e) {
            System.err.println("Failed to send move down: " + e.getMessage());
        }
    }

    @Override
    public void sendFire() {
        try {
            serverService.fire(playerId);
        } catch (RemoteException e) {
            System.err.println("Failed to send fire: " + e.getMessage());
        }
    }
}