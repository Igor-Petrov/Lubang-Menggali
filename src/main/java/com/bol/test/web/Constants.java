package com.bol.test.web;

import com.bol.test.web.dto.Message;

import javax.json.Json;

/**
 * Created with IntelliJ IDEA.
 * User: IPetrov
 * Date: 8/19/14
 * Time: 10:25 AM
 */
public class Constants {
    public static final String APP_TITLE = "Lubang Menggali";
    public static final String TEXT_LOGIN = "Login";
    public static final String TEXT_BUTTON_LOGIN = "Go";
    public static final String TEXT_OPPONENT_LOGIN = "Opponent login";
    public static final String TEXT_MY_LOGIN = "My login";
    public static final String TEXT_PLAY_AGAIN = "Play again";
    public static final String TEXT_WAIT = "Please wait for an opponent";
    public static final String TEXT_SERVER_DISCONNECTED = "Server disconnected. Please try again later";
    public static final String TEXT_SERVER_ERROR = "Error occurred. Please try again later";
    public static final String TEXT_OPPONENT_DISCONNECTED = "Opponent disconnected";
    public static final String TEXT_LOGIN_INVALID = "Login length must be between 1 and 255";
    public static final String TEXT_DRAW = "Draw";
    public static final String TEXT_LOSE = "You lose";
    public static final String TEXT_WIN = "You win";

    public static final String MSG_TYPE_LOGIN = "login";
    public static final String MSG_TYPE_PIT_CLICK = "pitClick";
    public static final String MSG_TYPE_ERROR = "error";
    public static final String MSG_TYPE_WAIT = "wait";
    public static final String MSG_TYPE_GAME = "game";
    public static final String MSG_TYPE_OPPONENT_DISCONNECTED = "opponentDisconnected";
    public static final String MSG_TYPE_WIN = "win";
    public static final String MSG_TYPE_DRAW = "draw";
    public static final String MSG_TYPE_LOSE = "lose";

    public static final String MSG_PROP_TYPE = "type";
    public static final String MSG_PROP_PIT_NUMBER = "pitNumber";
    public static final String MSG_PROP_LOGIN = "login";
    public static final String MSG_PROP_DATA = "data";

    public static final String WS_SERVER_NAME = "/LmWebSocket";

    public static final Message MSG_DRAW = new Message(Json.createObjectBuilder()
            .add(MSG_PROP_TYPE, MSG_TYPE_DRAW)
            .add(MSG_PROP_DATA, Constants.TEXT_DRAW)
            .build());
    public static final Message MSG_LOSE = new Message(Json.createObjectBuilder()
            .add(MSG_PROP_TYPE, MSG_TYPE_DRAW)
            .add(MSG_PROP_DATA, Constants.TEXT_LOSE)
            .build());
    public static final Message MSG_WIN = new Message(Json.createObjectBuilder()
            .add(MSG_PROP_TYPE, MSG_TYPE_WIN)
            .add(MSG_PROP_DATA, Constants.TEXT_WIN)
            .build());
    public static final Message MSG_INVALID_LOGIN = new Message(Json.createObjectBuilder()
            .add(MSG_PROP_TYPE, MSG_TYPE_ERROR)
            .add(MSG_PROP_DATA, TEXT_LOGIN_INVALID)
            .build());
    public static final Message MSG_OPPONENT_DISCONNECTED = new Message(Json.createObjectBuilder()
            .add(MSG_PROP_TYPE, MSG_TYPE_OPPONENT_DISCONNECTED)
            .add(MSG_PROP_DATA, Constants.TEXT_OPPONENT_DISCONNECTED)
            .build());
    public static final Message MSG_WAIT = new Message(Json.createObjectBuilder()
            .add(MSG_PROP_TYPE, MSG_TYPE_WAIT)
            .build());

    private Constants() {

    }
}
