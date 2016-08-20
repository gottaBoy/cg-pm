<#include "/constants.ftl"> 
<@i.tHtml>
	 
		<!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">项目设置</h1>
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
                <div class="row">    
                	<div class="col-lg-12"> 
						<div class="panel panel-default">
	                        <div class="panel-body">
	                        	<p>
	                        	<@shiro.hasPermission name="system:project:create">
	                        		<a href="${request.contextPath}/project/create" class="btn btn-default" >新增项目</a>
	                            </@shiro.hasPermission>
	                            </p>
	                        	<div class="dataTable_wrapper">
		                        	<table id="dt_1" width="100%" class="table table-striped table-bordered table-hover">
									    <thead>
									        <tr>
									        	<th>操作</th> 
									            <th>编号</th>
									            <th>名称</th>
									            <th>负责人</th>
									            <th>开始时间</th>
									            <th>结束时间</th>
									            <th>预算</th> 
									            <th>备注</th> 
									        </tr>
									    </thead> 
									</table>
								</div>
                            	<!-- /.table-responsive -->	
	                        </div>
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
<script>
	var dataTable_config = {//jquery_datatable插件的默认参数皮质
        //"bStateSave": true, //是否把获得数据存入cookie

        //"bLengthChange":false, //关闭每页显示多少条数据
        "bProcessing": true,
        //"bAutoWidth":true,//自动宽度
        "bServerSide": true,
        "responsive": true,
        "sAjaxSource": "${request.contextPath}/project/j_list",
        "fnServerData":retrieveData,
        "bPaginate": true,  //是否分页。
        "bFilter": false,
        "sPaginationType": "full_numbers",      //分页样式
        "pageLength": 10,
        "columns" : [ {
            "data" : "projectId",
        }, {
            "data" : "projectCode"
        }, {
            "data" : "projectName"
        }, {
            "data" : "realname"
        }, {
        
            "data" : "beginDate",
            "type" : "date",
            "render": $.fn.dataTable.render.moment( 'YYYY-MM-DD HH:mm:ss', 'YY-MM-DD' )
        }, {
            "data" : "endDate",
            "type" : "date",
            "render": $.fn.dataTable.render.moment( 'YYYY-MM-DD HH:mm:ss', 'YY-MM-DD' )
        }, {
            "data" : "budget"
        }, {
            "data" : "memo"
        }, ],
        "lengthMenu": [
            [10, 15, 20],
            [10, 15, 20] // 更改每页显示记录数
        ],

        "language": {
            "lengthMenu": "  _MENU_ 条数据",
            "emptyTable": "表格中没有数据~",
            "info": "显示 _START_ 到 _END_ 条数据 共 _TOTAL_ 条数据",
            "infoEmpty": "没有数据~",
            "infoFiltered": "(在 _MAX_ 条数据中查询)",
            "lengthMenu": "显示 _MENU_ 条数据",
            "search": "查询:",
            "zeroRecords": "没有找到对应的数据",
            "paginate": {
                "previous":"上一页",
                "next": "下一页",
                "last": "末页",
                "first": "首页"
            }
        },
        "columnDefs": [
        {
         sDefaultContent: '',
         aTargets: [ '_all' ]
          }
        ,{  // 设置默认值
            'orderable': false,
            aTargets: [0],
        },
        {  aTargets: [0],
            "render": function ( data, type, full, meta ) {
            	var rh = '';
            	rh += ' <a href="${request.contextPath}/reportproject/'+data+'/view">查看</a>';
            	return rh;
            }
           },
        ],
        "order": [
            [1, "asc"]
        ] 
    };

	oTable = $('#dt_1').dataTable(dataTable_config);

	function retrieveData( sSource, aoData, fnCallback ) {     
        //查询条件称加入参数数组     
        //var rentRuleId =document.getElementById('rentRuleId').value;  
        //alert(rentRuleId);
        $.ajax( {     
            type: "POST",
            url: sSource,   
            dataType:"json",  
            data: "dtParaJson="+JSON.stringify(aoData),  
            success: function(data) {   
               //$("#url_sortdata").val(data.aaData);  
                fnCallback(data); //服务器端返回的对象的returnObject部分是要求的格式     
            }     
        });    
    }  
</script> 