<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--浮层控件-->
<a href="#facebox_message" rel="facebox" id="facebox_message_a"></a>
<div id="facebox_message" style="display: none;">
    <div>
        <img id="facebox_message_img" src=""><span id="facebox_message_txt"></span>
    </div>
    <div style="text-align: center;">
        <input class="minBtn" type="button" onclick="$('#facebox .close_image')[0].click()" value="OK">
    </div>
</div>