/**
 * 添加或者修改页面
 */
var OrderPropInfoDlg = {
    data: {
        orderNum: "",
        type: "",
        typeName: "",
        name: "",
        doOver: "",
        sx: ""
    }
};

layui.use(['transfer', 'layer', 'util'], function() {
    var $ = layui.$
        , transfer = layui.transfer
        , layer = layui.layer
        , util = layui.util;

    //模拟数据
    var data1 = [
        {"value": "1", "title": "李白"}
        , {"value": "2", "title": "杜甫"}
        , {"value": "3", "title": "苏轼"}
        , {"value": "4", "title": "李清照"}
        , {"value": "5", "title": "鲁迅", "disabled": true}
        , {"value": "6", "title": "巴金"}
        , {"value": "7", "title": "冰心"}
        , {"value": "8", "title": "矛盾"}
        , {"value": "9", "title": "贤心"}
    ]

    //基础效果
    transfer.render({
        elem: '#test1'
        , data: data1
    })
    $("input[name='layTransferRightCheckAll']").change(function () {
        console.log(123)
    });
})
