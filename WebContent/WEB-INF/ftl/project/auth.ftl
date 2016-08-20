<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]/>
<#include "/constants.ftl"> 
<@i.tHtml>
 
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">项目授权</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row --> 
			    <@form.form method="post" commandName="project" role="form" >
			        <@form.hidden path="projectId"/>
                <div class="row">
                    <div class="col-lg-6"> 
					        <div class="form-group">
					            <@form.label path="projectName">名称：</@form.label>
					            <@form.input path="projectName" class="form-control"/>
					        </div> 
                    </div>
                    <!-- /.col-lg-6 -->
                    <div class="col-lg-6">  
					        <div class="form-group">
					            <@form.label path="projectCode">编号：</@form.label>
					            <@form.input path="projectCode" class="form-control"/>
					        </div>  
                    </div>
                    <!-- /.col-lg-6 --> 
                </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-lg-12"> 
					        <div class="form-group">
					            <@form.label path="userIds">用户列表：</@form.label>
					            <@form.select path="userIds" multiple="true" class="form-control">
								    <#list userList as user>
								       <@form.option value="${user.id}" label="${user.realname}" />
								    </#list>
								</@form.select>  
					            (按住shift键多选)
					        </div>
                    </div>
                    <!-- /.col-lg-6 --> 
                </div>
                <!-- /.row -->
                 
                <div class="row"> 
                	<div class="col-lg-12">  
					<@form.button class="btn btn-default">授权</@form.button>
					</div>
                    <!-- /.col-lg-12 --> 
                </div>
                <!-- /.row -->
				</@form.form>
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->        

</@i.tHtml>  