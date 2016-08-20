<#include "/constants.ftl"> 
<@i.tHtml>
 
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Blank</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row --> 
                <div class="row">
                    <div class="col-lg-3 col-md-4">
                    	
                    	<!-- /.panel -->
	                    <div class="panel panel-default">
	                        <div class="panel-heading">
	                            <i class="fa fa-group fa-fw"></i> 组织结构
	                        </div>
	                        <div class="panel-body">
	                            <ul id="tree" class="ztree"></ul> 
	                        </div>
	                        <!-- /.panel-body -->
	                    </div>
                    	 
                    </div>
                    <div class="col-lg-9 col-md-8">
                        <!-- /.panel -->
	                    <div class="panel panel-default">
	                        <div class="panel-heading">
	                            <i class="fa fa-edit fa-fw"></i> 组织结构编辑
	                        </div>
	                        <div class="panel-body" style="height:350px">
	                        	<iframe name="divOrgMaintain" style="width:100%;height:100%;" frameborder="0" seamless="seamless"></iframe>
	                        </div>
	                        <!-- /.panel-body -->
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
    $(function () {
        var setting = {
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback : {
                onClick : function(event, treeId, treeNode) {  
                    window.open("${request.contextPath}/organization/"+treeNode.id+"/maintain",'divOrgMaintain','');
                }
            }
        };

        var zNodes =[
            <#list organizationList as o>
                { id:${o.id}, pId:${o.parentId}, name:"${o.name}", open:${o.rootNode?string('true','false')}},
            </#list>
        ];

        $(document).ready(function(){
            $.fn.zTree.init($("#tree"), setting, zNodes);
        });
    });
</script>