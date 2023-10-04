<%@page import="model.UreservationBean"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.List"%>
<%@page import="model.ItemBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" session="false"%>

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
function BuyLink(itemNo){

		var form = document.forms[1];
		var input = document.getElementById(itemNo);
		form.appendChild(input);
		document.body.appendChild(form);
		form.submit();

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
function Logout(){
	if(window.confirm("ログアウトしますか？")){
		window.location.replace("Logout");
		return true;
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Anson_Store</title>

</head>

<body>

	<form method="post" action="ViewCon"></form>
	<form method="get" action="BuyCon"></form>
	<h1>商品一覧画面</h1>


	<%
		HttpSession sessionCheck = request.getSession(false);
	%>
	<ul>
	<%
		if (sessionCheck != null) {
			System.out.println("sessionCheck");
		 	UreservationBean user= (UreservationBean)sessionCheck.getAttribute("user");
	%>
	<li><a>Hello,<%= user.getUserName() %>さん 残額:<%= user.getMoney() %>円</a>
		<ul class="dropdown">
		<li><a href="Charge.jsp">チャージ</a></li>
		<li><a href="PurchaseRecord.jsp">注文履歴</a></li>
		<li><a href="javascript:void(0)" onclick="Logout();">ログアウト</a></li>
		</ul>
		</li>

	</ul>
	<%} %>

	<%
	Integer message = (Integer) request.getAttribute("message");
	%>
	<div class="message">
		&nbsp
		<!-- このspaceキーは詳細/更新/削除の出力結果メッセージの位置を確保するために据えるものです %-->
		<%
			if (message != null) {
				if (message == 0) {
		%>
		<span class="success">●商品がカートに追加されました●&nbsp</span>
		<%}else if(message == 1){ %>
		<span class="success">●注文できました●&nbsp</span>

		<%}} %>

	</div>



	<div class="href">

		<%	if (sessionCheck == null) {%>
		<a href="http://localhost:8080/Anson_Store/Login.jsp">ログイン画面へ</a> <a></a>
		<%}else{ %>

		<a></a> <a href="CartCon">カートへ</a>
		<%} %>


	</div>

	<div class="fixed">
		<table border=1>
			<caption>♦登録されている商品情報です♦</caption>
			<thead>
				<tr>

					<th>商品名</th>
					<th>値段</th>
					<th style="width: 150px">在庫数</th>
					<th>項目選択</th>
				</tr>
			</thead>

			<tbody>

				<%
					System.out.println("UserList.jsp");
					@SuppressWarnings("unchecked")
					ArrayList<ItemBean> result = (ArrayList<ItemBean>) request.getAttribute("itemList");
				if(result!=null){
					for (ItemBean item : result) {
				%>

				<tr>
					<td class="itemName"><%=item.getItemName()%></td>
					<td class="price"><%=item.getItemPrice()%>円</td>
					<td class="stock" style="width: 150px"><%=item.getStockCount()%>個<% if(item.getStockCount() == 0) %>(売り切れ)</td>
					<td class="choice"><input type="hidden" name="itemNo"
						value="<%=item.getItemNo()%>" id="<%=item.getItemNo()%>">
						<a href="javascript:void(0)"
						onclick="Detail(<%=item.getItemNo()%>);">詳細</a> <% if(item.getStockCount() != 0) {%>
						<a href="javascript:void(0)"
						onclick="BuyLink(<%=item.getItemNo()%>);">購買</a></td>
				</tr>
				<%
						}}}
				%>
			</tbody>

		</table>
	</div>

	<br>
	<br>

	<form action="UserListCon" method="post" id="formID" name="searchForm">
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
			<input type="radio" name="specialItem" value="0" id="radioButton"
				checked>目玉商品
			<%
				} else {
			%><input type="radio" name="specialItem" value="0" id="radioButton">目玉商品
			<%
				}
			%>

			<%
				if (specialItem != null && specialItem.equals("1")) {
			%>
			<input type="radio" name="specialItem" value="1" id="radioButton2"
				checked>一般商品
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
			<input type="submit" value="検　索" class="" id="buttonDesign">
		</div>
		<br> <br>
	</form>
</body>
</html>