<%@page import="controller.LoginCon"%>
<%@page import="org.apache.catalina.webresources.Cache"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" session="false"%>
<% request.setCharacterEncoding("UTF-8");
HttpSession sessionCheck = request.getSession(false);
if (sessionCheck == null || sessionCheck.getAttribute("userID") == null
		|| (Integer)sessionCheck.getAttribute("role") != 0) {
	response.sendRedirect("Login.jsp");
	return;
} %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/mouseHoverDropDownDesign.css">
<link rel="stylesheet" type="text/css" href="css/Style.css">
<link rel="stylesheet" type="text/css"
	href="css/validationEngine.jquery.css">


<script src="js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript"
	charset="UTF-8"></script>
<script src="js/languages/jquery.validationEngine-ja.js"
	type="text/javascript" charset="UTF-8"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#formID").validationEngine();
		jQuery("#csvformID").validationEngine();
	});
</script>

<script type="text/javascript">
function Logout(){
	if(window.confirm("ログアウトしますか？")){
		window.location.replace('Logout');
		return true;
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Anson_Store</title>
<meta http-equiv='cache-control' content='no-cache'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>
</head>
<%

if (sessionCheck.getAttribute("userID") == null) {
    response.sendRedirect("/Login.jsp");
}
%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility %>
<body>

	<script defer src="js/click.js" type="text/javascript" charset="UTF-8"></script>
	<h1>商品登録画面</h1>


		<%  sessionCheck = request.getSession(false); %>
	<ul>
	<%	if (sessionCheck != null) {
		System.out.println("sessionCheck");
	 %>
	<li><a>Hello,<%= sessionCheck.getAttribute("userID") %>さん</a>
		<ul class="dropdown">
		<li><a href="javascript:void(0)" onclick="Logout();">ログアウト</a></li>
		</ul>
		</li>
	</ul>
	<%} %>



	<%
		Integer result = (Integer) request.getAttribute("result");
		Integer successedResult = (Integer) request.getAttribute("succussedNumber");
	%>

	<div class="register">
	&nbsp
		<%
			if (result != null) {
				if (result == 0) {
		%>
		<span class="fail">✖登録が失敗しました✖&nbsp</span>

		<%
			} else if (result == -1) {
		%>
		<span class="fail">✖その拡張子はサポートされていません✖&nbsp</span>
		<%
			} else if (result == -2) {
		%>
		<span class="fail">✖登録に失敗しました（ファイルの拡張子が対応していません）✖&nbsp</span>
		<%
			} else if (result == -3) {
		%>
		<span class="fail">✖登録に失敗しました（データが不正です）✖&nbsp</span>
		<%
			} else if (result == -4) {
		%>
		<span class="fail">✖登録に失敗しました（商品名は100文字までです）✖&nbsp</span>
		<%
			} else if (result == -5) {
		%>
		<span class="fail">✖登録に失敗しました（商品価格は0-100000円までです）✖&nbsp</span>
		<%
			} else if (result == -6) {
		%>
		<span class="fail">✖登録に失敗しました（在庫数は0-1000までです）✖&nbsp</span>
		<%
			} else if (result == -7) {
		%>
		<span class="fail">✖登録に失敗しました（目玉商品の値が不正です）✖&nbsp</span>
		<%
			} else if (result == 1){
		%>
		<span class="success">●登録が完了しました●&nbsp</span>
		<%
			} else {

				Integer failedResult = (Integer) request.getAttribute("failedNumber");
				@SuppressWarnings("unchecked")
				ArrayList<String> registeredItem = (ArrayList<String>) request.getAttribute("successedItem");
				@SuppressWarnings("unchecked")
				ArrayList<String> nonRegisteredItem = (ArrayList<String>) request.getAttribute("failedItem");
		%>
		<% if(successedResult != 0){ %>
		<span class="success">●<%=successedResult%>件登録が完了しました●[
		<%for(int i = 0;i<registeredItem.size();i++){ %>
			<%= registeredItem.get(i) %>
			<% if(i != registeredItem.size()-1) %>,<%else{ %>]<br><%}}}%>
		</span>
		   <% if(failedResult != 0){ %>
		<span class="fail">✖<%=failedResult%>件登録が失敗しました✖[
		<%for(int i = 0;i<nonRegisteredItem.size();i++){ %>
			<%= nonRegisteredItem.get(i) %>
			<% if(i != nonRegisteredItem.size()-1) %>,<%else{ %>]<%}}} %>
		</span>
		<%
				}
			}
		%>
	</div>

	<div class="href">

		<a></a> <a class="linkRight" href="ListCon">一覧画面へ</a>

	</div>



	<form action="Register" method="post" id="formID"
		enctype="multipart/form-data">
		<table border=1>
			<caption>♦登録する商品を入力してください♦</caption>

			<tr>
				<th>項目</th>
				<th>入力欄</th>
			</tr>
			<tr>
				<td>商品NO</td>
				<td><input type="text" name="itemNO" size="25"
					placeholder="登録すると自動で採番されます"
					class="validate[required],custom[integer]" disabled></td>
			</tr>
			<tr>
				<td>商品名</td>
				<td><input type="text" name="itemName" size="25"
					placeholder="例:卵鮨" class="validate[required],[maxSize[100]]">
				</td>
			</tr>
			<tr>
				<td>値段</td>
				<td><input type="text" name="itemPrice" size="20"
					style="text-align: right;" placeholder="例:250"
					class="validate[required],custom[integer],[max[100000]]">円/個</td>
			</tr>
			<tr>
				<td>在庫数</td>
				<td><input type="text" name="stockCount" size="20"
					style="text-align: right;" placeholder="例:10"
					class="validate[required],custom[integer],[max[1000]]">個 　</td>
			</tr>
			<tr>
				<td>商品画像</td>
				<td><input type="file" name="itemImage" id="upload-photo"
					class="validate[required]"></td>
			</tr>
			<tr>
				<td>目玉商品</td>
				<td><input type="radio" name="specialItem" value="0"
					class="validate[required]" width="200">目玉商品 &nbsp&nbsp&nbsp
					<input type="radio" name="specialItem" value="1"
					class="validate[required]">一般商品</td>
			</tr>
		</table>

		<br> <br>
		<div class="buttonall">
			<input type="submit" value="登　録" class="button" id="register">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			<input type="reset" value="リセット" class="button"> <br> <br>
		</div>
	</form>





	<form action="CsvCon" method="post" id="csvformID"
		enctype="multipart/form-data">
		<div class="csvFile">
			CSV file選択：<input type="file" name="csvFile" id="csvFile" class="validate[required]">
			<input type="submit" value="一括登録" id="csvRegister">
		</div>

	</form>

</body>
</html>