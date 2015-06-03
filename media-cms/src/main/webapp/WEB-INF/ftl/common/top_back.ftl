<div class="head">
	<p class="h-p1" style="font-size:18px;color:#fff">
		<#if userSessionInfo?? && userSessionInfo.userInfo?? && userSessionInfo.userInfo.name??>
			${userSessionInfo.userInfo.name}
		</#if> 
		&nbsp;您好！ 欢迎使用当当网数字阅读管理系统&nbsp;&nbsp;&nbsp;<a href="/login/logout.go" style="color: white;">注销</a> 
	</p>
	
	<ul class="nav clear">
		<li class="head_nav_li_1" onclick="changeHeadCss(this)"><a href="javascript:void(0)" onclick="showGroupActionLeft(1)">系统管理</a></li>
		<li class="head_nav_li_1" onclick="changeHeadCss(this)"><a href="javascript:void(0)" onclick="showGroupActionLeft(2)">资源管理</a></li>
		<li class="head_nav_li_1" onclick="changeHeadCss(this)"><a href="javascript:void(0)" onclick="showGroupActionLeft(3)">运营管理</a></li>
		<li class="head_nav_li_1" onclick="changeHeadCss(this)"><a href="javascript:void(0)" onclick="showGroupActionLeft(4)">财务管理</a></li>
    </ul>
</div>