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
                        <h1 class="page-header">用户修改</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row --> 
                <div class="row">
                    <div class="col-lg-12">

					    <@form.form method="post" commandName="userObj" role="form">
					        <@form.hidden path="id"/>
					        <@form.hidden path="salt"/>
					        <@form.hidden path="locked"/>
					
					        <#if op != '新增'>
					            <@form.hidden path="password"/>
					        </#if>
					        
					        <div class="form-group">
					            <@form.label path="realname">姓名：</@form.label>
					            <@form.input path="realname" class="form-control"/>
					        </div>
					
					        <div class="form-group">
					            <@form.label path="username">用户名：</@form.label>
					            <@form.input path="username" class="form-control"/>
					        </div>
					
					        <#if op == '新增'>
					            <div class="form-group">
					                <@form.label path="password">密码：</@form.label>
					                <@form.password path="password" class="form-control"/>
					            </div>
					        </#if>
					
					        <div class="form-group">
					            <@form.label path="organizationId">所属组织：</@form.label>
					            <@form.hidden path="organizationId"/>
					            <input type="text" id="organizationName" name="organizationName" value="${zhangfn.organizationName(user.organizationId)}" readonly class="form-control">
					            <a id="menuBtn" href="#">选择</a>
					        </div>
					
					
					        <div class="form-group">
					            <@form.label path="roleIds">角色列表：</@form.label>
					            <@form.select path="roleIds" multiple="true" class="form-control">
								    <#list roleList as role>
								       <@form.option value="${role.id}" label="${role.description}" />
								    </#list>
								</@form.select>  
					            (按住shift键多选)
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
                <#list organizationList as o>
                    <#if !o.rootNode>
                        { id:${o.id}, pId:${o.parentId}, name:"${o.name}"},
                    </#if>
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
                $("#organizationId").val(id);
                $("#organizationName").val(name);
                hideMenu();
            }

            function showMenu() {
                var cityObj = $("#organizationName");
                var cityOffset = $("#organizationName").offset();
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
 