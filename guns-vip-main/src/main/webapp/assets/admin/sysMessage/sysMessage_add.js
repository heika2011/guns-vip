/**
 * 添加或者修改页面
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

    var u = admin.getTempData(admin.getTempData("data"))

    if(u != null){
            $("input[name='role']").each(function (index,item){
                var s = u.split('|')[0].split(",")
                for(var i=0;i<s.length;i++){
                    if(item.value == s[i]){
                        item.checked = true;
                    }
                }
            })
            $("input[name='user']").each(function (index,item){
                var s = u.split('|')[0].split(",")
                for(var i=0;i<s.length;i++){
                    if(item.value == s[i]){
                        item.checked = true;
                    }
                }
            })
            $("textarea[name='desc']").html(u.split('|')[1]);
            form.render();
    }else {
        var temp = getUrlParam('param');
        if(temp != null || temp != ''){
            u = admin.putTempData(admin.getTempData("data"),temp)
        }
    }
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
    form.on('select(typeA)',function (data) {
        if(data.value == 1){
            $('#role').css("display",'inline-block')
            $('#people').css("display",'none')
        }else if(data.value == 2){
            $('#role').css("display",'none')
            $('#people').css("display",'inline-block')
        }else if(data.value == 3){
            $('#role').css("display",'none')
            $('#people').css("display",'none')
        }
    })
    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var temp = "";
        $("input[name='role']:checked").each(function () {
            temp =temp+ ","+$(this).val();
        })
        $("input[name='user']:checked").each(function () {
            temp =temp+ ","+$(this).val();
        })
        temp = temp + "|" + $("textarea[name='desc']").val();
        temp = temp.substring(1,temp.length)
        admin.putTempData(admin.getTempData("data"),temp)
        Feng.success("添加成功！");

        //传给上个页面，刷新table用
        admin.putTempData('formOk', true);

        //关掉对话框
        admin.closeThisDialog();
        return false;
    });

});
function getUrlParam(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r!=null) return unescape(r[2]); return null;
}