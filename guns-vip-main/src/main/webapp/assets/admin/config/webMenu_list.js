layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 客户表管理
     */
    var Customer = {
        tableId: "customerTable"
    };
    /**
     * 初始化表格的列
     */
    Customer.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'role_id', sort: true, title: '角色id'},
            {field: 'name', sort: true, title: '角色名字'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Customer.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(Customer.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    Customer.openAddDlg = function () {
        func.open({
            title: '添加前端配置',
            content: Feng.ctxPath + '/webMenu',
            tableId: Customer.tableId

        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    Customer.openEditDlg = function (data) {
        func.open({
            title: '修改前端配置',
            content: Feng.ctxPath + '/webMenu/edit?id=' + data.role_id,
            tableId: Customer.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    Customer.exportExcel = function () {
        var checkRows = table.checkStatus(Customer.tableId);
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
    Customer.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/webMenu/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Customer.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.state.msg + "!");
            });
            ajax.set("roleId", data.role_id);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Customer.tableId,
        url: Feng.ctxPath + '/webMenu/getlist',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Customer.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Customer.search();
    });
    $('#btnUn').click(function () {
        var queryData = {};
        queryData['un'] = "1";
        table.reload(Customer.tableId, {
            where: queryData, page: {curr: 1}
        });
    });
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Customer.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Customer.exportExcel();
    });


    // 工具条点击事件
    table.on('tool(' + Customer.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Customer.openEditDlg(data);
            layui.close(obj)
        } else if (layEvent === 'delete') {
            Customer.onDeleteItem(data);
            layui.close(obj)
        }

    });



});
