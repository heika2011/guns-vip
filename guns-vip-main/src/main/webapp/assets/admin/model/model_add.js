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
            ,area: ['600px', '400px']
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
            {field: 'modelInfo.id',hide:true,sort: true, title: '裁片Id'},
            {field: 'names',templet:function (d) {
                    return d.modelInfo.names;
                }, sort: true, title: '名称'},
            {field: 'color', sort: true, title: '颜色'},
            {field: 'craft', sort: true, title: '工艺'},
            {align: 'center', toolbar: '#tableBarForColor', title: '操作'}
        ]];
    };
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Model.tableId,
        toolbar: '#tool',
        url: Feng.ctxPath + '/model/getModelInfoAndImage?num=' + (admin.getTempData("modelNum") == null? '':admin.getTempData("modelNum")),
        page:true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Model.initColumn(),
        id:Model.tableId,
    });
    table.render({
        elem: '#modelColorTable',
        toolbar: '#ColorTool',
        url: Feng.ctxPath + '/model/getColorList?modelId=' + (admin.getTempData("modelNum") == null? '9999':admin.getTempData("modelNum")),
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
            {align: 'center', toolbar: '#tableBarForColor', title: '操作'}
        ]],
    });
    table.on('tool(modelInfoTable)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            InfoEdit(data);
        } else if (layEvent === 'delete') {
            InfoDelete(data);
        }
    })
    var InfoEdit = function (data) {
        func.open({
            title: '修改裁片',
            tableId: Model.tableId,
            content: Feng.ctxPath + '/model/updateModelInfo?id='+data.modelInfo.id,
        });
    };

    var InfoDelete = function(data){
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
    };

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
                Feng.error("删除失败!" + data.responseJSON.state.msg + "!");
            });
            ajax.set("id",data.id);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };
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
    table.on('toolbar(modelColorTable)', function(obj){
        switch(obj.event){
            case 'addNewModelColor':
                var s = '';
                var z = "";
                if($("input[name='num']").val()!=''){
                    s = $("input[name='num']").val();
                }else if(admin.getTempData("modelNum")!=null){
                    s = admin.getTempData("modelNum");
                }
                if(admin.getTempData("sum")!=null){
                    z = admin.getTempData("sum");
                }
                sessionStorage.clear();
                admin.putTempData("modelNum",s);
                admin.putTempData("sum",z);
                func.open({
                    title: '添加配色表信息',
                    tableId: 'modelColorTable',
                    content: Feng.ctxPath + '/model/color',
                });
        };
    });
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
        var datas = {num:data.field.num,drawNum:data.field.drawNum,customer:data.field.customer,name:data.field.name,modelNum:data.field.modelNum,linling:$("#HandoverCompany").val(),linlingWhere:data.field.linlingWhere,linlingFrom:data.field.linlingFrom,yinhuaWhere:data.field.yinhuaWhere}
        console.log(datas)
        var ajax = new $ax(Feng.ctxPath + "/model/addModel", function (data) {
            Feng.success("添加成功！");

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

    form.render()
});