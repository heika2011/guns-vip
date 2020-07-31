layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var ModelInfo = {
        tableId: "modelInfoTable"
    };

    /**
     * 初始化表格的列
     */
    ModelInfo.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, title: '裁片id'},
            {field: 'num', sort: true, title: '款式id'},
            {field: 'color', sort: true, title: '裁片颜色'},
            {field: 'colorFrom', sort: true, title: '裁片依据；1,，按文件，2，按纸样，3，按点位，4，按样衣'},
            {field: 'sizes', sort: true, title: '裁片大小'},
            {field: 'places', sort: true, title: '裁片位置'},
            {field: 'craft', sort: true, title: '工艺：1，手工，2，数码'},
            {field: 'piece', sort: true, title: '有无拼缝：1，有 2，无'},
            {field: 'createdTime', sort: true, title: '创建时间'},
            {field: 'updateTime', sort: true, title: '修改时间'},
            {field: 'names', sort: true, title: '裁片名称'},
            {field: 'muchs', sort: true, title: '裁片单价'},
            {field: 'note', sort: true, title: '备注'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ModelInfo.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(ModelInfo.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    ModelInfo.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/modelInfo/add',
            tableId: ModelInfo.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    ModelInfo.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/modelInfo/edit?id=' + data.id,
            tableId: ModelInfo.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    ModelInfo.exportExcel = function () {
        var checkRows = table.checkStatus(ModelInfo.tableId);
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
    ModelInfo.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/modelInfo/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(ModelInfo.tableId);
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
        elem: '#' + ModelInfo.tableId,
        url: Feng.ctxPath + '/modelInfo/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: ModelInfo.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ModelInfo.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ModelInfo.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        ModelInfo.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + ModelInfo.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ModelInfo.openEditDlg(data);
        } else if (layEvent === 'delete') {
            ModelInfo.onDeleteItem(data);
        }
    });
});
