<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<head>
<meta charset="UTF-8" />
<title>SmartHouse登陆页</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="onlysoft SmartHouse登陆页 login smarthouse" />
<meta name="keywords" content="onlysoft SmartHouse登陆页 login smarthouse" />
<meta name="author" content="onlysoft" />
<link rel="shortcut icon" href="../favicon.ico">
<link rel="stylesheet" type="text/css" href="css/login.css" />
<link rel="stylesheet" type="text/css" href="css/login_style.css" />
<link rel="stylesheet" type="text/css" href="css/animate-custom.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
	var nameisOk = false;
	var pwdisOk = false;
	$(document).ready(function() {
		var name = $("#usernamesignup").val();
		verify();
	});
	function verify() {
		var name = $("#usernamesignup").val();
		$.get('/SmartHouseWeb/servlet/register?method=findName&username='
				+ name, null, callback);
	}
	function callback(data) {
		//		var resultObj=$("#usernameresult"); 
		/* $("#usernameresult").innerText=data; */
		var items = data.split(',')
		/* alert(data); */
		if (items[0] == 0) {
			alert(items[1]);
			nameisOk = false;
		} else {
			nameisOk = true;
		}
	}
	function veverifyPwd() {
		var passwordsignup_confirm = $("#passwordsignup_confirm").val();
		var passwordsignup = $("#passwordsignup").val();
		if (passwordsignup != passwordsignup_confirm) {
			alert('两次密码不一致，请检查后再试');
			pwdisOk = false;
		} else {
			pwdisOk = true;
		}
	}
	function verifyForm() {
		if (!nameisOk) {
			alert('用户名已存在,请修改后重试');
			return false;
		}
		if (!pwdisOk) {
			alert('两次密码不一致，请检查后再试');
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<div class="container">
		<section>
			<div id="container_demo">
				<a class="hiddenanchor" id="toregister"></a> <a class="hiddenanchor"
					id="tologin"></a>
				<div id="wrapper">
					<div id="login" class="animate form">
						<form action="/SmartHouseWeb/servlet/login" autocomplete="on">
							<h1>登陆</h1>
							<p>
								<label for="username" class="uname" data-icon="u"> 用户名 </label>
								<input id="username" name="username" required type="text"
									placeholder="请输入您的用户名或邮件" />
							</p>
							<p>
								<label for="password" class="youpasswd" data-icon="p">
									密码 </label> <input id="password" name="password" required
									type="password" placeholder="请输入您的密码" />
							</p>
							<p>
								<font color="red" style=""><b> ${error }</b>
								</font>
							</p>
							<p class="login button">
								<input type="submit" value="登陆" />
							</p>
							<p class="change_link">
								现在加入我们 ? <a href="#toregister" class="to_register">现在加入</a>
							</p>
						</form>
					</div>

					<div id="register" class="animate form">
						<form action="/SmartHouseWeb/servlet/register" autocomplete="on"
							method="post">
							<h1>注册</h1>
							<p>
							<div>
								<label for="usernamesignup" class="uname" data-icon="u">用户名</label>
								<label class="usernameresult"></label>
							</div>

							<input id="usernamesignup" name="usernamesignup" required
								type="text" placeholder="用户名" onblur="verify()" />
							</p>
							<p>
								<label for="emailsignup" class="youmail" data-icon="e">
									邮件</label> <input id="emailsignup" name="emailsignup" required
									type="email" placeholder="请输入正确的邮件地址" />
							</p>
							<p>
								<label for="passwordsignup" class="youpasswd" data-icon="p">密码
								</label> <input id="passwordsignup" name="passwordsignup" required
									type="password" placeholder="请输入密码" />
							</p>
							<p>
								<label for="passwordsignup_confirm" class="youpasswd"
									data-icon="p">请再次输入密码 </label> <input
									id="passwordsignup_confirm" name="passwordsignup_confirm"
									required type="password" placeholder="再次输入密码"
									onblur="veverifyPwd()" />
							</p>
							<p class="signin button">
								<input type="submit" value="注册" onclick="return verifyForm();" />
							</p>
							<p class="change_link">
								已有账号 ? <a href="#tologin" class="to_register"> 去登陆 </a>
							</p>
						</form>
					</div>

				</div>
			</div>
		</section>

	</div>

</body>