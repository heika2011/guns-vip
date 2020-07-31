layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var SysCount = {
        tableId: "sysCountTable"
    };

    /**
     * 初始化表格的列
     */
    SysCount.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, title: '价格计算id'},
            {field: 'type', sort: true, title: '类型'},
            {field: 'value', sort: true, title: '值'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    SysCount.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(SysCount.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    SysCount.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/sysCount/add',
            tableId: SysCount.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    SysCount.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/sysCount/edit?id=' + data.id,
            tableId: SysCount.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    SysCount.exportExcel = function () {
        var checkRows = table.checkStatus(SysCount.tableId);
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
    SysCount.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/sysCount/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(SysCount.tableId);
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
        elem: '#' + SysCount.tableId,
        url: Feng.ctxPath + '/sysCount/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: SysCount.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        SysCount.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        SysCount.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        SysCount.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + SysCount.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            SysCount.openEditDlg(data);
        } else if (layEvent === 'delete') {
            SysCount.onDeleteItem(data);
        }
    });
});
