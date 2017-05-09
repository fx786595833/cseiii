<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>教学支持系统</title>
<link href="css/main.css" type="text/css" rel="stylesheet">
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

#nav {
	width: 960px;
	margin: 0px auto 0px auto;
	background: #27A9E3;
	color: #ffffff;
}

#nav li {
	float: left;
	display: inline;
}

#nav li a {
	display: block;
	color: #ffffff;
	padding: 10px 15px;
	font-size: 16px;
	font-weight: bold;
	text-decoration: none;
}
</style>
</head>
<body>
	<%
		String user = (String) request.getAttribute("person");
		int num = Integer.parseInt(request.getAttribute("number") + "");
	%>
	<div class="message">教学支持系统</div>
	<div align=right style="font-size: 16px">
		欢迎你:<%=user%>; <a style="color: #ff0000; font-size: 16px" href="sign_in">退出!</a>
	</div>
	<div id="nav">
		<ul>
			<li><a href="main?id=<%=user%>">提交作业</a></li>
			<li><a href="person?id=<%=user%>">个人信息</a></li>
		</ul>
		<div style="clear: both"></div>
	</div>
	<div class="main_person">
		<form>
			<input name="username" type="text" value=${username
				}
				readonly="readonly">
			<hr class="hr15">
			<input name="password" type="text" value=${password
				}
				readonly="readonly">
			<hr class="hr15">
			<input name="name" type="text" value=${name } readonly="readonly">
			<hr class="hr15">
			<input name="studentid" type="text" value=${studentid
				}
				readonly="readonly">
			<hr class="hr15">
			<input name="group" type="text" value=${group } readonly="readonly">
			<hr class="hr15">
			<a style="color: #27A9E3; font-size: 16px">${gitlab}</a>


		</form>
	</div>
</body>
</html>