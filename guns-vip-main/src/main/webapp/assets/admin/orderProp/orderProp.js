layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var OrderProp = {
        tableId: "orderPropTable"
    };

    /**
     * 初始化表格的列
     */
    OrderProp.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'orderNum', sort: true, title: '生产单编号'},
            {field: 'type', sort: true, title: '角色对应id'},
            {field: 'typeName', sort: true, title: '角色对应名字'},
            {field: 'name', sort: true, title: '操作人员名字'},
            {field: 'doOver', sort: true, title: '操作是否完毕'},
            {field: 'sx', sort: true, title: '顺序'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    OrderProp.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(OrderProp.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    OrderProp.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/orderProp/add',
            tableId: OrderProp.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    OrderProp.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/orderProp/edit?=',
            tableId: OrderProp.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    OrderProp.exportExcel = function () {
        var checkRows = table.checkStatus(OrderProp.tableId);
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
    OrderProp.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/orderProp/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(OrderProp.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + OrderProp.tableId,
        url: Feng.ctxPath + '/orderProp/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: OrderProp.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        OrderProp.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        OrderProp.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        OrderProp.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + OrderProp.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            OrderProp.openEditDlg(data);
        } else if (layEvent === 'delete') {
            OrderProp.onDeleteItem(data);
        }
    });
});
