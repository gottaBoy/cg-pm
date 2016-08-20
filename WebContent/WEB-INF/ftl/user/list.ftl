<#assign zhangfn=JspTaglibs["/WEB-INF/tld/zhang-functions.tld"]/>
<#include "/constants.ftl"> 
<@i.tHtml> 
		<!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Blank</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row --> 
                <div class="row">    
                	<div class="col-lg-12">

						<#if msg?exists>
						    <div class="message">${msg}</div>
						</#if>
						
						<@shiro.hasPermission name="system:user:create">
						    <a href="${request.contextPath}/user/create">用户新增</a><br/>
						</@shiro.hasPermission>
						
						<table class="table">
						    <thead>
						        <tr>
						            <th>姓名名</th>
						            <th>用户名</th>
						            <th>所属组织</th>
						            <th>角色列表</th>
						            <th>操作</th>
						        </tr>
						    </thead>
						    <tbody>
						        <#list userList as user>
						            <tr>
						                <td>${user.realname}</td>
						                <td>${user.username}</td>
						                <td>${zhangfn.organizationName(user.organizationId)}</td>
						                <td>${zhangfn.roleNames(user.roleIds)}</td>
						                <td>
						                    <@shiro.hasPermission name="system:user:update">
						                        <a href="${request.contextPath}/user/${user.id}/update">修改</a>
						                    </@shiro.hasPermission>
						
						                    <@shiro.hasPermission name="system:user:delete">
						                        <a href="${request.contextPath}/user/${user.id}/delete">删除</a>
						                    </@shiro.hasPermission>
						
						                    <@shiro.hasPermission name="system:user:update">
						                        <a href="${request.contextPath}/user/${user.id}/changePassword">改密</a>
						                    </@shiro.hasPermission>
						                    
						                    <@shiro.hasPermission name="system:user:level">
						                        <a href="${request.contextPath}/user/${user.id}/level">层级</a>
						                    </@shiro.hasPermission>
						                    
						                    <@shiro.hasPermission name="system:user:cost">
						                        <a href="${request.contextPath}/user/${user.id}/cost">成本</a>
						                    </@shiro.hasPermission>
						                </td>
						            </tr>
						        </#list>
						    </tbody>
						</table>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->        

</@i.tHtml> 