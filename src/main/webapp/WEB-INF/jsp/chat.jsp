<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>聊天室</title>
    <link rel="stylesheet" href="/css/index.css">
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="/js/index.js"></script>
    <script type="text/javascript">
        var userId = '${userId}';
        // 避免重复连接
        var lockReconnect = false;
        var wsUrl = "ws://127.0.0.1:9090?userId="+userId;
        //连接对象
        var websocket = null;
        window.onload=function (){
            changeSize();
            //建立socket链接
            createWebSocket(wsUrl);
        };

        //建立连接
        function createWebSocket(url) {
            try {
                websocket = new WebSocket(url);
                initEventHandle();
            } catch (e) {
                reconnect(url);
            }
        }

        function initEventHandle(){
            //建立连接成功的方法
            websocket.onopen = function () {
                setTip("提示：连接成功");
                // 心跳检测重置
                heartCheck.reset().start();
            };
            websocket.onclose = function () {
                setTip("提示：连接关闭");
                reconnect(wsUrl);
            };
            websocket.onerror = function () {
                setTip("提示：连接异常");
                reconnect(wsUrl);
            };
            websocket.onmessage = function (event) {
                setMessage(event.data);
                // 如果获取到消息，心跳检测重置
                // 拿到任何消息都说明当前连接是正常的
                heartCheck.reset().start();
            };
        }

        function reconnect(url) {
            if(lockReconnect){
                return;
            }
            lockReconnect = true;
            // 没连接上会一直重连，设置延迟避免请求过多
            setTimeout(function () {
                createWebSocket(url);
                lockReconnect = false;
            }, 5000);
        }

        //心跳检测
        var heartCheck = {
            timeout: 60000,//60秒
            timeoutObj: null,
            reset: function(){
                clearTimeout(this.timeoutObj);
                return this;
            },
            start: function(){
                this.timeoutObj = setTimeout(function(){
                    // 这里发送一个心跳，后端收到后，返回一个心跳消息，
                    // onmessage拿到返回的心跳就说明连接正常
                    websocket.send("ping");
                }, this.timeout)
            }
        }

        function setTip(msg){
            var chatArea = document.getElementById('chatArea');
            msg = '<span class="tip">'+msg+'</span>'
            chatArea.innerHTML += msg+"<br/>";
        }
        // 连接成功打印的消息
        function setMessage(msg){
            var chatArea = document.getElementById('chatArea');
            var msgObj = JSON.parse(msg);
            console.log(msgObj);
            if(msgObj.msgType == "0"){
                msg = '<div><span class="tip">'+msgObj.msg+'</span></div>';
                chatArea.innerHTML += msg+"<br/>";
            }else if(msgObj.msgType == "2"){
                $("#other").empty();
                //拼接在线人员
                var users = msgObj.users;
                for(var i = 0;i<users.length;i++){
                    var other = users[i].userName;
                    var li = $("<li onclick='chatToPeople(\""+other+"\")'></li>");
                    var img = $('<img src=""/>');
                    var span = $('<span>'+other+'</span>');
                    li.append(img).append(span);
                    $("#other").append(li);
                }
            }else if(msgObj.msgType == "-2"){
                if(msgObj.nickName == "dw"){
                    msg = '<div style="text-align: right;margin-top: 2%;"><span class="msg">'+msgObj.body+'</span>:'+msgObj.nickName+'</div>';
                }else{
                    msg = '<div style="text-align: left;margin-top: 2%;">'+msgObj.nickName+':<span class="msg">'+msgObj.body+'</span></div>';
                }
                chatArea.innerHTML += msg+"<br/>";
            }
        }
        //发送消息
        function send(){
            var msg = $("#text").val();
            var wsMessage = {msgType:2,nickName:name,body:msg};
            websocket.send(JSON.stringify(wsMessage));
        }
        //关闭连接
        function closeWebsocket() {
            websocket.close();
        }

        //监听窗口关闭事件，窗口关闭，连接关闭
        window.onbeforeunload = function () {
            websocket.close();
        }
    </script>
</head>
<body>
    <div id="chat">
        <div id="online">
            <ul style="list-style-type:none;">

            </ul>
            <ul style="list-style-type:none;" id="other">

            </ul>
        </div>
        <div id="message">
            <div id="toPeople"></div>
            <div id="chatArea"></div>
        </div>
        <div id="textDiv">
            <textarea id="text"></textarea>
        </div>
        <div id="buttenArea">
            <button onclick="closeWebsocket()">退出</button>
            <button onclick="clearWindow()">清屏</button>
            <button onclick="send()">发送</button>
        </div>
    </div>
</body>
</html>