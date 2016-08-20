<#include "/constants.ftl"> 
<!DOCTYPE html>
<html lang="zh-CN"> 
<head>
    <@i.tHeadBox /><#--head标签模块引入 -->
    <style>
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:200px;overflow-y:scroll;overflow-x:auto;}
    </style>
</head>
<body>

    <form id="form" method="post" role="form">
        <div class="form-group">
            <label>源节点名称：</label>
            ${source.name}
        </div>

        <div class="form-group">
            <label>目标节点名称：</label>
            <input type="text" id="targetName" readonly="true" class="form-control"/>
            <input type="hidden" id="targetId" name="targetId"/>
            <a id="menuBtn" href="#">选择</a>
        </div>

        <input type="submit" value="移动" class="btn btn-default"/>
    </form>

    <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
        <ul id="tree" class="ztree" style="margin-top:0; width:160px;"></ul>
    </div>

    <@i.tJSBox />
    
    <script>
        $(function () {
            var setting = {
                view: {
                    dblClickExpand: false
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onClick: onClick
                }
            };

            var zNodes =[
                <#list targetList as o>
                { id:${o.id}, pId:${o.parentId}, name:"${o.name}", open:${o.rootNode?string('true','false')}},
                </#list>
            ];

            function onClick(e, treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj("tree"),
                        nodes = zTree.getSelectedNodes(),
                        id = "",
                        name = "";
                nodes.sort(function compare(a,b){return a.id-b.id;});
                for (var i=0, l=nodes.length; i<l; i++) {
                    id += nodes[i].id + ",";
                    name += nodes[i].name + ",";
                }
                if (id.length > 0 ) id = id.substring(0, id.length-1);
                if (name.length > 0 ) name = name.substring(0, name.length-1);
                $("#targetId").val(id);
                $("#targetName").val(name);
                hideMenu();
            }

            function showMenu() {
                var cityObj = $("#targetName");
                var cityOffset = $("#targetName").offset();
                $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

                $("body").bind("mousedown", onBodyDown);
            }
            function hideMenu() {
                $("#menuContent").fadeOut("fast");
                $("body").unbind("mousedown", onBodyDown);
            }
            function onBodyDown(event) {
                if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
                    hideMenu();
                }
            }

            $.fn.zTree.init($("#tree"), setting, zNodes);
            $("#menuBtn").click(showMenu);
        });
    </script>
</body>
</html>