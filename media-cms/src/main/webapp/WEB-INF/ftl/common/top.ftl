<div class="head" id="top_head">
	<p class="h-p1" style="font-size:18px;color:#fff">
		<#if userSessionInfo?? && userSessionInfo.userInfo?? && userSessionInfo.userInfo.name??>
			${userSessionInfo.userInfo.name}
		</#if> 
		&nbsp;您好！ 欢迎使用当当网数字阅读管理系统&nbsp;&nbsp;&nbsp;<a href="/login/logout.go" style="color: white;">注销</a> 
	</p>
	
	<ul class="nav clear">
		<li class="head_nav_li_1" onclick="changeHeadCss(this)"><a href="javascript:void(0)" onclick="showGroupActionLeft(1)">系统管理</a></li>
		<li class="head_nav_li_1" onclick="changeHeadCss(this)"><a href="javascript:void(0)" onclick="showGroupActionLeft(2)">财务数据</a></li>
		<li class="head_nav_li_1" onclick="changeHeadCss(this)"><a href="javascript:void(0)" onclick="showGroupActionLeft(3)">信息推送</a></li>
		<li class="head_nav_li_1" onclick="changeHeadCss(this)"><a href="javascript:void(0)" onclick="showGroupActionLeft(4)">当读小说</a></li>
		<li class="head_nav_li_1" onclick="changeHeadCss(this)"><a href="javascript:void(0)" onclick="showGroupActionLeft(5)">当心好文</a></li>
    </ul>
    <div style="text-align: center; position: absolute; top: 2px; left: 50%;">
		<img id="reduce_enlarge" src="/images/reduce.png" style="width:20px;height:20px;" onclick="reduceEnlarge()"/>
	</div>
</div>
<script type="text/javascript">
	var flag = 1;
	function reduceEnlarge(){
   		if(flag == 1){
   			flag = 2;
   			$("#top_head").attr({style:"height:25px;"});
   			$("#reduce_enlarge").attr({src:"/images/enlarge.png"});
   		}else if(flag == 2){
   			flag = 1;
   			$("#top_head").attr({style:"height:110px;"});
   			$("#reduce_enlarge").attr({src:"/images/reduce.png"});
   		}
   	}
</script>
