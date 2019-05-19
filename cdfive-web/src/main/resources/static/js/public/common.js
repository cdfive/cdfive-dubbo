//# sourceURL=common.js
var form={
	getData:function($form) {
		var _formData = $form.serialize().replace(/\+/g, '%20');//空格会被转成 \+,真正的加号不会；
		var _formDataArr = _formData.split('&');
		var _formDataObj = {};
		for (var i = 0, len = _formDataArr.length; i < len; i++) {
			var _arr = _formDataArr[i].split('=');
			var _key=decodeURIComponent(_arr[0]);
			var _val=decodeURIComponent(_arr[1]);
			var _type=$form.find('input[name="'+_key+'"]').attr('type');
			if(_type=='checkbox'){
				if(!_formDataObj[_key]){
					_formDataObj[_key]=[];
				}
				_formDataObj[_key].push(_val)
			}else{
				_formDataObj[_key] =_val;	
			}
		}
		return _formDataObj;
	},
	setData:function($form,data){
		for(var _key in data){
			var _data=data[_key]
			if(tool.isArray(_data)){
				var $ele=$form.find('[name='+_key+']');
				for(var i=0,l=_data.length;i<l;i++){
					$form.find('[name='+_key+'][value='+_data[i]+']').prop('checked',true);
				}
			}else{
				var $ele=$form.find('[name='+_key+']');
				if ($ele.length==0) {continue;}
				var _type=$ele.attr('type');
				var tagName = $ele[0].tagName;
				if(_type=='radio'){
					$form.find('[name='+_key+'][value='+_data+']').prop('checked',true);
				} else if (tagName == 'SELECT'){
//					$ele.find('option').prop("selected", false);
//					$ele.find('option').each(function() {
//						if ($(this).text() == _data) {
//							$(this).prop("selected",true);
//							return false;
//						}
//					});
					$ele.find('option[value="' + _data + '"]').prop("selected", true);
				} else {
					$ele.val(_data);
				}
			}
		}
	},
	clearData:function($form) {
		$form.find('input[type="text"]').val('');
		$form.find('input[type="hidden"]').val('');
		$form.find('input[type="checkbox"]').prop('checked', false);
		$form.find('select option').prop("selected", false);
	}
}

var str={
	isOver:function(str,len){ //判断字符串长度是否已经超过限定的长度 
		var strLen=this.getLenth(str);
		if(strLen>len){
			return true;
		}else{
			return false;
		}
	},
	getLenth:function(str) {  //字符串常用方法
	    ///<summary>获得字符串实际长度，中文2，英文1</summary>  
	    ///<param name="str">要获得长度的字符串</param>  
	    var realLength = 0, len = str.length, charCode = -1;  
	    for (var i = 0; i < len; i++) {  
	        charCode = str.charCodeAt(i);  
	        if (charCode >= 0 && charCode <= 128) realLength += 1;  
	        else realLength += 2;  
	    }  
	    return realLength;  
	},
	isEmpty:function(str){
		if (str === null || str === undefined || str === '' || str === 'null') {
			return true;
		} 
		return false;
	},
	format:function(str){
		if(this.isEmpty(str)){
			return '';
		}else{
			return str;
		}
	},
	validSpecialChars:function(str){
		var _reg = /[`~!@#$%^&*()_+<>?:"{},;，、。》']/im; 
		if(_reg.test(str)){
			return true;
		}else{
			return false;
		}
	}
};

var sidebar = {
	time : 500,
	show : function($sidebar) {
		// ele.siblings().hide();
		$sidebar.animate({
			"right" : "0"
		}, 500);
	},
	hide : function($sidebar) {
		$sidebar.animate({
			"right" : "-200%"
		}, 500, function() {
//			$('.sidebar-item').hide();
		});
	}
}

/**
 * 通过模板将数据填充进dom
 * @param templateId 模板id
 * @param jqueryId jquery获取dom的id或class
 * @param data 数据
 */
function templateHtml(templateId, domId, data) {
	  var html = template(templateId, data);
	  $(domId).html(html);
}

/**
 * 通过模板将数据填充进dom,append方式
 * @param templateId 模板id
 * @param jqueryId jquery获取dom的id或class
 * @param data 数据
 */
function templateHtmlAppend(templateId, domId, data) {
	  var html = template(templateId, data);
	  $(domId).append(html);
}

/**
 * 通过模板将数据填充进dom
 * @param templateHtml 模板html
 * @param domId jquery获取dom的id或class
 * @param data 数据
 */
function templateCompileHtml(templateHtml, domId, data) {
	  var html = template.compile(templateHtml)(data);
	  $(domId).html(html);
}

/**
 * 通过模板将数据填充进dom,append方式
 * @param templateHtml 模板html
 * @param domId jquery获取dom的id或class
 * @param data 数据
 */
function templateCompileHtmlAppend(templateHtml, domId, data) {
	  var html = template.compile(templateHtml)(data);
	  $(domId).append(html);
}

template.helper('timeCost', function (startTimeStr, endTimeStr) {
	var startDate = new Date(startTimeStr);
	var endDate = new Date(endTimeStr);
	var cost = (endDate - startDate) / 1000.0;
	return cost;
});

template.helper('substr', function (str, start, len) {
	return str.substr(start, len);
});

var jqGridCommonSetting = {
	datatype : "local",
	mtype : 'post',// 以post或get方式向数据源请求数据
	datatype : "json",// json,xml,local
	viewrecords : true,
	styleUI : "Bootstrap",
	multiselect : false,
	responsive : true,
	rownumbers : true,
	autowidth : true,
	height : '400px',
	forceFit : true,
	shrinkToFit : true,
	loadui : 'disable',
	page : 1,
	pagerpos : "center",
	pgtext : "{0}共{1}页",
	toppager : true// 显示顶部分页
};

function reloadJqGrid($jqGrid, param) {
	if (param) {
		$jqGrid.setGridParam({
			postData : param
		}).trigger("reloadGrid");
	} else {
		$jqGrid.trigger("reloadGrid");
	}
}

var msg={
	loading:function(){
		   layer.load(4, {shade : [ 0.1, '#000' ]});
	},
	success:function(msg){
		layer.msg(msg, {icon: 1,shade:0.2,shadeClose:true});
	},
	error:function(msg){
		layer.msg(msg, {icon: 2,shade:0.2,shadeClose:true});
	},
	tip:function(msg){
		layer.msg(msg,{offset:'100px'});
	},
	confirm:function(msg,successFun,defaultFun){
		layer.confirm(msg, {
			  btn: ['确定','取消'] //按钮
			}, function(){
				layer.closeAll();
				successFun();
			}, function(){
				layer.closeAll();
				if(defaultFun){
					defaultFun();
				}
			});
	},
	prompt:function(title, successFun){
		layer.prompt({
			title: title, 
			formType: 2},
			function(text,index){
			    layer.close(index);
			    successFun(text);
			});
	},
	open:function(title, dom, width, height){
		layer.open({
			type : 1,
			title : title,
			area : [ width, height ], // 宽高
			content :dom
		});
	},
	openIframe:function(title, url, width, height){
		layer.open({
			  type: 2,
			  title : title,
			  shadeClose: true,
			  shade: 0.8,
			  area : [ width, height ], // 宽高
			  content : url
			}); 
	}
};


var successStatusCode='0';//成功状态码
var statusCode={
		'0':false,
		'502':'频繁操作'
};

function checkSessionStatus(xhr) {
	var sessionStatus = xhr.getResponseHeader('session_status');
	if (sessionStatus == 'timeout') {
		layer.alert('登录超时，请重新登录', function() {
			sendAjax(LOGOUT,null,function() {
				window.location.href = "/";
			});
		});
		return false;
	} else if (sessionStatus == "other_login") {
		layer.alert('您的账号已在其它地点登录', function() {
			sendAjax(LOGOUT,null,function() {
				window.location.href = "/";
			});
		});
		return false;
	} else {
		return true;
	}
}

$.ajaxSetup({
	cache: false,
	complete : function(xhr, status) {
		if (!checkSessionStatus(xhr)) return;
	}
});

function sendAjaxOri(url, param, succFunc,errorFunc,comFunc) {
	$.ajax({
        url : url+"?r="+Math.random(),
        dataType:"json",
        data : param,
        type : "POST",
        beforeSend : function(xmlHttp) {
            msg.loading();
    	},
        error:function(e){
         	layer.closeAll();
        	msg.error('系统错误');
        	if(errorFunc){
        		errorFunc({
        			status:'error'
        		});
        	}
        },
    	complete : function(xhr, status) {
    		if (!checkSessionStatus(xhr)) return;
    		var _resData={};
    		if(status=='success'){
    			_resData=$.parseJSON(xhr.responseText.replace(/'null'/g,''))
    		}
    		if(comFunc){
    			comFunc(_resData, status);
        	}
    	},
        success : function(d) {
        	layer.closeAll();
            var res = eval(d);
        	if(succFunc){
        		succFunc(res);
        	}
        }
    });
}

function sendAjax(url, param,succFunc,errorFunc,comFunc) {
	  $.ajax({
	        url : url+"?r="+Math.random(),
	        dataType:"json",
	        data : param,
	        type : "POST",
	        beforeSend : function(xmlHttp) {
	            msg.loading();
	    	},
	        error:function(e){
	         	layer.closeAll();
	        	msg.error('系统错误');
	        	if(errorFunc){
//	        		当请求失败返回对象中的状态status为error
	        		errorFunc({
	        			status:'error'
	        		});
	        	}
	        },
	    	complete : function(xhr, status) {
	    		if (!checkSessionStatus(xhr)) return;
	    		var _resData={};
	    		if(status=='success'){
	    			_resData=$.parseJSON(xhr.responseText.replace(/'null'/g,''))
	    		}
	    		if(comFunc){
	    			comFunc(_resData, status);
	        	}
	    	},
	        success : function(d) {
	        	layer.closeAll();
	            var res = eval(d);
	        	if(res.code==successStatusCode){
	        		if(succFunc){
	        			succFunc(res.data);
	        		}
	        	}else{
	        		msg.error(res.msg);
//	        		请求成功但是状态码不正确，返回对象中status为success,和返回数据data
	        		if(errorFunc){
		        		errorFunc({
		        			status:'success',
		        			data:d
		        		});
		        	}
	        	}
				//修改此处传值
	        }
	    });
}

var uploadSeting = {
	"width" : 120,
	'height' : 30,
	'buttonText' : '上传图片',
	'fileSizeLimit' : '20MB',
	'multi':false,//是否可以多文件上传
	'fileTypeExts' : '*.gif; *.jpg; *.png;',
	//'buttonImage' : '',
	'fileTypeDesc' : '支持格式:jpg/gif/png/',
	'swf' : "/static/plugin/uploadify/uploadify.swf", // 指定上传控件的主体文件
	'uploader' : UPLOAD, // 指定服务器端上传处理文件
	// 上传到服务器，服务器返回相应信息到data里
//		'onUploadStart':function() {
//			layer.msg('正在导入，请耐心等待。。。',{shade:0.3});
//		},
//		'onUploadSuccess' : function(fileObj, data, response) {
//			layer.closeAll();
//		},
	'onFallback' : function() {
		alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
	}
}

var timeTicker = (function() {
	function getTime(date) {  
	    var str;
	    var yy = date.getYear();  
	    if(yy<1900) yy = yy+1900;  
	    var MM = date.getMonth()+1;  
	    if(MM<10) MM = '0' + MM;  
	    var dd = date.getDate();  
	    if(dd<10) dd = '0' + dd;  
	    var hh = date.getHours();  
	    if(hh<10) hh = '0' + hh;  
	    var mm = date.getMinutes();  
	    if(mm<10) mm = '0' + mm;  
	    var ss = date.getSeconds();  
	    if(ss<10) ss = '0' + ss;  
	    var ww = '星期' + ['日','一','二','三','四','五','六'][date.getDay()];
	    str = yy + "-" + MM + "-" + dd + " " + hh + ":" + mm + ":" + ss + "  " + ww;  
	    return str;  
	};  
	function tick(id) {
	    var today = new Date();
	    $(id).text(getTime(today));  
	    window.setTimeout("timeTicker.tick('" + id + "')", 1000);  
	};  
	return {tick:tick};
})();

$(function() {

});