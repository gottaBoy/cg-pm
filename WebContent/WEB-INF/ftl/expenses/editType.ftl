<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]/>
<#include "/constants.ftl"> 
<@i.tHtml>
 
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">费用类型${op}</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row --> 
			    <@form.form method="post" commandName="expensesType" role="form" >
			        <@form.hidden path="typeId"/>
                <div class="row">
                    <div class="col-lg-12"> 
					        <div class="form-group">
					            <@form.label path="typeName">名称：</@form.label>
					            <@form.input path="typeName" class="form-control"/>
					        </div>
					 
					 
                    </div>
                    <!-- /.col-lg-12 --> 
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