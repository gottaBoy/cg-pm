<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]/>
<#include "/constants.ftl"> 
<@i.tHtml>
 
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">员工成本设定</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row --> 
			    <@form.form method="post" commandName="userCost" role="form" >
			        <@form.hidden path="userId"/>
			        <input type="hidden" id="chgSheetCost" name="chgSheetCost" value="false"/>
                <div class="row">
                    <div class="col-lg-6"> 
					        <div class="form-group">
					            <label>姓名：</label>
            					${userObj.realname}
					        </div> 
					        <div class="form-group">
					            <@form.label path="cost">成本：</@form.label>
					            <@form.input path="cost" class="form-control"/>
					        </div>
                    </div>
                    <!-- /.col-lg-6 -->
                    <div class="col-lg-6">  
					        <div class="form-group">
					            <label>登录名：</label>
            					${userObj.username}
					        </div>  
					        <div class="form-group">
					            <@form.label path="beginDate">生效日期：</@form.label>
					            <@form.input path="beginDate" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					        </div> 
                    </div>
                    <!-- /.col-lg-6 --> 
                </div>
                <!-- /.row --> 
                 
                <div class="row"> 
                	<div class="col-lg-12">  
					<button class="btn btn-default" onclick="javascript:submitNoChg();">设定成本/不修改已经填报工时成本</button>
					<button class="btn btn-default" onclick="javascript:submitWithChg();">设定成本/修改已经填报工时成本</button>
					</div>
                    <!-- /.col-lg-12 --> 
                </div>
                <!-- /.row -->
                <!-- /.row --> 
                <div class="row">    
                	<div class="col-lg-12">
						<div class="panel-group" id="accordion">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">历史记录</a>
                                    </h4>
                                </div>
                                <div id="collapseOne" class="panel-collapse collapse in">
                                    <div class="panel-body">
                                        <table id="dt_11" width="100%" class="table table-striped table-bordered table-hover">
										    <thead>
										        <tr>
										            <th>生效时间</th>
										            <th>成本</th> 
										        </tr>
										    </thead> 
										    <tbody>
										    <#list userCosts as cl>
										    	<tr>
										            <td>${(cl.beginDate?string('yy.MM.dd'))!}</td>
										            <td>${cl.cost}</td> 
										        </tr>
										    </#list>
										    </tbody>
										</table>
                                    </div>
                                </div>
                            </div> 
                        </div> 
						 
                    </div> 
		                <!-- /.col-lg-12 --> 
                </div>
				</@form.form>
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->             
<script>
function submitNoChg() {
	$('#chgSheetCost').val("false");
	$('#userCost').submit();
}
function submitWithChg() {
	$('#chgSheetCost').val("true");
	$('#userCost').submit();
}
</script>  

</@i.tHtml>  