/**
 * 添加或者修改页面
 */
var ModelInfoDlg = {
    data: {
        num: "",
        name: "",
        customer: "",
        modelNum: "",
        drawNum: "",
        screenNum: "",
        linling: "",
        linlingWhere: "",
        yinhuaWhere: "",
        linlingFrom: "",
        createdTime: "",
        updateTime: "",
        status: ""
    }
};

layui.use(['form', 'admin', 'ax','table','func'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var table = layui.table;
    var func = layui.func;
    var Model = {
        tableId: "modelInfoTable"
    };

    $('body').on('click','#addNew',function () {
        layer.open({
            type:2
            ,area:  ['600px', '400px']
            ,title:'添加客户'
            ,content: Feng.ctxPath + '/customer/add'
            ,maxmin: true
            ,success: function(index, layero){
            },end: function(index, layero){
                $.get("/customer/getCustomer",{},function (data) {
                    var result = data.data;
                    var s = "";
                    for(var i = 0; i<result.length;i++){
                        s = s + "<option value=\""+result[i].num+"\">"+result[i].name+"</option>"
                    }
                    $('#customer').html(s);
                    form.render('select')
                },'json')
                layer.close(index);
            }
        })
    })
    /**
     * 初始化表格的列
     */
    Model.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {type: 'numbers',sort: true ,width:100, title: '编号'},
            {field: 'num',hide:true, templet:function(d){
                    return d.modelInfo.num;
                },sort: true, title: '款式编号'},
            {field: 'id',hide:true, templet:function(d){
                return d.modelInfo.id;
                },sort: true, title: '裁片Id'},
            {field: 'names',templet:function (d) {
                    return d.modelInfo.names;
                }, sort: true, title: '名称'},
            {field: 'color',templet:function (d) {
                    return d.modelInfo.color;
                }, sort: true, title: '颜色'},
            {field: 'craft',templet:function (d) {
                    return d.modelInfo.craft;
                }, sort: true, title: '工艺'},
            {align: 'center', toolbar: '#tableBarModel', title: '操作'}
        ]];
    };
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Model.tableId,
        toolbar: '#tool',
        url: Feng.ctxPath + '/model/getModelInfoAndImage?num='  + $("input[name='num']").val(),
        page:true,
        height: 300,
        cellMinWidth: 100,
        cols: Model.initColumn(),
    });
    //头工具栏事件
    table.on('toolbar(modelInfoTable)', function(obj){
        switch(obj.event){
            case 'addNewModelInfo':
                var s = '';
                if($("input[name='num']").val()!=''){
                    s = $("input[name='num']").val();
                }else if(admin.getTempData("modelNum")!=null){
                    s = admin.getTempData("modelNum");
                }
                sessionStorage.clear();
                admin.putTempData("modelNum",s);
                func.open({
                    title: '添加裁片',
                    tableId: Model.tableId,
                    content: Feng.ctxPath + '/model/addInfo',
                });
        };
    });
    $('#btnModelNote').click(function () {
        func.open({
            title: '款式日志',
            tableId: Model.tableId,
            content: Feng.ctxPath + '/model/getModelNote?num='+$("input[name='num']").val(),
        });
    })
    $("#orderDown").click(function () {
        admin.putTempData("modelNum",$("input[name='num']").val())
        func.open({
            title: '下单',
            content: Feng.ctxPath + '/orders/orderDown?num='+$("input[name='num']").val(),
            tableId: Model.tableId
        });
    })
    form.on('select(hc_select)', function (data) {   //选择移交单位 赋值给input框
        $("#HandoverCompany").val(data.value);
        $("#hc_select").next().find("dl").css({ "display": "none" });
        form.render();
    });
    window.search = function () {
        var value = $("#HandoverCompany").val();
        $("#hc_select").val(value);
        form.render();
        $("#hc_select").next().find("dl").css({ "display": "block" });
        var dl = $("#hc_select").next().find("dl").children();
        var j = -1;
        for (var i = 0; i < dl.length; i++) {
            if (dl[i].innerHTML.indexOf(value) <= -1) {
                dl[i].style.display = "none";
                j++;
            }
            if (j == dl.length-1) {
                $("#hc_select").next().find("dl").css({ "display": "none" });
            }
        }

    }
    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var datas = {id:data.field.id,num:data.field.num,drawNum:data.field.drawNum,customer:data.field.customer,name:data.field.name,modelNum:data.field.modelNum,linling:$("#HandoverCompany").val(),linlingWhere:data.field.linlingWhere,linlingFrom:data.field.linlingFrom,yinhuaWhere:data.field.yinhuaWhere}
        console.log(datas)
        var ajax = new $ax(Feng.ctxPath + "/model/updateModelData", function (data) {
            Feng.success("修改成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            window.location.href='/model'
            //关掉对话框
            admin.closeThisDialog();

        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(datas);
        ajax.start();

        return false;
    });
    // 工具条点击事件
    table.on('tool(' + Model.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Model.openEditDlg(data);
        } else if (layEvent === 'delete') {
            Model.onDeleteItem(data);
        }
    })
    Model.onDeleteItem = function (data){
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/model/deleteModelInfoAndImage", function (data) {
                Feng.success("删除成功!");
                table.reload(Model.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.state.msg + "!");
            });
            ajax.set("id", data.modelInfo.id);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    }
    Model.openEditDlg = function (data){
        console.log(data)
        func.open({
            title: '修改裁片',
            tableId: Model.tableId,
            content: Feng.ctxPath + '/model/updateModelInfo?id='+data.modelInfo.id,
        });
    }


    table.render({
        elem: '#orderForModel',
        title:'生产单列表',
        url: Feng.ctxPath + '/orders/list?modelId='  + $("input[name='num']").val(),
        page:true,
        height: 300,
        cellMinWidth: 100,
        cols: [[
            {type: 'checkbox'},
            {field: 'id',hide:true,sort: true, title: '生产单id'},
            {field: 'orderNum',sort: true, title: '生产单编号'},
            {field: 'allcount',sort: true, title: '数量'},
            {field: 'units', sort: true, title: '单位'},
            {field: 'createdTime',sort: true, title: '创建时间'},
            {field: 'overTime',templet:function (d) {
                    if(d.overTime - new Date() >10)
                    return '<span style="color: #F581B1;"> d.overTime </span>'
                        else  return d.overTime
                },sort: true, title: '交货时间'},
            {field: 'orderProg',templet:function (d){
                if(d.orderProg == 1)return '打样'
                    else if(d.orderProg == 2) return '大货'
                },sort: true, title: '创建时间'},
            {field: 'note',sort: true, title: '备注'},
        ]]
    })

    table.render({
        elem: '#modelColorTable',
        toolbar: '#ColorTool',
        url: Feng.ctxPath + '/model/getColorList?modelId=' + $("input[name='num']").val(),
        page:true,
        height: "full-158",
        cellMinWidth: 100,
        id:'modelColorTable',
        cols: [[
            {type: 'checkbox'},
            {type: 'numbers',sort: true ,width:100, title: '编号'},
            {field: 'id',hide:true,sort: true, title: '裁片Id'},
            {field: 'color',sort: true, title: '面料色'},
            {field: 'textColor', sort: true, title: '字母颜色'},
            {field: 'sum', sort: true, title: '色数'},
            {align: 'center', toolbar: '#tableBarModel', title: '操作'}
        ]],
    });
    table.on('toolbar(modelColorTable)', function(obj){
        switch(obj.event){
            case 'addNewModelColor':
                admin.putTempData("modelNum",$("input[name='num']").val());
                func.open({
                    title: '添加配色表信息',
                    tableId: 'modelColorTable',
                    content: Feng.ctxPath + '/model/color',
                });
        };
    });
    table.on('tool(modelColorTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            colorEdit(data);
        } else if (layEvent === 'delete') {
            colorDelete(data);
        }
    })
    var colorEdit = function (data) {
        func.open({
            title: '修改配色表',
            tableId:'modelColorTable',
            content: Feng.ctxPath + '/model/colorEdit?id='+data.id,
        });
    };
    var colorDelete = function(data){
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/model/deleteModelColor", function (data) {
                Feng.success("删除成功!");
                table.reload('modelColorTable');
            }, function (data) {
                Feng.error("删除失败!" + data.state.msg + "!");
            });
            ajax.set("id",data.id);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };
    form.render();
});