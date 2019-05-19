$(function() {
	var $body = $('body');
	$body.on('click', '.siderbar-nav-items>a', function(e) {
		e.preventDefault();/* 阻止a标签的默认行为 */
		$(this).parent().find('.sub-menu').slideToggle(300);
		$(this).find('span').toggleClass('icon-angle-left').toggleClass('icon-angle-down');
//		loadContent($(this));
//		$(this).parent().siblings().find('.sub-menu').slideUp(300);
//		$(this).parent().siblings().find('a').find('span').addClass('icon-angle-left').removeClass('icon-angle-down');
		
		var ele = $(this);
		var href = ele.prop('href');
		if(href){
			$('#js-index-contain-content').load(href+'?r='+Math.random(),'',function(d) {
				ele.parents('.siderbar-nav-items').siblings().removeClass('current');
				ele.parents('.siderbar-nav-items').addClass('current');
				ele.parents('.siderbar-nav').find("ul.sub-menu li").removeClass('current');
//				ele.parent().addClass('current');
			});
		}
	});
	
	function loadContent(ele) {
		var _href = ele.prop('href');
		var _indexContent = $('#js-index-contain-content');
		var _curHref = location.href;
		if(_href){
			_indexContent.load(_href+'?r='+Math.random(),'',function(d) {
				ele.parents('.siderbar-nav-items').siblings().removeClass('current');
				ele.parents('.siderbar-nav-items').addClass('current');
				ele.parents('.siderbar-nav').find("ul.sub-menu li").removeClass('current');
				ele.parent().addClass('current');
			});
		}else{
			layer.msg('开发中...');
		}
	}
	
	sendAjax(USER_SHOW, {}, function(data) {
		var html = template('tpl-side-nav', {"list" : data.menus});
		$('#js_ul_siderbarNav').html(html);
//		$("#js_ul_siderbarNav li:eq(0) ul").show();
//		$('#js-index-contain-content').load('/admin/dashboard'+'?r='+Math.random(),'',function(d) {
//			
//		});
		
		$("#js_ul_siderbarNav a:eq(0)").click();
	});
	
	$body.on('click', ".js-side-nav", function(e) {
		e.preventDefault();/* 阻止a标签的默认行为 */
		loadContent($(this));
	});
	
	$body.on('click', '.index-header-user', function(event) {
		event.stopPropagation();/* 阻止事件冒泡 */
		$(this).find('.index-header-submenu').addClass('db');
	});
	$body.on('click', function() {
		$(this).find('.index-header-submenu').removeClass('db');
	});

	$("#js_btn_change_password").click(function() {
		$('.index-header-submenu').removeClass('db');
		event.stopPropagation();
		msg.openIframe("修改密码", "/admin/change_password", "500px", "300px");
	});
	
	$("#js_btn_logout").click(function() {
		msg.confirm("确定要退出系统吗？",function() {
			sendAjax(LOGOUT,null,function() {
				window.location.href = "/login2017";
			});
		});
	});
	
	timeTicker.tick("#js_h2_curTime");
});