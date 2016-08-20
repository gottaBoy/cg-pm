<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]/>
<#include "/constants.ftl"> 
<!DOCTYPE html>
<html lang="zh-CN"> 
<head> 
	<@i.tHeadBox /><#--head标签模块引入 -->
</head>
<body>  

    <@form.form id="form" method="post" commandName="child" role="form">
        <@form.hidden path="id"/>
        <@form.hidden path="available"/>
        <@form.hidden path="parentId"/>
        <@form.hidden path="parentIds"/>

        <div class="form-group">
            <label>父节点名称：</label>
            ${parent.name}
        </div>

        <div class="form-group">
            <@form.label path="name">子节点名称：</@form.label>
            <@form.input path="name" class="form-control"/>
        </div>

        <@form.button class="btn btn-default">新增子节点</@form.button>
    </@form.form>
</body>
</html>