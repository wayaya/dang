<div class="left"><!--left开始-->
    <div class="l-content"><!--l-content"-->
    	<dl class="l-dl1">
    		<#if userSessionInfo?? && userSessionInfo.f['3']?? >
    		<div id="div_1" style="display:none">
	        	<div class="dt-1" onclick="javascript:showActionLeft('ul01', this);">管理员管理</div>
	    		<ul id="ul01" style="display:none">
	    			<#if userSessionInfo?? && userSessionInfo.f['4']?? >
		            <li class="dd" onclick="changeLiCss(this)">
		           		<a href="javascript:changeIframe('${rc.contextPath}/functionality/showTree.go')" >权限管理</a>
		            </li>
		            </#if>
		            
		            <#if userSessionInfo?? && userSessionInfo.f['10']?? >
		            <li class="dd" onclick="changeLiCss(this)">
		           		<a href="javascript:changeIframe('${rc.contextPath}/role/list.go')" >角色管理</a>
		            </li>
		            </#if>
		            <#if userSessionInfo?? && userSessionInfo.f['28']?? >
		            <li class="dd" onclick="changeLiCss(this)" >
		           		<a href="javascript:changeIframe('${rc.contextPath}/usercms/list.go')" >用户管理</a>
		            </li>
		            </#if>
	            </ul>
	            <div class="dt-1" onclick="javascript:showActionLeft('ul02', this);">系统参数管理</div>
	    		<ul id="ul02" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/syspp/list.go')">系统参数列表</a>
		        	</li>
	            </ul>
	            <div class="dt-1" onclick="javascript:showActionLeft('ul03', this);">入库任务调度</div>
	    		<ul id="ul03" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/importTask/list.go')">入库任务</a>
		        	</li>
	            </ul>
            </div>
            </#if>
            <div id="div_2" style="display:none">
	            <div class="dt-1" onclick="javascript:showActionLeft('ul11', this);">图书管理</div>
	            <ul id="ul11" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/catetory/main.go')">分类管理</a>
		        	</li>
		        	<!--
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/style/list.go')">样式管理</a>
		        	</li>
		        	-->
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/media/main.go')">图书管理</a>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/mediaSale/list.go')">销售主体管理</a>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/author/list.go')">作者管理</a>
		        	</li>
	        	</ul>
	            <!--
	            <div class="dt-1" onclick="javascript:showActionLeft('ul13', this);">标签管理</div>
	    		<ul id="ul13" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/label/list.go')">标签列表</a>
		        	</li>
	            </ul>
	            -->
	            <div class="dt-1" onclick="javascript:showActionLeft('ul18', this);">发现管理</div>
	    		<ul id="ul18" style="display:none">
		        	<li class="dd">
		        		<a href="javascript:changeIframe('/discovery/list.go?type=1')">发现列表</a>
		        	</li>
		        	<li class="dd">
		        		<a href="javascript:changeIframe('/discovery/list.go?type=2')">当心好文</a>
		        	</li>
		        	<li class="dd">
		        		<a href="javascript:changeIframe('/goodArticleSign/main.go')">当心好文标签管理</a>
		        	</li>
	            </ul>
            </div>
        	
            <div id="div_3" style="display:none">
	        	<div class="dt-1" onclick="javascript:showActionLeft('ul31', this);">促销管理</div>
	    		<ul id="ul31" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/activity/type/list.go')">促销类型列表</a>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/activity/type/add.go')">添加促销类型</a>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/activity/info/list.go')">促销活动列表</a>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/activity/info/add.go')">促销活动添加</a>
		        	</li>
	            </ul>
	            <div class="dt-1" onclick="javascript:showActionLeft('ul52', this);">公告管理</div>
	    		<ul id="ul52" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/announcements/list.go')">公告</a>
		        	</li>
	            </ul>
	            <div class="dt-1" onclick="javascript:showActionLeft('ul32', this);">榜单管理</div>
	    		<ul id="ul32" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/ranking/list.go')">榜单基础信息（书籍）</a>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/rankConsToBook/list.go')" >榜单列表（壕赏）</a>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">	
		        		<a href="javascript:changeIframe('/rankConsToBook/addPre.go')">壕赏榜单_增加</a>
		        	</li>
	            </ul>
	            <div class="dt-1" onclick="javascript:showActionLeft('ul33', this);">栏目管理</div>
	    		<ul id="ul33" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/column/list.go')">栏目列表</a>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/column/content/list.go')">栏目内容</a>
		        	</li>
	            </ul>
	            <div class="dt-1" onclick="javascript:showActionLeft('ul34', this);">推荐位管理</div>
	            <ul id="ul34" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/block/main.go')">推荐位分组列表</a>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/block/main.go')">推荐位列表</a>
		        	</li>
	            </ul>
	            <div class="dt-1" onclick="javascript:showActionLeft('ul35', this);">奖品管理</div>
	            <ul id="ul35" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/prize/list.go')" >奖品列表</a>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">	
		        		<a href="javascript:changeIframe('/prize/addPre.go')">奖品添加</a>
		        	</li>
		        <#--	
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/prize/lotDetail.go')">抽奖记录明细</a>
		        	</li>
		        -->	
	            </ul>
	            <div class="dt-1" onclick="javascript:showActionLeft('ul36', this);">用户管理</div>
	            <ul id="ul36" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/activityUser/list.go')">活动用户列表</a>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/activityUser/record.go')">活动参与记录</a>
		        	</li>
	            </ul>
	            <div class="dt-1" onclick="javascript:showActionLeft('ul37', this);">道具管理</div>
	    		<ul id="ul37" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/saleBase/prop/list.go')">道具管理</a>
		        	</li>
	            </ul>
	            <div class="dt-1" onclick="javascript:showActionLeft('ul38', this);">敏感词</div>
	    		<ul id="ul38" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/illword/list.go')">敏感词列表</a>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/illegalmedia/list.go')">敏感作品列表</a>
		        	</li>
	            </ul>
	            <div class="dt-1" onclick="javascript:showActionLeft('ul39', this);">意见管理</div>
	    		<ul id="ul39" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/suggestion/list.go')">意见列表</a>
		        	</li>
	            </ul>
        	</div>
        	
        	<div id="div_4" style="display:none">
        		<div class="dt-1" onclick="javascript:showActionLeft('ul41', this);">订单管理</div>
	    		<ul id="ul41" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/orderBase/list.go')">订单列表</a>
		        	</li>
	            </ul>
	            <div class="dt-1" onclick="javascript:showActionLeft('ul42', this);">其他消费管理</div>
	    		<ul id="ul42" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/saleBase/sale/list.go')">消费列表</a>
		        	</li>
	            </ul>
        		<!--
	            <div class="dt-1" onclick="javascript:showActionLeft('ul43', this);">CP管理</div>
	    		<ul id="ul43" style="display:none">
		        	<li class="dd">
		        		<a href="javascript:changeIframe('/cp/list.go')">CP列表</a>
		        	</li>
		        	<li class="dd">
		        		<a href="javascript:changeIframe('/cp/contract/list.go')">合同列表</a>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/pullLog/list.go')">CP同步日志管理</a>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/pullMedia/list.go')">CP同步原创书籍管理</a>
		        	</li>
	            </ul>
	            -->
	            <div class="dt-1" onclick="javascript:showActionLeft('ul44', this);">充值管理</div>
	    		<ul id="ul44" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/consumerDeposit/list.go')">充值记录列表</a>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/consumerDeposit/abnormalList.go')">异常充值记录列表</a>
		        	</li>
	            </ul>
            </div>
    </div>
</div>