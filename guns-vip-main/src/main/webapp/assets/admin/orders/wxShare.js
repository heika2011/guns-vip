//alert(window.location.href.split('#')[0]);/***用于获得当前连接url用**/
/***用户点击分享到微信圈后加载接口接口*******/
$.post("/weixin/initWXJSSDKConfigInfo",{"shareUrl":window.location.href.split('#')[0]},function(data,status){
    data=eval("("+data+")");
    wx.config({
        beta: true,
        debug: false,
        appId: data.appid,
        timestamp:data.timestamp,
        nonceStr:data.nonceStr,
        signature:data.signature,
        jsApiList: ['shareAppMessage']
    });
    var shareTitle = $("#wx_share_span").data("shareTitle");
    if(!shareTitle){
        shareTitle = $("title").html();
    }
    var shareImg = $("#wx_share_span").data("shareImg");
    if(!shareImg){
        //shareImg = common.bp()+'/m_images/shareImg.jpg';
    }
    var shareLink = $("#wx_share_span").data("shareLink");
    if(!shareLink){
        shareLink = window.location.href.split('#')[0];
    }
    var shareDesc = $("#wx_share_span").data("shareDesc");
    if(!shareDesc){
        shareDesc = $("meta[name=description]").attr("content");
    }
    wx.ready(function(){
        //alert("准备分享");
        wx.invoke(
            "shareAppMessage", {
                title: shareTitle, // 分享标题
                desc:shareDesc , // 分享描述
                link: shareLink, // 分享链接
                imgUrl: shareImg // 分享封面
            }, function(res) {
                if (res.err_msg == "shareAppMessage:ok") {
                    history.back();
                }
                if (res.err_msg == "shareAppMessage:cancel") {
                    history.back();
                }
            }
        );
    });
    wx.error(function(res){
        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
    });
});