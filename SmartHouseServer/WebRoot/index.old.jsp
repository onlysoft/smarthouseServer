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

<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="ext/resources/css/ext-all.css">
<script type="text/javascript" src="ext/ext-all-dev.js"></script>
<script type="text/javascript">
		function fn(){
			//Ext.MessageBox.alert("hello","world");
			/* var myPanel=new Ext.Panel({
				renderTo:Ext.getBody(),
				title:'面板',
				width:400,
				height:300,
				html:'test面板'
			}) */
			
			/* tabPanel */
			/* var tabpanel=new Ext.TabPanel({
				renderTo:Ext.getBody(),
				width:300,
				height:200,
				activeTab:0,
				items:[new Ext.Panel({
						title:"1",
						height:30,
						html:'1'
				}),new Ext.Panel({
						title:"2",
						height:30,
						html:'2'
				}),new Ext.Panel({
				
						title:"3",
						height:30,
						html:'3'
				})]
			}); */
			/* 介绍面板 */
			/* new Ext.Panel({
				renderTo:Ext.getBody(),
				title:"面板的头部",
				width:300,
				height:200,
				html:'<h1>面板主区域</h1>',
				tbar:[{
					text:'顶部工具栏'
				}],
				bbar:[{
					text:'底部工具栏'	
				}],
				buttons:[{
					text:'按钮位于footer'
				}],
				items:[{
					xtype:'htmleditor'
				}]
			}); */
			/* 介绍布局 */
			/* 1、column布局 */
			/* new Ext.Panel({
				renderTo:Ext.getBody(),
				width:400,
				height:200,
				layout:"column",
				items:[{
					columnWidth:0.5,
					title:"1"
				},{
					columnWidth:0.5,
					title:"2"
				}]
			}); */
			/* var viewport=new Ext.Viewport({
				layout:'border',
				renderTo:Ext.getBody(),
				items:[
				{
					title:'north',
					region:'north',
					split:true,
					border:true,
					collasible:true,
					height:100,
					minSize:100,
					maxSize:120
				},
				{
					title:'south',
					region:'south',
					split:true,
					border:true,
					collapsible:true,
					height:100,
					minSize:100,
					maxSize:120
				},
				{
					title:'east',
					region:'east',
					split:true,
					border:true,
					collapsible:true,
					width:120,
					minSize:120,
					maxSize:120
				},
				{
					title:'west',
					region:'west',
					split:true,
					border:true,
					collapsible:true,
					width:120,
					minSize:120,
					maxSize:120
				},
				{
					title:'center',
					region:'center',
					split:true,
					border:true,
					collapsible:true
				}
				]
			}); */
			/* 表格布局 */
			//定义列
			/* var cm=new Ext.grid.ColumnModel([
				{header:"编号",dataIndex:"id"},
				{header:"名称",dataIndex:"name"},
				{header:"描述",dataIndex:"desc"}
			]);
			var data=[
				['1',"name1","desc"],
				['2',"name1","descw"],
				['12',"name1","descw"],
				['123',"name1","descw"]
			];
			var ds=new Ext.data.Store({
				proxy:new Ext.data.MemoryProxy(data),
				reader:new Ext.data.ArrayReader({},[
					{name:'id'},
					{name:'name'},
					{name:'desc'}
				
				])
			});
			ds.load();
			var grid=new Ext.grid.GridPanel({
				renderTo:Ext.getBody(),
				ds:ds,
				cm:cm,
				width:300,
				autoHeight:true
			}); */
			/* Ext.Msg.wait("1","2"); */
			//表单
			Ext.QuickTips.init(); 
			var genres = new Ext.data.SimpleStore({  fields: ['id', 'genre'], 
 data : [['1','Comedy'],['2','Drama'],['3','Action']] 
});
			var movie_form=new Ext.FormPanel({
				url:'',
				renderTo:document.body,
				frame:true,
				title:'movie Information Form',
				width:350,
				items:
				[
					{
						xtype:'textfield',
						fieldLabel:'Title',
						name:'title',
						allowBlank:false
					},
					{
						
						xtype:'textfield',
						fieldLabel:'Director',
						name:'director',
						vtype:'alpha'
					},
					{
						xtype:'datefield',
						fieldLabel:'Released',
						name:'released'
					},
					{
						xtype:'combo',
						name:'genre',
						fieldLabel:'Genre',
						mode:'local',
						store:genres,
						displayField:'genre',
						width:240,
						listeners:{
							select:function(f,r,i){
								if(i==0)
								{
									Ext.Msg.prompt('New Genre','genre',Ext.emptyFn);
								}
							}
						}
					}
				],
				buttons:[{
					text:'save',
					handler:function(){
					movie_form.getForm().submit({
						success:function(f,a){
							Ext.Msg.alert("success");
						},
						failure:function(f,a)
						{
							Ext.Msg.alert("failure:function",a);
						}
					});
					}
				}]
			});
		}
		Ext.onReady(fn);
	</script>
</head>

<body>
	This is my JSP page.
	<br>
</body>
</html>
