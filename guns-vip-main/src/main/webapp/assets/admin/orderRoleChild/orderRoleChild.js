layui.use(['table', 'admin', 'ax', 'func','index'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var index = layui.index;

    /**
     * 管理
     */
    var OrderRoleChild = {
        tableId: "orderRoleChildTable"
    };

    /**
     * 初始化表格的列
     */
    OrderRoleChild.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id',sort: true , title: '编号'},
            {field: 'name', sort: true, title: '名称'},
            {field: 'type',templet:function(d){
                switch(d.type){
                    case '0':return '未下单';
                    case '1':return '开发';
                    case '14':return '同款首样';
                    case '2':return '首样';
                    case '3':return '改版';
                    case '4':return '翻样';
                    case '5':return '产前样';
                    case '6':return '首单';
                    case '7':return '翻单';
                    case '8':return '补片（打样）';
                    case '9':return '补片（大货）';
                    case '10':return '返工（打样）';
                    case '11':return '返工（大货）';
                    case '12':return '重做（打样）';
                    case '13':return '重做（大货）';
                }
                }, sort: true, title: '生产单进度'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    OrderRoleChild.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(OrderRoleChild.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    OrderRoleChild.openAddDlg = function () {
        sessionStorage.clear()
        window.location.href = Feng.ctxPath + '/orderRoleChild/add';
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    OrderRoleChild.openEditDlg = function (data) {
        sessionStorage.clear()
        window.location.href = Feng.ctxPath + '/orderRoleChild/edit?id=' + data.id;
    };

    /**
     * 导出excel按钮
     */
    OrderRoleChild.exportExcel = function () {
        var checkRows = table.checkStatus(OrderRoleChild.tableId);
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
    OrderRoleChild.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/orderRoleChild/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(OrderRoleChild.tableId);
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
        elem: '#' + OrderRoleChild.tableId,
        url: Feng.ctxPath + '/orderRoleChild/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: OrderRoleChild.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        OrderRoleChild.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        OrderRoleChild.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        OrderRoleChild.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + OrderRoleChild.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            OrderRoleChild.openEditDlg(data);
        } else if (layEvent === 'delete') {
            OrderRoleChild.onDeleteItem(data);
        }
    });
});
