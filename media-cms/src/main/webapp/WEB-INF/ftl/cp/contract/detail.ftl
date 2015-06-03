<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增CP</title>
<#include "../../common/common-css.ftl">
</head>
<body>
<div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
<h3>CP管理&gt;&gt;CP修改</h3>
<form id="add" name="add" method="post" action="/cp/update.go">
<input type="hidden" id="cpId" name="cpId" value="${cp.cpId?c}">
	<table class="table2">
		<tr>
		<td>CP名称:</td><td><#if cp.cnName??>${cp.cnName}</#if></td>
		</tr>
		<tr>
		<td>CP类型:</td><td>
		<select id="type" name="type">
		<option value="0">作者个人</option>	
		<option value="1">内容方</option>	
		<option value="2">渠道</option>	
		</select>
		</td>
		</tr>
		<tr>
		<td>登录账户:</td><td><#if cp.loginCode??>${cp.loginCode}</#if></td>
		</tr>
		<tr>
		<td>登录口令:</td><td><#if cp.loginAccount??>${cp.loginAccount}</#if></td>
		</tr>
		<tr>
		<td>组织机构代码:</td><td><#if cp.organizationCode??>${cp.organizationCode}</#if></td>
		</tr>
		<tr>
		<td>是否是分公司:</td><td><input type="radio" id="hasBranch" name="hasBranch" checked="true" value="1">有&nbsp;<input type="radio" id="hasBranch" name="hasBranch"  value="0">无</td>
		</tr>
		<tr>
		<td>总公司名称:</td><td><#if cp.company??>${cp.company}</#if></td>
		</tr>
		<tr>
		<td>法人代表姓名:</td><td><#if cp.legalPerson??>${cp.legalPerson}</#if></span></td>
		</tr>
		<tr>
		<td> 法人证件类型:</td>
		<td>
		<select id="legalPersonCardType" name="legalPersonCardType">
			<option value="1">身份证</option>
			<option value="2">护照</option>
			<option value="3">其它</option>
		</select>
		</tr>
		<tr>
		<td>法人代表证件号:</td><td><#if cp.legalPersonCardNo??>${cp.legalPersonCardNo}</#if></td>
		</tr>
		<tr>
		<td>联系人:</td><td><#if cp.contractor??>${cp.contractor}</#if></td>
		</tr>
		<tr>
		<td>移动电话:</td><td><#if cp.mobilephone??>${cp.mobilephone}</#if></td>
		</tr>
		<tr>
		<td>固定电话:</td><td><#if cp.telphone??>${cp.telphone}</#if></td>
		</tr>
		<tr>
		<td>公司地址:</td><td><#if cp.address??>${cp.address}</#if></td>
		</tr>
		<tr>
		<td>等级:</td><td><select  id="level" name="level" >
			<option value="1">C级</option>
			<option value="2">B级</option>
			<option value="3">A级</option>
		</select></td>
		</tr>
		<tr>
		<td>注册资金币种:</td>
		<td>
		<select id="registeredMoneyType" name="registeredMoneyType">
			<option value="1">人民币</option>
			<option value="2">美元</option>
			<option value="3">港元</option>
			<option value="4">其它</option>
		</select>
		</td>
		</tr>
		<tr>
		<td>注册资金其它币种:</td><td><input type="text" id="registeredOtherType" name="registeredOtherType" ></td>
		</tr>
		<tr>
		<td>注册资金（万）:</td><td><#if cp.registeredMoney??>${cp.registeredMoney?c}</#if></td>
		</tr>
		<tr>
		<td>外资比例:</td><td><#if cp.foreignCapitalRatio??>${cp.foreignCapitalRatio}</#if></td>
		</tr>
		<tr>
		<td>公司性质:</td><td>
		<select id="companyType" name="companyType">
			<option value="1">国有控股</option>
			<option value="2">民营控股</option>
			<option value="3">外商投资</option>
			<option value="4">事业单位</option>
		</select>
		</td>
		</tr>
		<tr>
		<td>公司上市情况:</td><td>
		<select id="listedType" name="listedType">
			<option value="1">未上市</option>
			<option value="2">境内上市</option>
			<option value="3">香港上市</option>
			<option value="4">其它地方上市</option>
		</select>
		</td>
		</tr>
		
		<tr>
		<td>公司简介:</td><td>
		<textarea rows="3" id="companyIntroduction"　name="companyIntroduction" >
		<#if cp.companyIntroduction??>${cp.companyIntroduction}</#if>
	   </textarea> 
		</td>
		</tr>
	</table>
</form>
</div>
</div>
</div>
</div>
</body>
<script type="text/javascript">
function checkForm(){
	var labelName = $('#name').val();
		if(labelName == null || labelName == ""){
			$('#nameInfo').html('<img src="/images/wrong.jpg"/ style="width: 10px;height: 10 px">请填写标签名称');
			$('#nameInfo').focus();
			return false;
		}
}
</script>
</html>