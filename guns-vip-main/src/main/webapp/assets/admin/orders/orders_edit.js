/**
 * 详情对话框
 */
var OrdersInfoDlg = {
    data: {
        modelId: "",
        customerName: "",
        modelName: "",
        orderNum: "",
        name: "",
        orderProgress: "",
        orderType: "",
        note: "",
        urag: "",
        status: "",
        createdTime: "",
        overTime: "",
        error: "",
        orderProg: "",
        units: "",
        allcount: "",
        havSheet: "",
        nameId: "",
        realyCount: "",
        reaTime: "",
        lastConst: ""
    }
};

layui.use(['form', 'admin', 'ax','element','laydate','func'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
    var laydate = layui.laydate;
    var func = layui.func;
    var s = 0;
    //日期
    laydate.render({
        elem: '#createdTime'
        ,type: 'datetime'
    });
    laydate.render({
        elem: '#overTime'
        ,type: 'datetime'
    });
    //一些事件监听
    element.on('tab(demo)', function(data){
        s = $(this).index();
        if($(this).index()==1){
            func.open({
                title: '查看裁片',
                content: Feng.ctxPath + '/model/updateModel?num='+$("input[name='modelId']").val(),
            });
        }
    });

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/orders/detail?id=" + Feng.getUrlParam("id"));
    var result = ajax.start();
    $("#sendCD").html("催单("+result.data.urag+")&emsp;");
    form.val('ordersForm', result.data);

    $("#sendBJ").click(function () {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/orders/sendOrderBJ", function (data) {
                Feng.success("操作成功!");
                table.reload(Orders.tableId);
            }, function (data) {
                Feng.error("操作失败!" + data.responseJSON.state.msg + "!");
            });
            ajax.set("orderNum", $("input[name='orderNum']").val());
            ajax.start();
        };
        Feng.confirm("是否请求报价?", operation);
    })
    $("#sendCD").click(function () {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/orders/uragOrder", function (data) {
                Feng.success("操作成功!");
                table.reload(Orders.tableId);
            }, function (data) {
                Feng.error("操作失败!" + data.responseJSON.state.msg + "!");
            });
            ajax.set("orderNum", $("input[name='orderNum']").val());
            ajax.start();
        };
        Feng.confirm("是否催单?", operation);
    })

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        if(s == 0){
            var datas = {id:data.field.id,allcount:data.field.allcount,units:data.field.units,createdTime:data.field.createdTime,note:data.field.note,overTime:data.field.overTime}
            var ajax = new $ax(Feng.ctxPath + "/orders/updateOrder", function (data) {
                Feng.success("更新成功！");

                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);

                //关掉对话框
                admin.closeThisDialog();

            }, function (data) {
                Feng.error("更新失败！" + data.responseJSON.message)
            });
            ajax.set(datas);
            ajax.start();

            return false;
        }else if(s == 2){

            alert("你当前在数量表页面"+$("input[name='orderNum']").val())
        }
    });
    //因为动态添加的元素class属性是无效的，所以不能用$('.add').click(function(){});
    $('body').on('click', '.add', function() {
        var html = '<tr>'+
            '<td><input type="text" class="layui-input" name="color"></td>'+
            '<td><input type="number" class="layui-input" name="colorNum"></td>'+
            '<td>'+
            '<a class="layui-btn layui-btn-xs save">保存</a>'+
            '<a class="layui-btn layui-btn-danger layui-btn-xs del">删除</a>'+
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
    $('body').on('click', '.save', function() {
        alert($(this).closest("tr td input[name='colorNum']").val())
        var ajax = new $ax(Feng.ctxPath + "/orders/updateOrderNum",function (result) {
            if(result.state.code == 0){
                Feng.success("更新成功！");
            }
        })
        ajax.set()
    });

});
