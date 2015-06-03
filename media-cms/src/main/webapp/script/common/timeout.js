var timeout;

$(function(){
	function logout(){
		window.top.location.href="/login/logout.go";
	}
	window.top.clearTimeout(window.top.timeout);
	window.top.timeout = window.top.setTimeout(logout, 1000*60*60);
}); 