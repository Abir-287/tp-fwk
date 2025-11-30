/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.gaming.atom.examples.kombla.main.shared.engine;

/**
 *
 * @author vpc
 */
public class AppConfig {
    private String serverAddress = "localhost";
    private String playerName;
    private int serverPort = 1234;

    public String getPlayerName() {
        return playerName;
    }

    public AppConfig setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

}
