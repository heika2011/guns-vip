layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var OrderNum = {
        tableId: "orderNumTable"
    };

    /**
     * 初始化表格的列
     */
    OrderNum.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'num', sort: true, title: '款式id'},
            {field: 'orderNum', sort: true, title: '生产单编号'},
            {field: 'color', sort: true, title: '颜色'},
            {field: 'sum', sort: true, title: '数量'},
            {field: 'units', sort: true, title: '单位'},
            {field: 'realyCut', sort: true, title: '实裁数'},
            {field: 'realyPoint', sort: true, title: '实点数'},
            {field: 'badLinling', sort: true, title: '坏布'},
            {field: 'badF', sort: true, title: '印坏'},
            {field: 'outTime', sort: true, title: '出库时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    OrderNum.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(OrderNum.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    OrderNum.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/orderNum/add',
            tableId: OrderNum.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    OrderNum.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/orderNum/edit?=' + data.,
            tableId: OrderNum.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    OrderNum.exportExcel = function () {
        var checkRows = table.checkStatus(OrderNum.tableId);
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
    OrderNum.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/orderNum/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(OrderNum.tableId);
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
        elem: '#' + OrderNum.tableId,
        url: Feng.ctxPath + '/orderNum/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: OrderNum.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        OrderNum.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        OrderNum.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        OrderNum.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + OrderNum.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            OrderNum.openEditDlg(data);
        } else if (layEvent === 'delete') {
            OrderNum.onDeleteItem(data);
        }
    });
});
