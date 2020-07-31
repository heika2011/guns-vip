/**
 * 添加或者修改页面
 */
var ModelColorInfoDlg = {
    data: {
        modelId: "",
        textColor: "",
        sum: "",
        color: ""
    }
};

layui.use(['form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    if(admin.getTempData("sum")!=null){
        $("input[name='sum']").val(admin.getTempData("sum"))
        form.render();
    }
    //表单提交事件
    form.on('submit(btnSubmitForColor)', function (data) {
        var datas = {modelId: admin.getTempData("modelNum") ==null?'': admin.getTempData("modelNum"),textColor:data.field.textColor,sum:data.field.sum,color:data.field.color}
        var linlingColor = data.field.sum;
        var ajax = new $ax(Feng.ctxPath + "/model/addColor", function (data) {
            if(data.state.code == 0){
                Feng.success("添加成功！");
                admin.putTempData('modelNum',data.data)
                admin.putTempData("sum",linlingColor);
                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);
                admin.putTempData("url",Feng.ctxPath + '/model/getColorList?modelId=' + (admin.getTempData("modelNum") == null? '9999':admin.getTempData("modelNum")))

                //关掉对话框
                admin.closeThisDialog();
            }
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.state.msg)
        });
        ajax.set(datas);
        ajax.start();

        return false;
    });

});