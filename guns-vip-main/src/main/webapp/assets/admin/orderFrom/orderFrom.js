layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var OrderFrom = {
        tableId: "orderFromTable"
    };

    /**
     * 初始化表格的列
     */
    OrderFrom.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'modelId', sort: true, title: '款式编号'},
            {field: 'orderNum', sort: true, title: '生产单顺序'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    OrderFrom.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(OrderFrom.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    OrderFrom.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/orderFrom/add',
            tableId: OrderFrom.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    OrderFrom.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/orderFrom/edit?=' + data.,
            tableId: OrderFrom.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    OrderFrom.exportExcel = function () {
        var checkRows = table.checkStatus(OrderFrom.tableId);
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
    OrderFrom.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/orderFrom/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(OrderFrom.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("", data.);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + OrderFrom.tableId,
        url: Feng.ctxPath + '/orderFrom/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: OrderFrom.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        OrderFrom.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        OrderFrom.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        OrderFrom.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + OrderFrom.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            OrderFrom.openEditDlg(data);
        } else if (layEvent === 'delete') {
            OrderFrom.onDeleteItem(data);
        }
    });
});
