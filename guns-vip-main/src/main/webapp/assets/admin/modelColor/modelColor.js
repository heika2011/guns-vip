layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var ModelColor = {
        tableId: "modelColorTable"
    };

    /**
     * 初始化表格的列
     */
    ModelColor.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, title: '款式配色id'},
            {field: 'modelId', sort: true, title: '款式Id'},
            {field: 'textColor', sort: true, title: '字母颜色'},
            {field: 'sum', sort: true, title: '色数'},
            {field: 'color', sort: true, title: '面料色'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ModelColor.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(ModelColor.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    ModelColor.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/modelColor/add',
            tableId: ModelColor.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    ModelColor.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/modelColor/edit?id=' + data.id,
            tableId: ModelColor.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    ModelColor.exportExcel = function () {
        var checkRows = table.checkStatus(ModelColor.tableId);
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
    ModelColor.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/modelColor/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(ModelColor.tableId);
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
        elem: '#' + ModelColor.tableId,
        url: Feng.ctxPath + '/modelColor/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: ModelColor.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ModelColor.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ModelColor.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        ModelColor.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + ModelColor.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ModelColor.openEditDlg(data);
        } else if (layEvent === 'delete') {
            ModelColor.onDeleteItem(data);
        }
    });
});
