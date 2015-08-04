package com.ipetrov.lm.web;

import com.ipetrov.lm.web.dto.Message;
import com.ipetrov.lm.web.dto.MessageDecoder;
import com.ipetrov.lm.web.dto.MessageEncoder;
import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import static com.ipetrov.lm.web.Constants.*;

/**
 * Created with IntelliJ IDEA.
 * User: IPetrov
 * Date: 8/18/14
 * Time: 2:43 PM
 */
@ServerEndpoint(value = Constants.WS_SERVER_NAME, encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
public class LmWebSocketServer {
    private static final GameEngine gameEngine = new GameEngine();

    private static final Logger LOGGER = Logger.getLogger(LmWebSocketServer.class);

    @OnOpen
    public void onOpen(Session session) {
        gameEngine.onSessionOpened(session);
    }

    @OnMessage
    public void onMessage(Message message, Session session) {
        String messageType = message.getStringValue(MSG_PROP_TYPE);
        try {
            if (MSG_TYPE_LOGIN.equals(messageType)) {
                processLoginMessage(message, session);
            } else if (MSG_TYPE_PIT_CLICK.equals(messageType)) {
                processPitClickMessage(message, session);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (EncodeException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @OnClose
    public void onClose(Session session) {
        try {
            gameEngine.onSessionClosed(session);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (EncodeException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void processLoginMessage(Message message, Session session) throws IOException, EncodeException {
        String login = message.getStringValue(MSG_PROP_LOGIN);
        if (login == null || login.length() == 0 || login.length() > 255) {
            session.getBasicRemote().sendObject(MSG_INVALID_LOGIN);
        } else {
            gameEngine.onLogged(session, login);
        }
    }

    private void processPitClickMessage(Message message, Session session) throws IOException, EncodeException {
        gameEngine.onSow(session, message.getIntValue(MSG_PROP_PIT_NUMBER));
    }
}
