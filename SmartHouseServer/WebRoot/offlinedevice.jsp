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

<title>离线设备</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="离线设备">
<meta http-equiv="description" content="离线设备">
<link rel="stylesheet" type="text/css" href="js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="js/themes/icon.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/ChineseCalendar.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/locale/easyui-lang-zh_CN.js"></script>
</head>

<body>
	<table id="dg" class="easyui-datagrid" title="离线设备列表" style="width:900px;height:500px" url="/SmartHouseWeb/servlet/device?method=offline" 
	pagination="true" rownumbers="true" fitColumns="true"
		singleSelect="true"
	>
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
</body>
</html>
