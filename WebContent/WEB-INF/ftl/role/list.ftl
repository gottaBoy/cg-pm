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
						
						<@shiro.hasPermission name="system:role:create">
						    <a href="${request.contextPath}/role/create">角色新增</a><br/>
						</@shiro.hasPermission>
						<table class="table">
						    <thead>
						        <tr>
						            <th>角色名称</th>
						            <th>角色描述</th>
						            <th>拥有的资源</th>
						            <th>操作</th>
						        </tr>
						    </thead>
						    <tbody>
						        <#list roleList as role>
						            <tr>
						                <td>${role.role}</td>
						                <td>${role.description}</td>
						                <td>${zhangfn.resourceNames(role.resourceIds)}</td>
						                <td>
						                    <@shiro.hasPermission name="system:role:update">
						                        <a href="${request.contextPath}/role/${role.id}/update">修改</a>
						                    </@shiro.hasPermission>
						
						                    <@shiro.hasPermission name="system:role:delete">
						                        <a href="${request.contextPath}/role/${role.id}/delete">删除</a>
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