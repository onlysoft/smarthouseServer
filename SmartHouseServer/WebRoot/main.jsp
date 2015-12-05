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
<title>SmartHouse 管理后台</title>
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
			$.messager.confirm('系统提示', '您确定要注销吗?', function(r) {

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
		//刷新tabRefresh
		$('#mm-tabRefresh').click(function(){
			var currtab_title = $('#mm').data("currtab");
			var _ctab = $('#main').tabs('getTab', currtab_title);
			var html = _ctab.html();
			_ctab.html(html);
		})
		//关闭当前
		$('#mm-tabclose').click(function(){
			var currtab_title = $('#mm').data("currtab");
			$('#main').tabs('close',currtab_title);
		})
		
		//全部关闭
		$('#mm-tabcloseall').click(function(){
			$('.tabs-inner span').each(function(i,n){
				var t = $(n).text();
				$('#main').tabs('close',t);
			});	
		});
		//关闭除当前之外的TAB
		$('#mm-tabcloseother').click(function(){
			var currtab_title = $('#mm').data("currtab");
			$('.tabs-inner span').each(function(i,n){
				var t = $(n).text();
				if(t!=currtab_title)
					$('#main').tabs('close',t);
			});	
		});
		//关闭当前右侧的TAB
		$('#mm-tabcloseright').click(function(){
			var nextall = $('.tabs-selected').nextAll();
			if(nextall.length==0){
				//msgShow('系统提示','后边没有啦~~','error');
				alert('后边没有啦~~');
				return false;
			}
			nextall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				$('#main').tabs('close',t);
			});
			return false;
		});
		//关闭当前左侧的TAB
		$('#mm-tabcloseleft').click(function(){
			var prevall = $('.tabs-selected').prevAll();
			if(prevall.length==0){
				alert('到头了，前边没有啦~~');
				return false;
			}
			prevall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				$('#main').tabs('close',t);
			});
			return false;
		});

		//退出
		$("#mm-exit").click(function(){
			$('#mm').menu('hide');
		})
	}
	function freshAD() {
		var AdHtml = "<font color='red'>现在时间：" + new Date().format("hh:mm:ss")
				+ "&nbsp;&nbsp;今天：" + getChineseCalendar() + "</font>";
		$("#opt_info").panel({
			title : AdHtml
		});
		setTimeout("freshAD()", 500);
	}
	function goHome() {
		$("#main").tabs('select', "后台首页");
	}
	function wopen(url) {
		window
				.open(
						url,
						'newwindow',
						'height=800,width=1100,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no')
	}
	function bindfresh(title) {
		/*双击刷新TAB选项卡*/
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
			<img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
	
	<!-- 北部布局 -->
	<div region="north" split="true" border="false"
		style="overflow: hidden; height: 30px;
        background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
		<span style="float:right; padding-right:20px;" class="head">欢迎
			<%=session.getAttribute("name")%> 
			<a href="#" id="loginOut">注销</a> </span> <span
			style="padding-left:10px; font-size: 16px; "><img
			src="images/blocks.gif" width="20" height="20" align="absmiddle" />Smart
			House开启智能新生活</span>
	</div>

	<div region="west" split="false" icon="icon-light" title="后台系统菜单"
		style="width:208px;">
		<div class="easyui-accordion" border="false" ><%--
		<div title="后台首页" style="padding:10px" data-options="
                selected:true,
                tools:[{
                    iconCls:'icon-reload',
                    handler:function(){
                        $('#dg').datagrid('reload');
                    }
                }]">
         </div>
		--%><c:if test="${type}">
			<div title="系统数据" data-options="iconCls:'icon-ok'" 
				style="overflow:auto;padding:10px;">
				<ul class="easyui-tree">
					<li state="open"><span>基础数据</span>
						<ul>
							<li><span><a link="http://www.google.com.hk">设备类别录入</a>
							</span>
							</li>
							<li><span><a link="/SmartHouseWeb/managergateforadmin.jsp">网关录入</a>
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
			<div title="网关管理" style="padding:10px;" data-options="iconCls:'icon-ok'" selected="true">
				<ul class="easyui-tree">
				<li><span><a link="#">网关信息</a> </span>
				<ul>
					<li><span><a link="/SmartHouseWeb/managergate.jsp">我的网关</a> </span>
					</li>
					</ul>
				</li>
				</ul>
			</div>
			<div title="设备管理" data-options="iconCls:'icon-ok'" style="padding:10px;">
				<ul class="easyui-tree">
					<li><span><a link="#">我的设备</a> </span>
						<ul>
							<li><span><a link="/SmartHouseWeb/onlinedevice.jsp">在线设备</a> </span>
							</li>
							<li><span><a link="/SmartHouseWeb/offlinedevice.jsp">离线设备</a> </span>
							</li>
						</ul>
					</li>
					
				</ul>
			</div>
			<div title="系统管理" data-options="iconCls:'icon-ok'" style="padding:10px;">
				<ul class="easyui-tree">
					<li><span><a link="http://www.google.com.hk">系统设置</a> </span>
					</li>
				</ul>
			</div>
		</div>
	</div>

	<div id="opt_info" border="false" region="center" title="小广告">
		<div id="main" class="easyui-tabs" fit="true" border="false"
			plain="true">
			<div title="后台首页" style="padding:10px;">
				<h1>Smart House开启智能新生活</h1>
			</div>
		</div>
	</div>
	<div region="south" style="text-align:center">SmartHouse 开启智能新生活
		by onlysoft</div>
		
	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabRefresh">刷新</div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>
</body>
</html>
