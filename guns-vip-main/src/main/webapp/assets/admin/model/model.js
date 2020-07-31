layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var Model = {
        tableId: "modelTable"
    };

    /**
     * 初始化表格的列
     */
    Model.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, title: '款式主键'},
            {field: 'num', sort: true, title: '款式编号'},
            {field: 'customerName', sort: true, title: '客户'},
            {field: 'name', sort: true, title: '款式名'},
            {field: 'modelNum', sort: true, title: '款号'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Model.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(Model.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    Model.openAddDlg = function () {
        window.location.href=Feng.ctxPath + '/model/add'
    };
    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    Model.openEditDlg = function (data) {
        window.location.href=Feng.ctxPath + '/model/updateModel?num=' + data.num
    };

    /**
     * 导出excel按钮
     */
    Model.exportExcel = function () {
        var checkRows = table.checkStatus(Model.tableId);
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
    Model.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/model/deleteModel", function (data) {
                Feng.success("删除成功!");
                table.reload(Model.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.state.msg + "!");
            });
            ajax.set("num", data.num);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };
    sessionStorage.clear();
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Model.tableId,
        url: Feng.ctxPath + '/model/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Model.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Model.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Model.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Model.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Model.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Model.openEditDlg(data);
        } else if (layEvent === 'delete') {
            Model.onDeleteItem(data);
        }
    });
});
