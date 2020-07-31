layui.use(['table', 'admin', 'ax', 'func','form'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;

    /**
     * 款式管理
     */
    var Orders = {
        tableId: "ordersTable"
    };

    /**
     * 初始化表格的列
     */
    Orders.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, title: '生产单id'},
            {field: 'modelId',  hide: true,sort: true, title: '款式id'},
            {field: 'customerName',templet:function(d){
                if(d.model.customerName == null)
                    return ''
                else
                return d.model.customerName;
                }, sort: true, title: '客户'},
            {field: 'orderNum', sort: true, title: '生产单编号'},
            {field: 'modelName',templet:function(d){
                if(d.model.name == null)
                    return ''
                else
                    return d.model.name;
                }, sort: true, title: '名称'},
            {field: 'modelNum',templet:function(d){
                if(d.model.modelNum == null)
                    return ''
                else
                    return d.model.modelNum;
                },  sort: true, title: '款号'},
            {field: 'allcount', sort: true, title: '数量'},
            {field: 'units', sort: true, title: '单位'},
            {field: 'createdTime', sort: true, title: '创建时间'},
            {field: 'orderProg',templet:function (d){
                    if(d.orderProg == 1)return '打样'
                    else if(d.orderProg == 2) return '大货'
                    else if(d.orderProg == 3) return '大货'
                }, sort: true, title: '类型'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Orders.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(Orders.tableId, {
            where: queryData, page: {curr: 1}
        });
    };


    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    Orders.openEditDlg = function (data) {
        func.open({
            title: '查看生产单',
            content: Feng.ctxPath + '/orders/edit?id=' + data.id+"&num="+data.modelId+"&orderNum="+data.orderNum,
            tableId: Orders.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    Orders.exportExcel = function () {
        var checkRows = table.checkStatus(Orders.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    Orders.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/orders/deleteOrder", function (data) {
                Feng.success("删除成功!");
                table.reload(Orders.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.state.msg + "!");
            });
            ajax.set("orderNum", data.orderNum);
            ajax.start();
        };
        Feng.confirm("该生成单所有相关信息都会删除，是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Orders.tableId,
        toolbar: '#out', //开启头部工具栏，并为其绑定左侧模板
        url: Feng.ctxPath + '/orders/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Orders.initColumn()
    });

    //头工具栏事件
    table.on('toolbar(' + Orders.tableId + ')', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'getCheckData':
                var data = checkStatus.data;
                if(data.length>1){
                    layer.msg("只能选择一个进行打印")
                }else{
                    window.open(Feng.ctxPath + '/orders/getOrderOut?num='+data[0].orderNum);
                }
                break;
            case 'getCheckLength':
                var data = checkStatus.data;
                if(data.length>1){
                    layer.msg("只能选择一个进行打印")
                }else{
                    window.open(Feng.ctxPath + '/orders/getGYDOut?num='+data[0].orderNum);
                }
                break;
            //自定义头工具栏右侧图标 - 提示
            case 'LAYTABLE_TIPS':
                layer.alert('这是工具栏右侧自定义的一个图标按钮');
                break;
        };
    });
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Orders.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Orders.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Orders.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Orders.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Orders.openEditDlg(data);
        } else if (layEvent === 'delete') {
            Orders.onDeleteItem(data);
        }
    });
    form.on('select(orderProgress)',function (data) {
        var queryData = {};
        queryData['orderProgress'] = data.value;
        table.reload(Orders.tableId, {
            where: queryData, page: {curr: 1}
        });
    })
});
