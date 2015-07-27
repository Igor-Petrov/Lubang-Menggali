package com.bol.test.web.dto;

import org.apache.log4j.Logger;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

/**
 * Created with IntelliJ IDEA.
 * User: IPetrov
 * Date: 8/18/14
 * Time: 4:04 PM
 */
public class MessageDecoder implements Decoder.Text<Message> {
    private static final Logger logger = Logger.getLogger(MessageDecoder.class);

    public Message decode(String string) throws DecodeException {
        JsonObject json = Json.createReader(new StringReader(string)).readObject();
        return new Message(json);
    }

    public boolean willDecode(String string) {
        try {
            Json.createReader(new StringReader(string)).read();
            return true;
        } catch (JsonException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public void init(EndpointConfig config) {
        // no implementation needed
    }

    public void destroy() {
        // no implementation needed
    }
}
