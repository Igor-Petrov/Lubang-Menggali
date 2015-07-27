$(document).ready(function () {
    var webSocket = null;
    var myLogin;

    $('#loginSubmit').click(doLogin);
    $('#login').keypress(function (e) {
        if (e.which == 13) {
            doLogin();
        }
    });

    $('#playAgain').click(playAgain);
    $('.myPit :input').click(myPitClick);
    $('#login').focus();

    function onMessage(event) {
        var obj = JSON.parse(event.data);
        if (obj.type == config.MSG_TYPE_ERROR) {
            onError(obj);
        } else if (obj.type == config.MSG_TYPE_WAIT) {
            onWait(obj);
        } else if (obj.type == config.MSG_TYPE_GAME) {
            showGame(obj);
        } else if (obj.type == config.MSG_TYPE_OPPONENT_DISCONNECTED) {
            onOpponentDisconnected(obj);
        } else if (obj.type == config.MSG_TYPE_WIN) {
            onWin(obj);
        } else if (obj.type == config.MSG_TYPE_LOSE) {
            onLose(obj);
        } else if (obj.type == config.MSG_TYPE_DRAW) {
            onDraw(obj);
        }
    }

    function doLogin() {
        $('#loginContainer :input').prop('disabled', true);
        myLogin = $('#login').val();

        if (webSocket == null) {
            webSocket = new WebSocket(config.WS_SERVER_PATH);
            webSocket.onmessage = onMessage;
            webSocket.onclose = onServerDisconnected;
            webSocket.onerror = onWebsocketError;

            var send = function () {
                if (webSocket.readyState === 1) {
                    webSocket.send(JSON.stringify({type:config.MSG_TYPE_LOGIN, login: myLogin}));
                } else {
                    setTimeout(send, 1000);
                }
            };

            setTimeout(send, 1000);
        } else {
            webSocket.send(JSON.stringify({type:config.MSG_TYPE_LOGIN, login: myLogin}));
        }
    }

    function playAgain() {
        $('#playAgain').hide();
        webSocket.send(JSON.stringify({type:config.MSG_TYPE_LOGIN, login: myLogin}));
    }

    function onWait(obj) {
        $('#loginContainer').hide();
        $('#waitContainer').show();
        $('#gameContainer').hide();
    }

    function onError(obj) {
        showMessage(obj.data);
        $('#loginContainer :input').prop('disabled', false);
        $('#login').focus();
    }

    function onServerDisconnected() {
        showMessage(config.TEXT_SERVER_DISCONNECTED);
        $('#waitContainer').hide();
        $('#loginContainer').show();
        $('#login').focus();
        $('#loginContainer :input').prop('disabled', false);
        $('#gameContainer').hide();
        webSocket = null
    }

    function onWebsocketError() {
        showMessage(config.TEXT_SERVER_ERROR);
        $('#waitContainer').hide();
        $('#loginContainer').show();
        $('#login').focus();
        $('#loginContainer :input').prop('disabled', false);
        $('#gameContainer').hide();
        webSocket = null
    }

    function onOpponentDisconnected(obj) {
        showMessage(obj.data);
        webSocket.send(JSON.stringify({type:config.MSG_TYPE_LOGIN, login: myLogin}));
    }

    function showGame(obj) {
        $('#waitContainer').hide();
        $('#loginContainer').hide();
        $('#gameContainer').show();
        $('#opponentLogin').text(obj.opponentLogin);
        $('#myLogin').text(myLogin);


        $('#myLm').text(obj.myLm);
        for (i = 0; i < obj.myPits.length; i++) {
            var elem = $('#myPit' + i + ' :input');
            elem.val(obj.myPits[i]);
            elem.prop('disabled', !obj.isMyTurn || obj.myPits[i] == 0);
        }

        $('#opponentLm').text(obj.opponentLm);
        for (i = 0; i < obj.opponentPits.length; i++) {
            $('#opponentPit' + i + ' :input').val(obj.opponentPits[i]);
        }
    }

    function myPitClick(event) {
        if ($(this).prop('disabled') == false || $(this).prop('disabled') == undefined) {
            $('.myPit :input').prop('disabled', true);
            webSocket.send(JSON.stringify({type:config.MSG_TYPE_PIT_CLICK, pitNumber:event.target.name}));
        }
    }

    function onWin(obj) {
        showMessage(obj.data);
        $('#playAgain').show();
    }

    function onLose(obj) {
        showMessage(obj.data);
        $('#playAgain').show();
    }

    function onDraw(obj) {
        showMessage(obj.data);
        $('#playAgain').show();
    }

    function showMessage(msg) {
        alert(msg);
    }
});
