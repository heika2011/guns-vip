layui.use(['table', 'admin', 'ax', 'func','form'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;

    /**
     * 管理
     */
    var PrintLog = {
        tableId: "printLogTable"
    };
//监听指定开关
    form.on('switch(switchTest)', function(data){
        var ajax = new $ax("/printLog/updateAutoPrint");
        if(this.checked){
            ajax.set({flag:1});
        }else {
            ajax.set({flag:0});
        }
        ajax.start();
        layer.tips('温馨提示：自动打印是指下单后动作的功能', data.othis)
    });
    /**
     * 初始化表格的列
     */
    PrintLog.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, title: '日志id'},
            {field: 'orderNum', sort: true, title: '生产单编号'},
            {field: 'name', sort: true, title: '操作人名字'},
            {field: 'url', sort: true, title: '文件地址'},
            {field: 'status', templet:function (d){
                if(d.status == 1){
                    return '打印完成';
                }else if(d.status == 0){
                    return '未打印';
                }else if(d.status == 2){
                    return '正在打印';
                }else if(d.status == 4){
                    return '打印失败！失败原因未知';
                }
                },sort: true, title: '打印状态'},
            {field: 'createdTime', sort: true, title: '打印时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };
    table.on('row(' + PrintLog.tableId +')', function(obj){
        var data = obj.data;
        if(data.url != ''){
            window.location.href=data.url;
        }
        //标注选中样式
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
    });
    /**
     * 点击查询按钮
     */
    PrintLog.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(PrintLog.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    PrintLog.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/printLog/add',
            tableId: PrintLog.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    PrintLog.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/printLog/edit?id=' + data.id,
            tableId: PrintLog.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    PrintLog.exportExcel = function () {
        var checkRows = table.checkStatus(PrintLog.tableId);
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
    PrintLog.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/printLog/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(PrintLog.tableId);
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
        elem: '#' + PrintLog.tableId,
        url: Feng.ctxPath + '/printLog/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: PrintLog.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        PrintLog.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        PrintLog.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        PrintLog.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + PrintLog.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            PrintLog.openEditDlg(data);
        } else if (layEvent === 'delete') {
            PrintLog.onDeleteItem(data);
        }
    });
});
