<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="search.search.SearchDb" %>

<%!
	public String result;
	SearchDb searchDb = new SearchDb();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="jquery.min.js" type="text/javascript"></script>
	<style>
		.top{
			position:fixed;
			top:0;
			width:100%;
			height:60px;
			border-bottom: 1px solid #ebebeb;
			background-color:#fff;
			overflow:hidden;
		}
		.logo{
			float:left;
			width:50px;
			height:50px;
			margin-top:5px;
			margin-left:10px;
		}
		.logo img{
			width:50px;
			height:50px;
		}
		.search{
			float:left;
			width:600px;
			height:40px;
			border:1px solid #c6c6c6;
			margin-top:10px;
			margin-left:20px;
		}
		.text{
			width:500px;
			height:38px;
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
			line-height:40px;
			text-align:center;
			font-size:16px;
			float:right;
			cursor:pointer;
		}
		.container{
			margin-top:70px;
			margin-left:80px;
			width:600px;
		}
		.section{
			margin-top:20px;
		}
		.title{
			color:#0000CC;
			font-size:20px;
			line-height:40px;			
		}
		.a_links{
		text-decoration:none;
		}
		.details{
			color:#1A1A1A;
			line-height:20px;
			font-size:14px;
		}
		.links{
			color:#008000;
		}
		.paging{
			width:230px;
			margin:0 auto;
			margin-top:30px;
		}
		.before,.after,.one,.two,.three{
			float:left;
			border:1px solid #c6c6c6;
			margin-left:10px;
			padding:3px;
			cursor: pointer;
		}
		.one,.two,.three{
			width:15px;
			height:15px;
			border:0;
		}
		.active{
			color:#077bd9;
		}
	</style>
<title>Jimmy_Shi</title>
</head>
<body>
<div class="top">
	<div class="logo"><img src="images/logo.png"></div>
	<div class="search">
		<form action="/Searcher/web/result" method="post" >
			<input type="text"  name="s_form" placeholder="请输入你想搜索的内容" class="text">
			<input type="submit" value="搜索一下" class="submit">
		</form>
	</div>
</div>

<%
	String search =  new String(request.getParameter("s_form").getBytes("iso-8859-1"), "utf-8"); 
	String result = searchDb.search(search);
	
	out.println(result);
%>



<!-----------------active 类是当前页的类。------------------------>
<div class="paging">
	<div class="before">上一页</div>
	<div class="one active num">1</div>
	<div class="two num">2</div>
	<div class="three num">3</div>
	<div class="after">下一页</div>
</div>
</body>
<script>
$(function(){								
	$(".num").click(function(){     		//为类名为num的元素绑定点击事件
		$(".num").removeClass("active");	//类名为num的元素移除类名active
		$(this).addClass("active");			//为点击的元素加上active类名
	})
})
</script>
</html>