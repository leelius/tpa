/**
 * 用户管理页面，用ajax上传图片
 */
$(document).ready(function() {
	//------------
	$("#update_btn_wechat_headimgurl").click(function() {			
        var uploadfile = $("#uploadfile").val();
        //alert('准备更新用户头像:' + uploadfile);
        if(uploadfile==''){
            $("#uploadinfo").html("请先选择需要上传的头像图片！");
        }else{                  
            //构建新的表单提交数据（只提交文本域）
            var form  = $("<form id='formUpload' method='post' enctype='multipart/form-data'></form>");
            // 设置属性  
            form.attr('action', 'admin/ajaxheadimgupload');  
            form.attr('method', 'post');                
            var uploadfile = $("#uploadfile");
            form.append(uploadfile.clone()); //获取文件选择控件添加到新表单
            $("#uploadinfo").before(uploadfile); //克隆添加到原来表单
            form.ajaxSubmit({
                //提交前的回调函数
                beforeSubmit: function () {
                    $("#uploadinfo").html("正在上传图片...");
                    $("#uploadinfo").after("<img src='bssets/i/loadingAnimation.gif'  id='loading'/>").hide();
                },
                //提交后的回调函数
                success: function (data) {
                    //alert('fileName='+data.fileName);
                    $('#wechat_headimg').attr('src',data.fileName);
                    $('#wechat_headimgurl').val(data.fileName);
                    $('#myModal').modal('hide');
                    $("#loading").remove();
                },
                error: function (error) { alert(error); },
                url: 'admin/ajaxheadimgupload', /*设置post提交到的页面*/
                type: "post", /*设置表单以post方法提交*/
                dataType: "json" /*设置返回值类型为文本*/
            });
        }
    });
});