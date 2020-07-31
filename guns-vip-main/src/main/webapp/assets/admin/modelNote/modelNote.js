layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var ModelNote = {
        tableId: "modelNoteTable"
    };

    /**
     * 初始化表格的列
     */
    ModelNote.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, title: '裁片日志id'},
            {field: 'num', sort: true, title: '款式编号'},
            {field: 'text', sort: true, title: '裁片内容'},
            {field: 'image', sort: true, title: '裁片图片'},
            {field: 'name', sort: true, title: '日志发起人姓名'},
            {field: 'createdTime', sort: true, title: '日志创建时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ModelNote.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(ModelNote.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    ModelNote.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/modelNote/add',
            tableId: ModelNote.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    ModelNote.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/modelNote/edit?id=' + data.id,
            tableId: ModelNote.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    ModelNote.exportExcel = function () {
        var checkRows = table.checkStatus(ModelNote.tableId);
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
    ModelNote.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/modelNote/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(ModelNote.tableId);
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
        elem: '#' + ModelNote.tableId,
        url: Feng.ctxPath + '/modelNote/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: ModelNote.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ModelNote.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ModelNote.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        ModelNote.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + ModelNote.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ModelNote.openEditDlg(data);
        } else if (layEvent === 'delete') {
            ModelNote.onDeleteItem(data);
        }
    });
});
