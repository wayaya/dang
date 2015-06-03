<div class="left"><!--left开始-->
    <div class="l-content"><!--l-content"-->
    	<dl class="l-dl1">
    	
    		<div id="div_1" style="display:none">
    			<#if userSessionInfo?? && userSessionInfo.f['3']?? >
		        	<div class="dt-1" onclick="javascript:showActionLeft('ul11', this);">后台用户</div>
		    		<ul id="ul11" style="display:none">
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
	            </#if>
	            <#if userSessionInfo?? && userSessionInfo.f['73']?? >
		            <div class="dt-1" onclick="javascript:showActionLeft('ul12', this);">系统参数管理</div>
		    		<ul id="ul12" style="display:none">
		    			<#if userSessionInfo?? && userSessionInfo.f['74']?? >
			        	<li class="dd" onclick="changeLiCss(this)">
			        		<a href="javascript:changeIframe('/syspp/list.go')">系统参数列表</a>
			        	</li>
			        	</#if>
		            </ul>
	            </#if>
	            <#if userSessionInfo?? && userSessionInfo.f['129']?? >
		            <div class="dt-1" onclick="javascript:showActionLeft('ul13', this);">入库任务调度</div>
		    		<ul id="ul13" style="display:none">
		    			<#if userSessionInfo?? && userSessionInfo.f['134']?? >
			        	<li class="dd" onclick="changeLiCss(this)">
			        		<a href="javascript:changeIframe('/importTask/list.go')">入库任务</a>
			        	</li>
			        	</#if>
		            </ul>	
	            </#if>	       
	            <#if userSessionInfo?? && userSessionInfo.f['130']?? >
	            <div class="dt-1" onclick="javascript:showActionLeft('ul15', this);">分类管理</div>
		    		<ul id="ul15" style="display:none">
		    			<li class="dd" onclick="changeLiCss(this)">
		    				<#if userSessionInfo?? && userSessionInfo.f['150']?? >
			        			<a href="javascript:changeIframe('/catetory/main.go')">分类管理</a>
			        		</#if>
			        	</li>		  
		            </ul>
	            </#if>
	            <#if userSessionInfo?? && userSessionInfo.f['131']?? >
		            <div class="dt-1" onclick="javascript:showActionLeft('ul16', this);">CP拉取日志管理</div>
		            <ul id="ul16" style="display:none">
		            <li class="dd" onclick="changeLiCss(this)">
		                    <#if userSessionInfo?? && userSessionInfo.f['151']?? >
			        			<a href="javascript:changeIframe('/pullLog/list.go')">CP同步日志管理</a>
			        		</#if>
			        	</li>
			        	<li class="dd" onclick="changeLiCss(this)">
			        	    <#if userSessionInfo?? && userSessionInfo.f['152']?? >
			        		 	<a href="javascript:changeIframe('/pullMedia/list.go')">CP同步原创书籍管理</a>
			        		</#if>
			        	</li> 
			        </ul> 
		        </#if>
		        <#if userSessionInfo?? && userSessionInfo.f['132']?? >
			        <div class="dt-1" onclick="javascript:showActionLeft('ul17', this);">操作日志管理</div>
			        <ul id="ul17" style="display:none">
			    			<li class="dd" onclick="changeLiCss(this)">
			    				<#if userSessionInfo?? && userSessionInfo.f['153']?? >
				        			<a href="javascript:changeIframe('/managerOperateLog/list.go')">操作日志列表</a>
				        		</#if>	
				        	</li>
		            </ul>
	            </#if>	
	            <#if userSessionInfo?? && userSessionInfo.f['133']?? >
	            <div class="dt-1" onclick="javascript:showActionLeft('ul18', this);">资源管理</div>
			        <ul id="ul18" style="display:none">
			    			<li class="dd" onclick="changeLiCss(this)">
			    			<#if userSessionInfo?? && userSessionInfo.f['154']?? >
				        		<a href="javascript:changeIframe('/mediaResource/main.go?isCdn=1')">CDN资源列表</a>
				        	</#if>
				        	</li>
				        	<li class="dd" onclick="changeLiCss(this)">
				        	<#if userSessionInfo?? && userSessionInfo.f['155']?? >
				        		<a href="javascript:changeIframe('/mediaResource/main.go?isCdn=0')">服务器资源列表</a>
				        	</#if>
				        	</li>
		            </ul>
	            </#if>	
            </div>
			<#if userSessionInfo?? && userSessionInfo.f['77']?? >
            <div id="div_2" style="display:none">
        		<div class="dt-1" onclick="javascript:showActionLeft('ul21', this);">财务管理</div>
	    		<ul id="ul21" style="display:none">
					<#if userSessionInfo?? && userSessionInfo.f['78']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/orderBase/list.go')">订单列表</a>
		        	</li>
					</#if>
					<#if userSessionInfo?? && userSessionInfo.f['81']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/consumerDeposit/list.go')">充值列表</a>
		        	</li>
					</#if>
					<#if userSessionInfo?? && userSessionInfo.f['166']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/consumerDeposit/abnormalList.go')">异常充值列表</a>
		        	</li>
					</#if>
					<#if userSessionInfo?? && userSessionInfo.f['169']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/saleBase/sale/list.go')">消费列表</a>
		        	</li>
					</#if>
	            </ul>
            </div>
			</#if>
            <#if userSessionInfo?? && userSessionInfo.f['156']?? >
	            <div id="div_3" style="display:none">
		        	<div class="dt-1" onclick="javascript:showActionLeft('ul31', this);">消息推送</div>
		    		<ul id="ul31" style="display:none">	
		    			<li class="dd" onclick="changeLiCss(this)">
		    				<#if userSessionInfo?? && userSessionInfo.f['157']?? >
			        			<a href="javascript:changeIframe('/manualPush/list.go')">手动推送列表</a>
			        		</#if>
			        	</li>	 
			        	<li class="dd" onclick="changeLiCss(this)">
			        		<#if userSessionInfo?? && userSessionInfo.f['158']?? >
			        			<a href="javascript:changeIframe('/autoPush/list.go')">自动推送列表</a>
			        		</#if>
			        	</li>	   
		            </ul>	        
	        	</div>
        	</#if>
        	 <#if userSessionInfo?? && userSessionInfo.f['180']?? >
        	<div id="div_4" style="display:none">
				 <#if userSessionInfo?? && userSessionInfo.f['200']?? >
        		<div class="dt-1" onclick="javascript:showActionLeft('ul41', this);">作品管理</div>
	    		<ul id="ul41" style="display:none">
					 <#if userSessionInfo?? && userSessionInfo.f['201']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/media/main.go')">作品列表</a>
		        	</li>
					</#if>
	            </ul>
				</#if>
				 <#if userSessionInfo?? && userSessionInfo.f['207']?? >
	            <div class="dt-1" onclick="javascript:showActionLeft('ul42', this);">销售主体</div>
	    		<ul id="ul42" style="display:none">
					 <#if userSessionInfo?? && userSessionInfo.f['208']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/mediaSale/list.go')">销售主体管理</a>
		        	</li>
					 </#if>
	            </ul>
				 </#if>
				 <#if userSessionInfo?? && userSessionInfo.f['212']?? >
	            <div class="dt-1" onclick="javascript:showActionLeft('ul43', this);">作家管理</div>
	    		<ul id="ul43" style="display:none">
					 <#if userSessionInfo?? && userSessionInfo.f['213']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/author/list.go')">作家列表</a>
		        	</li>
					 </#if>
	            </ul>
	            </#if>
	            
	            <#if userSessionInfo?? && userSessionInfo.f['82']?? >
	            <div class="dt-1" onclick="javascript:showActionLeft('ul44', this);">敏感词管理</div>
	            
	    		<ul id="ul44" style="display:none">
	    		 <#if userSessionInfo?? && userSessionInfo.f['83']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/illword/list.go')">敏感词列表</a>
		        	</li>
		        </#if>
		        
		         <#if userSessionInfo?? && userSessionInfo.f['87']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/illegalmedia/list.go')">敏感作品列表</a>
		        	</li>
		        </#if>
	            </ul>
	            </#if>
	            
	            
	            <#if userSessionInfo?? && userSessionInfo.f['90']?? >
	            <div class="dt-1" onclick="javascript:showActionLeft('ul45', this);">榜单管理</div>
	    		<ul id="ul45" style="display:none">
	    		
	    			<#if userSessionInfo?? && userSessionInfo.f['91']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/ranking/list.go')">榜单基础信息（书籍）</a>
		        	</li>
		        	</#if>
		        	
		        	
		        	<#if userSessionInfo?? && userSessionInfo.f['92']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/ranking/category.go')">榜单分类信息查看（书籍）</a>
		        	</li>
		        	</#if>
		        	<#if userSessionInfo?? && userSessionInfo.f['93']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/rankConsToBook/list.go')" >榜单列表（壕赏）</a>
		        	</li>
		        	</#if>
		        	
		        	<#if userSessionInfo?? && userSessionInfo.f['94']?? >
		        	<li class="dd" onclick="changeLiCss(this)">	
		        		<a href="javascript:changeIframe('/rankConsToBook/addPre.go')">增加壕赏榜单记录</a>
		        	</li>
		        	</#if>
		        	<#if userSessionInfo?? && userSessionInfo.f['95']?? >
		        	<li class="dd" onclick="changeLiCss(this)">	
		        		<a href="javascript:changeIframe('/column/content/list.go')">分类销量榜单</a>
		        	</li>
		        	</#if>
	            </ul>
	            </#if>
	            
	            <#if userSessionInfo?? && userSessionInfo.f['96']?? >
	            <div class="dt-1" onclick="javascript:showActionLeft('ul46', this);">推荐管理</div>
	    		<ul id="ul46" style="display:none">
	    		 <#if userSessionInfo?? && userSessionInfo.f['97']?? >
	    			<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/block/special/main.go')">专题列表</a>
		        	</li>
		        </#if>
		         <#if userSessionInfo?? && userSessionInfo.f['98']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/block/main.go')">Banner列表</a>
		        	</li>
		        </#if>
		        <#if userSessionInfo?? && userSessionInfo.f['99']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/column/list.go')">推荐列表</a>
		        	</li>
		        </#if>
	            </ul>	   
	            </#if>
	            
	            <#if userSessionInfo?? && userSessionInfo.f['100']?? >        
	            <div class="dt-1" onclick="javascript:showActionLeft('ul48', this);">公告管理</div>
	    		<ul id="ul48" style="display:none">
	    		
	    		<#if userSessionInfo?? && userSessionInfo.f['101']?? > 
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/notice/list.go')">公告列表</a>
		        	</li>
		        </#if>
		        
		       	<#if userSessionInfo?? && userSessionInfo.f['102']?? > 
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/notice/add.go')">添加公告</a>
		        	</li>
		        </#if>
		       <#if userSessionInfo?? && userSessionInfo.f['103']?? > 
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/notice/type/list.go')">公告类型列表</a>
		        	</li>
		        </#if>
	            </ul>
	            <div class="dt-1" onclick="javascript:showActionLeft('ul49', this);">发现管理</div>
	    		<ul id="ul49" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/discovery/list.go?type=1')">发现列表</a>
		        	</li>		        	
	            </ul>
	            </#if>
	            <!--
	            <div class="dt-1" onclick="javascript:showActionLeft('ul401', this);">道具管理</div>
	    		<ul id="ul401" style="display:none">
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/saleBase/prop/list.go')">道具列表</a>
		        	</li>
	            </ul>
	            	        
	            <div class="dt-1" onclick="javascript:showActionLeft('ul402', this);">打赏管理</div>
	    		<ul id="ul402" style="display:none">
		        	
	            </ul>
	            -->
				 <#if userSessionInfo?? && userSessionInfo.f['222']?? >
	            <div class="dt-1" onclick="javascript:showActionLeft('ul403', this);">奖品管理</div>
	    		<ul id="ul403" style="display:none">
					 <#if userSessionInfo?? && userSessionInfo.f['223']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/prize/list.go')" >奖品列表</a>
		        	</li>
					</#if>
					 <#if userSessionInfo?? && userSessionInfo.f['225']?? >
		        	<li class="dd" onclick="changeLiCss(this)">	
		        		<a href="javascript:changeIframe('/prize/addPre.go')">奖品添加</a>
		        	</li>
					 </#if>
	            </ul>
				 </#if>
				 <#if userSessionInfo?? && userSessionInfo.f['227']?? >
	            <div class="dt-1" onclick="javascript:showActionLeft('ul404', this);">促销管理</div>
	    		<ul id="ul404" style="display:none">
					 <#if userSessionInfo?? && userSessionInfo.f['228']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/activity/type/list.go')">促销类型列表</a>
		        	</li>
					</#if>
					 <#if userSessionInfo?? && userSessionInfo.f['233']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/activity/type/add.go')">添加促销类型</a>
		        	</li>
					 </#if>
					 <#if userSessionInfo?? && userSessionInfo.f['234']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/activity/info/list.go')">促销活动列表</a>
		        	</li>
					 </#if>
					 <#if userSessionInfo?? && userSessionInfo.f['236']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/activity/info/add.go')">促销活动添加</a>
		        	</li>
					 </#if>
					 <#if userSessionInfo?? && userSessionInfo.f['238']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/activityUser/list.go')">活动用户列表</a>
		        	</li>
					 </#if>
					 <#if userSessionInfo?? && userSessionInfo.f['240']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/activityUser/record.go')">活动参与记录</a>
		        	</li>
					 </#if>
	            </ul>
	            </#if>
	            <#if userSessionInfo?? && userSessionInfo.f['104']?? > 
	            <div class="dt-1" onclick="javascript:showActionLeft('ul405', this);">用户反馈管理</div>
	    		<ul id="ul405" style="display:none">
	    		 <#if userSessionInfo?? && userSessionInfo.f['105']?? > 
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/suggestion/list.go')">反馈列表</a>
		        	</li>
		         </#if>
		         <#if userSessionInfo?? && userSessionInfo.f['106']?? > 
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/iwant/list.go')">我想要列表</a>
		        	</li>
		        </#if>
		        <#if userSessionInfo?? && userSessionInfo.f['107']?? > 
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/report/list.go')">举报列表</a>
		        	</li>
		        </#if>
		        <#if userSessionInfo?? && userSessionInfo.f['108']?? > 
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/form/test.go')">验证</a>
		        	</li>
		        </#if>
	            </ul>
	            </#if>
				 <#if userSessionInfo?? && userSessionInfo.f['242']?? >
	           <div class="dt-1" onclick="javascript:showActionLeft('ul406', this);">运营数据统计</div>
	    		<ul id="ul406" style="display:none">
					 <#if userSessionInfo?? && userSessionInfo.f['243']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/statistics/singleview.go')">单品浏览</a>
		        	</li>
					</#if>
					 <#if userSessionInfo?? && userSessionInfo.f['244']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/statistics/freeview.go')">免费试读</a>
		        	</li>
					 </#if>
					 <#if userSessionInfo?? && userSessionInfo.f['245']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/statistics/list.go')">收入查询</a>
		        	</li>
					 </#if>
					 <#if userSessionInfo?? && userSessionInfo.f['246']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/statistics/rewardTop.go')">打赏金额Top100</a>
		        	</li>
					 </#if>
					 <#if userSessionInfo?? && userSessionInfo.f['247']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/statistics/buyChapterGlobTop.go')">单章购买金铃铛Top100</a>
		        	</li>
					 </#if>
					 <#if userSessionInfo?? && userSessionInfo.f['248']?? >
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<a href="javascript:changeIframe('/statistics/buyChapterSilverTop.go')">单章购买银铃铛Top100</a>
		        	</li>
					 </#if>
	            </ul>
				 </#if>
	            <!--
	            <div class="dt-1" onclick="javascript:showActionLeft('ul406', this);">评论管理</div>
	    		<ul id="ul406" style="display:none">
		        	
	            </ul>
	            
	            <div class="dt-1" onclick="javascript:showActionLeft('ul407', this);">搜索热词管理</div>
	    		<ul id="ul407" style="display:none">
		        	
	            </ul>
	            -->
            </div>                   
     	    </#if>              
     		<#if userSessionInfo?? && userSessionInfo.f['159']?? >
     		<div id="div_5" style="display:none">
	        	<div class="dt-1" onclick="javascript:showActionLeft('ul51', this);">当心好文</div>
	    		<ul id="ul51" style="display:none">	
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<#if userSessionInfo?? && userSessionInfo.f['160']?? >
		        			<a href="javascript:changeIframe('/goodArticleSign/main.go')">标签管理</a>
		        		</#if>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<#if userSessionInfo?? && userSessionInfo.f['161']?? >
		        			<a href="javascript:changeIframe('/epubImport/list.go')">导入功能</a>
		        		</#if>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<#if userSessionInfo?? && userSessionInfo.f['162']?? >
		        			<a href="javascript:changeIframe('/epub/main.go')">作品管理功能</a>
		        		</#if>
		        	</li>
		        	<li class="dd" onclick="changeLiCss(this)">
		        		<#if userSessionInfo?? && userSessionInfo.f['163']?? >
		        			<a href="javascript:changeIframe('/digest/list.go')">文章管理</a>
		        		</#if>
		        	</li>	  	     
	            </ul>	        
        	</div>
        	</#if>
    </div>
</div>