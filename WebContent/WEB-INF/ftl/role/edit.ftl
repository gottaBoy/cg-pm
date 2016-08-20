<#assign zhangfn=JspTaglibs["/WEB-INF/tld/zhang-functions.tld"]/>
<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]/>
<#include "/constants.ftl"> 
<@i.tHtml>
	<style>
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:200px;overflow-y:scroll;overflow-x:auto;}
    </style>
 
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">资源修改</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row --> 
                <div class="row">
                    <div class="col-lg-12">
					    <@form.form method="post" commandName="role" role="form">
					        <@form.hidden path="id"/>
					        <@form.hidden path="available"/>
					
					        <div class="form-group">
					            <@form.label path="role">角色名：</@form.label>
					            <@form.input path="role" class="form-control"/>
					        </div>
					
					        <div class="form-group">
					            <@form.label path="description">角色描述：</@form.label>
					            <@form.input path="description" class="form-control"/>
					        </div>
					
					
					        <div class="form-group">
					            <@form.label path="resourceIds">拥有的资源列表：</@form.label>
					            <@form.hidden path="resourceIds"/>
					            <input type="text" id="resourceName" name="resourceName" value="${zhangfn.resourceNames(role.resourceIds)}" readonly class="form-control">
					            <a id="menuBtn" href="#">选择</a>
					        </div>
					
					        <@form.button class="btn btn-default">${op}</@form.button>
					
					    </@form.form>
					
					
					    <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
					        <ul id="tree" class="ztree" style="margin-top:0; width:160px;"></ul>
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
                check: {
                    enable: true ,
                    chkboxType: { "Y": "", "N": "" }
                },
                view: {
                    dblClickExpand: false
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onCheck: onCheck
                }
            };

            var zNodes =[
                <#list resourceList as r>
                <#if !r.rootNode>
                { id:${r.id}, pId:${r.parentId}, name:"${r.name}", checked:${zhangfn.in(role.resourceIds, r.id)?string('true','false')}},
                </#if>
                </#list>
            ];

            function onCheck(e, treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj("tree"),
                        nodes = zTree.getCheckedNodes(true),
                        id = "",
                        name = "";
                nodes.sort(function compare(a,b){return a.id-b.id;});
                for (var i=0, l=nodes.length; i<l; i++) {
                    id += nodes[i].id + ",";
                    name += nodes[i].name + ",";
                }
                if (id.length > 0 ) id = id.substring(0, id.length-1);
                if (name.length > 0 ) name = name.substring(0, name.length-1);
                $("#resourceIds").val(id);
                $("#resourceName").val(name);
//                hideMenu();
            }

            function showMenu() {
                var cityObj = $("#resourceName");
                var cityOffset = $("#resourceName").offset();
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

 