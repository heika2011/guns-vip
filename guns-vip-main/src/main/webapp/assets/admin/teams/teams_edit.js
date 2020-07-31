

layui.use(['form', 'admin', 'ax','element','table','func'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var tables = layui.table;
    var func = layui.func;

    var ajaxx = new $ax(Feng.ctxPath + "/teams/getAllPeople")
    var results = ajaxx.start().data;

    var text = ""
    console.log(results.length)
    for(var i=0;i<results.length;i++){
        text = text  + "<option value=\""+ results[i].name+"|"+ results[i].userId+ "--&--" +"\">"+results[i].name+"</option>"
    }

    form.on('submit(btnSubmitChild)', function (data) {
        var operation = function () {
            var milasUrl = new Array();//新建对象，用来存储所有数据
            var subMilasUrlArr = {};//存储每一行数据
            var tableData = {};
            $("table tbody tr").each(function (trindex, tritem) {//遍历每一行
                tableData[trindex] = new Array();
                $(tritem).find("select :selected").each(function (tdindex, tditem) {
                    tableData[trindex][tdindex] = $(tditem).val();//遍历每一个数据，并存入
                    subMilasUrlArr[trindex] = tableData[trindex];//将每一行的数据存入
                });
            });
            for (var key in subMilasUrlArr) {
                milasUrl.push({
                    id: tableData[key][0].split("--&--")[1],
                    userId: tableData[key][0].split("--&--")[0].split("|")[1],
                    name: tableData[key][0].split("--&--")[0].split("|")[0],
                    job: tableData[key][1],
                    teamId: $("input[name='id']").val()
                });//将每一行存入对象
            }
            var dataz = {id:$("input[name='id']").val(),name:$("input[name='name']").val(),type:data.field.type,users:milasUrl}

            $.ajax({
                url: Feng.ctxPath + "/teams/updateTeam?flag="+(data.field.show==0?false:true),
                method: 'post', // 注意这里，传递对象给后台，这里必须是 POST 否则无法将对象封装到POST的body中流
                dataType: 'json',
                async:false,
                contentType: 'application/json;charset=UTF-8', // 注意这里，传递对象给后台，这里必须是 application/json
                data: JSON.stringify(dataz) ,// 注意这里，传递对象给后台，这里必须将对象进行序列化
                success:function (result) {
                    if(result.state.code == 0){
                        Feng.success("修改成功!");

                        //传给上个页面，刷新table用
                        admin.putTempData('formOk', true);
                        window.location.href = "/teams";
                        //关掉对话框
                        admin.closeThisDialog();
                    }
                },error:function (data) {
                    Feng.error(data.responseJSON.state.msg + "!");
                }
            })
        };
        if(data.field.show == 1){
            Feng.confirm("将清除该组成员下所有角色<br/>并重新写入角色，您确定吗？", operation);
        }else {
            Feng.confirm("确定修改？", operation);
        }
     });

        //因为动态添加的元素class属性是无效的，所以不能用$('.add').click(function(){});
        $('body').on('click', '.add', function () {
            var html = '<tr>' +
                '<td><select id="menu" name="menu" lay-filter="menu">\n' + text +
                '                                 </select></td>' +
                '<td>\n' +
                '                                <select id="leader" name="leader" lay-filter="leader">\n' +
                '                                    <option  value="1">组长</option>\n' +
                '                                    <option  value="2">副组长</option>\n' +
                '                                    <option  value="3">组员</option>\n' +
                '                                </select></td>' +
                '<td>' +
                '<a class="layui-btn layui-btn-danger layui-btn-xs del">删除</a>' +
                '</td>' +
                '</tr>';
            //添加到表格最后
            $(html).appendTo($('#table tbody:last'));
            form.render();
        });
        $('body').on('click', '.del', function () {
            if ($('#table tbody tr').length === 1) {
                layer.msg('只有一条不允许删除。', {
                    time: 2000
                });
            } else {
             //删除当前按钮所在的tr
             new $ax( Feng.ctxPath + "/teams/deleteTeamUser?id="+$(this).closest('tr').attr("class")).start()
             $(this).closest('tr').remove();
            }
        });
        form.render();
        /* //获取详情信息，填充表单
         var ajax = new $ax(Feng.ctxPath + "/teams/detail?id=" + Feng.getUrlParam("id"));
         var result = ajax.start();
         form.val('teamsForm', result.data);

         //表单提交事件
         form.on('submit(btnSubmit)', function (data) {
             var ajax = new $ax(Feng.ctxPath + "/teams/editItem", function (data) {
                 Feng.success("更新成功！");

                 //传给上个页面，刷新table用
                 admin.putTempData('formOk', true);

                 //关掉对话框
                 admin.closeThisDialog();

             }, function (data) {
                 Feng.error("更新失败！" + data.responseJSON.message)
             });
             ajax.set(data.field);
             ajax.start();
             return false;
         });*/

});