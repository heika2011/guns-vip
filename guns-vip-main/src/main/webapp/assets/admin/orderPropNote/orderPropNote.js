layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var OrderPropNote = {
        tableId: "orderPropNoteTable"
    };

    /**
     * 初始化表格的列
     */
    OrderPropNote.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'orderNum', sort: true, title: '生产单编号'},
            {field: 'flagDo', sort: true, title: '是否完成'},
            {field: 'text', sort: true, title: '内容'},
            {field: 'overTime', sort: true, title: '预计结束时间'},
            {field: 'userName', sort: true, title: '名字'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    OrderPropNote.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(OrderPropNote.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    OrderPropNote.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/orderPropNote/add',
            tableId: OrderPropNote.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    OrderPropNote.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/orderPropNote/edit?=' + data.,
            tableId: OrderPropNote.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    OrderPropNote.exportExcel = function () {
        var checkRows = table.checkStatus(OrderPropNote.tableId);
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
    OrderPropNote.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/orderPropNote/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(OrderPropNote.tableId);
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
        elem: '#' + OrderPropNote.tableId,
        url: Feng.ctxPath + '/orderPropNote/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: OrderPropNote.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        OrderPropNote.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        OrderPropNote.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        OrderPropNote.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + OrderPropNote.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            OrderPropNote.openEditDlg(data);
        } else if (layEvent === 'delete') {
            OrderPropNote.onDeleteItem(data);
        }
    });
});
