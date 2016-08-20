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
                     <div class="col-lg-12">
                        <div class="panel panel-red">
	                        <div class="panel-heading">
	                            	没有权限
	                        </div>
	                        <div class="panel-body">
	                            <p>您没有权限[${exception.message}]</p>
	                        </div>
	                        <div class="panel-footer">
	                            Panel Footer
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
 