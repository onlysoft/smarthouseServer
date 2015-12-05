<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
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

<title>网关管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="网关管理">
<meta http-equiv="description" content="网关管理">
<link href="<c:url value="/css/default.css"/>" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/js/themes/default/easyui.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/js/themes/icon.css"/>" />
<script type="text/javascript"
	src="<c:url value="/js/jquery.min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/js/jquery.easyui.min.js"/>"></script>
<script type="text/javascript" src="js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/json.js"></script>
</head>

<body>
	<table id="dg" title="网关管理" class="easyui-datagrid"
		style="width:950px;height:500px"
		url="/SmartHouseWeb/servlet/gate?method=getAll" toolbar="#toolbar"
		pagination="true" rownumbers="true" fitColumns="true"
		singleSelect="true">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th field="id" width="33%" >网关编号</th>
				<th field="name" width="33%">网关名</th>
				<th field="status" width="33%">状态</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" onclick="newUser()">新建网关</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="editGate()">编辑网关</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" onclick="changeGate()">停用/启用</a>
	</div>
	<div id="dlg" class="easyui-dialog"
		style="width:400px;height:280px;padding:10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">网关信息</div>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>网关编号:</label> <input name="id" class="easyui-validatebox"
					required="true" required="true">
			</div>
			<div class="fitem">
				<label>网关名字:</label> <input name="name" class="easyui-validatebox"
					required="true">
			</div>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-ok"
			onclick="saveUser()">保存</a> <a href="javascript:void(0)"
			class="easyui-linkbutton" icon="icon-cancel"
			onclick="javascript:$('#dlg').dialog('close')">关闭</a>
	</div>

	<div id="dlg1" class="easyui-dialog"
		style="width:400px;height:280px;padding:10px 20px" closed="true"
		buttons="#dlg-buttons1">
		<div class="ftitle">网关信息</div>
		<form id="fm1" method="post" novalidate>
			<div class="fitem">
				<label>网关编号:</label> <input name="id" class="easyui-validatebox"
					required="true" readonly>
			</div>
			<div class="fitem">
				<label>网关名字:</label> <input name="name" class="easyui-validatebox"
					required="true">
			</div>
		</form>
	</div>

	<div id="dlg-buttons1">
		<a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-ok"
			onclick="updateGate()">更新</a> <a href="javascript:void(0)"
			class="easyui-linkbutton" icon="icon-cancel"
			onclick="javascript:$('#dlg1').dialog('close')">关闭</a>
	</div>
	<script type="text/javascript">
		var url;
		function newUser() {
			$('#dlg').dialog('open').dialog('setTitle', '新建网关');
			$('#fm').form('clear');
		}
		function editGate() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$('#dlg1').dialog('open').dialog('setTitle', '编辑网关');
				$('#fm1').form('load', row);
			}
		}
		function updateGate() {
			$('#fm1').form('submit', {
				url : "/SmartHouseWeb/servlet/gate?method=update",
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
						$('#dlg1').dialog('close'); // close the dialog
						$('#dg').datagrid('reload'); // reload the user data
					}
				}
			});
		}
		function saveUser() {
			$('#fm').form('submit', {
				url : "/SmartHouseWeb/servlet/gate?method=save",
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(result) {
					var result = eval('(' + result + ')'); 
					if (result.errorMsg!=null) {
						/* $.messager.alert({
							title : 'Error',
							msg : result.errorMsg
						});  */
						$.messager.alert('提示',result.errorMsg,'error');
					}  
					else {
						$('#dlg').dialog('close'); // close the dialog
						$('#dg').datagrid('reload'); // reload the user data
					}
				}
			});
		}
		function changeGate() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$.messager.confirm('Confirm', '您确定要改变网关使用状态么?', function(r) {
					if (r) {
						$.post("/SmartHouseWeb/servlet/gate", {
							id : row.id,
							method:'stop'
						}, function(result,status) {
							var result = eval('(' + result + ')');
							if (result.errorMsg) {
								$.messager.show({ // show error message
									title : 'Error',
									msg : result.errorMsg
								});
							} else {
								$('#dg').datagrid('reload'); // reload the user data
							}
						});
					}
				});
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
