<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]/>
<#include "/constants.ftl"> 
<@i.tHtml>
 
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">资源修改</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row --> 
                <div class="row">
                    <div class="col-lg-12">

					    <@form.form method="post" commandName="resource" role="form">
					        <@form.hidden path="id"/>
					        <@form.hidden path="available"/>
					        <@form.hidden path="parentId"/>
					        <@form.hidden path="parentIds"/>
					
					        <#if parent?exists>
					            <div class="form-group">
					                <label>父节点名称：</label>
					                ${parent.name}
					            </div>
					        </#if>
					
					        <div class="form-group">
					            <@form.label path="name"><#if parent?exists>子</#if>名称：</@form.label>
					            <@form.input path="name" class="form-control"/>
					        </div>
					        <div class="form-group">
					            <@form.label path="type">类型：</@form.label>
					            <@form.select path="type" class="form-control">
								    <#list types as type>
								       <@form.option value="${type}" label="${type.info}" />
								    </#list>
								</@form.select> 
					        </div>
					
					        <div class="form-group">
					            <@form.label path="url">URL路径：</@form.label>
					            <@form.input path="url" class="form-control"/>
					        </div>
					
					
					        <div class="form-group">
					            <@form.label path="permission">权限字符串：</@form.label>
					            <@form.input path="permission" class="form-control"/>
					        </div>
					
					        <@form.button class="btn btn-default">${op}</@form.button>
					
					    </@form.form>

                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->        

</@i.tHtml> 