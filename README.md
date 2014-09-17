webime
======

WebIME - A webservice for providing an input method engine for any language given a file of words/characters and their frequencies.

Released under LGPLv3

Javascript Code modified from chinese-ime by Ironzeb

Webservice written in Java

In addition to the code provided you need:

Apache Jersey Webservice
Apache Xerces XML Parser (to deal with UTF16 languages)

The code can be packed into a WAR file and can be run on (for example) an Apache Tomcat Server.
IME files in the IME xml format (example given for Akkadian cuneiform) should be put in the folder "ime" located above the webserver execution folder. (e.g. tomcat/webapps)

Happy testing!



