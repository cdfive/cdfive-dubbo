$(function(){
	//页面获取kindEditor富文本内容window.editor.html();
	window.editor = KindEditor.create('textarea.kindEditor',{
		autoHeightMode : true,
		width : "100%", //编辑器的宽度为100%
		filterMode : false, //不会过滤HTML代码
		//打开autoSetDataMode模式后会降低输入性能，理想的做法是关闭autoSetDataMode，提交数据前执行KE.util.setData。
	    //true时自动将编辑器内容设置到原来的textarea，也就是每次输入内容就执行KE.util.setData函数。
	    autoSetDataMode : false,
	    //关闭浏览服务器图片功能。
	    allowFileManager : false,
	    //关闭远程图片应用
	    allowImageRemote:false,
	    //图片上传地址；成功返回JSONObject{"error":0,"url":pictureUrl},失败返回JSONObject{"error":1,"message":"errorMessage"}
	    uploadJson : "../product/fileupload/kindeditorUpload.do",
		afterCreate : function() {
			this.loadPlugin('autoheight');
		}
    });
});