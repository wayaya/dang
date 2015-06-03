<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
    <head>
        <title>SWFObject 2 dynamic publishing example page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <script type="text/javascript" src="/script/swfobject.js"></script>
        <script type="text/javascript">
        swfobject.embedSWF("/script/treeTest.swf?dataURL=/digest/getLabel.go?chk=${id}&idValue=${id}&nameValue=${name}&allowSelectParent=false&singleSelect=false",
         "myMovieName", "490", "355", "9.0.0", "expressInstall.swf");
        </script>
        <script>
        	function thisMovie(movieName) { 
		if (navigator.appName.indexOf("Microsoft") != -1) { 
			return window[movieName] 
		} 
		else { 
			return document[movieName] 
		} 
	} 
		function setUp(signIds,signNames){
			signIds.value = thisMovie('myMovieName').getSelectId();
			signNames.value = thisMovie('myMovieName').getSelectName();
		}
	</script>
        </script>
    </head>
    <body>
        <div id="myMovieName">
            <h1>Alternative content</h1>
            <p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" 
　　　　　　　　　　　　alt="Get Adobe Flash player" /></a></p>
        </div>
    </body>
</html>