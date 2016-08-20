<#include "/constants.ftl"> 
<@i.tHtml>
	<style>
        #table th, #table td {
            font-size: 14px;
            padding : 8px;
        }

    </style>
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
						
						<table id="table">
						    <thead>
						        <tr>
						            <th>名称</th>
						            <th>类型</th>
						            <th>URL路径</th>
						            <th>权限字符串</th>
						            <th>操作</th>
						        </tr>
						    </thead>
						    <tbody>
						        <#list resourceList as resource>
						            <tr data-tt-id='${resource.id}' <#if !resource.rootNode>data-tt-parent-id='${resource.parentId}'</#if>>
						                <td>${resource.name}</td>
						                <td>${resource.type.info}</td>
						                <td>${resource.url}</td>
						                <td>${resource.permission}</td>
						                <td>
						                    <@shiro.hasPermission name="system:resource:create">
						                        <#if resource.type != 'button'>
						                        <a href="${request.contextPath}/resource/${resource.id}/appendChild">添加子节点</a>
						                        </#if>
						                    </@shiro.hasPermission>
						
						                    <@shiro.hasPermission name="system:resource:update">
						                        <a href="${request.contextPath}/resource/${resource.id}/update">修改</a>
						                    </@shiro.hasPermission>
						                    <#if !resource.rootNode>
						
						                    <@shiro.hasPermission name="system:resource:delete">
						                        <a class="deleteBtn" href="#" data-id="${resource.id}">删除</a>
						                    </@shiro.hasPermission>
						                    </#if>
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
<script>
    $(function() {
        $("#table").treetable({ expandable: true }).treetable("expandNode", 1);
        $(".deleteBtn").click(function() {
            if(confirm("确认删除吗?")) {
                location.href = "${request.contextPath}/resource/"+$(this).data("id")+"/delete";
            }
        });
    });
</script> 