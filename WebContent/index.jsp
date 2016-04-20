<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/search.css" rel="stylesheet" type="text/css">
<style>
		.logo{
			margin:0 auto;
			width:100px;
			height:100px;
			margin-top:200px;
		}
		.logo img{
			width:100px;
			height:100px;
		}
		.search{
			margin:0 auto;
			width:400px;
			border:1px solid #c6c6c6;
			margin-top:20px;
		}
		.text{
			width:300px;
			height:50px;
			border:0px;
			font-size:14px;
			text-indent:10px;
			outline:0;
		}
		.submit{
			width:97px;
			height:100%;
			border:0px;
			outline:0;
			background-color:#4A8BF5;
			color:#fff;
			line-height:50px;
			text-align:center;
			font-size:16px;
			float:right;
			cursor:pointer;
		}
	</style>
<title>Jimmy_Shi</title>
</head>
<body>
<div class="logo"><img src="images/logo.png"></div>
	<div class="search">
		<form action="/Searcher/result" method="post" >
			<input type="text"  name="s_form" placeholder="请输入你想搜索的内容" class="text">
			<input type="submit" value="搜索一下" class="submit">
		</form>
	</div>
</body>
</html>