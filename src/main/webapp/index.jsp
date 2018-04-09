<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>SessionControlTest</title>
<script src="js/jquery-1.9.1.min.js"></script>
<style>
    *{
    padding:0;
    margin:0;
    font-family:"微软雅黑";
}
.header{
    height:72px;
    background:#458fce ;
}
.header .logo{
    color:#fff ;
    line-height:70px;
    font-size:30px;
    margin-left:20px;
    display:inline-block;
    text-align:center;

}
a {
    color: #fff ;
    text-decoration: none ;
}
.header .action{
    float:center;
    color:#fff ;
    line-height:72px;
    width:200px;
    margin-left:100px;
    display:inline-block;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	$(".action").click(function(){
		var actionType = $(this).html();
		actionType = actionType.toLocaleLowerCase();
		$.ajax({
			url:actionType+"Session.action",
			type:"post",
			dataType:"text",
			success:function(data){
				$("#info").html("success:"+data);
			},
			error:function(data){
				$("#info").html("error:"+data);
			}
		});
		
	});
});


</script>
</head>
<body>
    <div class="header">
        <div class="logo">LTE-B</div>
        <div class ="login">
            <button class="action" >Start</button>
            <span>|</span>
            <button class="action" >Stop</button>
        </div>
        <div id="info" ></div>
    </div>
</body>
</html>