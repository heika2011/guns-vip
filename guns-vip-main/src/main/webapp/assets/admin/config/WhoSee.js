
layui.use(['form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

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

        var role  = new Array();
        $("input[name='role']:checked").each(function () {
            role.push($(this).val())
        })
        var user = new Array();
        $("input[name='user']:checked").each(function () {
            user.push($(this).val())
        })
        var datas = {role:role,user:user}

        $.ajax({
            url: Feng.ctxPath + "/permission/updatePer",
            method: 'post', // 注意这里，传递对象给后台，这里必须是 POST 否则无法将对象封装到POST的body中流
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8', // 注意这里，传递对象给后台，这里必须是 application/json
            data: JSON.stringify(datas) ,// 注意这里，传递对象给后台，这里必须将对象进行序列化
            success:function (result) {
                if(result.state.code == 0){
                    Feng.success("保存成功，重新登录即可生效");

                    //传给上个页面，刷新table用
                    admin.putTempData('formOk', true);

                    //关掉对话框
                    admin.closeThisDialog();
                }
            }
        })
        return false;
    });

});