<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>数据统计</title>
	<#include "../common/common-css.ftl">
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3>数据统计&gt;&gt;收入</h3>
					<div style="padding:5px;background:none repeat scroll 0 0 #E4EAF6; margin-bottom:5px;border:1px solid #4F69A0">
		      		<form action="/statistics/list.go" method="post" id="query" name="query">
			      		 <table >
		        			<tr>
								<td>
								开始日期：<input type="text" id="startDate" name="startDate" class="Wdate" type="text" onFocus="WdatePicker()" value="<#if RequestParameters.startDate??>${RequestParameters["startDate"]}</#if>">
								&nbsp;&nbsp;结束日期：<input type="text" id="endDate" name="endDate" class="Wdate" type="text" onFocus="WdatePicker()" value="<#if RequestParameters.endDate??>${RequestParameters["endDate"]}</#if>">
								<button  type="submit" >查询</button>(两个日期不要为空)
								</td>
							</tr>
						</table>
				    </form>
				    </div>
				    <div>
				    <table class="table2">
				    	<thead>
		            	<tr>
		                    <td rowspan="2">送包月</td>
		                    <td rowspan="2" colspan="3">购买包月</td>
		                    <td rowspan="2" colspan="3">打赏</td>
		                    <td rowspan="2" colspan="3">充值</td>
		                    <td rowspan="2" colspan="4">单章购买</td>	                    
		                    <td colspan="3">购买福袋</td><td colspan="3">福袋抽奖</td>
		                    <td rowspan="2" colspan="3">膜拜</td>
		                    <td rowspan="2" colspan="3">领取掉钱带</td>
	               	    </tr>
	               	    </thead>
	               	    <tr>
		                    <td>人数</td>
		                    <td>人数</td>
		                    <td>次数</td>
		                    <td>金额(金铃铛)</td>
		                    <td>人数</td>
		                    <td>次数</td>
		                    <td>金额(金铃铛)</td>
		                    <td>人数</td>
		                    <td>次数</td>
		                    <td>金额(金铃铛)</td>
		                    <td>人数</td>
		                    <td>次数</td>
		                    <td>金铃铛总额</td>
		                    <td>银铃铛总额</td>
		                    <td>人数</td>
		                    <td>次数</td>
		                    <td>金额(金币)</td>
		                    <td>人数</td>
		                    <td>次数</td>
		                    <td>银铃铛</td>
		                    <td>人数</td>
		                    <td>次数</td>
		                    <td>银铃铛</td>
		                    <td>人数</td>
		                    <td>次数</td>
		                    <td>银铃铛</td>
	               	    </tr> 
	               	    <#if income??>
	               	    <tr>     	    
					    	<td><#if income.userCountSend??>${income.userCountSend}</#if></td>
					    	<td><#if income.userCountBuyMonthly??>${income.userCountBuyMonthly}</#if></td>
						    <td><#if income.timesBuyMonthly??>${income.timesBuyMonthly}</#if></td>
						    <td><#if income.totalBuyMonthly??>${income.totalBuyMonthly}</#if></td>
						    <td><#if income.userCountReward??>${income.userCountReward}</#if></td>
					    	<td><#if income.timesReward??>${income.timesReward}</#if></td>
						    <td><#if income.totalReward??>${income.totalReward}</#if></td>
						    <td><#if income.userCountDeposit??>${income.userCountDeposit}</#if></td>
						    <td><#if income.timesDeposit??>${income.timesDeposit}</#if></td>
					    	<td><#if income.totalDeposit??>${income.totalDeposit}</#if></td>
						    <td><#if income.userCountBuyChapter??>${income.userCountBuyChapter}</#if></td>
						    <td><#if income.timesBuyChapter??>${income.timesBuyChapter}</#if></td>
						    <td><#if income.totalGoldBuyChapter??>${income.totalGoldBuyChapter}</#if></td>
					    	<td><#if income.totalSilverBuyChapter??>${income.totalSilverBuyChapter}</#if></td>
						    <td><#if income.userCountBuyLuckyBag??>${income.userCountBuyLuckyBag}</#if></td>
						    <td><#if income.timesBuyLuckyBag??>${income.timesBuyLuckyBag}</#if></td>
						    <td><#if income.totalCoinBuyLuckyBag??>${income.totalCoinBuyLuckyBag}</#if></td>
						    <td><#if income.userCountLottery??>${income.userCountLottery}</#if></td>
						    <td><#if income.timesLottery??>${income.timesLottery}</#if></td>
					    	<td><#if income.totalSilverLottery??>${income.totalSilverLottery}</#if></td>
						    <td><#if income.userCountWorship??>${income.userCountWorship}</#if></td>
						    <td><#if income.timesWorship??>${income.timesWorship}</#if></td>
						    <td><#if income.totalSilverWorship??>${income.totalSilverWorship}</#if></td>
					    	<td><#if income.userCountSpreadCoins??>${income.userCountSpreadCoins}</#if></td>
						    <td><#if income.timesSpreadCoins??>${income.timesSpreadCoins}</#if></td>
						    <td><#if income.totalSilverSpreadCoins??>${income.totalSilverSpreadCoins}</#if></td>
						</tr>
						</#if>
		            </table>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
	<#include "../common/common-js.ftl">
  </body>
 	<script type="text/javascript">
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
	</script>

</html>
