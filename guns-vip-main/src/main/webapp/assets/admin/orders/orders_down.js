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
        if($(this).index()==1){
            func.open({
                title: '查看裁片',
                content: Feng.ctxPath + '/model/updateModel?num='+$("input[name='modelId']").val(),
            });
        }
    });
    var flag = 0;


    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {

        var milasUrl= new Array();//新建对象，用来存储所有数据
        var subMilasUrlArr={};//存储每一行数据
        var tableData={};
        $("table tbody tr").each(function(trindex,tritem){//遍历每一行
            tableData[trindex]=new Array();
            $(tritem).find("input").each(function(tdindex,tditem){
                tableData[trindex][tdindex]=$(tditem).val();//遍历每一个数据，并存入
                subMilasUrlArr[trindex]=tableData[trindex];//将每一行的数据存入
            });
        });
        for(var key in subMilasUrlArr)
        {
            milasUrl.push({color:tableData[key][0],sum:tableData[key][1]});//将每一行存入对象
        }
        var datas = {modelId:admin.getTempData("modelNum"),allcount:data.field.allcount,units:data.field.units,note:data.field.note,orderProgress:data.field.orderProgress,orderType:data.field.orderType,overTime:data.field.overTime,orderNums:milasUrl}
        $.ajax({
            url: Feng.ctxPath + "/orders/addorder?flag="+flag,
            method: 'post', // 注意这里，传递对象给后台，这里必须是 POST 否则无法将对象封装到POST的body中流
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8', // 注意这里，传递对象给后台，这里必须是 application/json
            data: JSON.stringify(datas) ,// 注意这里，传递对象给后台，这里必须将对象进行序列化
            success:function (result) {
                if(result.state.code == 0){
                    Feng.success("下单成功，请刷新页面！");

                    //传给上个页面，刷新table用
                    admin.putTempData('formOk', true);

                    //关掉对话框
                    admin.closeThisDialog();
                }
            }
        })

        return false;
    });

    //因为动态添加的元素class属性是无效的，所以不能用$('.add').click(function(){});
    $('body').on('click', '.add', function() {
        var html = '<tr>'+
            '<td><input type="text" class="layui-input" name="color"></td>'+
            '<td><input type="number" class="layui-input" name="colorNum"></td>'+
            '<td>'+
            '<a class="layui-btn layui-btn-xs add">添加</a>'+
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
    form.on('switch(switchTest)', function(data){
        flag = this.checked ? 1 : 0;
        layer.tips('温馨提示：打开后自动打印', data.othis)
    });

    $('body').on('change',"input[name='colorNum']",function () {
        var s = 0;
        $("input[name='colorNum']").each(function () {
            if($(this).val() == null || $(this).val() == ""){
                s = 0;
            }else{
                s = s + parseInt($(this).val())
            }
        })
        $("#allcount").val(s);
    })
});