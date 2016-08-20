<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]/>
<#include "/constants.ftl"> 
<@i.tHtml>
 
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">项目${op}</h1>
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
					
					        <div class="form-group">
					            <@form.label path="projectCode">编号：</@form.label>
					            <@form.input path="projectCode" class="form-control"/>
					        </div>
					
					        <div class="form-group">
					            <@form.label path="budget">预算：</@form.label>
					            <@form.input path="budget" class="form-control"/>
					        </div>
					        
					 
                    </div>
                    <!-- /.col-lg-6 -->
                    <div class="col-lg-6"> 
					        <div class="form-group">
					            <@form.label path="beginDate">开始日期：</@form.label>
					            <@form.input path="beginDate" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					        </div> 
					        <div class="form-group">
					            <@form.label path="endDate">结束日期：</@form.label>
					            <@form.input path="endDate" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					        </div>  
					        <div class="form-group">
					            <@form.label path="memo">备注：</@form.label>
					            <@form.input path="memo" class="form-control"/>
					        </div>
                    </div>
                    <!-- /.col-lg-6 --> 
                </div>
                <!-- /.row -->
                
                <!-- /.row --> 
                <div class="row"> 
                	<div class="col-lg-12">  
					<@form.button class="btn btn-default">${op}</@form.button>
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