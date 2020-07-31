layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var OrderConstLog = {
        tableId: "orderConstLogTable"
    };

    /**
     * 初始化表格的列
     */
    OrderConstLog.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'orderNum', sort: true, title: '生产单编号'},
            {field: 'kickback', sort: true, title: '回扣'},
            {field: 'lastConst', sort: true, title: '最终报价'},
            {field: 'createdTime', sort: true, title: '创建时间'},
            {field: 'consts', sort: true, title: '单价'},
            {field: 'makeUp', sort: true, title: '赔偿金额'},
            {field: 'sceenConst', sort: true, title: '版费'},
            {field: 'note', sort: true, title: '备注'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    OrderConstLog.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(OrderConstLog.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    OrderConstLog.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/orderConstLog/add',
            tableId: OrderConstLog.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    OrderConstLog.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/orderConstLog/edit?=' + data.,
            tableId: OrderConstLog.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    OrderConstLog.exportExcel = function () {
        var checkRows = table.checkStatus(OrderConstLog.tableId);
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
    OrderConstLog.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/orderConstLog/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(OrderConstLog.tableId);
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
        elem: '#' + OrderConstLog.tableId,
        url: Feng.ctxPath + '/orderConstLog/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: OrderConstLog.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        OrderConstLog.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        OrderConstLog.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        OrderConstLog.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + OrderConstLog.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            OrderConstLog.openEditDlg(data);
        } else if (layEvent === 'delete') {
            OrderConstLog.onDeleteItem(data);
        }
    });
});
