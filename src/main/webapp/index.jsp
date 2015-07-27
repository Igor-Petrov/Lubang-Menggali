<%@ page import="com.bol.test.lm.GameBoard" %>
<%@ page import="com.bol.test.web.Constants" %>
<html>
<head>
    <script type="text/javascript">
        var config = {
            TEXT_SERVER_DISCONNECTED:"<%=Constants.TEXT_SERVER_DISCONNECTED%>",
            TEXT_SERVER_ERROR:"<%=Constants.TEXT_SERVER_ERROR%>",
            MSG_TYPE_ERROR:"<%=Constants.MSG_TYPE_ERROR%>",
            MSG_TYPE_WAIT:"<%=Constants.MSG_TYPE_WAIT%>",
            MSG_TYPE_GAME:"<%=Constants.MSG_TYPE_GAME%>",
            MSG_TYPE_OPPONENT_DISCONNECTED:"<%=Constants.MSG_TYPE_OPPONENT_DISCONNECTED%>",
            MSG_TYPE_WIN:"<%=Constants.MSG_TYPE_WIN%>",
            MSG_TYPE_LOSE:"<%=Constants.MSG_TYPE_LOSE%>",
            MSG_TYPE_DRAW:"<%=Constants.MSG_TYPE_DRAW%>",
            MSG_TYPE_LOGIN:"<%=Constants.MSG_TYPE_LOGIN%>",
            MSG_TYPE_PIT_CLICK:"<%=Constants.MSG_TYPE_PIT_CLICK%>",
            WS_SERVER_PATH:"ws://<%=request.getServerName()%>:<%=request.getServerPort()%><%=application.getContextPath()%><%=Constants.WS_SERVER_NAME%>"
        };
    </script>
    <script type="text/javascript" src="js/jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="js/lm.js"></script>
    <link rel="stylesheet" type="text/css" href="css/lm.css"/>
    <title><%=Constants.APP_TITLE%>
    </title>
</head>
<body>
    <h2><%=Constants.APP_TITLE%></h2>

    <div id="loginContainer">
        <%=Constants.TEXT_LOGIN%>: <input type="text" id="login" maxlength="255"/>
        <input type="button" id="loginSubmit" value="<%=Constants.TEXT_BUTTON_LOGIN%>">
    </div>

    <div id="gameContainer" style="display:none;">
        <%=Constants.TEXT_OPPONENT_LOGIN%>: <span id="opponentLogin"></span><br/>
        <table id="gameTable">
            <tr>
                <td rowspan="2" id="opponentLm" class="opponentLm"></td>
                <% for (int i = GameBoard.PITS_COUNT - 1; i >= 0; i--) { %>
                <td id="opponentPit<%=i%>" class="opponentPit">
                    <input type="button" value="" disabled="disabled"/>
                </td>
                <% } %>
                <td rowspan="2" id="myLm" class="myLm"></td>
            </tr>
            <tr>
                <% for (int i = 0; i < GameBoard.PITS_COUNT; i++) { %>
                <td id="myPit<%=i%>" class="myPit">
                    <input type="button" value="" name="<%=i%>"/>
                </td>
                <% } %>
            </tr>
        </table>
        <%=Constants.TEXT_MY_LOGIN%>: <span id="myLogin"></span><br/>
        <input type="button" id="playAgain" value="<%=Constants.TEXT_PLAY_AGAIN%>" style="display: none">
    </div>

    <div id="waitContainer" style="display:none;">
        <%=Constants.TEXT_WAIT%>
    </div>
</body>
</html>
