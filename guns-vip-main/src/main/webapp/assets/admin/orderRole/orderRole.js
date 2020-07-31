layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var OrderRole = {
        tableId: "orderRoleTable"
    };

    /**
     * 初始化表格的列
     */
    OrderRole.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id',sort: true , title: '编号'},
            {field: 'name', sort: true, title: '名称'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    OrderRole.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(OrderRole.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    OrderRole.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/orderRole/add',
            tableId: OrderRole.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    OrderRole.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/orderRole/edit?id=' + data.id,
            tableId: OrderRole.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    OrderRole.exportExcel = function () {
        var checkRows = table.checkStatus(OrderRole.tableId);
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
    OrderRole.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/orderRole/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(OrderRole.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + OrderRole.tableId,
        url: Feng.ctxPath + '/orderRole/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: OrderRole.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        OrderRole.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        OrderRole.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        OrderRole.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + OrderRole.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            OrderRole.openEditDlg(data);
        } else if (layEvent === 'delete') {
            OrderRole.onDeleteItem(data);
        }
    });
});
