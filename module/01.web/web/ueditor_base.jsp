<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- ueditor控件 -->
<script type="text/javascript" charset="utf-8" src="ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<!--这一行显示ie会有问题xxxxxx-->
<%--<script type="text/javascript" charset="utf-8" src="ueditor/lang/zh-cn/zh-cn.js"></script>--%>
<script type="text/javascript" charset="utf-8" src="ueditor/ueditor.parse.min.js"></script>