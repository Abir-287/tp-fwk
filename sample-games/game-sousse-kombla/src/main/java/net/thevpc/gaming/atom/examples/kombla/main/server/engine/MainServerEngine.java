/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.gaming.atom.examples.kombla.main.server.engine;

import net.thevpc.gaming.atom.examples.kombla.main.shared.engine.BaseMainEngine;
import net.thevpc.gaming.atom.examples.kombla.main.server.dal.MainServerDAOListener;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;
import net.thevpc.gaming.atom.annotations.AtomSceneEngine;
import net.thevpc.gaming.atom.model.*;
import net.thevpc.gaming.atom.examples.kombla.main.server.dal.MainServerDAO;
import net.thevpc.gaming.atom.examples.kombla.main.server.dal.RMIMainServerDAO;

import java.util.stream.Collectors;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 */
@AtomSceneEngine(id = "mainServer", columns = 12, rows = 12)
public class MainServerEngine extends BaseMainEngine {

    private MainServerDAO dal;

    @Override
    protected void sceneActivating() {
        super.sceneActivating();
        // put here your MainClientDAO instance
        dal = new RMIMainServerDAO();
        // dal = new UDPMainServerDAO();
        dal.start(new MainServerDAOListener() {
            @Override
            public StartGameInfo onReceivePlayerJoined(String name) {
                Sprite sprite = addBomberPlayer(name);
                int playerId = sprite.getPlayerId();
                return new StartGameInfo(playerId, maze);
            }

            @Override
            public void onReceiveMoveLeft(int playerId) {
                move(playerId, Orientation.WEST);
            }

            @Override
            public void onReceiveMoveRight(int playerId) {
                move(playerId, Orientation.EAST);
            }

            @Override
            public void onReceiveMoveUp(int playerId) {
                move(playerId, Orientation.NORTH);

            }

            @Override
            public void onReceiveMoveDown(int playerId) {
                move(playerId, Orientation.SOUTH);
            }

            @Override
            public void onReceiveReleaseBomb(int playerId) {
                releaseBomb(playerId);
            }
        }, getAppConfig(getGameEngine()));
    }

    /**
     * each frame broadcast shared data to players. This method is called by
     * ATOM to READ model (R/O mode)
     */
    @Override
    protected void modelUpdated() {
        switch ((String) getModel().getProperty("Phase")) {
            case "WAITING": {
                // do nothing
                break;
            }
            case "GAMING":
            case "GAMEOVER": {
                // Convert sprites to serializable DTOs
                java.util.List<net.thevpc.gaming.atom.examples.kombla.main.shared.model.SerializableSprite> serializableSprites = getSprites()
                        .stream().map(sprite -> {
                            net.thevpc.gaming.atom.examples.kombla.main.shared.model.SerializableSprite s = new net.thevpc.gaming.atom.examples.kombla.main.shared.model.SerializableSprite();
                            s.setId(sprite.getId());
                            s.setKind(sprite.getKind());
                            s.setName(sprite.getName());
                            s.setX(sprite.getLocation().getX());
                            s.setY(sprite.getLocation().getY());
                            s.setDirection(sprite.getDirection());
                            s.setPlayerId(sprite.getPlayerId());
                            s.setMovementStyle(sprite.getMovementStyle());
                            s.setLife(sprite.getLife());
                            return s;
                        }).collect(Collectors.toList());

                // Convert players to serializable DTOs
                java.util.List<net.thevpc.gaming.atom.examples.kombla.main.shared.model.SerializablePlayer> serializablePlayers = getPlayers()
                        .stream().map(player -> {
                            net.thevpc.gaming.atom.examples.kombla.main.shared.model.SerializablePlayer p = new net.thevpc.gaming.atom.examples.kombla.main.shared.model.SerializablePlayer();
                            p.setId(player.getId());
                            p.setName(player.getName());
                            return p;
                        }).collect(Collectors.toList());

                dal.sendModelChanged(new DynamicGameModel(getFrame(), serializableSprites, serializablePlayers));
                break;
            }
        }
    }
}
