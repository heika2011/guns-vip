layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var SysMessage = {
        tableId: "sysMessageTable"
    };

    /**
     * 初始化表格的列
     */
    SysMessage.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, title: '消息通知id'},
            {field: 'type',templet:function(d){
                switch (d.type) {
                    case 1:
                        return '跟单下单完成'
                    case 2:
                        return '制版完成'
                    case 3:
                        return '晒版完成'
                    case 4:
                        return '调色完成'
                    case 5:
                        return '打样完成'
                    case 6:
                        return '分派任务完成'
                    case 7:
                        return '生产完成'
                    case 8:
                        return '财务完成'
                }
                }, title: '通知类型'},
            {field: 'name', title: '角色'},
            {field: 'userName',templet:function(d){
                if(d.userName=="")
                    return '全部'
                    else
                        return d.userName
                }, sort: true, title: '用户'},
            {field: 'createdTime', sort: true, title: '创建时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    SysMessage.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(SysMessage.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    SysMessage.openAddDlg = function () {
        window.location.href = Feng.ctxPath + '/sysMessage/add';
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    SysMessage.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/sysMessage/edit?id=' + data.id,
            tableId: SysMessage.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    SysMessage.exportExcel = function () {
        var checkRows = table.checkStatus(SysMessage.tableId);
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
    SysMessage.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/sysMessage/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(SysMessage.tableId);
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
        elem: '#' + SysMessage.tableId,
        url: Feng.ctxPath + '/sysMessage/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: SysMessage.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        SysMessage.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        SysMessage.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        SysMessage.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + SysMessage.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            SysMessage.openEditDlg(data);
        } else if (layEvent === 'delete') {
            SysMessage.onDeleteItem(data);
        }
    });
});
