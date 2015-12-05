<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SmartHouse �����̨</title>
<link rel="stylesheet" type="text/css"
	href="js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="js/themes/icon.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/ChineseCalendar.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/locale/easyui-lang-zh_CN.js"></script>
<style type="text/css">
a {
	text-decoration: none;
	color: black;
}

body {
	background-color: #DDD;
}
</style>
<script>
	$(function() {
		$('#loginOut').click(function() {
			$.messager.confirm('ϵͳ��ʾ', '��ȷ��Ҫע����?', function(r) {

				if (r) {
					location.href = "/SmartHouseWeb/servlet/logout";
				}
			});

		});
		
		
		var _tabPanel = $("#main");
		freshAD();
		$(".easyui-accordion")
				.find("a[link]")
				.each(
						function() {
							$(this)
									.click(
											function() {
												var url = $(this).attr("link");
												var title = $(this).html();
												if (url !== "#" && url !== "") {
													if (!_tabPanel.tabs(
															'exists', title)) {
														_tabPanel
																.tabs(
																		'add',
																		{
																			title : title,
																			content : '<iframe src="'
																					+ url
																					+ '" style="padding:0;margin:0;border:0;width:100%;height:100%;"></iframe>',
																			closable : true
																		});
														bindfresh(title);
													} else {
														_tabPanel
																.tabs('select',
																		title);
													}
												}
											});
						});//end each
		window.document.addTab = function(url, title) {
			var _tabPanel = $("#main");
			if (!_tabPanel.tabs('exists', title)) {
				_tabPanel
						.tabs(
								'add',
								{
									title : title,
									content : '<iframe src="'
											+ url
											+ '" style="padding:0;margin:0;border:0;width:100%;height:100%;"></iframe>',
									closable : true
								});
				bindfresh(title);
			} else {
				_tabPanel.tabs('select', title);
			}
		}//end window.document.addTab
		tabCloseEven();
	});//end ready
	function tabCloseEven()
	{
		//ˢ��tabRefresh
		$('#mm-tabRefresh').click(function(){
			var currtab_title = $('#mm').data("currtab");
			var _ctab = $('#main').tabs('getTab', currtab_title);
			var html = _ctab.html();
			_ctab.html(html);
		})
		//�رյ�ǰ
		$('#mm-tabclose').click(function(){
			var currtab_title = $('#mm').data("currtab");
			$('#main').tabs('close',currtab_title);
		})
		
		//ȫ���ر�
		$('#mm-tabcloseall').click(function(){
			$('.tabs-inner span').each(function(i,n){
				var t = $(n).text();
				$('#main').tabs('close',t);
			});	
		});
		//�رճ���ǰ֮���TAB
		$('#mm-tabcloseother').click(function(){
			var currtab_title = $('#mm').data("currtab");
			$('.tabs-inner span').each(function(i,n){
				var t = $(n).text();
				if(t!=currtab_title)
					$('#main').tabs('close',t);
			});	
		});
		//�رյ�ǰ�Ҳ��TAB
		$('#mm-tabcloseright').click(function(){
			var nextall = $('.tabs-selected').nextAll();
			if(nextall.length==0){
				//msgShow('ϵͳ��ʾ','���û����~~','error');
				alert('���û����~~');
				return false;
			}
			nextall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				$('#main').tabs('close',t);
			});
			return false;
		});
		//�رյ�ǰ����TAB
		$('#mm-tabcloseleft').click(function(){
			var prevall = $('.tabs-selected').prevAll();
			if(prevall.length==0){
				alert('��ͷ�ˣ�ǰ��û����~~');
				return false;
			}
			prevall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				$('#main').tabs('close',t);
			});
			return false;
		});

		//�˳�
		$("#mm-exit").click(function(){
			$('#mm').menu('hide');
		})
	}
	function freshAD() {
		var AdHtml = "<font color='red'>����ʱ�䣺" + new Date().format("hh:mm:ss")
				+ "&nbsp;&nbsp;���죺" + getChineseCalendar() + "</font>";
		$("#opt_info").panel({
			title : AdHtml
		});
		setTimeout("freshAD()", 500);
	}
	function goHome() {
		$("#main").tabs('select', "��̨��ҳ");
	}
	function wopen(url) {
		window
				.open(
						url,
						'newwindow',
						'height=800,width=1100,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no')
	}
	function bindfresh(title) {
		/*˫��ˢ��TABѡ�*/
		$(".tabs-inner").dblclick(function() {
			var _ctab = $('#main').tabs('getTab', title);
			var html = _ctab.html();
			_ctab.html(html);
		});//end $(".tabs-inner").dblclick
	
		$(".tabs-inner").bind('contextmenu',function(e){
			var currtab_title = $('#mm').data("currtab");
			$('#mm').menu('show', {
				left: e.pageX,
				top: e.pageY,
			});
			
			var subtitle =$(this).children("span").text();
			$('#mm').data("currtab",subtitle);
			
			return false;
		});
		
	}
</script>
</head>
<body class="easyui-layout" style="text-align:left">
	<noscript>
		<div
			style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
			<img src="images/noscript.gif" alt='��Ǹ���뿪���ű�֧�֣�' />
		</div>
	</noscript>
	
	<!-- �������� -->
	<div region="north" split="true" border="false"
		style="overflow: hidden; height: 30px;
        background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, ΢���ź�,����">
		<span style="float:right; padding-right:20px;" class="head">��ӭ
			<%=session.getAttribute("name")%> 
			<a href="#" id="loginOut">ע��</a> </span> <span
			style="padding-left:10px; font-size: 16px; "><img
			src="images/blocks.gif" width="20" height="20" align="absmiddle" />Smart
			House��������������</span>
	</div>

	<div region="west" split="false" icon="icon-light" title="��̨ϵͳ�˵�"
		style="width:208px;">
		<div class="easyui-accordion" border="false" ><%--
		<div title="��̨��ҳ" style="padding:10px" data-options="
                selected:true,
                tools:[{
                    iconCls:'icon-reload',
                    handler:function(){
                        $('#dg').datagrid('reload');
                    }
                }]">
         </div>
		--%><c:if test="${type}">
			<div title="ϵͳ����" data-options="iconCls:'icon-ok'" 
				style="overflow:auto;padding:10px;">
				<ul class="easyui-tree">
					<li state="open"><span>��������</span>
						<ul>
							<li><span><a link="http://www.google.com.hk">�豸���¼��</a>
							</span>
							</li>
							<li><span><a link="/SmartHouseWeb/managergateforadmin.jsp">����¼��</a>
							</span>
							</li>
						</ul></li>
					<li state="closed"><span>xx</span>
						<ul>
							<li><span><a link="http://www.google.com.hk">xx</a> </span>
							</li>
							<li><span><a link="http://www.baidu.com">xx</a> </span>
							</li>
							<li><span><a link="http://www.google.com.hk">xx</a> </span>
							</li>
						</ul></li>
				</ul>
			</div>
			</c:if>
			<div title="���ع���" style="padding:10px;" data-options="iconCls:'icon-ok'" selected="true">
				<ul class="easyui-tree">
				<li><span><a link="#">������Ϣ</a> </span>
				<ul>
					<li><span><a link="/SmartHouseWeb/managergate.jsp">�ҵ�����</a> </span>
					</li>
					</ul>
				</li>
				</ul>
			</div>
			<div title="�豸����" data-options="iconCls:'icon-ok'" style="padding:10px;">
				<ul class="easyui-tree">
					<li><span><a link="#">�ҵ��豸</a> </span>
						<ul>
							<li><span><a link="/SmartHouseWeb/onlinedevice.jsp">�����豸</a> </span>
							</li>
							<li><span><a link="/SmartHouseWeb/offlinedevice.jsp">�����豸</a> </span>
							</li>
						</ul>
					</li>
					
				</ul>
			</div>
			<div title="ϵͳ����" data-options="iconCls:'icon-ok'" style="padding:10px;">
				<ul class="easyui-tree">
					<li><span><a link="http://www.google.com.hk">ϵͳ����</a> </span>
					</li>
				</ul>
			</div>
		</div>
	</div>

	<div id="opt_info" border="false" region="center" title="С���">
		<div id="main" class="easyui-tabs" fit="true" border="false"
			plain="true">
			<div title="��̨��ҳ" style="padding:10px;">
				<h1>Smart House��������������</h1>
			</div>
		</div>
	</div>
	<div region="south" style="text-align:center">SmartHouse ��������������
		by onlysoft</div>
		
	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabRefresh">ˢ��</div>
		<div id="mm-tabclose">�ر�</div>
		<div id="mm-tabcloseall">ȫ���ر�</div>
		<div id="mm-tabcloseother">����֮��ȫ���ر�</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">��ǰҳ�Ҳ�ȫ���ر�</div>
		<div id="mm-tabcloseleft">��ǰҳ���ȫ���ر�</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">�˳�</div>
	</div>
</body>
</html>
