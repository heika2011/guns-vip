layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var OrderNumLog = {
        tableId: "orderNumLogTable"
    };

    /**
     * 初始化表格的列
     */
    OrderNumLog.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'orderNum', sort: true, title: '生产单编号'},
            {field: 'outSum', sort: true, title: '出货数'},
            {field: 'createdTime', sort: true, title: '操作时间'},
            {field: 'teamId', sort: true, title: '出货小组id'},
            {field: 'color', sort: true, title: '颜色'},
            {field: 'name', sort: true, title: '操作人名字'},
            {field: 'type', sort: true, title: '类型'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    OrderNumLog.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(OrderNumLog.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    OrderNumLog.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/orderNumLog/add',
            tableId: OrderNumLog.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    OrderNumLog.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/orderNumLog/edit?=' + data.,
            tableId: OrderNumLog.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    OrderNumLog.exportExcel = function () {
        var checkRows = table.checkStatus(OrderNumLog.tableId);
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
    OrderNumLog.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/orderNumLog/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(OrderNumLog.tableId);
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
        elem: '#' + OrderNumLog.tableId,
        url: Feng.ctxPath + '/orderNumLog/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: OrderNumLog.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        OrderNumLog.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        OrderNumLog.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        OrderNumLog.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + OrderNumLog.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            OrderNumLog.openEditDlg(data);
        } else if (layEvent === 'delete') {
            OrderNumLog.onDeleteItem(data);
        }
    });
});
