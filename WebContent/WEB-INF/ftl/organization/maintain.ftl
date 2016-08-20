<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]/>
<#include "/constants.ftl"> 
<!DOCTYPE html>
<html lang="zh-CN"> 
<head> 
	<@i.tHeadBox /><#--head标签模块引入 -->
</head>
<body>   
	    <@form.form id="orgMaintainform" method="post" commandName="organization" role="form">
	        <@form.hidden path="id"/>
	        <@form.hidden path="available"/>
	        <@form.hidden path="parentId"/>
	        <@form.hidden path="parentIds"/>
	
	        <div class="form-group">
	            <@form.label path="name">名称：</@form.label>
	            <@form.input path="name" class="form-control"/>
	        </div>
	        <@shiro.hasPermission name="system:organization:update">
	            <@form.button id="updateBtn" class="btn btn-default">修改</@form.button>
	        </@shiro.hasPermission>
	
	        <@shiro.hasPermission name="system:organization:delete">
	            <#if organization.rootNode == false>
	            <@form.button id="deleteBtn" class="btn btn-default">删除</@form.button>
	            </#if>
	        </@shiro.hasPermission>
	
	        <@shiro.hasPermission name="system:organization:create">
	            <@form.button id="appendChildBtn" class="btn btn-default">添加子节点</@form.button>
	        </@shiro.hasPermission>
	
	        <@shiro.hasPermission name="system:organization:update">
	            <#if organization.rootNode == false>
	            <@form.button id="moveBtn" class="btn btn-default">移动节点</@form.button>
	            </#if>
	        </@shiro.hasPermission>
	    </@form.form>
	
	    <script src="${request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
	    <script>
	        $(function() { 
	            $("#updateBtn").click(function() {
	                $("#orgMaintainform")
	                        .attr("action", "${request.contextPath}/organization/${organization.id}/update")
	                        .submit();
	                return false;
	            });
	            $("#deleteBtn").click(function() {
	                if(confirm("确认删除吗？")) {
	                    $("#orgMaintainform")
	                            .attr("action", "${request.contextPath}/organization/${organization.id}/delete")
	                            .submit();
	                }
	                return false;
	            });
	
	            $("#appendChildBtn").click(function() {
	                location.href="${request.contextPath}/organization/${organization.id}/appendChild";
	                return false;
	            });
	
	            $("#moveBtn").click(function() {
	                location.href="${request.contextPath}/organization/${organization.id}/move";
	                return false;
	            });
	        });
	    </script>
	 
</body>
</html>