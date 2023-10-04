<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@page import="model.ItemBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" session="false"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/View.css">
<link rel="stylesheet" type="text/css"
	href="css/validationEngine.jquery.css">


<script src="js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript"
	charset="UTF-8"></script>
<script src="js/languages/jquery.validationEngine-ja.js"
	type="text/javascript" charset="UTF-8"></script>

<!-- 一覧画面へのリンクを押したとき、sessionと一般ユーザー/管理者の状態で行く場所を決める
一般ユーザー向けの商品一覧画面はUserListCon、管理者のはListCon。変数roleには0が管理者、
1が一般ユーザーを指す -->
<script type="text/javascript">
function itemList(){
	<% HttpSession session = request.getSession(false);
		if(session == null){%>
		window.location = "UserListCon";
	<% }else{
			Integer role = (Integer) session.getAttribute("role");
			if(role == 1){%>
			window.location = "UserListCon";

			<%}else{%>
				window.location = "ListCon";
			<%}}%>
}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Anson_Store</title>
</head>
<body>

	<h1>商品閲覧画面</h1>

	<div class="href">
		<a href="javascript:void(0)" onclick="itemList();">一覧画面へ</a><a href=""></a>
	</div>

	<div class="fixed">
		<table border=1>
			<caption>♦商品の詳細情報です♦</caption>
			<%	ItemBean beforeItem =(ItemBean)request.getAttribute("itemDetail");	%>
			<thead>
				<tr>
					<th colspan="2"><img src=".\files\<%= beforeItem.getItemImage() %>"	alt="image"  width="400" height="260"></th>
				</tr>

			</thead>

			<tbody>

				<tr>
					<td>項目</td>
					<td>詳細内容</td>
				</tr>

				<tr>
					<td>商品NO</td>
					<td class="itemNo">
					<input type="text" size="25" name="itemNo" value="<%=beforeItem.getItemNo() %>" readonly></td>
				</tr>

				<tr>
					<td>商品名</td>
					<td class="itemName">
					<input type="text" size="25" name="itemName" value="<%=beforeItem.getItemName()%>" readonly></td>
				</tr>

				<tr>
					<td>値段</td>
					<td class="itemPrice">
					<input type="text" size="25" name="itemPrice" value="<%=beforeItem.getItemPrice()%>円/個" readonly></td>
				</tr>
				<tr>
					<td>在庫数</td>
					<td class="stockCount">
					<input type="text" size="25" name="stockCount" value="<%=beforeItem.getStockCount()%>個" readonly></td>
				</tr>
				<tr>
					<td>目玉商品</td>
					<%
						if (beforeItem.getSpecialItem() == 0) {
					%>
					<td><input type="radio" name="specialItem" value="0" checked>目玉商品&nbsp&nbsp&nbsp <input
						type="radio" name="specialItem" value="1" disabled>一般商品</td>
					<%
						} else {
					%>
					<td><input type="radio" name="specialItem" value="0"
						 disabled>目玉商品 &nbsp&nbsp&nbsp <input
						type="radio" name="specialItem" value="1" checked >一般商品</td>
					<% } %>
				</tr>

			</tbody>

		</table>
	</div>








</body>
</html>