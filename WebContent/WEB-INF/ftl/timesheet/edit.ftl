<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]/>
<#include "/constants.ftl"> 
<@i.tHtml>
 
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">工时填报</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row --> 
                <div class="row">    
                	<div class="col-lg-12">

						<#if msg?exists>
						    <div class="message">${msg}</div>
						</#if>
						
                    </div> 
		                <!-- /.col-lg-12 --> 
                </div>
                <!-- /.row --> 
			    <@form.form method="get" commandName="timesheet" role="form" > 
                <div class="row">
                    <div class="col-lg-12"> 
					        <div class="form-group">
					            <@form.label path="tsDate">选择填报周的任意一天：</@form.label>
					            <@form.input path="tsDate" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',firstDayOfWeek:1})"/>
					        </div>
					 		<div class="form-group">
					        	<@form.button class="btn btn-default">查询</@form.button>
					        </div>
                    </div>
                    <!-- /.col-lg-12 -->  
                </div>
                <!-- /.row --> 
				</@form.form>
				
                <div class="row">
                    <div class="col-lg-12">  
					 		<div class="form-group">
					        	
								<#if Request["tableJson"]?exists>
								<#assign tableJson = Request["tableJson"]>
								<#if Request["cols"]?exists>
								<#assign tableCols = Request["cols"]>
								
								<form id="fm2" method="post" > 
							         		<input type="hidden" id="updated" name="updated"/> 					  
								<table id="dg2" class="easyui-datagrid" title="项目工时填报" style="width:650px;height:auto"
										data-options="
											iconCls: 'icon-edit',
							                singleSelect: true, 
							                onClickRow: onClickRow, 
							                toolbar:'#toolbar',
							                idField: 'colid'">
									<thead>
										<tr> 
											<th data-options="field:'col0',width:100"></th>
											<th data-options="field:'colm0',width:100,editor:'numberbox',
												formatter:function(value,row){
													return showStatusColor(row.cols0,row.colm0);
							                    }">${tableCols[0]}</th>
											<th data-options="field:'colm1',width:100,editor:'numberbox',
												formatter:function(value,row){
													return showStatusColor(row.cols1,row.colm1);
							                    }">${tableCols[1]}</th>
											<th data-options="field:'colm2',width:100,editor:'numberbox',
												formatter:function(value,row){
													return showStatusColor(row.cols2,row.colm2);
							                    }">${tableCols[2]}</th>
											<th data-options="field:'colm3',width:100,editor:'numberbox',
												formatter:function(value,row){
													return showStatusColor(row.cols3,row.colm3);
							                    }">${tableCols[3]}</th>
											<th data-options="field:'colm4',width:100,editor:'numberbox',
												formatter:function(value,row){
													return showStatusColor(row.cols4,row.colm4);
							                    }">${tableCols[4]}</th>
										</tr>
									</thead>
								</table>  
								</form>
								*红色已经审批，蓝色待审批
								<div id="toolbar"> 
									<div> 
										<a href="javascript:void(0)" class="easyui-linkbutton"
											iconCls="icon-save" plain="true"
											onclick="save();">保存</a>
									 	<a href="javascript:void(0)" class="easyui-linkbutton"
											iconCls="icon-save" plain="true"
											onclick="pending();">提交</a>
									</div>
								</div>  
								
									
								
								</#if>
								</#if> 	
					        	
					        	
					        </div>
                    </div>
                    <!-- /.col-lg-12 -->  
                </div>
                <!-- /.row --> 
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->        


	
</@i.tHtml>  


<link rel="stylesheet" type="text/css" href="${STATIC_LIBS}/jquery-easyui/1.4.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${STATIC_LIBS}/jquery-easyui/1.4.5/themes/icon.css"> 
<link rel="stylesheet" type="text/css" href="${STATIC_LIBS}/jquery-easyui/1.4.5/demo/demo.css">

<script type="text/javascript" src="${STATIC_LIBS}/jquery-easyui/1.4.5/jquery.easyui.min.js"></script> 
<script type="text/javascript" src="${STATIC_LIBS}/jquery-easyui/1.4.5/locale/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript" src="${request.contextPath}/static/js/json2.js"></script> 

<#if Request["tableJson"]?exists>
								<script>
					
								var editIndex = undefined;
							    function endEditing(){
							        if (editIndex == undefined){return true}
							        if ($('#dg2').datagrid('validateRow', editIndex)){
							        	$('#dg2').datagrid('endEdit', editIndex);
							            editIndex = undefined;
							            return true;
							        } else {
							            return false;
							        }
							    }
							    function onClickRow(index){
							        if (editIndex != index){
							            if (endEditing()){
							                $('#dg2').datagrid('selectRow', index)
							                        .datagrid('beginEdit', index);
							                editIndex = index;
							            } else {
							                $('#dg2').datagrid('selectRow', editIndex);
							            }
							        }
							    } 
							    
							    function beforeSubmit() {
							    	endEditing();  
										$('#updated').attr("value","");
									var effectRow = new Object();
									if ($('#dg2').datagrid('getChanges').length) { 
										var updated = $('#dg2').datagrid('getChanges', "updated");  
										if (updated.length) {
											var istr = encodeURI(JSON.stringify(updated));
											$('#updated').attr("value",istr);
										}
									}
							    }
					     
					
					
								$(document).ready(function(){  
									$('#dg2').datagrid({data:${tableJson}});  	 
								});

function showStatusColor(tsStatus,tsHour) {
	if (tsStatus == 'confirmed')
		return '<font color="red">'+tsHour+'</font>';
	if (tsStatus == 'pending')
		return '<font color="blue">'+tsHour+'</font>';
	return tsHour;
}
								
//保存
function save() { 
	beforeSubmit();  
	$('#fm2').submit();
}

function pending() { 
	beforeSubmit();  
	$('#fm2').attr('action','${request.contextPath}/timesheetcreate/pending?tsDate=${timesheet.tsDate?string("yyyy-MM-dd")}');
	$('#fm2').submit();
}
								</script>
</#if> 	