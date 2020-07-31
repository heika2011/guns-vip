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
            {field: 'id', hide: true, title: '客户编号'},
            {field: 'num', sort: true, title: '客户编号'},
            {field: 'name', sort: true, title: '客户名称'},
            {field: 'contact', sort: true, title: '联系人'},
            {field: 'phone', sort: true, title: '联系电话'},
            {field: 'level', templet:function(d){
                switch (d.level) {
                    case 1:
                        return '一般'
                    case 2:
                        return  '中等'
                    case 3:
                        return '重要'
                }
                }, sort: true, title: '等级'},
            {field: 'requires',templet:function(d){
                    switch (d.requires) {
                        case 1:
                            return '严格'
                        case 2:
                            return  '普通'
                    }
                }, sort: true, title: '要求'},
            {field: 'settle',templet:function(d){
                    switch (d.settle) {
                        case 1:
                            return '月结'
                        case 2:
                            return  '现金'
                        case 3:
                            return  '记账'
                    }
                }, sort: true, title: '结算方式'},
            {field: 'openId',templet:function(d){
                    if(d.openId == '')
                        return  '<span style="color: #ff0000;">未对接</span>'
                    else
                        return '已对接'

                }, sort: true, title: '微信对接'},
            {field: 'createdTime', sort: true, title: '创建时间'},
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
            title: '添加客户',
            content: Feng.ctxPath + '/customer/add',
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
            title: '修改客户表',
            content: Feng.ctxPath + '/customer/edit?id=' + data.id,
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
            var ajax = new $ax(Feng.ctxPath + "/customer/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Customer.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.state.msg + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Customer.tableId,
        url: Feng.ctxPath + '/customer/list',
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

    //监听行单击事件（双击事件为：rowDouble）
    table.on('row(' + Customer.tableId +')', function(obj){
        var data = obj.data;
        var url = Feng.ctxPath + '/customer/weChatPeople?type=1'+ "&id="+data.id;
        if(data.openId != ''){
            url = Feng.ctxPath + '/customer/weChatPeople'+"?type=2"+ "&id="+data.id;
        }
        func.open({
            title: '查看微信用户',
            content: url,
            tableId: Customer.tableId
        });
        //标注选中样式
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
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
