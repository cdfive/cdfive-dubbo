var toolbox = (function(){
	
	var paopaoHomePage = 'http://www.aipai.com/space.php?bid=1467001';
	var indexPage = '/mp3/index';
	var randomPage = '/mp3/random';
	
	var toolboxHtml = '<div id="toolbox">'		
					   + '<div class="btnClose btn hide" title="关闭">x</div>'
				       + '<div class="btnOpen btn show">√</div>'
				       + '<div class="btnHome btn show" title="返回首页"><img src="/static/js/plugin/toolbox/image/ico_home.png" /></div>'
				       + '<div class="btnRandom btn">R</div>'
				       + '<div class="btnPopKart btn">P</div>'
				       + '<div class="btnAbout btn hide" title="关于">关于</div>'
			        + '</div>';
	
	var dialogHtml = '<div id="txtNoteInner">'
					  + '<p style="text-indent: ;">听什么歌，你会想起什么人<span class="five">[cdfive since 2015-02-22]</span></div></p>'
					  + '<hr />'
					  + '<p style="text-align: left;">申明：本站所有音乐、图片均来源于互联网，仅用于个人学习和交流使用。</p>'
					  + '<p style="text-align: left;">版权归原作者或者公司所有，如侵犯到您的权益，请联系cdfive@qq.com删除。</p>'
				   + '</div>';
	
	var $toolbox;
	
	var curPage;
	
	function init(page) {
		curPage = page;
		$toolbox = $('#toolbox');
		if ($toolbox.length > 0) { return;}
		$('body').append(toolboxHtml);
		$toolbox = $('#toolbox');
		
		$toolbox.find(".btn").hover(function(){
			$(this).addClass("btnActive");
		},function(){
			$(this).removeClass("btnActive");
		});
		$toolbox.find(".btnClose").click(function(){
			$toolbox.find(".btnClose").hide();
			$toolbox.find(".btnAbout").hide();
			$toolbox.find(".btnOpen").show();
			$(".wrap").hide();
		});
		$toolbox.find(".btnOpen").click(function(){
			$toolbox.find(".btnClose").show();
			$toolbox.find(".btnAbout").show();
			$toolbox.find(".btnOpen").hide();
			$(".wrap").show();
		});
		$toolbox.find(".btnAbout").click(function(){
			ds.dialog({
				   title : '关于',
				   content : dialogHtml	  
			});
		});
		$toolbox.find(".btnHome").click(function(){
			window.location.href = indexPage;
		});
		
		$toolbox.find(".btnRandom").hover(function(){
			$(this).text("-RANDOM");
			$(this).animate().css("width", "auto");
		}, function() {
			$(this).text("R");
			$(this).animate().css("width", "20px");
		});
		
		$toolbox.find(".btnRandom").click(function() {
			window.location.href = randomPage;
		});
		
		$toolbox.find(".btnPopKart").hover(function(){
			$(this).text("-PopKart");
			$(this).animate().css("width", "auto");
		}, function() {
			$(this).text("P");
			$(this).animate().css("width", "20px");
		});
		
		$toolbox.find(".btnPopKart").click(function() {
			window.location.href = paopaoHomePage;
		});
		
		if (curPage == 'random') {
			$toolbox.find(".btnRandom").hide();
		} else {
			$toolbox.find(".btnHome").hide();
		}
	}
	
	return {init:init};
})();


