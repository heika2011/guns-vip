layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var OrderTeam = {
        tableId: "orderTeamTable"
    };

    /**
     * 初始化表格的列
     */
    OrderTeam.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'orderNum', sort: true, title: '订单编号'},
            {field: 'teamId', sort: true, title: '小组id'},
            {field: 'type', sort: true, title: '1 定位 2点数 3刮板 4总检台上 5 总检后道 6总检后道'},
            {field: 'userName', sort: true, title: '员工名字'},
            {field: 'userId', sort: true, title: '员工Id'},
            {field: 'sureFlag', sort: true, title: '是否同意'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    OrderTeam.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(OrderTeam.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    OrderTeam.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/orderTeam/add',
            tableId: OrderTeam.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    OrderTeam.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/orderTeam/edit?=' + data.,
            tableId: OrderTeam.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    OrderTeam.exportExcel = function () {
        var checkRows = table.checkStatus(OrderTeam.tableId);
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
    OrderTeam.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/orderTeam/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(OrderTeam.tableId);
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
        elem: '#' + OrderTeam.tableId,
        url: Feng.ctxPath + '/orderTeam/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: OrderTeam.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        OrderTeam.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        OrderTeam.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        OrderTeam.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + OrderTeam.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            OrderTeam.openEditDlg(data);
        } else if (layEvent === 'delete') {
            OrderTeam.onDeleteItem(data);
        }
    });
});
