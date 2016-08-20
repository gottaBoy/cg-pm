<#include "/constants.ftl">
<#macro tHtml>
<!DOCTYPE html>
<html lang="zh-CN"> 
<head> 
	<@tHeadBox /><#--head标签模块引入 -->
</head>
<body>  
	
	<div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${request.contextPath}/">Cargocn内部工时、费用管理</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
                 
                  
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                         
                        <li><a href="${request.contextPath}/logout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu"> 
                    	<@shiro.hasPermission name="cost:view">
                        <li>
                            <a href="#"><i class="fa fa-wrench fa-fw"></i> 成本填报<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level"> 
                        		<@shiro.hasPermission name="cost:expenses:view">
                            	<li>
                                    <a href="${request.contextPath}/expensescreate">费用填报</a>
                                </li> 
                        		</@shiro.hasPermission> 
                        		<@shiro.hasPermission name="cost:timesheet:create">
                            	<li>
                                    <a href="${request.contextPath}/timesheetcreate">工时填报</a>
                                </li> 
                        		</@shiro.hasPermission> 
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        </@shiro.hasPermission> 
                    	<@shiro.hasPermission name="approve:view">
                        <li>
                            <a href="#"><i class="fa fa-wrench fa-fw"></i> 成本审批<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level"> 
                        		<@shiro.hasPermission name="approve:expenses:view">
                            	<li>
                                    <a href="${request.contextPath}/expensesapprove">费用审批</a>
                                </li> 
                        		</@shiro.hasPermission> 
                        		<@shiro.hasPermission name="approve:timesheet:view">
                            	<li>
                                    <a href="${request.contextPath}/timesheetapprove">工时审批</a>
                                </li> 
                        		</@shiro.hasPermission> 
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        </@shiro.hasPermission> 
                    	<@shiro.hasPermission name="report:view">
                        <li>
                            <a href="#"><i class="fa fa-wrench fa-fw"></i> 报表统计<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level"> 
                        		<@shiro.hasPermission name="report:project:view">
                            	<li>
                                    <a href="${request.contextPath}/reportproject">项目成本</a>
                                </li> 
                        		</@shiro.hasPermission>  
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        </@shiro.hasPermission> 
                    	<@shiro.hasPermission name="system:view">
                        <li>
                            <a href="#"><i class="fa fa-wrench fa-fw"></i> 系统设置<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level"> 
                        		<@shiro.hasPermission name="system:organization:view">
                            	<li>
                                    <a href="${request.contextPath}/organization">组织结构管理</a>
                                </li> 
                        		</@shiro.hasPermission>
                        		<@shiro.hasPermission name="system:user:view">
                                <li>
                                    <a href="${request.contextPath}/user">用户管理</a>
                                </li> 
                        		</@shiro.hasPermission>
                        		<@shiro.hasPermission name="system:resource:view">
                                <li>
                                    <a href="${request.contextPath}/resource">资源管理</a>
                                </li> 
                        		</@shiro.hasPermission>
                        		<@shiro.hasPermission name="system:role:view">
                                <li>
                                    <a href="${request.contextPath}/role">角色管理</a>
                                </li> 
                        		</@shiro.hasPermission>
                        		<@shiro.hasPermission name="system:project:view">
                                <li>
                                    <a href="${request.contextPath}/project">项目设定</a>
                                </li> 
                        		</@shiro.hasPermission>
                        		<@shiro.hasPermission name="system:level:view">
                                <li>
                                    <a href="${request.contextPath}/level">员工层级设定</a>
                                </li> 
                        		</@shiro.hasPermission>
                        		<@shiro.hasPermission name="system:expensestype:view">
                                <li>
                                    <a href="${request.contextPath}/expensestype">费用类型设定</a>
                                </li> 
                        		</@shiro.hasPermission>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        </@shiro.hasPermission>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>

        <!-- Page Content --> 
        <#nested>
              

    </div>
    <!-- /#wrapper --> 
	<!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<@tJSBox /><#--js标签模块引入 -->
	
</body>
</html> 
</#macro> 
  

<#-- 首页顶部Head栏模块 --> 
<#macro tHeadBox>
	
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>管理后台</title>

    <!-- Bootstrap Core CSS -->
    <link href="${STATIC_LIBS}/startbootstrap-sb-admin-2/1.0.8/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="${STATIC_LIBS}/startbootstrap-sb-admin-2/1.0.8/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">
	
	<!-- DataTables CSS -->
    <link href="${STATIC_LIBS}/datatables-plugins/1.10.11/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="${STATIC_LIBS}/datatables-responsive/2.0.2/css/responsive.dataTables.css" rel="stylesheet">
    
    <!-- Timeline CSS -->
    <link href="${STATIC_LIBS}/startbootstrap-sb-admin-2/1.0.8/dist/css/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${STATIC_LIBS}/startbootstrap-sb-admin-2/1.0.8/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="${STATIC_LIBS}/startbootstrap-sb-admin-2/1.0.8/bower_components/morrisjs/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="${STATIC_LIBS}/startbootstrap-sb-admin-2/1.0.8/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="${STATIC_LIBS}/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="${STATIC_LIBS}/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <!--<link rel="stylesheet" href="${STATIC_LIBS}/bootstrap-chosen/1.0.1/bootstrap-chosen.css"> -->
    
    <link rel="stylesheet" href="${STATIC_LIBS}/select2/4.0.2/dist/css/select2.min.css">
    <link rel="stylesheet" href="${STATIC_LIBS}/select2-bootstrap-theme/0.1.0-beta.4/dist/select2-bootstrap.min.css">
      
    <link rel="stylesheet" href="${STATIC_LIBS}/JQueryzTree/3.5.15/css/zTreeStyle/zTreeStyle.css">
    
    <link rel="stylesheet" href="${STATIC_LIBS}/jquery-treetable/3.1.0/stylesheets/jquery.treetable.css">
    <link rel="stylesheet" href="${STATIC_LIBS}/jquery-treetable/3.1.0/stylesheets/jquery.treetable.theme.default.css">

<style type="text/css">
table.dataTable.select tbody tr,
table.dataTable thead th:first-child {
  cursor: pointer;
}

table.dataTable tbody tr.selected {
  background-color: #B0BED9;
}
</style> 

</#macro> 

<#-- 首页尾部JS模块 --> 
<#macro tJSBox>
	
	<!-- jQuery -->
    <script src="${STATIC_LIBS}/startbootstrap-sb-admin-2/1.0.8/bower_components/jquery/dist/jquery.min.js"></script>
	<!-- <script src="${STATIC_LIBS}/jquery/1.11.3/jquery-1.11.3.min.js"></script> -->
	
    <!-- Bootstrap Core JavaScript -->
    <script src="${STATIC_LIBS}/startbootstrap-sb-admin-2/1.0.8/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    
    <!-- DataTables JavaScript -->
    <script src="${STATIC_LIBS}/datatables/1.10.11/media/js/jquery.dataTables.min.js"></script>
    <script src="${STATIC_LIBS}/datatables-plugins/1.10.11/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
    <script src="${STATIC_LIBS}/datatables-responsive/2.0.2/js/dataTables.responsive.js"></script>
    <script src="${STATIC_LIBS}/datatables-plugins/1.10.11/dataRender/datetime.js"></script>
    <script src="${STATIC_LIBS}/Moment.js/2.12.0/moment.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="${STATIC_LIBS}/startbootstrap-sb-admin-2/1.0.8/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Morris Charts JavaScript -->
    <script src="${STATIC_LIBS}/startbootstrap-sb-admin-2/1.0.8/bower_components/raphael/raphael-min.js"></script>
    <script src="${STATIC_LIBS}/startbootstrap-sb-admin-2/1.0.8/bower_components/morrisjs/morris.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="${STATIC_LIBS}/startbootstrap-sb-admin-2/1.0.8/dist/js/sb-admin-2.js"></script>  	
    
    <!-- <script src="${STATIC_LIBS}/chosen/1.5.1/chosen.jquery.min.js"></script>   -->
    
    <script src="${STATIC_LIBS}/select2/4.0.2/dist/js/select2.full.min.js"></script>  
    
	<script src="${STATIC_LIBS}/JQueryzTree/3.5.15/js/jquery.ztree.all-3.5.min.js"></script>
	
	<script src="${STATIC_LIBS}/jquery-treetable/3.1.0/javascripts/src/jquery.treetable.js"></script>
	
	<script src="${STATIC_LIBS}/My97DatePicker/WdatePicker.js"></script>

</#macro> 