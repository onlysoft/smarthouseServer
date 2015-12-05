<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>在线设备</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="在线设备">
<meta http-equiv="description" content="在线设备页">
<link href="css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="js/themes/icon.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/ChineseCalendar.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="http://www.jeasyui.com/easyui/datagrid-groupview.js"></script>
<script type="text/javascript">
	var ws = null;
	function startWebSocket() {
		if (ws == null || (ws != null && ws.readyState != 1)) {
			if ('WebSocket' in window)
				ws = new WebSocket(
						"ws://127.0.0.1:8080/SmartHouseWeb/mywebsocket/1.do");
			else if ('MozWebSocket' in window)
				ws = new MozWebSocket(
						"ws://127.0.0.1:8080/SmartHouseWeb/mywebsocket/1.do");
			else
				alert("您的浏览器不支持该操作");

			ws.onmessage = function(evt) {
				var OBJ = eval('(' + evt.data + ')');
				if (row.id != OBJ.deviceid) {
					if (OBJ.type == 1) {
					} else if (OBJ.type == 2) {
					var a = 'checkbox-off.png';
						$('#switch_img').attr("src", "images/" + a)
						alert("开灯成功");
					} else if (OBJ.type == 3) {
						alert("关灯成功");
					}

				}

			};

			ws.onclose = function(evt) {
				alert("与服务器失去连接!");
				var a = 'checkbox-off.png';
				$('#run_state').attr("src", "images/" + a)
			};

			ws.onopen = function(evt) {
				alert("控制连接连接成功!");
				var a = 'checkbox-on.png';
				$('#switch_img').attr("src", "images/" + a);
			};
		} else {
			alert("已申请控制成功，无需重复申请!");
		}
	}

	function openPwd() {
		$('#switch_event_window').window({
			title : '操作事件',
			width : 300,
			modal : true,
			shadow : true,
			closed : true,
			height : 250,
			resizable : false
		});
	}
	function close() {
		$('#switch_event_window').window('close');
	}
	var row;
	function op() {
		row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#switch_event_window').window('open');
		}
	}
	$(function() {
		openPwd();
	});
	$(document).ready(function() {
		startWebSocket();
	});
</script>
</head>

<body>
	<table id="dg" class="easyui-datagrid" title="在线设备列表"
		style="width:900px;height:500px"
		data-options="
                singleSelect:true,
                collapsible:true,
                rownumbers:true,
                fitColumns:true,
                pagination:true,
                toolbar:'#toolbar',
                url:'/SmartHouseWeb/servlet/device?method=online',
                view:groupview,
                groupField:'typeName',
                groupFormatter:function(value,rows){
                    return value + ' - ' + rows.length + '个';
                }">
		<thead>
			<tr>
				<th data-options="field:'id',width:120">设备编号</th>
				<th data-options="field:'name',width:100">设备名称</th>
				<th data-options="field:'gateId',width:120,align:'right'">网关编号</th>
				<th data-options="field:'typeId',width:120,align:'right'">设备类型编号</th>
				<th data-options="field:'typeName',width:100">设备类型</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar" style="padding:5px;border:1px solid #ddd">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-edit'" onclick="op()">操作事件</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit" onclick="editDevice()">编辑设备</a> <img
			href="javascript:void(0)" id='run_state'></a>
	</div>

	<!--switch 操作接口-->
	<div id="switch_event_window" class="easyui-window" title="操作事件"
		collapsible="false" minimizable="false" maximizable="false"
		icon="icon-save"
		style="width:300px; height: 250px; padding: 5px;
        background: #fafafa;">
		<img id="switch_img" alt="deng" src="images/checkbox-on.png">
		<div style="padding:5px;">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-add'" onclick="getLatestStatus()">获取最新状态</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-remove'" onclick="createCmd()">生成指令</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-add'" onclick="openSwitch()">开灯</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-remove'" onclick="closedSwitch()">关灯</a>
		</div>
	</div>

	<div id="edit_dlg" class="easyui-dialog"
		style="width:400px;height:280px;padding:10px 20px" closed="true"
		buttons="#edit_dlg-buttons">
		<div class="ftitle">设备信息</div>
		<form id="edit_fm" method="post" novalidate>
			<div class="fitem">
				<label>设备id:</label> <input name="id" class="easyui-validatebox"
					required="true" readonly>
			</div>
			<div class="fitem">
				<label>设备名字:</label> <input name="name" class="easyui-validatebox"
					required="true">
			</div>
		</form>
	</div>
	<div id="edit_dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-ok"
			onclick="updateDevice()">更新</a> <a href="javascript:void(0)"
			class="easyui-linkbutton" icon="icon-cancel"
			onclick="javascript:$('#edit_dlg').dialog('close')">关闭</a>
	</div>
	<script type="text/javascript">
		function getLatestStatus() {
			if (ws.readyState != 1) {
				alert("请先申请控制后再试");
				return;
			}
			var cmd1 = "{workFlowId:'12',gateId:'0',type:2,content:'cmd'}\r";
			ws.send(cmd1);
		}
		function closedConn() {
			if (ws.readyState != 3 && ws.readyState == 1) {
				ws.close();
			}
		}
		function createCmd() {
			document.getElementById('writeMsg').value = "{workFlowId:'12',gateId:'0',type:2,content:'cmd'}";
		}
		function openSwitch() {
			if (ws.readyState != 1) {
				alert("请先申请控制后再试");
				return;
			}
			var cmd = "{workFlowId:'12',gateId:'0',type:2,content:'cmd'}\r";
			ws.send(cmd);
		}
		function closedSwitch() {
			if (ws.readyState != 1) {
				alert("请先申请控制后再试");
				return;
			}
			var cmd = "{workFlowId:'12',gateId:'0',type:2,content:'cmd'}\r";
			ws.send(cmd);
		}
		function sendMsg(msg) {
			if (ws.readyState != 1) {
				alert("请先申请控制后再试");
				return;
			}
			ws.send(document.getElementById('writeMsg').value);
		}
		function sw_open() {
			var cmd = "{'workFlowId':'12','gateId':'0','type':2,'content':'open'}\r\n";
			sendMsg(cmd);
		}
		function sw_close() {
			var cmd = "{'workFlowId':'12','gateId':'0','type':3,'content':'close'}\r\n";
			sendMsg(cmd);
		}

		function updateDevice() {
			$('#edit_fm').form('submit', {
				url : "/SmartHouseWeb/servlet/device?method=update",
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(result) {
					var result = eval('(' + result + ')');
					if (result.errorMsg) {
						$.messager.show({
							title : 'Error',
							msg : result.errorMsg
						});
					} else {
						$('#edit_dlg').dialog('close'); // close the dialog
						$('#dg').datagrid('reload'); // reload the user data
					}
				}
			});
		}
		function editDevice() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$('#edit_dlg').dialog('open').dialog('setTitle', '编辑设备');
				$('#edit_fm').form('load', row);
			}
		}
	</script>
	<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}
</style>
</body>
</html>
