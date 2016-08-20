<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]/>
<#include "/constants.ftl"> 
<@i.tHtml>
 
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">费用报销表上传</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row --> 
                <div class="row">
                    <div class="col-lg-12">
						<#if msg?exists>
						    <div class="message">${msg}</div>
						</#if>
					    <form role="form" method="post" enctype="multipart/form-data"> 
					
					        <div class="form-group">
					            <label>File input</label>
                                <input type="file" name="myfile">
					        </div> 
							<a href="#" class="btn btn-default" onclick="javascript:urupload();">上传</a> 
					
					    </form>

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
function urupload() {
	$("form").submit();
}    
</script>
