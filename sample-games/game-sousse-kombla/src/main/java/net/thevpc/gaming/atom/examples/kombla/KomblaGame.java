/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.gaming.atom.examples.kombla;

import net.thevpc.gaming.atom.Atom;

import net.thevpc.gaming.atom.engine.GameEngine;
import net.thevpc.gaming.atom.examples.kombla.main.client.engine.MainClientEngine;
import net.thevpc.gaming.atom.examples.kombla.main.server.engine.MainServerEngine;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 */
public class KomblaGame {

        public static void main(String[] args) {
                System.out.println("Kombla Game Starting... (Version 2)");
                GameEngine engine = Atom.createGameEngine();

                // Create and register scene engines
                MainServerEngine serverEngine = new MainServerEngine();
                MainClientEngine clientEngine = new MainClientEngine();
                engine.addSceneEngine(serverEngine);
                engine.addSceneEngine(clientEngine);

                // Create the game
                net.thevpc.gaming.atom.presentation.Game game = Atom.createGame(engine);

                // Register scenes for both engines using their class names as IDs
                net.thevpc.gaming.atom.presentation.DefaultSceneFactory factory = (net.thevpc.gaming.atom.presentation.DefaultSceneFactory) game
                                .getSceneFactory();

                factory.bindScene(
                                new net.thevpc.gaming.atom.examples.kombla.main.shared.prensentation.BomberScene(),
                                MainServerEngine.class.getName());
                factory.bindScene(
                                new net.thevpc.gaming.atom.examples.kombla.main.shared.prensentation.BomberScene(),
                                MainClientEngine.class.getName());

                // Start the game
                game.start();
        }
}
