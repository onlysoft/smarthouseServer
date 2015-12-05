<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模拟开灯演示页</title>
<script type="text/javascript">
	var ws = null;
	function startWebSocket() {
		if (ws == null||(ws!=null&&ws.readyState!=1)) {
			if ('WebSocket' in window)
				ws = new WebSocket("ws://127.0.0.1:8080/SmartHouseWeb/mywebsocket/1.do");
			else if ('MozWebSocket' in window)
				ws = new MozWebSocket("ws://127.0.0.1:8080/SmartHouseWeb/mywebsocket/1.do");
			else
				alert("您的浏览器不支持该操作");

			ws.onmessage = function(evt) {
				alert(evt.data);
			};

			ws.onclose = function(evt) {
				alert("控制连接关闭成功!");
			};

			ws.onopen = function(evt) {
				alert("申请控制成功!");
			};
		}else
		{
			alert("已申请控制成功，无需重复申请!");
		}

	}
	function closedConn() {
		if (ws.readyState != 3 && ws.readyState == 1) {
			ws.close();
		}
	}
	function sendMsg() {
		/* if(ws.readyState==3)
		{
			startWebSocket();
		}  */
		if (ws.readyState != 1) {
			alert("请先申请控制后再试");
			return;
		}
		ws.send(document.getElementById('writeMsg').value);
	}
	function createCmd()
	{
		document.getElementById('writeMsg').value="{workFlowId:'12',gateId:'0',type:2,content:'cmd'}";
	}
</script>
</head>
<body>
	<input type="text" id="writeMsg"></input>
	<input type="button" value="申请控制" onclick="startWebSocket()"></input>
	<input type="button" value="生成控制指令" onclick="createCmd()"></input>
	<input type="button" value="模拟开灯" onclick="sendMsg()"></input>
	<input type="button" value="断开连接" onclick="closedConn()"></input>
</body>
</html>