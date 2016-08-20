<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]/>
<#include "/constants.ftl"> 
<@i.tHtml>
 
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">项目成本</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row --> 
			    <@form.form method="post" commandName="project" role="form" > 
                <div class="row">
                    <div class="col-lg-3"> 
					        <div class="form-group">
					            <@form.label path="projectName">名称：</@form.label>
					            <@form.input path="projectName" class="form-control"/>
					        </div> 
					        
					        <div class="form-group">
					            <@form.label path="totalExpenses">总费用：</@form.label>
					            <@form.input path="totalExpenses" class="form-control"/>
					        </div>
					        <div class="form-group">
					            <@form.label path="totalCosts">总工时成本：</@form.label>
					            <@form.input path="totalCosts" class="form-control"/>
					        </div>
					        <div class="form-group">
					            <@form.label path="totalAll">总成本：</@form.label>
					            <@form.input path="totalAll" class="form-control"/>
					        </div>
					
                    </div>
                    <!-- /.col-lg-3 --> 
                    
                    <div class="col-lg-3">  
					        <div class="form-group">
					            <@form.label path="projectCode">编号：</@form.label>
					            <@form.input path="projectCode" class="form-control"/>
					        </div>
					        
					        <div class="form-group">
					            <@form.label path="confirmedExpenses">已确认费用：</@form.label>
					            <@form.input path="confirmedExpenses" class="form-control"/>
					        </div>
					
					        <div class="form-group">
					            <@form.label path="confirmedCosts">已确认成本：</@form.label>
					            <@form.input path="confirmedCosts" class="form-control"/>
					        </div>
					        <div class="form-group">
					            <@form.label path="totalConfirmed">已确认总成本：</@form.label>
					            <@form.input path="totalConfirmed" class="form-control"/>
					        </div>
					
					 
                    </div>
                    <!-- /.col-lg-3 --> 
                    
                    <div class="col-lg-3">  
					        <div class="form-group">
					            <@form.label path="budget">预算：</@form.label>
					            <@form.input path="budget" class="form-control"/>
					        </div>  
					        
					        <div class="form-group">
					            <@form.label path="pendingExpenses">待审批费用：</@form.label>
					            <@form.input path="pendingExpenses" class="form-control"/>
					        </div>  
					        <div class="form-group">
					            <@form.label path="pendingCosts">待审批成本：</@form.label>
					            <@form.input path="pendingCosts" class="form-control"/>
					        </div>  
					        <div class="form-group">
					            <@form.label path="totalPending">待审批总成本：</@form.label>
					            <@form.input path="totalPending" class="form-control"/>
					        </div>  
                    </div>
                    <!-- /.col-lg-3 --> 
                    
                    <div class="col-lg-3">  
					        <div class="form-group">
					            <@form.label path="totalAll">总成本：</@form.label>
					            <@form.input path="totalAll" class="form-control"/>
					        </div>  
					        
					        <div class="form-group">
					            <@form.label path="createdExpenses">待提交费用：</@form.label>
					            <@form.input path="createdExpenses" class="form-control"/>
					        </div>  
					        <div class="form-group">
					            <@form.label path="createdCosts">待提交成本：</@form.label>
					            <@form.input path="createdCosts" class="form-control"/>
					        </div>  
					        <div class="form-group">
					            <@form.label path="totalCreated">待提交总成本：</@form.label>
					            <@form.input path="totalCreated" class="form-control"/>
					        </div>  
                    </div>
                    <!-- /.col-lg-3 --> 
                </div>
                <!-- /.row -->
                    
                <div class="row"> 
                	<div class="col-lg-12">  
					<@form.button class="btn btn-default" onclick="javascript:history.back();">返回</@form.button>
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