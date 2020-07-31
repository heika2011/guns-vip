layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var ModelImage = {
        tableId: "modelImageTable"
    };

    /**
     * 初始化表格的列
     */
    ModelImage.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, title: '裁片图片id'},
            {field: 'modelinfoId', sort: true, title: '裁片信息Id'},
            {field: 'type', sort: true, title: '裁片对应位置'},
            {field: 'url', sort: true, title: '图片url'},
            {field: 'num', sort: true, title: '款式编号'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ModelImage.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(ModelImage.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    ModelImage.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/modelImage/add',
            tableId: ModelImage.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    ModelImage.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/modelImage/edit?id=' + data.id,
            tableId: ModelImage.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    ModelImage.exportExcel = function () {
        var checkRows = table.checkStatus(ModelImage.tableId);
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
    ModelImage.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/modelImage/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(ModelImage.tableId);
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
        elem: '#' + ModelImage.tableId,
        url: Feng.ctxPath + '/modelImage/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: ModelImage.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ModelImage.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ModelImage.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        ModelImage.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + ModelImage.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ModelImage.openEditDlg(data);
        } else if (layEvent === 'delete') {
            ModelImage.onDeleteItem(data);
        }
    });
});
