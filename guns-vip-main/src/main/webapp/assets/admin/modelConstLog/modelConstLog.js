layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var ModelConstLog = {
        tableId: "modelConstLogTable"
    };

    /**
     * 初始化表格的列
     */
    ModelConstLog.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'num', sort: true, title: '裁片id'},
            {field: 'consts', sort: true, title: '价格'},
            {field: 'createdTime', sort: true, title: '报价时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ModelConstLog.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(ModelConstLog.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    ModelConstLog.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/modelConstLog/add',
            tableId: ModelConstLog.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    ModelConstLog.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/modelConstLog/edit?=' + data.,
            tableId: ModelConstLog.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    ModelConstLog.exportExcel = function () {
        var checkRows = table.checkStatus(ModelConstLog.tableId);
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
    ModelConstLog.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/modelConstLog/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(ModelConstLog.tableId);
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
        elem: '#' + ModelConstLog.tableId,
        url: Feng.ctxPath + '/modelConstLog/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: ModelConstLog.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ModelConstLog.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ModelConstLog.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        ModelConstLog.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + ModelConstLog.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ModelConstLog.openEditDlg(data);
        } else if (layEvent === 'delete') {
            ModelConstLog.onDeleteItem(data);
        }
    });
});
