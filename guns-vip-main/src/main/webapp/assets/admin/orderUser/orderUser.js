layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var OrderUser = {
        tableId: "orderUserTable"
    };

    /**
     * 初始化表格的列
     */
    OrderUser.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'userId', sort: true, title: '用户id'},
            {field: 'orderNum', sort: true, title: '订单编号'},
            {field: 'createdTime', sort: true, title: '产生时间'},
            {field: 'type', sort: true, title: '打样或者大货'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    OrderUser.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(OrderUser.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    OrderUser.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/orderUser/add',
            tableId: OrderUser.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    OrderUser.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/orderUser/edit?=' + data.,
            tableId: OrderUser.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    OrderUser.exportExcel = function () {
        var checkRows = table.checkStatus(OrderUser.tableId);
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
    OrderUser.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/orderUser/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(OrderUser.tableId);
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
        elem: '#' + OrderUser.tableId,
        url: Feng.ctxPath + '/orderUser/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: OrderUser.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        OrderUser.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        OrderUser.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        OrderUser.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + OrderUser.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            OrderUser.openEditDlg(data);
        } else if (layEvent === 'delete') {
            OrderUser.onDeleteItem(data);
        }
    });
});
