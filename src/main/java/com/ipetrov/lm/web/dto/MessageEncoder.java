package com.ipetrov.lm.web.dto;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Created with IntelliJ IDEA.
 * User: IPetrov
 * Date: 8/18/14
 * Time: 4:07 PM
 */
public class MessageEncoder implements Encoder.Text<Message> {
    public String encode(Message message) throws EncodeException {
        return message.getJson().toString();
    }

    public void init(EndpointConfig config) {
        // no implementation needed
    }

    public void destroy() {
        // no implementation needed
    }
}
