layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 进度表信息管理
     */
    var OrderProgress = {
        tableId: "orderProgressTable"
    };

    /**
     * 初始化表格的列
     */
    OrderProgress.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, title: 'id'},
            {field: 'name', sort: true, title: '名称'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    OrderProgress.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(OrderProgress.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    OrderProgress.openAddDlg = function () {
        func.open({
            title: '添加进度表信息',
            content: Feng.ctxPath + '/orderProgress/add',
            tableId: OrderProgress.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    OrderProgress.openEditDlg = function (data) {
        func.open({
            title: '修改进度表信息',
            content: Feng.ctxPath + '/orderProgress/edit?id=' + data.id,
            tableId: OrderProgress.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    OrderProgress.exportExcel = function () {
        var checkRows = table.checkStatus(OrderProgress.tableId);
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
    OrderProgress.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/orderProgress/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(OrderProgress.tableId);
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
        elem: '#' + OrderProgress.tableId,
        url: Feng.ctxPath + '/orderProgress/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: OrderProgress.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        OrderProgress.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        OrderProgress.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        OrderProgress.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + OrderProgress.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            OrderProgress.openEditDlg(data);
        } else if (layEvent === 'delete') {
            OrderProgress.onDeleteItem(data);
        }
    });
});
