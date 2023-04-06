<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- パッケージ名を指定してItemBeanをインポート -->
<%@ page import="drinkMachine.add.*"%>

<!-- ↓リストをインポートする文章 -->
<%@ page import="java.io.*,java.util.*,java.sql.*,javax.naming.*"%>

<!-- itembeanを受け取る -->
<%
	ItemBean itembean = (ItemBean) request.getAttribute("itembean");
%>

<!-- エラー表示のための変数設定 -->
<%
	String error1 = (String) request.getAttribute("errormessage1");
	String error2 = (String) request.getAttribute("errormessage2");
	String error3 = (String) request.getAttribute("errormessage3");
%>

<html xml:lang="ja" lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!--
    <link type="text/css" rel="stylesheet" href="exValidation/css/style.css" />
    -->
<link type="text/css" rel="stylesheet"
	href="exValidation/css/exvalidation.css" />
<title>商品変更</title>
</head>
<body>
	<h1>商品変更</h1>

	<%
		//if((itembean.getRecord()).equals("") || (itembean.getRecord()).equals(null)){
		//受け取ったafterDateの値がnullまたは空文字の場合
		if (error1 != null) {
			if (!error1.equals(null) || !error1.equals("")) {
	%>
	<br>
	<b><font color="#ff0000"> 削除済みエラー </font></b>
	<br>
	<br>
	<%
			}
		}

		if(error2 != null){
			if(!error2.equals(null) || !error2.equals("")) {
		//if(("RECORD_DATE").equals(itembean.getRecord())){
		//if(itembean.equals("0")){

	%>
	<br>
	<b><font color="#ff0000"> 更新失敗エラー
	</font></b>
	<br>
	<br>
	<%
			}
		}

		if(error3 != null){
			if(!error3.equals(null) || !error3.equals("")) {
		//if(itembean.equals("0")){

	%>
	<br>
	<b><font color="#ff0000"> 更新済みエラー
	</font></b>
	<br>
	<br>
	<%
			}
		}

	%>


	<a href="./list.jsp">一覧</a>
	<br>
	<br>

	<!-- 下のform内のactionの中身をUpdateControllerに書き換える前の内容 -->
	<!-- /edit/{{ person.key }} -->

	<form action="UpdateController" method="post">

		<table cellspacing="1" cellpadding="8" border="0" bgcolor="#999999">
			<tbody>
				<tr>
					<th width="100" bgcolor="#EBEBEB">商品コード</th>
					<td width="250" bgcolor="#FFFFFF"><input type="text" id="code"
						name="code" readonly="readonly" value="<%=itembean.getCode()%>">
					</td>
					</td>
				</tr>
				<tr>
					<th width="100" bgcolor="#EBEBEB">商品名<sup><font
							color="#FF0000">*</font></sup></th>

					<td width="250" bgcolor="#FFFFFF"><input type="text" id="name"
						name="name" value="<%=itembean.getName()%>"></td>
				</tr>
				<tr>
					<th width="100" bgcolor="#EBEBEB">金額<sup><font
							color="#FF0000">*</font></sup></th>
					<td width="250" bgcolor="#FFFFFF"><input type="text"
						id="unitPrice" name="unitPrice" value="<%=itembean.getPrice()%>">
					</td>
				</tr>
				<tr>
					<th width="100" bgcolor="#EBEBEB">数量<sup><font
							color="#FF0000">*</font></sup></th>

					<td width="250" bgcolor="#FFFFFF"><input type="text"
						id="count" name="count" value="<%=itembean.getCount()%>">
					</td>
				</tr>

				<tr>
					<th width="100" bgcolor="#EBEBEB">商品画像</th>
					<td width="250" bgcolor="#FFFFFF"><input type="file"
						id="image" name="image"></td>
				</tr>
				<tr>
					<th bgcolor="#EBEBEB">おすすめ商品</th>

					<td bgcolor="#FFFFFF"><input type="checkbox" id="isPR"
						name="isPR" value="True"{% ifitem.isPR %} checked{% endif %}>おすすめ商品棚に並べる</td>
				</tr>
			</tbody>
		</table>
		<br> <input type="submit"
			onclick="if(confirm('■商品情報を変更しますか？'))
    	//OKボタンを押したときは処理を続ける（Trueを返す）
    	{return true;
    	//OKでなければ・・・
    	}else{
    		//キャンセルボタンを押したときは処理を中断する（falseを返す）
    		return false;}"
			value="変更する">
	</form>

	<br>
	<font color="#FF0000">*</font>は必須項目

</body>
<!--<script type="text/javascript" src="jquery.js"></script>-->
<Script type="text/javascript" src="exValidation/js/jquery.min.js"></script>
<script type="text/javascript" src="exValidation/js/jquery.easing.jas"></script>
<script type="text/javascript" src="exValidation/js/jQselectable.js"></script>
<script type="text/javascript" src="exValidation/js/exvalidation.js"></script>
<script type="text/javascript" src="exValidation/js/exchecker-ja.js"></script>
<script type="text/javascript">
	var validation = $("form").exValidation({
		rules : {
			name : "chkrequired chkmax200 chknotnum",
			unitPrice : "chkrequired chknumonly chkmin1 chknum1000",
			count : "chkrequired chknumonly chkmin1 chknum100",
			filename : "chkfile"
		},
		stepValidation : true
	});
</script>

</html>