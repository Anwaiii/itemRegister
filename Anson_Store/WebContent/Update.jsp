<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.ItemBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" session="false"%>
<% request.setCharacterEncoding("UTF-8");
HttpSession sessionCheck = request.getSession(false);
if (sessionCheck == null){
	response.sendRedirect("Login.jsp");
	return;
} %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/Update.css">
<link rel="stylesheet" type="text/css"
	href="css/validationEngine.jquery.css">


<script src="js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript"
	charset="UTF-8"></script>
<script src="js/languages/jquery.validationEngine-ja.js"
	type="text/javascript" charset="UTF-8"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#updateFormID").validationEngine();
	});
</script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Anson_Store</title>
<script type="text/javascript">
	function noUpdate() {
		if (window.confirm('更新せずに一覧画面へ戻りますか？')) {
			window.location = "ListCon"
		} else {
			return false;
		}
	}
</script>
<script type="text/javascript">
	function confirmUpdate() {
		if (window.confirm('更新しますか？')) {
			var form = document.getElementById("updateFormID")
			form.submit();
		} else {
			return false;
		}
	}
</script>
</head>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility %>
<body>

	<script defer src="js/click.js" type="text/javascript" charset="UTF-8"></script>
	<h1>商品更新画面</h1>
	<div class="href">
		<a class="linkLeft" href="javascript:void(0)" onclick="noUpdate();">一覧画面へ</a>
		<a></a>
	</div>
	<form action="UpdateCon" method="post" id="updateFormID" enctype="multipart/form-data">
		<table border=1>
			<caption>♦更新する商品情報を入力してください♦</caption>

			<thead>
				<tr>
					<th>項目</th>
					<th>入力欄</th>
				</tr>
			</thead>
			<tbody>
				<%
					ItemBean itemBean = (ItemBean) request.getAttribute("beforeItemBean");
					Date date = new Date();
				%>

				<tr>
					<td>商品NO</td>
					<td><input name="itemNo" id="<%=itemBean.getItemNo()%>" value="<%=itemBean.getItemNo()%>"
						size="25" readonly></td>
				</tr>

				<tr>
					<td>商品名</td>
					<td><input type="text" name="itemName" size="25"
						placeholder="例:卵鮨" class="validate[required],[maxSize[100]]"
						value=<%=itemBean.getItemName()%>></td>
				</tr>
				<tr>
					<td>値段</td>
					<td><input type="text" name="itemPrice" size="20"
						style="text-align: right;" placeholder="例:250"
						class="validate[required],custom[integer],[min[0]],[max[100000]]"
						value="<%=itemBean.getItemPrice()%>">円/個</td>
				</tr>
				<tr>
					<td>在庫数</td>
					<td><input type="text" name="stockCount" size="20"
						style="text-align: right;" placeholder="例:10"
						class="validate[required],custom[integer],[min[0]],[max[1000]]"
						value="<%=itemBean.getStockCount()%>">個 　</td>
				</tr>
				<tr>
					<td>商品画像</td>
					<td><input type="file" name="itemImage" id="upload-photo">
						<input type="hidden" name="updateTime"
						value="<%=new SimpleDateFormat("MM-dd-yyyy HH:mm:ss:SSS").format(itemBean.getUpdateTime())%>">

					</td>
				</tr>
				<tr>
					<td>目玉商品</td>
					<%
						if (itemBean.getSpecialItem() == 0) {
					%>
					<td><input type="radio" name="specialItem" value="0" checked
						class="validate[required]">目玉商品&nbsp&nbsp&nbsp <input
						type="radio" name="specialItem" value="1"
						class="validate[required]">一般商品</td>
					<%
						} else {
					%>
					<td><input type="radio" name="specialItem" value="0"
						class="validate[required]">目玉商品 &nbsp&nbsp&nbsp <input
						type="radio" name="specialItem" value="1" checked
						class="validate[required]">一般商品</td>
					<%
						}
					%>
				</tr>
			</tbody>
		</table>

		<br> <br>
		<div class="buttonall">
			<input type="submit" value="更　新" class="button" id="update">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			<input type="reset" value="リセット" class="button"> <br> <br>
		</div>
</form>


</body>
</html>