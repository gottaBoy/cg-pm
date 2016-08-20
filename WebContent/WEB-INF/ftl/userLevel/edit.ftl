<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]/>
<#include "/constants.ftl"> 
<@i.tHtml>
 
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">员工层级${op}</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row --> 
			    <@form.form method="post" commandName="level" role="form" >
			        <@form.hidden path="levelId"/>
                <div class="row">
                    <div class="col-lg-6"> 
					        <div class="form-group">
					            <@form.label path="levelName">名称：</@form.label>
					            <@form.input path="levelName" class="form-control"/>
					        </div>
					
					        <div class="form-group">
					            <@form.label path="levelCode">编号：</@form.label>
					            <@form.input path="levelCode" class="form-control"/>
					        </div> 
					 
                    </div>
                    <!-- /.col-lg-6 -->
                    <div class="col-lg-6">  
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