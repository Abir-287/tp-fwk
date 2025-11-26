package net.thevpc.gaming.atom.examples.kombla.main.server.dal;

import net.thevpc.gaming.atom.examples.kombla.main.shared.dal.ProtocolConstants;
import net.thevpc.gaming.atom.examples.kombla.main.shared.engine.AppConfig;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TCPMainServerDAO implements MainServerDAO {
    MainServerDAOListener l;
    AppConfig c ;
    @Override
    public void start(MainServerDAOListener l, AppConfig c) {
        this.l = l;
        this.c = c;
        int port= c.getServerPort();
        new Thread(()-> {
            ServerSocket s;
            try {
                s = new ServerSocket(port);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    ;}
    void startServer() throws IOException {
            ServerSocket ss = new ServerSocket(c.getServerPort());
            while (true){
                Socket s = ss.accept();
                new Thread(()-> {
                    try {
                        processClient(s);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
    }

    void processClient(Socket s) throws IOException {
        DataInputStream inputStream = new DataInputStream(s.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
        int c = inputStream.readInt();
        if (c!= ProtocolConstants.CONNECT){
            s.close();
            return;
        }
        String n = inputStream.readUTF();
        StartGameInfo sgi = l.onReceivePlayerJoined(n);
        ClientSession cs = new ClientSession ();
        cs.playerId = sgi.getPlayerId();
        cs.dataInputStream = inputStream;
        cs.dataOutputStream = outputStream;
        cs.socket = s;
        playerToSocketMap.put(sgi.getPlayerId(),cs);
        outputStream.writeInt(ProtocolConstants.OK);
        outputStream.writeInt(cs.playerId);
        outputStream.writeInt(sgi.getMaze().length);
        outputStream.writeInt(sgi.getMaze()[0].length);
        for(int i=0; i<sgi.getMaze().length;i++){
            for (int j=0; j<sgi.getMaze()[0].length;j++){
                outputStream.writeInt(sgi.getMaze()[i][j]);
            }
        }
    }

    @Override
    public void sendModelChanged(DynamicGameModel dynamicGameModel) {

    }

    static class ClientSession {
        public int playerId;
        public Socket socket;
        public DataInputStream dataInputStream;
        public DataOutputStream dataOutputStream;
    }
    private Map<Integer, ClientSession> playerToSocketMap = new HashMap<>() ;

}
