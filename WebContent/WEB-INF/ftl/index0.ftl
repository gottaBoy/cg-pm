<html>
<head>
    <title>Shiro综合案例</title>
    <link rel="stylesheet" href="${request.contextPath}/static/css/layout-default-latest.css">
</head>
<body>

<iframe name="content" class="ui-layout-center"
        src="${request.contextPath}/welcome" frameborder="0" scrolling="auto"></iframe>
<div class="ui-layout-north">欢迎[<@shiro.principal/>]学习Shiro综合案例，<a href="${request.contextPath}/logout">退出</a></div>
<div class="ui-layout-south">
    获取源码：<a href="https://github.com/zhangkaitao/shiro-example" target="_blank">https://github.com/zhangkaitao/shiro-example</a>
</div>
<div class="ui-layout-west">
    功能菜单<br/>
    <#list menus as m>
        <a href="${request.contextPath}${m.url}" target="content">${m.name}</a><br/>
    </#list>
</div>


<script src="${request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
<script src="${request.contextPath}/static/js/jquery.layout-latest.min.js"></script>
<script>
    $(function () {
        $(document).ready(function () {
            $('body').layout({ applyDemoStyles: true });
        });
    });
</script>
</body>
</html>