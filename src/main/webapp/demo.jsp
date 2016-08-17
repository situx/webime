<html>
<head>
    <title>Akkadian Input Method Editor (IME) - A Free, Open-Source JavaScript jQuery plugin for Online Akkadian Typing</title>
    <meta name="description" content="A free and open-source site for typing Pinyin and getting either simplified or traditional characters. Available for direct online use, or as a jQuery plugin." />
    <meta name="keywords" content="chinese, typing, input, ime, input method, javascript, jQuery, pinyin, zhuyin, bopomofo, simplified, traditional, mandarin, language, tool, application, 輸入法, 键入中文, 輸入普通话" />

    <link rel="canonical" href="http://www.chinese-ime.com" />

    <meta name="http-equiv" content="Content-type: text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="css/demo.css" />
    <link rel="stylesheet" type="text/css" href="css/ime.css" />
</head>
<body>

    <div class="header">
        <h1>Akkadian Input Method</h1>
        <h2>Free and Open Source</h2>
    </div>
    <div class="container" id="imebox">
		<table><tr align="center"><td><textarea class="chinese"></textarea></td><td>
		<table border="2" cellpadding="0" cellspacing="0" style="background-color:#1C1B1C;border-color:white;color:white;" height="90%">
		<tr><th colspan="2">Manual</th></tr><tr align="center"><td>Enter</td><td> Phonetic Input</td></tr>
		<tr align="center"><td>Space</td><td>Current suggestion</td></tr><tr align="center"><td>
        1-5</td><td> Choose candidate</td></tr><tr align="center"><td>,</td><td> Get last 5 suggestions</td></tr>
        <tr align="center"><td>.</td><td> Get next 5 suggestions</td></tr></table></td></tr></table>
   <div id="toolbar">
        <label class="chinese-checkbox"><input type="checkbox" checked="checked" style="'position': 'absolute'; 'z-index': 1000" id="active"/>Enabled</label>
        <label class="chinese-checkbox">Input Method: <select id="ime"></select></label>
   </div>     
    </div>
    <div class="footer">
    	<p>If you cannot see any cuneiform characters you are probably missing a cuneiform font. Download it <a href="http://oracc.museum.upenn.edu/doc/user/fonts/#CuneiformNA">here</a>!</p>
        <p>A Free, Easy-to-use, Online Akkadian Input Method Editor.<br> Original idea by Herman Schaaf, modified for Akkadian by Timo Homburg</p>
        <p>Check out the <a href="https://github.com/hermanschaaf/chinese-ime">source</a> of this jQuery plugin at Github</p>
        <p class="attribution"><a href="https://www.twitter.com/ironzeb"><img src="https://si0.twimg.com/profile_images/1385624901/ironzebra2_normal.png"/></a> Developed by Herman Schaaf (<a href="http://www.twitter.com/ironzeb">@ironzeb</a>)<br/>&copy; 2012 All rights reserved<br>modified for Akkadian by Timo Homburg<br> &copy; 2014 All rights reserved</p>
    </div>

    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
    
    <!-- Load this script if you want traditional character support
    <script type="text/javascript" charset="utf-8" src="js/trad_chars.js"></script>-->
    <!-- Load this script if you want support for adding the text at the caret position -->
    <script type="text/javascript" src="js/caret.js"></script>
    <!-- The actual script -->
    <script type="text/javascript" src="js/jQuery.chineseIME.js"></script>
    
    <script type="text/javascript">
    $(document).ready(function(){
        $("textarea.chinese").chineseInput({
            debug: true, // print debug messages
            input: {
                initial: 'traditional', // or 'simplified'
                allowChange: true, // allow transition between traditional and simplified
        		hide: $("#active"),
        		ime: $("#ime"),
        		toolbar:$("#toolbar")
            },
            allowHide: true, // allow the chinese input to be switched off
            active: true // whether or not the plugin should be active by default
        });
    });
    </script>
</body>
</html>