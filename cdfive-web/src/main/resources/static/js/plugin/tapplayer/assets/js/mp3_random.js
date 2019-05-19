function MusicObj(name,fullName,url,author,cd,people,namelen,reason){
	var _this = this;
	this.name = name;
	this.fullName = fullName;
	this.url = url;
	this.author = author;
	this.CD = cd;
	this.people = people;
	this.namelen=namelen;
	this.reason=reason;
}

function canplay() {
	debugger;
}

index = -1;
colume = null;

function playPrev() {
	if (index > 0) {
		index--;
	} else {
		index = mp3_list.length - 1;
	}
	play();
}

function playNext() {
	if (index < mp3_list.length) {
		index++;
	} else {
		index = 0;
	}
	play();
}

function play() {
	mp3 = mp3_list[index];
	$(musicBox_H).attr("src", "/static/mp3" + mp3.url);
	$("#song-title").text(mp3.name);
	$("#artist-name").text(mp3.author);
	$("#album-name").text(mp3.name);
	musicBox_H.play();
}

$(function(){
	musicBox_H = document.getElementById("musicBox");
	
	musicBox_H.addEventListener("loadedmetadata",function(){
		var duration = musicBox_H.duration;
		var min = parseInt(duration / 60);
		var second = parseInt(duration % 60);
		if (second <= 9) {
			second = "0" + second;
		}
		$("#time-total").text(min + ":" + second);
		
	});
	musicBox_H.addEventListener("timeupdate",function(){
		var currentTime = musicBox_H.currentTime;
		var duration = musicBox_H.duration;
		var percent = currentTime*100.0/duration;
		
		var min = parseInt(currentTime / 60);
		var second = parseInt(currentTime % 60);
		if (second <= 9) {
			second = "0" + second;
		}
		$("#time-current").text(min + ":" + second);
		
		$("#progress-bar-indicator").css("width", percent + "%");
	});
	
	musicBox_H.addEventListener("ended",function() {
		playNext();
	});
	
	$("#button-mode").click(function(){
		ds.dialog({
			   title : '-_-!',
			   content : "功能开发中..."	  
		});
	});
	
	$("#button-play").click(function() {
		if (musicBox_H.paused) {
			musicBox_H.play();
			$(this).removeClass("button-play").addClass("button-pause");
		} else {
			musicBox_H.pause();
			$(this).removeClass("button-pause").addClass("button-play");
		}
	});
	
	$("#button-prev").click(function() {
		playPrev();
	});
	
	$("#button-next").click(function() {
		playNext();
	});
	
	
	$("#button-volumn").click(function() {
		var tmpVolume = musicBox_H.volume;
		if (tmpVolume > 0) {
			volume = tmpVolume;
			musicBox_H.volume = 0;
			$(this).removeClass("button-volumn").addClass("button-mute");
		} else {
			musicBox_H.volume = volume;
			$(this).removeClass("button-mute").addClass("button-volumn");
		}
	});
	
	playNext();
});