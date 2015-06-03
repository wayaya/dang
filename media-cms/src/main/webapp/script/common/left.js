function showGroupActionLeft(groupColumnId){
	
	window.top.$('#rightIframe').attr('src', '/index/welcome.go');
	
	if(groupColumnId != null){
		for(var i=1; i <=5; i++){
			if(document.getElementById('div_'+i)!=null){
				document.getElementById('div_'+i).style.display = "none";
			}
		}
		if(document.getElementById('div_'+groupColumnId)!=null){
			document.getElementById('div_'+groupColumnId).style.display = "block";
		}
	}
}

function showActionLeft(groupColumnId, obj){
	if(groupColumnId != null){
		if(document.getElementById(groupColumnId)!=null){
			var display = document.getElementById(groupColumnId).style.display;
			if("none" == display){
				document.getElementById(groupColumnId).style.display = "block";
			}else if("block" == display){
				document.getElementById(groupColumnId).style.display = "none";
			}
		}
	}
}

function changeLiCss(obj){
	$("li[class='dd-1']").removeClass("dd-1").addClass("dd");
	$(obj).removeClass("dd");
	$(obj).addClass("dd-1");
}

function changeHeadCss(obj){
	$("li[class='head_nav_li_2']").removeClass("head_nav_li_2").addClass("head_nav_li_1");
	$(obj).removeClass("head_nav_li_1");
	$(obj).addClass("head_nav_li_2");
}

function changeIframe(src){
	document.getElementById("rightIframe").src=src;
}

function overCurrent(i){
	$("#row_"+i).css("background-color","#BEBEBE");
}

function outCurrent(i){
	var h = i % 2 ;
	if(h == 0){
		$("#row_"+i).css("background-color","#E4EAF6");
	}else{
		$("#row_"+i).css("background-color","#eee");
	}
}

if (!Array.prototype.forEach)
{
    Array.prototype.forEach = function(fun /*, thisp*/)
    {
        var len = this.length;
        if (typeof fun != "function")
            throw new TypeError();

        var thisp = arguments[1];
        for (var i = 0; i < len; i++)
        {
            if (i in this)
                fun.call(thisp, this[i], i, this);
        }
    };
}

function validInput(id){
	var isValue = false;
	var value = $('#'+id).val();
	if(value == null || value == ""){
		$('#'+id+"Info").html('&nbsp;&nbsp;<img src="/images/wrong.jpg" style="width: 20px;">');
	}else{
		$('#'+id+"Info").html('&nbsp;&nbsp;<img src="/images/right.jpg" style="width: 20px;"/>');
		isValue = true;
	}
	
	if(id == 'price' || id == 'iosPrice'){
		if(value == null || value == "" || isNaN(value)){
			$('#'+id+"Info").html('&nbsp;&nbsp;<img src="/images/wrong.jpg" style="width: 20px;">');
		}
	}
	
	if('type' == id){
		if(value != 0 && value != 1 && value != 2 && value != 3 && value != 4){
			$('#'+id+"Info").html('&nbsp;&nbsp;<img src="/images/wrong.jpg" style="width: 20px;">');
			return;
		}
	}
	
	if(id=="effectiveDay" || id=="mainGoldPrice" || id=="subGoldPrice"){
		if(reg1.test(value)){
			$('#'+id+"Info").html('&nbsp;&nbsp;<img src="/images/right.jpg" style="width: 20px;"/>');
			isValue = true;
		}else{
			$('#'+id+"Info").html('&nbsp;&nbsp;<img src="/images/wrong.jpg" style="width: 20px;"/>');
		}
	}
	
	if(id=="propMainGoldPrice" || id=="propSubGoldPrice" || id=="propPurposeId"){
		var reg1 =  /^\d+$/;
		if(reg1.test(value)){
			$('#'+id+"Info").html('&nbsp;&nbsp;<img src="/images/right.jpg" style="width: 20px;"/>');
			isValue = true;
		}else{
			$('#'+id+"Info").html('&nbsp;&nbsp;<img src="/images/wrong.jpg" style="width: 20px;"/>');
		}
	}
	
	$('#'+id).val(value);
	
	return isValue;
}

function validInputMustNumber(id){
	var value = $('#'+id).val();
	var reg1 =  /^\d+$/;
	if(value == null || value == "" || !reg1.test(value)){
		$('#'+id+"Info").html('&nbsp;&nbsp;<img src="/images/wrong.jpg" style="width: 20px;">');
	}else{
		$('#'+id+"Info").html('&nbsp;&nbsp;<img src="/images/right.jpg" style="width: 20px;"/>');
	}
	
	$('#'+id).val(value);
}

function validInputMustNumberOrZimu(id){
	var value = $('#'+id).val();
	var reg1 =  /^[A-Za-z0-9-]+$/;
	if(value == null || value == "" || !reg1.test(value)){
		$('#'+id+"Info").html('&nbsp;&nbsp;<img src="/images/wrong.jpg" style="width: 20px;">');
	}else{
		$('#'+id+"Info").html('&nbsp;&nbsp;<img src="/images/right.jpg" style="width: 20px;"/>');
	}
	
	$('#'+id).val(value);
}



$(function(){
	//IE也能用textarea
	$("textarea[maxlength]").keyup(function(){
		var area=$(this);
		var max=parseInt(area.attr("maxlength"),10); //获取maxlength的值
		if(max>0){
			if(area.val().length>max){ //textarea的文本长度大于maxlength
				area.val(area.val().substr(0,max)); //截断textarea的文本重新赋值
			}
		}
	});
	//复制的字符处理问题
	$("textarea[maxlength]").blur(function(){
		var area=$(this);
		var max=parseInt(area.attr("maxlength"),10); //获取maxlength的值
		if(max>0){
			if(area.val().length>max){ //textarea的文本长度大于maxlength
				area.val(area.val().substr(0,max)); //截断textarea的文本重新赋值
			}
		}
	});
}); 