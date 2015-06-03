<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="width:99%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>当当网--数字阅读</title>
	<#include "../../common/common-css.ftl">
	<script type="text/javascript">
		$(function(){
		    $('#startTimeString').calendar({maxDate:'#endTimeString',format:'yyyy-MM-dd HH:mm:ss'}); 
			$('#endTimeString').calendar({minDate:'#startTimeString',format:'yyyy-MM-dd HH:mm:ss'});
		});		
		
		function reset(){
			$('#account').val('');
			$('#name').val('');
		}
		
		function calculateDiscount(){
			var monthlyPaymentOriginalPrice = $('#monthlyPaymentOriginalPrice').val();
			var monthlyPaymentPrice = $('#monthlyPaymentPrice').val();
			var discount = 10;
			if(monthlyPaymentOriginalPrice != '' && monthlyPaymentPrice != '' && monthlyPaymentOriginalPrice != 0){
				discount = Math.ceil(monthlyPaymentPrice / monthlyPaymentOriginalPrice * 10);
			}
			$('#monthlyPaymentDiscount').val(discount);
		}
		
	   	function changeActiveType(id){
	   		var array = $("td[name='messageInfo']");
	   		for(var i = 0; i < array.length; i++){
	   			$("td[name='messageInfo']").html("");
	   		}
	   		var value = $('#'+id).val();
   			var activeTypeId = value.split("_")[0];
   			$("#first_deposit_give").removeClass("dispalynone");
   			$("#first_deposit_give").addClass("dispalynone");
   			$("#give_scale").removeClass("dispalynone");
   			$("#give_scale").addClass("dispalynone");
   			{
   				$("#isFirstDeposit").val("");
   				$("#giveScale").val("");
   			}
   			$("#previous_number_give").removeClass("dispalynone");
   			$("#previous_number_give").addClass("dispalynone");
   			{
   				$("#isPreviousNumber").val("");
   				$("#giveScale").val("");
   			}
   			$("#consume_give").removeClass("dispalynone");
   			$("#consume_give").addClass("dispalynone");
   			{
   				$("#consumeSatisfy").val("");
   				$("#giveGoldPiece").val("");
   			}
   			$("#level_give").removeClass("dispalynone");
   			$("#level_give").addClass("dispalynone");
   			$("#consume_level_give").removeClass("dispalynone");
   			$("#consume_level_give").addClass("dispalynone");
   			{
   				$("#originalLevel").val("");
   				$("#newLevel").val("");
   				$("#giveGoldPiece").val("");
   			}
   			$("#day_worship_give_1").removeClass("dispalynone");
   			$("#day_worship_give_1").addClass("dispalynone");
   			$("#day_worship_give_2").removeClass("dispalynone");
   			$("#day_worship_give_2").addClass("dispalynone");
   			{
   				$("#dayWorshipLimit").val("");
   				$("#lowestGoldPiece").val("");
   				$("#highestGoldPiece").val("");
   			}
   			$("#monthly_payment_1").removeClass("dispalynone");
   			$("#monthly_payment_1").addClass("dispalynone");
   			$("#monthly_payment_2").removeClass("dispalynone");
   			$("#monthly_payment_2").addClass("dispalynone");
   			$("#monthly_payment_3").removeClass("dispalynone");
   			$("#monthly_payment_3").addClass("dispalynone");
   			$("#monthly_payment_4").removeClass("dispalynone");
   			$("#monthly_payment_4").addClass("dispalynone");
   			$("#monthly_payment_5").removeClass("dispalynone");
   			$("#monthly_payment_5").addClass("dispalynone");
   			$("#deposit_1").addClass("dispalynone");
   			$("#deposit_2").addClass("dispalynone");
   			$("#deposit_3").addClass("dispalynone");
   			{
   				$("#isMonthlyPayment").val("");
   				$("#monthlyPaymentType").val("");
   				$("#monthlyPaymentPrice").val("");
   				$("#monthlyPaymentRelation").val("");
   				$("#monthlyBuyDays").val("");
   				$("#monthlyGiveDays").val("");
   				$("#monthlyBuyOrGive").val("");
   				$("#monthlyPaymentOriginalPrice").val("");
   				$("#monthlyPaymentDiscount").val("");
   			}
   			$("#fixed_price").removeClass("dispalynone");
   			$("#fixed_price").addClass("dispalynone");
   			{
   				$("#isFixedPrice").val("");
   				$("#fixedPrice").val("");
   			}
   			$("#whole_media").removeClass("dispalynone");
   			$("#whole_media").addClass("dispalynone");
   			$("#discount_info").removeClass("dispalynone");
   			$("#discount_info").addClass("dispalynone");
   			{
   				$("#isWholeMedia").val("");
   				$("#discount").val("");
   			}
   			$("#consume_chapter").removeClass("dispalynone");
   			$("#consume_chapter").addClass("dispalynone");
   			{
   				$("#consumeChapter").val("");
   				$("#discount").val("");
   			}
   			$("#prize").removeClass("dispalynone");
   			$("#prize").addClass("dispalynone");
   			{
   				$("#prizePrice").val("");
   				$("#prizeQuantity").val("");
   			}
   			if(activeTypeId == '1000'){
   				$("#first_deposit_give").removeClass("dispalynone");
   				$("#give_scale").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1001'){
   				$("#previous_number_give").removeClass("dispalynone");
   				$("#give_scale").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1002'){
   				$("#consume_give").removeClass("dispalynone");
   				$("#consume_level_give").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1003'){
   				$("#level_give").removeClass("dispalynone");
   				$("#consume_level_give").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1004'){
   				$("#day_worship_give_1").removeClass("dispalynone");
   				$("#day_worship_give_2").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1005'){
   				$("#monthly_payment_1").removeClass("dispalynone");
   				$("#monthly_payment_2").removeClass("dispalynone");
   				$("#monthly_payment_3").removeClass("dispalynone");
   				$("#monthly_payment_4").removeClass("dispalynone");
   				$("#monthly_payment_5").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1006' || activeTypeId == '1013'){
   				$("#fixed_price").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1007'){
   				$("#whole_media").removeClass("dispalynone");
   				$("#discount_info").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1008'){
   				$("#consume_chapter").removeClass("dispalynone");
   				$("#discount_info").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1009'){
   				$("#prize").removeClass("dispalynone");
	   		}
	   		if(activeTypeId == '1010' || activeTypeId == '1011' || activeTypeId == '1012' || activeTypeId == '1014' || activeTypeId == '1015' || activeTypeId == '1016' || activeTypeId == '1017' || activeTypeId == '1018'){
   				$("#deposit_1").removeClass("dispalynone");
   				$("#deposit_2").removeClass("dispalynone");
   				$("#deposit_3").removeClass("dispalynone");
   			}
	   	}
	   	
	   	function activityInfoAddForm(){
	   		var reg1 =  /^\d+$/;
	   		var flag = true;
	   		var activityTypeCodeInfo = $('#activityTypeCode').val();
	   		if(activityTypeCodeInfo == null || activityTypeCodeInfo == ""){
	   			$('#activityTypeCodeInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
	   			flag = false;
	   		}else{
	   			$('#activityTypeCodeInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
	   			var activeTypeId = activityTypeCodeInfo.split("_")[0];
		   		var activityNameInfo = $('#activityName').val();
		   		if(activityNameInfo == null || activityNameInfo == ""){
		   			$('#activityNameInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
		   			flag = false;
		   		}else{
		   			$('#activityNameInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
		   		}
		   		
		   		var startTimeStringInfo = $('#startTimeString').val();
		   		if(startTimeStringInfo == null || startTimeStringInfo == ""){
		   			$('#startTimeStringInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
		   			flag = false;
		   		}else{
		   			$('#startTimeStringInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
		   		}
				var endTimeStringInfo = $('#endTimeString').val();
		   		if(endTimeStringInfo == null || endTimeStringInfo == ""){
		   			$('#endTimeStringInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
		   			flag = false;
		   		}else{
		   			$('#endTimeStringInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
		   		}
		   		var statusInfo = $('#status').val();
		   		if(statusInfo == null || statusInfo == ""){
		   			$('#statusInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
		   			flag = false;
		   		}else{
		   			$('#statusInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
		   		}
		   		
		   		if(activeTypeId == '1000'){
	   				var isFirstDepositInfo = $('#isFirstDeposit').val();
			   		if(isFirstDepositInfo == null || isFirstDepositInfo == ""){
			   			$('#isFirstDepositInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#isFirstDepositInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var giveScaleInfo = $('#giveScale').val();
			   		if(giveScaleInfo == null || giveScaleInfo == "" || !reg1.test(value)){
			   			$('#giveScaleInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#giveScaleInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
	   			}
	   			if(activeTypeId == '1001'){
	   				var isPreviousNumberInfo = $('#isPreviousNumber').val();
			   		if(isPreviousNumberInfo == null || isPreviousNumberInfo == ""){
			   			$('#isPreviousNumberInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#isPreviousNumberInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var giveScaleInfo = $('#giveScale').val();
			   		if(giveScaleInfo == null || giveScaleInfo == "" || !reg1.test(giveScaleInfo)){
			   			$('#giveScaleInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#giveScaleInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
	   			}
	   			if(activeTypeId == '1002'){
	   				var consumeSatisfyInfo = $('#consumeSatisfy').val();
			   		if(consumeSatisfyInfo == null || consumeSatisfyInfo == ""){
			   			$('#consumeSatisfyInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#consumeSatisfyInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var giveGoldPieceInfo = $('#giveGoldPiece').val();
			   		if(giveGoldPieceInfo == null || giveGoldPieceInfo == "" || !reg1.test(giveGoldPieceInfo)){
			   			$('#giveGoldPieceInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#giveGoldPieceInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
	   			}
	   			if(activeTypeId == '1003'){
	   				var originalLevelInfo = $('#originalLevel').val();
			   		if(originalLevelInfo == null || originalLevelInfo == ""){
			   			$('#originalLevelInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#originalLevelInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var newLevelInfo = $('#newLevel').val();
			   		if(newLevelInfo == null || newLevelInfo == ""){
			   			$('#newLevelInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#newLevelInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var giveGoldPieceInfo = $('#giveGoldPiece').val();
			   		if(giveGoldPieceInfo == null || giveGoldPieceInfo == "" || !reg1.test(giveGoldPieceInfo)){
			   			$('#newLevelInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#giveGoldPieceInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
	   			}
	   			if(activeTypeId == '1004'){
	   				var dayWorshipLimitInfo = $('#dayWorshipLimit').val();
			   		if(dayWorshipLimitInfo == null || dayWorshipLimitInfo == ""){
			   			$('#dayWorshipLimitInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#dayWorshipLimitInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var lowestGoldPieceInfo = $('#lowestGoldPiece').val();
			   		if(lowestGoldPieceInfo == null || lowestGoldPieceInfo == "" || !reg1.test(lowestGoldPieceInfo)){
			   			$('#lowestGoldPieceInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#lowestGoldPieceInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var highestGoldPieceInfo = $('#highestGoldPiece').val();
			   		if(highestGoldPieceInfo == null || highestGoldPieceInfo == "" || !reg1.test(highestGoldPieceInfo)){
			   			$('#newLevelInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#highestGoldPieceInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
	   			}
	   			if(activeTypeId == '1005'){
	   				var isMonthlyPaymentInfo = $('#isMonthlyPayment').val();
			   		if(isMonthlyPaymentInfo == null || isMonthlyPaymentInfo == ""){
			   			$('#isMonthlyPaymentInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#isMonthlyPaymentInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var monthlyPaymentTypeInfo = $('#monthlyPaymentType').val();
			   		if(monthlyPaymentTypeInfo == null || monthlyPaymentTypeInfo == ""){
			   			$('#monthlyPaymentTypeInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#monthlyPaymentTypeInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var monthlyPaymentPriceInfo = $('#monthlyPaymentPrice').val();
			   		if(monthlyPaymentPriceInfo == null || monthlyPaymentPriceInfo == "" || !reg1.test(monthlyPaymentPriceInfo)){
			   			$('#monthlyPaymentPriceInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#monthlyPaymentPriceInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var monthlyBuyDaysInfo = $('#monthlyBuyDays').val();
			   		if(monthlyBuyDaysInfo == null || monthlyBuyDaysInfo == "" || !reg1.test(monthlyBuyDaysInfo)){
			   			$('#monthlyBuyDaysInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#monthlyBuyDaysInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var monthlyGiveDaysInfo = $('#monthlyGiveDays').val();
			   		if(monthlyGiveDaysInfo == null || monthlyGiveDaysInfo == "" || !reg1.test(monthlyGiveDaysInfo)){
			   			$('#monthlyGiveDaysInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#monthlyGiveDaysInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var monthlyBuyOrGiveInfo = $('#monthlyBuyOrGive').val();
			   		if(monthlyBuyOrGiveInfo == null || monthlyBuyOrGiveInfo == "" || !reg1.test(monthlyBuyOrGiveInfo)){
			   			$('#monthlyBuyOrGiveInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#monthlyBuyOrGiveInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var monthlyPaymentOriginalPriceInfo = $('#monthlyPaymentOriginalPrice').val();
			   		if(monthlyPaymentOriginalPriceInfo == null || monthlyPaymentOriginalPriceInfo == "" || !reg1.test(monthlyPaymentOriginalPriceInfo)){
			   			$('#monthlyPaymentOriginalPriceInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#monthlyPaymentOriginalPriceInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
	   			}
	   			if(activeTypeId == '1006' || activeTypeId == '1013'){
	   				var isFixedPriceInfo = $('#isFixedPrice').val();
			   		if(isFixedPriceInfo == null || isFixedPriceInfo == ""){
			   			$('#isFixedPriceInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#isFixedPriceInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var fixedPriceInfo = $('#fixedPrice').val();
			   		if(fixedPriceInfo == null || fixedPriceInfo == "" || !reg1.test(fixedPriceInfo)){
			   			$('#newLevelInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#fixedPriceInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
	   			}
	   			if(activeTypeId == '1007'){
	   				$("#whole_media").removeClass("dispalynone");
	   				$("#discount_info").removeClass("dispalynone");
	   				$("#isWholeMedia").val("");
	   				$("#discount").val("");
	   				var isWholeMediaInfo = $('#isWholeMedia').val();
			   		if(isWholeMediaInfo == null || isWholeMediaInfo == ""){
			   			$('#isWholeMediaInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#isWholeMediaInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var discountInfo = $('#discount').val();
			   		if(discountInfo == null || discountInfo == "" || !reg1.test(discountInfo)){
			   			$('#discountInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#discountInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
	   			}
	   			if(activeTypeId == '1008'){
	   				var consumeChapterInfo = $('#consumeChapter').val();
			   		if(consumeChapterInfo == null || consumeChapterInfo == "" || !reg1.test(consumeChapterInfo)){
			   			$('#consumeChapterInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#consumeChapterInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var discountInfo = $('#discount').val();
			   		if(discountInfo == null || discountInfo == "" || !reg1.test(discountInfo)){
			   			$('#discountInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#discountInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
	   			}
	   			
	   			if(activeTypeId == '1009'){
	   				var prizeQuantityInfo = $('#prizeQuantity').val();
			   		if(prizeQuantityInfo == null || prizeQuantityInfo == "" || !reg1.test(prizeQuantityInfo)){
			   			$('#prizeQuantityInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#prizeQuantityInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var prizePriceInfo = $('#prizePrice').val();
			   		if(prizePriceInfo == null || prizePriceInfo == "" || !reg1.test(prizePriceInfo)){
			   			$('#prizePriceInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#prizePriceInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
	   			}
	   			
	   			if(activeTypeId == '1010' || activeTypeId == '1011' || activeTypeId == '1012' || activeTypeId == '1014' || activeTypeId == '1015' || activeTypeId == '1016' || activeTypeId == '1017' || activeTypeId == '1018'){
	   				var depositMoneyInfo = $('#depositMoney').val();
			   		if(depositMoneyInfo == null || depositMoneyInfo == "" || !reg1.test(depositMoneyInfo)){
			   			$('#depositMoneyInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#depositMoneyInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var depositReadPriceInfo = $('#depositReadPrice').val();
			   		if(depositReadPriceInfo == null || depositReadPriceInfo == "" || !reg1.test(depositReadPriceInfo)){
			   			$('#depositReadPriceInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#depositReadPriceInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var depositGiftReadPriceInfo = $('#depositGiftReadPrice').val();
			   		if(depositGiftReadPriceInfo == null || depositGiftReadPriceInfo == "" || !reg1.test(depositGiftReadPriceInfo)){
			   			$('#depositGiftReadPriceInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#depositGiftReadPriceInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
			   		var relationProductIdInfo = $('#relationProductId').val();
			   		if(relationProductIdInfo == null || relationProductIdInfo == ""){
			   			$('#relationProductIdInfo').html('&nbsp;&nbsp;<img src="/images/wrong.jpg"/ style="width: 20px;">');
			   			flag = false;
			   		}else{
			   			$('#relationProductIdInfo').html('&nbsp;&nbsp;<img src="/images/right.jpg"/ style="width: 20px;">');
			   		}
	   			}
		   		
		   		if(flag){
		   			$('#activity_type_add_form').submit();
		   		}
	   		}
   			
	   	}
	   	
	   	function initActivityType(){
	   		var value = $('#activityTypeCode').val();
   			var activeTypeId = value.split("_")[0];
			$("#first_deposit_give").addClass("dispalynone");
			$("#give_scale").addClass("dispalynone");
			$("#previous_number_give").addClass("dispalynone");
			$("#give_scale").addClass("dispalynone");
			$("#consume_give").addClass("dispalynone");
			$("#consume_level_give").addClass("dispalynone");
			$("#level_give").addClass("dispalynone");
			$("#consume_level_give").addClass("dispalynone");
			$("#day_worship_give_1").addClass("dispalynone");
			$("#day_worship_give_2").addClass("dispalynone");
			$("#monthly_payment_1").addClass("dispalynone");
			$("#monthly_payment_2").addClass("dispalynone");
			$("#monthly_payment_3").addClass("dispalynone");
			$("#monthly_payment_4").addClass("dispalynone");
			$("#monthly_payment_5").addClass("dispalynone");
			$("#fixed_price").addClass("dispalynone");
			$("#whole_media").addClass("dispalynone");
			$("#discount_info").addClass("dispalynone");
			$("#consume_chapter").addClass("dispalynone");
			$("#discount_info").addClass("dispalynone");
			$("#prize").addClass("dispalynone");
			$("#deposit_1").addClass("dispalynone");
			$("#deposit_2").addClass("dispalynone");
			$("#deposit_3").addClass("dispalynone");
   			if(activeTypeId == '1000'){
   				$("#first_deposit_give").removeClass("dispalynone");
   				$("#give_scale").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1001'){
   				$("#previous_number_give").removeClass("dispalynone");
   				$("#give_scale").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1002'){
   				$("#consume_give").removeClass("dispalynone");
   				$("#consume_level_give").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1003'){
   				$("#level_give").removeClass("dispalynone");
   				$("#consume_level_give").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1004'){
   				$("#day_worship_give_1").removeClass("dispalynone");
   				$("#day_worship_give_2").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1005'){
   				$("#monthly_payment_1").removeClass("dispalynone");
   				$("#monthly_payment_2").removeClass("dispalynone");
   				$("#monthly_payment_3").removeClass("dispalynone");
   				$("#monthly_payment_4").removeClass("dispalynone");
   				$("#monthly_payment_5").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1006'){
   				$("#fixed_price").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1007'){
   				$("#whole_media").removeClass("dispalynone");
   				$("#discount_info").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1008'){
   				$("#consume_chapter").removeClass("dispalynone");
   				$("#discount_info").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1009'){
   				$("#prize").removeClass("dispalynone");
   			}
   			if(activeTypeId == '1010' || activeTypeId == '1011' || activeTypeId == '1012' || activeTypeId == '1014' || activeTypeId == '1015' || activeTypeId == '1016' || activeTypeId == '1017' || activeTypeId == '1018'){
   				$("#deposit_1").removeClass("dispalynone");
   				$("#deposit_2").removeClass("dispalynone");
   				$("#deposit_3").removeClass("dispalynone");
   			}
	   	}
	</script>
	<style type="text/css">
		.dispalynone {
			display: none;
		}
	</style>
</head>   
  <body>
	 <div class="page"><!--page开始-->
		<div class="main clear"><!--main开始-->
			<div class="right">
				<div class="m-r">
					<h3><a href="/activity/info/list.go">促销信息管理</a>&nbsp;&gt;&gt;&nbsp;添加促销活动信息</h3>
					<#if returnInfo??>
						<div class="mrdiv">
				      		 <table >
			        			<tr>
									<td style="padding-left:50px;"><span id="message">${returnInfo}</span>
										<#if successFlag??>
					    					<#if successFlag == 0>&nbsp;&nbsp;&nbsp;&nbsp;<a href="/activity/info/add.go" style="height: 20px; font-size: 20px;">添加成功,继续添加</a>&nbsp;&nbsp;<a href="/activity/info/list.go" style="height: 20px; font-size: 20px;"><img src='/images/show_list.jpg' style='padding-bottom: 0px;'></a></#if>
					    				</#if>
									</td>
								</tr>
							</table>
					    </div>
				    </#if>
				    <div>
					    <form action="/activity/info/goadd.go" method="post" id="activity_type_add_form">
				    		<input type="hidden" name="activityId" id="activityId" value="<#if activityInfo??><#if activityInfo.activityId??>${activityInfo.activityId?c}</#if></#if>">
						     <table class="table3" border="1" bordercolor="#a0c6e5" rules=none>
			    				<tr>
					    			<td class="tdright" style="width:15%">促销名称：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="activityName" onblur="validInput('activityName')" id="activityName" value="<#if activityInfo??><#if activityInfo.activityName??>${activityInfo.activityName}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%" id="activityNameInfo" name="messageInfo"></td>
					    			<td class="tdright" style="width:15%">促销方案：</td>
					    			<td class="tdleft" style="width:10%">
					    				<select id="activityTypeCode" name="activityTypeCode" onchange="changeActiveType('activityTypeCode')">
					    					<option value="">请选择</option>
					    					<#if activityTypeList??>
			    								<#list activityTypeList as activityType>
							    					<option value="${activityType.activityTypeId?c}_${activityType.activityTypeCode}" <#if activityInfo??><#if activityInfo.activityTypeId??><#if activityInfo.activityTypeId == activityType.activityTypeId>selected="selected"</#if></#if></#if>>${activityType.activityTypeName}</option>
							    				</#list>
				      						</#if>
					    				</select>
					    			</td>
					    			<td class="tdleft" style="width:8%" id="activityTypeCodeInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr>
						      		<td class="tdright" style="width:15%">开始时间：</td>
						      		<td  class="tdleft" style="width:10%"><input type="text" readonly="readonly"  name="startTimeString" id="startTimeString" value="<#if activityInfo??><#if activityInfo.startTimeString??>${activityInfo.startTimeString}</#if></#if>"></td>
						      		<td class="tdleft" id="startTimeStringInfo" name="messageInfo"></td>
					    			<td class="tdright" style="width:15%">结束时间：</td>
					    			<td  class="tdleft" style="width:10%"><input type="text" readonly="readonly"  name="endTimeString" id="endTimeString" value="<#if activityInfo??><#if activityInfo.endTimeString??>${activityInfo.endTimeString}</#if></#if>"></td>
					    			<td class="tdleft" id="endTimeStringInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr id="first_deposit_give">
					    			<td class="tdright" style="width:15%">是否第一次充值：</td>
					    			<td class="tdleft" style="width:10%">
					    				<select id="isFirstDeposit" name="isFirstDeposit" onchange="validInput('isFirstDeposit')">
					    					<option value="">请选择</option>
					    					<option value="1" <#if activityInfo??><#if activityInfo.isFirstDeposit??><#if activityInfo.isFirstDeposit == 1>selected="selected"</#if></#if></#if>>第一次充值</option>
					    				</select>
					    			</td>
					    			<td class="tdleft" style="width:8%" id="isFirstDepositInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:8%">&nbsp;</td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr id="previous_number_give">
					    			<td class="tdright" style="width:15%">前N名充值用户：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="isPreviousNumber" onblur="validInputMustNumber('isPreviousNumber')" id="isPreviousNumber" value="<#if activityInfo??><#if activityInfo.isPreviousNumber??>${activityInfo.isPreviousNumber?c}</#if></#if>"></td>
					    			<td class="tdleft" style="width:8%" id="isPreviousNumberInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:8%">&nbsp;</td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr id="give_scale">
					    			<td class="tdright" style="width:15%">赠送金币比例：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="giveScale" onblur="validInputMustNumber('giveScale')" id="giveScale" value="<#if activityInfo??><#if activityInfo.giveScale??>${activityInfo.giveScale?c}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%" id="giveScaleInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:8%">&nbsp;</td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr id="consume_give">
					    			<td class="tdright" style="width:15%">消费满N金币：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="consumeSatisfy" onblur="validInputMustNumber('consumeSatisfy')" id="consumeSatisfy" value="<#if activityInfo??><#if activityInfo.consumeSatisfy??>${activityInfo.consumeSatisfy?c}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%"  id="consumeSatisfyInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:8%">&nbsp;</td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
					    		</tr>
						      	<tr id="level_give">
					    			<td class="tdright" style="width:15%">原会员级别：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="originalLevel" onblur="validInput('originalLevel')" id="originalLevel" value="<#if activityInfo??><#if activityInfo.originalLevel??>${activityInfo.originalLevel}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%"  id="originalLevelInfo" name="messageInfo"></td>
					    			<td class="tdright" style="width:15%">新会员级别：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="newLevel" onblur="validInput('newLevel')" id="newLevel" value="<#if activityInfo??><#if activityInfo.newLevel??>${activityInfo.newLevel}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%" id="newLevelInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr id="consume_level_give">
					    			<td class="tdright" style="width:15%">赠送金币：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="giveGoldPiece" onblur="validInputMustNumber('giveGoldPiece')" id="giveGoldPiece" value="<#if activityInfo??><#if activityInfo.giveGoldPiece??>${activityInfo.giveGoldPiece?c}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%"  id="giveGoldPieceInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:8%">&nbsp;</td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr id="day_worship_give_1">
					    			<td class="tdright" style="width:15%">每天最多膜拜次数：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="dayWorshipLimit" onblur="validInputMustNumber('dayWorshipLimit')" id="dayWorshipLimit" value="<#if activityInfo??><#if activityInfo.dayWorshipLimit??>${activityInfo.dayWorshipLimit?c}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%"  id="dayWorshipLimitInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:8%">&nbsp;</td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr id="day_worship_give_2">
					    			<td class="tdright" style="width:15%">每次膜拜最少奖励金币：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="lowestGoldPiece" onblur="validInputMustNumber('lowestGoldPiece')" id="lowestGoldPiece" value="<#if activityInfo??><#if activityInfo.lowestGoldPiece??>${activityInfo.lowestGoldPiece?c}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%"  id="lowestGoldPieceInfo" name="messageInfo"></td>
					    			<td class="tdright" style="width:15%">每次膜拜最多奖励金币：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="highestGoldPiece" onblur="validInputMustNumber('highestGoldPiece')" id="highestGoldPiece" value="<#if activityInfo??><#if activityInfo.highestGoldPiece??>${activityInfo.highestGoldPiece?c}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%"  id="highestGoldPieceInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr id="monthly_payment_1">
					    			<td class="tdright" style="width:15%">是否包月：</td>
					    			<td class="tdleft" style="width:10%">
					    				<select id="isMonthlyPayment" name="isMonthlyPayment"  onchange="validInput('isMonthlyPayment')">
					    					<option value="">请选择</option>
					    					<option value="1" <#if activityInfo??><#if activityInfo.isMonthlyPayment??><#if activityInfo.isMonthlyPayment==1>selected="selected"</#if></#if></#if>>包月</option>
					    					<option value="0" <#if activityInfo??><#if activityInfo.isMonthlyPayment??><#if activityInfo.isMonthlyPayment==0>selected="selected"</#if></#if></#if>>非包月</option>
					    				</select>
					    			</td>
					    			<td class="tdleft"  style="width:8%"  id="isMonthlyPaymentInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:8%">&nbsp;</td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr id="monthly_payment_2">
					    			<td class="tdright" style="width:15%">包月类型：</td>
					    			<td class="tdleft" style="width:10%">
					    				<select id="monthlyPaymentType" name="monthlyPaymentType" onchange="validInput('monthlyPaymentType')">
					    					<option value="">请选择</option>
					    					<option value="1001" <#if activityInfo??><#if activityInfo.monthlyPaymentType??><#if activityInfo.monthlyPaymentType=="1001">selected="selected"</#if></#if></#if>>全场</option>
					    					<!--
					    					<option value="1002" <#if activityInfo??><#if activityInfo.monthlyPaymentType??><#if activityInfo.monthlyPaymentType=="1002">selected="selected"</#if></#if></#if>>按栏目包月</option>
					    					<option value="1003" <#if activityInfo??><#if activityInfo.monthlyPaymentType??><#if activityInfo.monthlyPaymentType=="1003">selected="selected"</#if></#if></#if>>按分类包月</option>
					    					-->
					    				</select>
					    			</td>
					    			<td class="tdleft"  style="width:8%"  id="monthlyPaymentTypeInfo" name="messageInfo"></td>
					    			<td class="tdright" style="width:15%">获取渠道：</td>
					    			<td class="tdleft" style="width:10%">
					    				<select id="monthlyBuyOrGive" name="monthlyBuyOrGive" onchange="validInput('monthlyBuyOrGive')">
					    					<option value="">请选择</option>
					    					<option value="0" <#if activityInfo??><#if activityInfo.monthlyBuyOrGive??><#if activityInfo.monthlyBuyOrGive==0>selected="selected"</#if></#if></#if>>购买</option>
					    					<option value="1" <#if activityInfo??><#if activityInfo.monthlyBuyOrGive??><#if activityInfo.monthlyBuyOrGive==0>selected="selected"</#if></#if></#if>>赠送</option>
					    				</select>
					    			</td>
					    			<td class="tdleft"  style="width:9%"  id="monthlyBuyOrGiveInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	
						      	<tr id="monthly_payment_3">
					    			<td class="tdright" style="width:15%">包月购买原金额：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="monthlyPaymentOriginalPrice" onchange="calculateDiscount()" onblur="validInputMustNumber('monthlyPaymentOriginalPrice')" id="monthlyPaymentOriginalPrice" value="<#if activityInfo??><#if activityInfo.monthlyPaymentOriginalPrice??>${activityInfo.monthlyPaymentOriginalPrice?c}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%"  id="monthlyPaymentOriginalPriceInfo" name="messageInfo"></td>
					    			<td class="tdright" style="width:15%">包月购买销售金额：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="monthlyPaymentPrice" onchange="calculateDiscount()" onblur="validInputMustNumber('monthlyPaymentPrice')" id="monthlyPaymentPrice" value="<#if activityInfo??><#if activityInfo.monthlyPaymentPrice??>${activityInfo.monthlyPaymentPrice?c}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%"  id="monthlyPaymentPriceInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr id="monthly_payment_4">
					    			<td class="tdright" style="width:15%">包月购买天数：</td>
					    			<td class="tdleft" style="width:10%">
					    				<input type="text" name="monthlyBuyDays" onblur="validInputMustNumber('monthlyBuyDays')" id="monthlyBuyDays" value="<#if activityInfo??><#if activityInfo.monthlyBuyDays??>${activityInfo.monthlyBuyDays}</#if></#if>">
					    			</td>
					    			<td class="tdleft"  style="width:8%"  id="monthlyBuyDaysInfo" name="messageInfo"></td>
					    			<td class="tdright" style="width:15%">包月赠送天数：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="monthlyGiveDays" onblur="validInputMustNumber('monthlyGiveDays')" id="monthlyGiveDays" value="<#if activityInfo??><#if activityInfo.monthlyGiveDays??>${activityInfo.monthlyGiveDays}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:9%"  id="monthlyGiveDaysInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	
						      	<tr id="monthly_payment_5">
					    			<td class="tdright" style="width:15%">关联（栏目或分类）ID：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="monthlyPaymentRelation" id="monthlyPaymentRelation" value="<#if activityInfo??><#if activityInfo.monthlyPaymentRelation??>${activityInfo.monthlyPaymentRelation}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%" id="monthlyPaymentRelationInfo" name="messageInfo"></td>
					    			<td class="tdright" style="width:15%"><font color="red">(不能输入，自动计算*)&nbsp;</font>折扣：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="monthlyPaymentDiscount" id="monthlyPaymentDiscount" readonly="readonly" value="<#if activityInfo??><#if activityInfo.monthlyPaymentDiscount??>${activityInfo.monthlyPaymentDiscount}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%" id="monthlyPaymentDiscountInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	
						      	<tr id="fixed_price">
					    			<td class="tdright" style="width:15%">是否一口价：</td>
					    			<td class="tdleft" style="width:10%">
					    				<select id="isFixedPrice" name="isFixedPrice" onblur="validInput('isFixedPrice')">
					    					<option value="1" <#if activityInfo??><#if activityInfo.isFixedPrice??><#if activityInfo.isFixedPrice==1>selected="selected"</#if></#if></#if>>一口价</option>
					    				</select>
					    			</td>
					    			<td class="tdleft"  style="width:8%"  id="isFixedPriceInfo" name="messageInfo"></td>
					    			<td class="tdright" style="width:15%">一口价金额：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="fixedPrice" onblur="validInputMustNumber('fixedPrice')" id="fixedPrice" value="<#if activityInfo??><#if activityInfo.fixedPrice??>${activityInfo.fixedPrice}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%" id="fixedPriceInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr id="whole_media">
					    			<td class="tdright" style="width:15%">是否为全本购买：</td>
					    			<td class="tdleft" style="width:10%">
					    				<select id="isWholeMedia" name="isWholeMedia" onblur="validInput('isWholeMedia')">
					    					<option value="">请选择</option>
					    					<option value="1" <#if activityInfo??><#if activityInfo.isWholeMedia??><#if activityInfo.isWholeMedia==1>selected="selected"</#if></#if></#if>>全本购买</option>
					    				</select>
					    			</td>
					    			<td class="tdleft"  style="width:8%" id="isWholeMediaInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:8%">&nbsp;</td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
					    		</tr>
					    		<tr id="consume_chapter">
					    			<td class="tdright" style="width:15%">一次性购买章节数量：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="consumeChapter" onblur="validInputMustNumber('consumeChapter')" id="consumeChapter" value="<#if activityInfo??><#if activityInfo.consumeChapter??>${activityInfo.consumeChapter}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%" id="consumeChapterInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:8%">&nbsp;</td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
					    		</tr>
					    		<tr id="discount_info">
					    			<td class="tdright" style="width:15%">打折比例：</td><td><input type="text" onblur="validInputMustNumber('discount')" name="discount" id="discount" value="<#if activityInfo??><#if activityInfo.discount??>${activityInfo.discount}</#if></#if>"></td><td class="tdleft" style="width:8%"  id="discountInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:8%">&nbsp;</td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
					    		</tr>
					    		<tr id="prize">
					    			<td class="tdright" style="width:15%">福袋个数：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="prizeQuantity" onblur="validInputMustNumber('prizeQuantity')" id="prizeQuantity" value="<#if activityInfo??><#if activityInfo.prizeQuantity??>${activityInfo.prizeQuantity}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%"  id="prizeQuantityInfo" name="messageInfo"></td>
					    			<td class="tdright" style="width:15%">福袋购买金额：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="prizePrice" onblur="validInputMustNumber('prizePrice')" id="prizePrice" value="<#if activityInfo??><#if activityInfo.prizePrice??>${activityInfo.prizePrice}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%" id="prizePriceInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr id="deposit_1">
					    			<td class="tdright" style="width:15%">所需真实金额（单位：分）：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="depositMoney" onblur="validInputMustNumber('depositMoney')" id="depositMoney" value="<#if activityInfo??><#if activityInfo.depositMoney??>${activityInfo.depositMoney?c}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%"  id="depositMoneyInfo" name="messageInfo"></td>
					    			<td class="tdright" style="width:15%">兑换阅读币个数：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="depositReadPrice" onblur="validInputMustNumber('depositReadPrice')" id="depositReadPrice" value="<#if activityInfo??><#if activityInfo.depositReadPrice??>${activityInfo.depositReadPrice?c}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%" id="depositReadPriceInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr id="deposit_2">
						      		<td class="tdright" style="width:15%">赠送银铃铛个数：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="depositGiftReadPrice" onblur="validInputMustNumber('depositGiftReadPrice')" id="depositGiftReadPrice" value="<#if activityInfo??><#if activityInfo.depositGiftReadPrice??>${activityInfo.depositGiftReadPrice?c}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%" id="depositGiftReadPriceInfo" name="messageInfo"></td>
					    			<td class="tdright" style="width:15%">关联商品ID：</td><td><input type="text" onblur="validInput('relationProductId')" name="relationProductId" id="relationProductId" value="<#if activityInfo??><#if activityInfo.relationProductId??>${activityInfo.relationProductId}</#if></#if>"></td><td class="tdleft" style="width:8%"  id="relationProductIdInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
					    		</tr>
					    		<tr id="deposit_3">
						      		<td class="tdright" style="width:15%">赠送金铃铛个数：</td>
					    			<td class="tdleft" style="width:10%"><input type="text" name="depositGiftGoldPrice" onblur="validInputMustNumber('depositGiftGoldPrice')" id="depositGiftGoldPrice" value="<#if activityInfo??><#if activityInfo.depositGiftGoldPrice??>${activityInfo.depositGiftGoldPrice?c}</#if></#if>"></td>
					    			<td class="tdleft"  style="width:8%" id="depositGiftReadPriceInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
					    		</tr>
						      	<tr>
					    			<td class="tdright" style="width:15%">是否有效：</td>
					    			<td class="tdleft" style="width:10%">
					    				<select id="status" name="status" onblur="validInput('status')">
					    					<option value="1" <#if activityInfo??><#if activityInfo.status??><#if activityInfo.status==1>selected="selected"</#if></#if></#if>>有效</option>
					    					<option value="0" <#if activityInfo??><#if activityInfo.status??><#if activityInfo.status==0>selected="selected"</#if></#if></#if>>无效</option>
					    				</select>
					    			</td>
					    			<td class="tdleft"  style="width:8%" id="statusInfo" name="messageInfo"></td>
					    			<td class="tdright" style="width:15%">平台来源：</td>
					    			<td class="tdleft" style="width:10%">
					    				<select id="fromPaltform" name="fromPaltform">
					    					<option value="">&nbsp;请选择</option>
											<option value="yc_android" <#if activityInfo ?? && activityInfo.fromPaltform ?? && activityInfo.fromPaltform == "yc_android">selected="selected"</#if>>当读小说安卓平台</option>
											<option value="yc_ios" <#if activityInfo ?? && activityInfo.fromPaltform ?? && activityInfo.fromPaltform == "yc_ios">selected="selected"</#if>>当读小说ios平台</option>
											<option value="ds_android" <#if activityInfo ?? && activityInfo.fromPaltform ?? && activityInfo.fromPaltform == "ds_android">selected="selected"</#if>>当当读书安卓平台</option>
											<option value="ds_ios" <#if activityInfo ?? && activityInfo.fromPaltform ?? && activityInfo.fromPaltform == "ds_ios">selected="selected"</#if>>当当读书ios平台</option>
					    				</select>
					    			</td>
					    			<td class="tdleft"  style="width:8%" id="statusInfo" name="messageInfo"></td>
					    			<td style="width:15%">&nbsp;</td>
					    			<td style="width:10%">&nbsp;</td>
					    			<td style="width:9%">&nbsp;</td>
						      	</tr>
						      	<tr>
					    			<td colspan="9" style="text-align:center;">
					    				<#if successFlag??>
					    					<#if successFlag == 1><image src="/images/save.jpg" onclick="javascript:activityInfoAddForm()" /></#if>
					    				<#else>
											<#if userSessionInfo?? && userSessionInfo.f['236']?? >
					    					<image src="/images/save.jpg" onclick="javascript:activityInfoAddForm()" />
											</#if>
					    				</#if>
					    				&nbsp;&nbsp;<image src="/images/back.png" onclick="javascript:history.go(-1)" />
					    			</td>
						      	</tr>
				            </table>
			            </form>
		            </div>
			    </div>
		    </div>
		</div>
	</div>
	<#include "../../common/common-js.ftl">
	<script type="text/javascript">
		initActivityType()
	</script>
  </body>
</html>
