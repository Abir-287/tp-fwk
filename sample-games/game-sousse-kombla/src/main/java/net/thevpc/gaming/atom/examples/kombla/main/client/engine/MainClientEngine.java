/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.gaming.atom.examples.kombla.main.client.engine;

import net.thevpc.gaming.atom.examples.kombla.main.client.dal.MainClientDAO;
import net.thevpc.gaming.atom.examples.kombla.main.client.dal.MainClientDAOListener;
import net.thevpc.gaming.atom.examples.kombla.main.client.dal.RMIMainClientDAO;
import net.thevpc.gaming.atom.examples.kombla.main.shared.engine.AbstractMainEngine;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;
import net.thevpc.gaming.atom.annotations.AtomSceneEngine;
import net.thevpc.gaming.atom.model.*;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 */
@AtomSceneEngine(id = "mainClient", columns = 12, rows = 12)
public class MainClientEngine extends AbstractMainEngine {
    private MainClientDAO dao = new RMIMainClientDAO();

    public MainClientEngine() {
    }

    @Override
    protected void sceneActivating() {
        // Don't auto-connect here - wait for explicit connect call
        // This prevents connection errors when the game starts
        dao.start(new MainClientDAOListener() {
            @Override
            public void onModelChanged(final DynamicGameModel model) {
                invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        resetSprites();
                        resetPlayers();
                        getModel().setFrame(model.getFrame());

                        // Convert SerializablePlayers to Players
                        for (net.thevpc.gaming.atom.examples.kombla.main.shared.model.SerializablePlayer serPlayer : model
                                .getPlayers()) {
                            Player p = createPlayer();
                            p.setId(serPlayer.getId());
                            p.setName(serPlayer.getName());
                            addPlayer(p);
                        }

                        // Convert SerializableSprites to Sprites
                        for (net.thevpc.gaming.atom.examples.kombla.main.shared.model.SerializableSprite serSprite : model
                                .getSprites()) {
                            Sprite s = createSprite(serSprite.getKind());
                            s.setId(serSprite.getId());
                            s.setName(serSprite.getName());
                            s.setLocation(serSprite.getX(), serSprite.getY());
                            s.setDirection(serSprite.getDirection());
                            s.setPlayerId(serSprite.getPlayerId());
                            s.setMovementStyle(serSprite.getMovementStyle());
                            s.setLife(serSprite.getLife());
                            if ("Person".equals(serSprite.getKind()) || "Bomb".equals(serSprite.getKind())) {
                                s.setSize(new ModelDimension(0.5, 0.5));
                            }
                            addSprite(s);
                        }
                        MainClientEngine.this.getModel().setProperty("modelChanged", System.currentTimeMillis());
                    }
                });
            }
        }, getAppConfig(getGameEngine()));

        // Try to connect - if it fails, it's okay, user might be in host mode
        try {
            StartGameInfo startGameInfo = dao.connect();
            setModel(new DefaultSceneEngineModel(startGameInfo.getMaze()));
            setCurrentPlayerId(startGameInfo.getPlayerId());
        } catch (Exception e) {
            // Connection failed - this is expected if no server is running
            // Initialize with a default maze for now
            int[][] defaultMaze = new int[12][12];
            setModel(new DefaultSceneEngineModel(defaultMaze));
            System.out.println("Client not connected - waiting for server or switch to host mode");
        }
    }

    public void releaseBomb() {
        dao.sendFire();
    }

    public void move(Orientation direction) {
        switch (direction) {
            case EAST: {
                dao.sendMoveRight();
                break;
            }
            case WEST: {
                dao.sendMoveLeft();
                break;
            }
            case NORTH: {
                dao.sendMoveUp();
                break;
            }
            case SOUTH: {
                dao.sendMoveDown();
                break;
            }
        }
    }
}
