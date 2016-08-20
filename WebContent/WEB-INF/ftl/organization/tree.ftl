<html>
<head>
    <link rel="stylesheet" href="${request.contextPath}/static/JQueryzTreev3.5.15/css/zTreeStyle/zTreeStyle.css">
</head>
<body>

<ul id="tree" class="ztree"></ul>

<script src="${request.contextPath}/static/js/jquery-1.11.0.min.js"></script>
<script src="${request.contextPath}/static/JQueryzTreev3.5.15/js/jquery.ztree.all-3.5.min.js"></script>
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
                    parent.frames['content'].location.href = "${request.contextPath}/organization/"+treeNode.id+"/maintain";
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
</body>
</html>