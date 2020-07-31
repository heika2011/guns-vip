layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var PrintAre = {
        tableId: "printAreTable"
    };

    /**
     * 初始化表格的列
     */
    PrintAre.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, title: '打印机id'},
            {field: 'printName', sort: true, title: '打印机名字'},
            {field: 'token', sort: true, title: '打印机token'},
            {field: 'printKey', sort: true, title: '打印机钥匙'},
            {field: 'nickname', sort: true, title: '打印机简称'},
            {field: 'printPort', sort: true, title: '打印机端口'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    PrintAre.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(PrintAre.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    PrintAre.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/printAre/add',
            tableId: PrintAre.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    PrintAre.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/printAre/edit?id=' + data.id,
            tableId: PrintAre.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    PrintAre.exportExcel = function () {
        var checkRows = table.checkStatus(PrintAre.tableId);
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
    PrintAre.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/printAre/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(PrintAre.tableId);
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
        elem: '#' + PrintAre.tableId,
        url: Feng.ctxPath + '/printAre/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: PrintAre.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        PrintAre.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        PrintAre.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        PrintAre.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + PrintAre.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            PrintAre.openEditDlg(data);
        } else if (layEvent === 'delete') {
            PrintAre.onDeleteItem(data);
        }
    });
});
