layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var Teams = {
        tableId: "teamsTable"
    };

    /**
     * 初始化表格的列
     */
    Teams.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', title: '小组编号',sort: true},
            {field: 'name', sort: true, title: '小组名'},
            {field: 'type',templet:function(d){
                    if(d.type==1){
                        return '印花类小组';
                    }else if(d.type==2){
                        return '激光类小组';
                    }else if(d.type==3){
                        return '移膜类小组';
                    }else {
                        return '未设置';
                    }
                }, sort: true, title: '小组类型'},
            {field: 'leaderName',templet:function(d){
                for(var i in d.leader){
                    if(d.leader[i].type == 1)
                        return d.leader[i].name
                }
                    return''
                }, sort: true, title: '组长名字'},
            {field: 'viceName',templet:function(d){
                    for(var i in d.leader){
                        if(d.leader[i].type == 2)
                            return d.leader[i].name
                    }
                    return''
                }, sort: true, title: '副组长名字'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Teams.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(Teams.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    Teams.openAddDlg = function () {
        window.location.href = Feng.ctxPath + '/teams/add';
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    Teams.openEditDlg = function (data) {
        window.location.href = Feng.ctxPath + '/teams/edit?id=' + data.id;
    };

    /**
     * 导出excel按钮
     */
    Teams.exportExcel = function () {
        var checkRows = table.checkStatus(Teams.tableId);
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
    Teams.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/teams/deleteTeam", function (data) {
                Feng.success("删除成功!");
                table.reload(Teams.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.state.msg + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        };
        Feng.confirm("请注意，当前删除及时生效，删除该小组，该小组的生产统计等数据将会被清空，您确定吗？", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Teams.tableId,
        url: Feng.ctxPath + '/teams/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Teams.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Teams.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Teams.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Teams.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Teams.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Teams.openEditDlg(data);
        } else if (layEvent === 'delete') {
            Teams.onDeleteItem(data);
        }
    });
});
