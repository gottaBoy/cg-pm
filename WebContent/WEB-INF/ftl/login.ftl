<#include "/constants.ftl"> 
<!DOCTYPE html>
<html lang="zh-CN"> 
<head> 
	<@i.tHeadBox /><#--head标签模块引入 -->
</head>
<body>  

    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">登录</h3><div class="uberror">${error}</div>
                    </div>
                    <div class="panel-body">
                        <form role="form" method="post">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="用户名" name="username" type="text" value="<@shiro.principal/>" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="密码" name="password" type="password" value="">
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input name="rememberMe" type="checkbox" value="true">Remember Me
                                    </label>
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <a href="#" class="btn btn-lg btn-success btn-block" onclick="javascript:ublogin();">登录</a>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
 

<@i.tJSBox /><#--head标签模块引入 -->

	
<script>
function ublogin() {
	$("form").submit();
}    
</script>


</body>
</html>