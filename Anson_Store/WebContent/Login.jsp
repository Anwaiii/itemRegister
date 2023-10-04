<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" session="false"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/Login.css">
<link rel="stylesheet" type="text/css"
	href="css/validationEngine.jquery.css">
<script src="js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript"
	charset="UTF-8"></script>
<script src="js/languages/jquery.validationEngine-ja.js"
	type="text/javascript" charset="UTF-8"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#loginFormID").validationEngine();
	});
</script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Anson_Store</title>

</head>

<body>
	<%
		request.setCharacterEncoding("UTF-8");
		HttpSession sessionCheck = request.getSession(false);
		System.out.println(sessionCheck);
		if (sessionCheck != null && sessionCheck.getAttribute("userID") != null
				&& sessionCheck.getAttribute("role") != null) {
			if ((Integer) sessionCheck.getAttribute("role") == 1) {
				System.out.println("login.jsp:role1");
				response.sendRedirect("UserListCon");
				return;
			} else if ((Integer) sessionCheck.getAttribute("role") == 0) {
				System.out.println("login.jsp:role0");
				response.sendRedirect("Register.jsp");
				return;
			}
		}
	%>

	<script defer src="js/click.js" type="text/javascript" charset="UTF-8"></script>
	<h1>ユーザーログイン画面</h1>


	<div class="href">
		<a href=""></a> <a href="UserListCon">商品一覧へ</a>
	</div>

	<%
		Integer signUpResult = (Integer) request.getAttribute("signUpResult");
		Integer loginResult = (Integer) request.getAttribute("message");
	%>
	<div class="message">
		&nbsp
		<!-- このspaceキーは詳細/更新/削除の出力結果メッセージの位置を確保するために据えるものです %-->
		<%
			if (signUpResult != null) {
				if (signUpResult == 1) {
		%>
		<span class="success">●ユーザー登録が完了しました●&nbsp</span>
		<%
			}
			}
		%>

		<%
			if (loginResult != null) {
				if (loginResult <= 0) {
		%>
		<span class="fail"> ユーザID・パスワードが一致しません。&nbsp</span>
		<%
			}
			}
		%>



	</div>
	<br>
	<form action="LoginCon" method="post" id="loginFormID" autocomplete="off">
		<table border=1>
			<caption>♦このサービスをご利用になるにはログインしてください。♦</caption>

			<tr>
				<th colspan="2">会員ログイン</th>

			</tr>
			<tr>
				<td>ユーザID</td>
				<td><input type="text" name="userID" size="30"
					class="validate[required]"></td>
			</tr>
			<tr>
				<td>パスワード</td>
				<td><input type="password" name="password" size="30"
					class="validate[required]"></td>
			</tr>

		</table>
		<br>

		<div class="buttonall">
			<input type="submit" value="ログイン" class="button" id="login">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			<a href="SignUp.jsp"> 新規登録</a><br> <br>
		</div>
	</form>











</body>
</html>