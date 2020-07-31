/**
 * 添加或者修改页面
 */
var modelInfoForm = {
    data: {
        num: "",
        name: "",
        customer: "",
        modelNum: "",
        drawNum: "",
        screenNum: "",
        linling: "",
        linlingWhere: "",
        yinhuaWhere: "",
        linlingFrom: "",
        createdTime: "",
        updateTime: "",
        status: ""
    }
};
layui.use(['form', 'admin', 'ax','table','func'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var table = layui.table;
    var func = layui.func;

    form.on('select(hc_select)', function (data) {   //选择移交单位 赋值给input框
        $("#HandoverCompany").val(data.value);
        $("#hc_select").next().find("dl").css({ "display": "none" });
        form.render();
    });
    window.search = function () {
        var value = $("#HandoverCompany").val();
        $("#hc_select").val(value);
        form.render();
        $("#hc_select").next().find("dl").css({ "display": "block" });
        var dl = $("#hc_select").next().find("dl").children();
        var j = -1;
        for (var i = 0; i < dl.length; i++) {
            if (dl[i].innerHTML.indexOf(value) <= -1) {
                dl[i].style.display = "none";
                j++;
            }
            if (j == dl.length-1) {
                $("#hc_select").next().find("dl").css({ "display": "none" });
            }
        }

    }
    $('input[name^="t"]').each(function () {
        admin.putTempData($(this).attr('name'),$(this).val())
    })
    form.on('select(gongyi)', function (data) {   //选择移交单位 赋值给input框
        $("#gongyi").val(data.value);
        $("#gongyi").next().find("dl").css({ "display": "none" });
        form.render();
    });
    window.search = function () {
        var value = $("#gongyi").val();
        $("#gongyi").val(value);
        form.render();
        $("#gongyi").next().find("dl").css({ "display": "block" });
        var dl = $("#gongyi").next().find("dl").children();
        var j = -1;
        for (var i = 0; i < dl.length; i++) {
            if (dl[i].innerHTML.indexOf(value) <= -1) {
                dl[i].style.display = "none";
                j++;
            }
            if (j == dl.length-1) {
                $("#gongyi").next().find("dl").css({ "display": "none" });
            }
        }

    }
    form.on('select(weizhi)', function (data) {   //选择移交单位 赋值给input框
        $("#weizhi").val(data.value);
        $("#weizhi").next().find("dl").css({ "display": "none" });
        form.render();
    });
    window.search = function () {
        var value = $("#weizhi").val();
        $("#weizhi").val(value);
        form.render();
        $("#weizhi").next().find("dl").css({ "display": "block" });
        var dl = $("#weizhi").next().find("dl").children();
        var j = -1;
        for (var i = 0; i < dl.length; i++) {
            if (dl[i].innerHTML.indexOf(value) <= -1) {
                dl[i].style.display = "none";
                j++;
            }
            if (j == dl.length-1) {
                $("#weizhi").next().find("dl").css({ "display": "none" });
            }
        }

    }
    //表单提交事件
    form.on('submit(btnSubmitForInfo)', function (data) {
        var s ="";
        //取图片id
        for(var i=1;i<7;i++){
            if(admin.getTempData("t"+i)!=null){
                s = s + ','+admin.getTempData("t"+i);
            }
        }
        s = s.substring(1);

        var datas = {id:data.field.id,num: data.field.num,names:data.field.names,color:data.field.color,colorFrom:data.field.colorFrom,linling:$("#HandoverCompany").val(),sizes:data.field.sizes,places:$("#weizhi").val(),craft:$("#gongyi").val(),piece:data.field.piece,note:data.field.note,imageId:s}
        console.log(datas)
        var ajax = new $ax( Feng.ctxPath + '/model/updateModelInfoData',function (result) {
            if(result.state.code==0){
                admin.putTempData('modelNum',result.data)

                Feng.success('修改成功')

                admin.putTempData("formOk",true)
                admin.putTempData("url",Feng.ctxPath + '/model/getModelInfoAndImage?num=' + (admin.getTempData("modelNum") == null? '':admin.getTempData("modelNum")))

                admin.closeThisDialog();
            }
        })
        ajax.set(datas);
        ajax.start();
        return false;
    });

    var htm = "";
    var ajax = new $ax(Feng.ctxPath + '/model/getModelInfoImage?id='+$("input[name='t1']").val()+"&type=1",function (data) {
        $("#image").html("");
        var s = data.data.url.split(",");
        console.log(s)
        for(var i=0;i<s.length;i++){
            htm = htm + "<img src=\""+s[i]+"\" style=\"width: 80px;height: 80px\"/>";
        }
        $("#image").html(htm);
    })
    ajax.start();

    $('button[id^="t"]').click(function () {

        var z = $(this).attr("id")+""+"_data";
        //将类型送过去
        admin.putTempData("Infotype",$(this).attr("id"));
        //判断数据是否事null
        if(admin.getTempData($(this).attr("id")) != null){
            var ajax = new $ax(Feng.ctxPath + '/model/getModelInfoImage?id='+admin.getTempData($(this).attr("id"))+"&type="+$(this).attr("id").substr(1),function (data) {
                admin.putTempData(z,data.data);
            })
            ajax.start();
            func.open({
                title: '添加裁片图片',
                content: Feng.ctxPath + '/model/addInfoImage',
            });
            return;
        }
        func.open({
            title: '添加裁片图片',
            content: Feng.ctxPath + '/model/addInfoImage',
        });
    })
});