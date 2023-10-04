<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.List"%>
<%@page import="model.ItemBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.*" session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");
HttpSession sessionCheck = request.getSession(false);
if (sessionCheck == null){
	response.sendRedirect("Login.jsp");
	return;
} %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/mouseHoverDropDownDesign.css">
<link rel="stylesheet" type="text/css" href="css/List.css">
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
	});
</script>

<script type="text/javascript">
function Detail(itemNo){
	if(window.confirm("この商品を閲覧しますか？")){
		var form = document.forms[0];
		var input = document.getElementById(itemNo)
		form.appendChild(input);
		document.body.appendChild(form);
		form.submit();
}else{
	return false;
}
	}
</script>

<%-- 更新リンクのfunction --%>
<script type="text/javascript">
function UpdateLink(itemNo){
	if(window.confirm("この商品を更新しますか？")){
		var form = document.forms[1];
		var input = document.getElementById(itemNo);
		form.appendChild(input);
		document.body.appendChild(form);
		form.submit();
	}else{
		return false;
	}
		}
</script>

<script type="text/javascript">
function DeleteLink(itemNo){
	if(window.confirm("この商品を削除しますか？")){
		var form = document.forms[2];
		var input = document.getElementById(itemNo);
		form.appendChild(input);
		document.body.appendChild(form);
		form.submit();
}else{
	return false;
}
	}
</script>

<script type="text/javascript">
function clearResult(){
		document.getElementById("itemName").value=''
		var radio = document.getElementById("radioButton");
		var radio2 = document.getElementById("radioButton2");

			radio.checked=false;
			radio2.checked=false;
}

</script>

<script type="text/javascript">
function writeCsv(){
	if(window.confirm("出力しますか？")){
		var form = document.forms[3];
		var input = document.getElementById("itemName");
		var input2 = document.getElementById("radioButton");
		var input3 = document.getElementById("radioButton2");

		form.appendChild(input);
		form.appendChild(input2);
		form.appendChild(input3);
		document.body.appendChild(form);
		form.submit();
	}else{
		return false;
	}
}

</script>

<script type="text/javascript">
function Logout(){
	if(window.confirm("ログアウトしますか？")){
		window.location.replace('Logout');
		return false;
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Anson_Store</title>

</head>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility %>
<body>

	<form method="post" action="ViewCon"></form>
	<form method="post" action="UpdateCon" enctype="multipart/form-data"></form>
	<form method="post" action="DeleteCon"></form>
	<form method="post" action="DynamicCsvServlet"></form>

	<h1>商品一覧画面（管理者）</h1>

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
		Integer updateResult = (Integer) request.getAttribute("updateResult");
		Integer deleteResult = (Integer) request.getAttribute("deleteResult");
		Integer csvResult = (Integer) request.getAttribute("csv");
	%>
	<div class="message">
	&nbsp
	<!-- このspaceキーは詳細/更新/削除の出力結果メッセージの位置を確保するために据えるものです %-->
		<%
			if (updateResult != null) {
				if (updateResult == 0) {
		%>
		<span class="fail">✖既に登録されている商品です✖&nbsp</span>
		<%
			} else if (updateResult == 1) {
		%>
		<span class="success">●更新が完了しました●&nbsp</span>
		<%
			} else {
		%>
		<span class="fail">✖更新済みエラーですです✖&nbsp</span>
		<%
			}
			}
		%>

		<%
			if (deleteResult != null) {
				if (deleteResult == 0) {
		%>
		<span class="fail">✖削除が失敗✖&nbsp</span>
		<%
			} else if (deleteResult == 1) {
		%>
		<span class="success">●削除が完了しました●&nbsp</span>
		<%
			} else {
		%>
		<span class="fail">✖削除エラーです✖&nbsp</span>
		<%
			}
			}
		%>

		<%if(csvResult != null){
			if(csvResult == 1){
		%>
			<span class="success">●出力が完了しました●&nbsp</span><%}} %>
	</div>

	<div class="href">
		<a href="http://localhost:8080/Anson_Store/Register.jsp">登録画面へ</a> <a
			href=""></a>
	</div>

	<div class="fixed">
		<table border=1>
			<caption>♦登録されている商品情報です♦</caption>
			<thead>
				<tr>

					<th>商品名</th>
					<th>値段</th>
					<th>在庫数</th>
					<th>項目選択</th>
				</tr>
			</thead>

			<tbody>

				<%
					@SuppressWarnings("unchecked")
					ArrayList<ItemBean> result = (ArrayList<ItemBean>) request.getAttribute("itemList");
					for (ItemBean item : result) {
				%>

				<tr>
					<td class="itemName"><%=item.getItemName()%></td>
					<td class="price"><%=item.getItemPrice()%>円</td>
					<td class="stock"><%=item.getStockCount()%>個</td>
					<td class="choice"><input type="hidden" name="itemNo"
						value="<%=item.getItemNo()%>" id="<%=item.getItemNo()%>">
						<a href="javascript:void(0)"
						onclick="Detail(<%=item.getItemNo()%>);">詳細</a>　<a
						href="javascript:void(0)"
						onclick="UpdateLink(<%=item.getItemNo()%>);">更新</a>　<a
						href="javascript:void(0)"
						onclick="DeleteLink(<%=item.getItemNo()%>);">削除</a></td>
				</tr>
				<%
					}
				%>
			</tbody>

		</table>
	</div>

	<br>
	<br>

	<form action="ListCon" method="post" id="formID" name="searchForm">
		<div class="serach">
			<%
				String name = (String) request.getAttribute("itemName");
				String specialItem = (String) request.getAttribute("specialItem");

				if (name == null) {
					name = "";
				}
			%>
			名前検索： <input type="text" name="itemName" size="30"
				class="validate[maxSize[100]]" value="<%=name%>" id="itemName">
			<%
				if (specialItem != null && specialItem.equals("0")) {
			%>
			<input type="radio" name="specialItem" value="0" id="radioButton" checked>目玉商品
			<%
				} else {
			%><input type="radio" name="specialItem" value="0" id="radioButton">目玉商品
			<%
				}
			%>

			<%
				if (specialItem != null && specialItem.equals("1")) {
			%>
			<input type="radio" name="specialItem" value="1" id="radioButton2" checked>一般商品
			<%
				} else {
			%>
			<input type="radio" name="specialItem" value="1" id="radioButton2">一般商品<%
				}
			%>


			<input type="button" value="リセット" onclick="clearResult()">
		</div>
		<br> <br>

		<div class="buttonall">
			<input type="submit" value="検　索" class="" id="buttonDesign">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			<input type="button" value="出　力" class="" id="buttonDesign" onclick="writeCsv();">
		</div>
		<br> <br>
	</form>
</body>
</html>