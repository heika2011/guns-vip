/**
 * 详情对话框
 */
var OrderRoleChildInfoDlg = {
    data: {
        name: "",
        type: "",
        isname: "",
        shunxu: "",
        parentId: "",
        typeName: "",
        num: ""
    }
};

layui.use(['form', 'admin', 'ax','func'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var func = layui.func;

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/orderRole/detail?id=" + Feng.getUrlParam("id"));
    var result = ajax.start();
    form.val('orderRoleChildForm', result.data);
    var ajaxx = new $ax(Feng.ctxPath + "/orderRoleChild/getMenu")
    var results = ajaxx.start().data;
    var text = ""
    for(var i=0;i<results.length;i++){
        text = text  + "<option value=\""+ results[i].name+"|"+ results[i].menuId +"\">"+results[i].name+"</option>"
    }

    $("#menu").html(text);
    //表单提交事件

    var results = new $ax(Feng.ctxPath + "/orderRoleChild/roleList").start().data;
    var peopleText = ""
    for(var i=0;i<results.length;i++){
        peopleText = peopleText  + "<option value=\""+ results[i].name+"|"+ results[i].roleId+ "\">"+results[i].name+"</option>"
    }
    $("#people").html(peopleText);
    form.on('submit(btnSubmitChild)', function (data) {
        var milasUrl= new Array();//新建对象，用来存储所有数据
        var subMilasUrlArr={};//存储每一行数据
        var tableData={};
        $("table tbody tr").each(function(trindex,tritem){//遍历每一行
            tableData[trindex]=new Array();
            $(tritem).find("select[name='menu'] :selected").each(function(tdindex,tditem){
                tableData[trindex][1]=$(tditem).val();//遍历每一个数据，并存入
                subMilasUrlArr[trindex]=tableData[trindex];//将每一行的数据存入
            });
            $(tritem).find("select[name='people'] :selected").each(function(tdindex,tditem){
                tableData[trindex][3]=$(tditem).val();//遍历每一个数据，并存入
                subMilasUrlArr[trindex]=tableData[trindex];//将每一行的数据存入
            });
            $(tritem).find("input").each(function(tdindex,tditem){
                tableData[trindex][2]=$(tditem).val();//遍历每一个数据，并存入
                subMilasUrlArr[trindex]=tableData[trindex];//将每一行的数据存入
            });
        });
        var dataz = {name:data.field.name,type:data.field.type}
        console.log(tableData)
        var ajax = new $ax(Feng.ctxPath + "/orderRole/addItem");
        ajax.set(dataz);
        var id = ajax.start();
        if(id.data == 0){
            Feng.error("该进度已配置");
        }else {
            for(var key in subMilasUrlArr)
            {
                milasUrl.push({
                    name:tableData[key][3].split("|")[1],
                    type:tableData[key][1].split("|")[1],
                    typeName:tableData[key][1].split("|")[0],
                    parentId:id.data,
                    num:admin.getTempData(tableData[key][1].split("|")[0]),
                    shunxu:tableData[key][2]});//将每一行存入对象
            }

            //表单提交事件

            $.ajax({
                url: Feng.ctxPath + "/orderRoleChild/updateRoleChild",
                method: 'post', // 注意这里，传递对象给后台，这里必须是 POST 否则无法将对象封装到POST的body中流
                dataType: 'json',
                async:false,
                contentType: 'application/json;charset=UTF-8', // 注意这里，传递对象给后台，这里必须是 application/json
                data: JSON.stringify(milasUrl) ,// 注意这里，传递对象给后台，这里必须将对象进行序列化
                success:function (result) {
                    if(result.state.code == 0){
                        Feng.success("添加成功!");

                        //传给上个页面，刷新table用
                        window.location = "/orderRoleChild";
                    }
                }
            })
        }

    });
    $('body').on('click', '.config', function() {
        console.log(admin.getTempData($(this).closest('tr').find("input").val()))
        admin.putTempData("data",$(this).closest('tr').find("input").val());
        add();
    });
    var add = function(){
        func.open({
            title: '推送配置',
            content: Feng.ctxPath + '/sysMessage/add',
        });
    }
    //因为动态添加的元素class属性是无效的，所以不能用$('.add').click(function(){});
    $('body').on('click', '.add', function() {
        var html = '<tr>'+
            '<td><select id="menu" name="menu" lay-filter="menu">\n' + text +
            '                                 </select></td>'+
            '<td><select id="people" name="people" lay-filter="people">\n' + peopleText +
            '                                 </select></td>'+
            '<td><input type="number" class="layui-input" name="sx"></td>'+
            '<td>'+
            '<a class="layui-btn layui-btn-danger layui-btn-xs del">删除</a>'+
            '<a class="layui-btn layui-btn-xs config">推送配置</a>'+
            '</td>'+
            '</tr>';
        //添加到表格最后
        $(html).appendTo($('#table tbody:last'));
        form.render();
    });
    $('body').on('click', '.del', function() {
        if ($('#table tbody tr').length === 1) {
            layer.msg('只有一条不允许删除。', {
                time : 2000
            });
        } else {
            //删除当前按钮所在的tr
            $(this).closest('tr').remove();
        }
    });

    form.render();
});