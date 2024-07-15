var tapplayer = (function(){
	var $musicBox;
	var $timeCurrent;
	var $timeTotal;
	var $progressBarIndicator;
	var $songTitle;
	var $artistName;
	var $albumName;
	
	var $btnMode;
	var $btnPrev;
	var $btnPlay;
	var $btnNext;
	var $btnVolume;
	
	var mp3List;
	var index = -1;
	var volume = null;
	
	function playPrev() {
		if (index > 0) {
			index--;
		} else {
			index = mp3List.length - 1;
		}
		play();
	}

	function playNext() {
		if (index < mp3List.length - 1) {
			index++;
		} else {
			index = 0;
		}
		play();
	}

	function play() {
		var mp3 = mp3List[index];
		$musicBox.attr("src", "/2017" + mp3.path);
		$songTitle.text(mp3.songName);
		$artistName.text(mp3.author);
		$albumName.text(mp3.songName);
		$musicBox[0].play();
		
		$.ajax({
	        type: "post",
	        // url: "http://api.cdfive.com/api/v1/mp3/play" + '?r=' + Math.random(),
	        url: "http://gateway.cdfive.com/api/v1/mp3/song/play" + '?r=' + Math.random(),
	        // url: "http://localhost:9090/api/v1/mp3/play" + '?r=' + Math.random(),
	        dataType: "json",
	        data: {id:mp3.id},
	        success: function (data) {
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
	
	function init() {
		$musicBox = $("#musicBox");
		$timeCurrent = $("#timeCurrent");
		$timeTotal = $("#timeTotal");
		$progressBarIndicator = $("#progressBarIndicator");
		$songTitle = $("#songTitle");
		$artistName = $("#artistName");
		$albumName = $("#albumName");
		
		$btnMode = $("#btnMode");
		$btnPrev = $("#btnPrev");
		$btnPlay = $("#btnPlay");
		$btnNext = $("#btnNext");
		$btnVolume = $("#btnVolume");
		
		$musicBox[0].addEventListener("loadedmetadata",function(){
			var duration = $musicBox[0].duration;
			var min = parseInt(duration / 60);
			var second = parseInt(duration % 60);
			if (second <= 9) {
				second = "0" + second;
			}
			$timeTotal.text(min + ":" + second);
			
		});
		$musicBox[0].addEventListener("timeupdate",function(){
			var currentTime = $musicBox[0].currentTime;
			var duration = $musicBox[0].duration;
			var percent = currentTime*100.0/duration;
			
			var min = parseInt(currentTime / 60);
			var second = parseInt(currentTime % 60);
			if (second <= 9) {
				second = "0" + second;
			}
			$timeCurrent.text(min + ":" + second);
			$progressBarIndicator.css("width", percent + "%");
		});
		
		$musicBox[0].addEventListener("ended",function() {
			playNext();
		});
		
		$btnMode.click(function(){
			ds.dialog({
				   title : '-_-!',
				   content : "功能开发中..."	  
			});
		});
		
		$btnPlay.click(function() {
			if ($musicBox[0].paused) {
				$musicBox[0].play();
				$(this).removeClass("button-play").addClass("button-pause");
			} else {
				$musicBox[0].pause();
				$(this).removeClass("button-pause").addClass("button-play");
			}
		});
		
		$btnPrev.click(function() {
			playPrev();
		});
		
		$btnNext.click(function() {
			playNext();
		});
		
		
		$btnVolume.click(function() {
			var tmpVolume = $musicBox[0].volume;
			if (tmpVolume > 0) {
				volume = tmpVolume;
				$musicBox[0].volume = 0;
				$(this).removeClass("button-volumn").addClass("button-mute");
			} else {
				$musicBox[0].volume = volume;
				$(this).removeClass("button-mute").addClass("button-volumn");
			}
		});
		
		$.ajax({
	        url : 'http://gateway.cdfive.com/api/v1/mp3/song/random_list' + '?r=' + Math.random(),
	        // url : 'http://api.cdfive.com/api/v1/mp3/random_list' + '?r=' + Math.random(),
	        // url : 'http://localhost:9090/api/v1/mp3/random_list' + '?r=' + Math.random(),
			data: {num:20},
	        dataType:'json',
	        type : 'post',
	        success : function(d) {
	        	if (d.code!=200) return;
	        	mp3List = d.data;
	        	playNext();
	        }
		});
	}
	return {init:init};
})();

$(function(){
	tapplayer.init();
	toolbox.init('random');
});