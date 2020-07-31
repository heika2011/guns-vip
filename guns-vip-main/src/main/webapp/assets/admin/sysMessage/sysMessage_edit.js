/**
 * 详情对话框
 */
var SysMessageInfoDlg = {
    data: {
        type: "",
        toJob: "",
        toName: "",
        createdTime: "",
        msg: ""
    }
};

layui.use(['form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/sysMessage/detail?id=" + Feng.getUrlParam("id"));
    var result = ajax.start();
    form.val('sysMessageForm', result.data);

    form.on('checkbox(checkAllForRole)',function (data) {
        $("input[name='role']").each(function (index,item){
            item.checked = data.elem.checked;
        })
        form.render()
    })
    form.on('checkbox(checkAllForUser)',function (data) {
        $("input[name='user']").each(function (index,item){
            item.checked = data.elem.checked;
        })
        form.render()
    })
    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var role = "";
        $("input[name='role']:checked").each(function () {
            role =role+ ","+$(this).val();
        })
        var user = "";
        $("input[name='user']:checked").each(function () {
            user =user+ ","+$(this).val();
        })
        role = role.substring(1,role.length)
        user = user.substring(1,user.length)
        var datas = {id:data.field.id,type:data.field.type,toJob:role,toName:user};
        var ajax = new $ax(Feng.ctxPath + "/sysMessage/editItem", function (data) {
            Feng.success("更新成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();

        }, function (data) {
            Feng.error("更新失败！" + data.responseJSON.state.msg)
        });
        ajax.set(datas);
        ajax.start();

        return false;
    });

});