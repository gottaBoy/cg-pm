<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]/>
<#include "/constants.ftl"> 
<@i.tHtml>
 
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">费用填报${op}</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row --> 
			    <@form.form method="post" commandName="expenses" role="form" >
			        <@form.hidden path="expensesId"/>
                <div class="row">
                    <div class="col-lg-6"> 
					        <div class="form-group">
					            <@form.label path="applyDate">申请日期：</@form.label>
					            <@form.input path="applyDate" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					        </div>
					        <div class="form-group">
					            <@form.label path="invoiceDate">发票日期：</@form.label>
					            <@form.input path="invoiceDate" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					        </div>
					        <div class="form-group">
					            <@form.label path="invoiceAmount">发票金额：</@form.label>
					            <@form.input path="invoiceAmount" class="form-control"/>
					        </div>
					
					        <div class="form-group">
					            <@form.label path="applyAmount">报销金额：</@form.label>
					            <@form.input path="applyAmount" class="form-control"/>
					        </div>
					         
					        <div class="form-group">
					            <@form.label path="memo">备注：</@form.label>
					            <@form.input path="memo" class="form-control"/>
					        </div>
					 
                    </div>
                    <!-- /.col-lg-6 -->
                    <div class="col-lg-6"> 
					        <div class="form-group">
					            <@form.label path="applyDate2">申请日期2：</@form.label>
					            <@form.input path="applyDate2" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					        </div>
					
					        <div class="form-group">
					            <@form.label path="applyContent">申请事由：</@form.label>
					            <@form.input path="applyContent" class="form-control"/>
					        </div> 
					        <div class="form-group">
					            <@form.label path="expensesType">费用类型：</@form.label>
					            <@form.select path="expensesType" class="form-control">
								    <#list expensesTypeList as et>
								       <@form.option value="${et.typeId}" label="${et.typeName}" />
								    </#list>
								</@form.select>  
					        </div> 
					        <div class="form-group">
					            <@form.label path="projectId">所属项目：</@form.label>
					            <@form.select path="projectId" class="form-control">
								    <#list projectList as pl>
								       <@form.option value="${pl.projectId}" label="${pl.projectName}" />
								    </#list>
								</@form.select>  
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