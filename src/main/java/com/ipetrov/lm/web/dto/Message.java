package com.ipetrov.lm.web.dto;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.StringWriter;

/**
 * Created with IntelliJ IDEA.
 * User: IPetrov
 * Date: 8/18/14
 * Time: 3:55 PM
 */
public class Message {
    private JsonObject json;

    public Message(JsonObject json) {
        this.json = json;
    }

    public JsonObject getJson() {
        return json;
    }

    public void setJson(JsonObject json) {
        this.json = json;
    }

    public String getStringValue(String name) {
        return json.get(name).toString().replaceAll("\"", "");
    }

    public int getIntValue(String name) {
        return Integer.parseInt(getStringValue(name));
    }

    @Override
    public String toString() {
        StringWriter writer = new StringWriter();
        Json.createWriter(writer).write(json);
        return writer.toString();
    }
}
