
layui.use(['ax','upload','admin','layer'], function(){
    var $ = layui.jquery;
    var upload = layui.upload;
    var $ax = layui.ax;
    var admin = layui.admin;
    var layer = layui.layer;
    $("#btnSubmit").click(function () {
        var txt = "";
        $('.img').each(function () {
            txt = txt + ","  + $(this).attr("src");
        })
        txt = txt.substr(1,txt.length);
        var z = admin.getTempData("Infotype")+""+"_data";
        if(admin.getTempData(z)!=null){
            //判断不为空说明有值
            var datas = {id:admin.getTempData(z).id,type:admin.getTempData("Infotype").substring(1),url:txt}
            var ajax = new $ax(Feng.ctxPath + "/model/updateModelImage",function (data) {
                if(data.state.code == 0){
                    admin.putTempData(admin.getTempData("Infotype")+''+'_data',datas);
                    layer.msg("修改成功")
                }else{
                    layer.msg(data.state.msg)
                }
            });
            ajax.set(datas);
            ajax.start();
            admin.closeThisDialog();
            return;
        }
        var data = {type:admin.getTempData("Infotype").substring(1),url:txt}
        var ajax = new $ax(Feng.ctxPath + "/model/addModelInfoImage",function (data) {
            if(data.state.code == 0){
                admin.putTempData(admin.getTempData("Infotype"),data.data);
                layer.msg("创建成功")
            }else{
                layer.msg(data.state.msg)
            }
        });
        ajax.set(data);
        ajax.start();
        admin.closeThisDialog();
    })
    upload.render({
        elem: '#slide-pc',
        url:Feng.ctxPath + '/fileUploads',
        exts: 'jpg|png|jpeg',
        multiple: true,
        before: function(obj) {
        layer.msg('图片上传中...', {
            icon: 16,
            shade: 0.01,
            time: 0
        })
    },
    done: function(res) {
        layer.close(layer.msg());//关闭上传提示窗口
        if(res.state.code != 0) {
            return layer.msg(res.state.msg);
        }
        //$('#slide-pc-priview').append('<input type="hidden" name="pc_src[]" value="' + res.filepath + '" />');
        $('#slide-pc-priview').append('<li class="item_img"><div class="operate"><i class="toleft layui-icon">《</i><i class="toright layui-icon">》</i><i  class="close layui-icon"></i></div><img src="' + res.data + '" class="img" ><input type="hidden" name="pc_src[]" value="' + res.data + '" /></li>');
    }
});
    var z = admin.getTempData("Infotype")+""+"_data";
    if(admin.getTempData(z) != null){
        console.log(admin.getTempData("Infotype"))
        var data = admin.getTempData(z);
        var s = data.url.split(",");
        for(var i=0;i<s.length;i++){
            $('#slide-pc-priview').append('<li class="item_img"><div class="operate"><i class="toleft layui-icon">《</i><i class="toright layui-icon">》</i><i  class="close layui-icon"></i></div><img src="' + s[i] + '" class="img" ><input type="hidden" name="pc_src[]" value="' + s[i] + '" /></li>');
        }
    }

    //点击多图上传的X,删除当前的图片
    $("#pics").on("click",".close",function(){
        $(this).closest("li").remove();
        var path = $(this).closest("li").find('img').attr("src");
        var ajax = new $ax(Feng.ctxPath + "/deleteFile?path="+path);
        ajax.start();
    });
//多图上传点击<>左右移动图片
    $("#pics").on("click",".pic-more ul li .toleft",function(){
        var li_index=$(this).closest("li").index();
        if(li_index>=1){
            $(this).closest("li").insertBefore($(this).closest("ul").find("li").eq(Number(li_index)-1));
        }
    });
    $("#pics").on("click",".pic-more ul li .toright",function(){
        var li_index=$(this).closest("li").index();
        $(this).closest("li").insertAfter($(this).closest("ul").find("li").eq(Number(li_index)+1));
    });
});

