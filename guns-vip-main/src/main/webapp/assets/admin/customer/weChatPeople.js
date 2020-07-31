layui.use(['form', 'admin', 'ax','laytpl'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laytpl = layui.laytpl;

    var getTpl = searchText.innerHTML;
    var people = new Map();
    $(".deleteForPeople").each(function () {
        var s = $(this).attr("data").split("|||||");
        people.set(s[0],s[1])
    })
    $('body').on('keyup',"input[type='text']",function () {
        laytpl(getTpl).render({"val":$(this).val(),"isShow":$(this).val()}, //json值,isShouw为了那边做出判断使用
            function(html){
                $("#cam").html(html);//jquery把模板加载到div   id是cam中<div id="cam"></div>
                console.log(html);
                form.render(null,"cam"); //更新这个容器中的页面
            });
    })

    $('body').on('click','.changeWe',function () {

        if(people.get($(this).attr("data").split("|||||")[0])!=null){
            Feng.error("请不要重复勾选");
        }else{
            people.set($(this).attr("data").split("|||||")[0],$(this).attr("data").split("|||||")[1]);
            $("#names").append(" <a class='deleteForPeople' data='"+$(this).attr("data").split("|||||")[0]+"'>"+$(this).attr("data").split("|||||")[0]+"<i class=\"layui-icon layui-icon-delete\"></i></a>");
        }
        /*people.forEach(function (value,key) {
            console.log(key + value)
        },people)*/
    })
    $('body').on('click','.deleteForPeople',function () {
        people.delete($(this).attr("data").split("|||||")[0]);
        $(this).remove();
    })
    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var name = "";
        var v = "";
        people.forEach(function (value,key) {
            name = name  + ",,,," +key;
            v = v + ","+value;
        })
        name = name.substring(4,name.length);
        v = v.substring(1,v.length);
        if(name == ""){
            Feng.error("信息不能为空");
        }else{
            var ajax = new $ax(Feng.ctxPath + "/customer/updateWeChatUser", function (data) {
                Feng.success("提交成功！");

                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);

                //关掉对话框
                admin.closeThisDialog();

            }, function (data) {
                Feng.error("修改失败！" + data.state.msg)
            });
            ajax.set({id:data.field.id,openId:v,wName:name});
            ajax.start();
        }
        return false;
    });

});
function isContains(str, substr) {
    console.log(substr)
    return str.indexOf(substr) >= 0;
}