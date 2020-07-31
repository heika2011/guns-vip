
layui.use(['form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/teams/detailinfo?id=" + Feng.getUrlParam("id"));
    var result = ajax.start();

    if(result.data.leaderId == ""){
        form.val('teamsForm', {
            "zhiwu":0,
            "leaderId":result.data.id
        });
    }else if(result.data.viceId == ""){
        form.val('teamsForm', {
            "zhiwu":1
        });
    }else{
        form.val('teamsForm', {
            "zhiwu":2
        });
    }
    if(result.data.userId == ""){
        form.val('teamsForm', {
            "username":0
        });
    }else{
        $(".op").each(function (index,ele) {
            if(result.data.userId == $(this).val()){
                form.val('teamsForm', {
                    "username":result.data.userId
                });
            }
        })
    }
    form.val('teamsForm', result.data);
    //表单提交事件
    form.on('submit(btnSubmits)', function (data) {
        console.log($(data.form.username))
        var datas = {id:$(data.form.id).val(),name:$("#username option:selected").text(),leaderId:$(data.form.zhiwu).val() == 0?"":$(data.form.id).val(),userId:$(data.form.username).val(),viceId:$(data.form.zhiwu).val() == 1?"":$(data.form.viceId).val(),teamName:$(data.form.teamName).val(),teamNum:$(data.form.teamNum).val()}
        var ajax = new $ax(Feng.ctxPath + "/teams/editItem", function (data) {
            Feng.success("更新成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();

        }, function (data) {
            Feng.error("更新失败！" + data.responseJSON.message)
        });
        ajax.set(datas);
        ajax.start();

        return false;
    });

});