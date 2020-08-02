var player = (function(){
	
	var $musicBox;
	var $playMode;
	var $musicList;
	var $playMsg;
	var $name;
	var $author;
	var $CD;
	var $picInfo;
	var $pop;
	var $songs;
	var $songInfo;
	var $infos;
	var $lrc;
	var $reasonTr;
	var $reasonDiv;
	
	var mp3List;
	
	
	var playMode = 1;
	var index = -1;
	var musicFiles;
	var length;
	var playMsg = null;
	var showerLastScrollTop = 0;
    var currentKey = 0;
    var lrcShower = "";
    var prevLine = "";
    var prevKey = null;
    var normalCss = "normalLyric";
    var currentCss = "redLyric";
    var offset = 0;
    var currentMusic;
    
    var lrcShower;
    var interval = 100;
    var $lrc = $("#lrc");
    var songTitle;
    var singer;
    var albumName;
    var lrcMaker;
    var offset;
    var textList = new Array();
    var timeList = new Array();
    var intTimeList = new Array();
    var textLrc = "";
    var textJoiner = "<br />";
    var currentKey = 0;
    var prevLine = "";
    var prevKey = null;
    var normalCss = "normalLyric";
    var currentCss = "redLyric";
    
    function unsetList(list, n){
        if(n < 0) {
            return list;
        }
        else{
            return list.slice(0, n).concat(list.slice(n + 1, list.length));
        }
    };
    
    function splitLrcString(lrcString) {
    	textList = lrcString.split(/\[.*?\]/);
        timeList = lrcString.split(/\][^\[]+/m);
        
        var tmp = 0;
        while(true) {
        	if (tmp == textList.length) {
        		break;
        	}
        	if (textList[tmp] == "") {
        		textList.splice(tmp,1);
        	} else {
        		tmp++;
        	}
        }
        tmp = 0;
        while(true) {
        	if (tmp == timeList.length) {
        		break;
        	}
        	if (timeList[tmp] == "") {
        		timeList.splice(tmp,1);
        	} else {
        		tmp++;
        	}
        }
        
        var deleteList = new Array();
        var item = "";
        var tmp = "";

        for (var i in timeList) {
            if (/\[ti:.*/.test(timeList[i])){
                songTitle = timeList[i].substr(4,timeList[i].length-4);
                deleteList.push(i);
            }
            else if (/\[ar:.*/.test(timeList[i])){
                singer = timeList[i].substr(4,timeList[i].length-4);
                deleteList.push(i);
            }
            else if (/\[al:.*/.test(timeList[i])){
                albumName = timeList[i].substr(4,timeList[i].length-4);
                deleteList.push(i);
            }
            else if (/\[by:.*/.test(timeList[i])){
                lrcMaker = timeList[i].substr(4,timeList[i].length-4);
                deleteList.push(i);
            }
            else if (/\[offset:.*/.test(timeList[i])){
                offset = timeList[i].substr(8,timeList[i].length-4);
                deleteList.push(i);
            }
            else{
                item = timeList[i].substr(1,timeList[i].length-1);

                tmp = item.split(/\]\w?\[/);
			
                if (tmp.length > 1){
                    item = tmp[0];

                    for(var j = 1 ; j < tmp.length ; ++j){
                        timeList.push(tmp[j]);
                        textList.push(textList[i]);
                    }
                }

                timeList[i] = item;
            }
        }

        deleteList = deleteList.sort();

        for ( i = 0 ; i < deleteList.length ; ++i){
            timeList = unsetList(timeList , deleteList[i]-i);
            textList = unsetList(textList , deleteList[i]-i);
        }
    }
    
    function sortLrcList() {
    	intTimeList = new Array();

        for(var i = 0; i < timeList.length; i++){
            try {
            	var min = timeList[i].split(":");      
            	var second = min[1].split(".");
            	intTimeList.push(parseInt(min[0]*60000) + parseInt(second[0]*1000) + parseInt(second[1])*1);
            } catch(e) {
            }
        }

        for(var i = 0 ; i < intTimeList.length - 1; ++i){
            for(var j = i + 1 ; j < intTimeList.length  ; ++j){
                if ( intTimeList[i] > intTimeList[j]){
                    var tmp = intTimeList[i];
                    intTimeList[i] = intTimeList[j];
                    intTimeList[j] = tmp;

                    tmp = timeList[i];
                    timeList[i] = timeList[j];
                    timeList[j] = tmp;

                    tmp = textList[i];
                    textList[i] = this._textList[j];
                    textList[j] = tmp;
                }
            }
        }
    }
    
    function joinLrcText() {
    	textLrc = "";
        for (var i = -5 ; i < 0 ; ++ i){
            //this._textLrc += "<span id='lyric"+i+"' class='prefix'>&nbsp;</span>" + this._textJoiner ; 
        }
        for (var i = 0 ; i < textList.length ; ++i){
            textLrc += "<span id='lyric"+i+"'>" + textList[i] + "</span>" + textJoiner ;
        }
    }
    
    function getCurrentTime() {
    	return $musicBox[0].currentTime * 1000;
    };
    
    function binarySearch(list , seekTime) {
    	var start = 0;
        var finish = list.length - 1;
        var mid = Math.floor( (start + length ) / 2) ;

        while(start <= finish){
            if (seekTime == list[mid] ){
                return mid;
            }
            else if(seekTime > list[mid] ){
                start = mid + 1;
            }
            else{
                finish = mid - 1;
            }
            mid = Math.floor( (start + finish ) / 2) ;
        }

        return mid;
    }
    
    function seekLrcText(seekTime){
        currentKey = binarySearch(intTimeList , seekTime);
    };
    
    function showCurrentLyric() {
    	var currentTime = getCurrentTime() + parseInt(offset);
        seekLrcText(currentTime);
        
        if (currentKey != prevKey && currentKey > -1){
            currentKey = parseInt(currentKey);
            
            var fontSize =   parseInt(lrcShower.style.fontSize.substr(0,lrcShower.style.fontSize.length-2));

            var lineHeight = parseInt(lrcShower.style.lineHeight.substr(0,lrcShower.style.lineHeight.length-2));

            var space = lineHeight - fontSize;
            
            var t1 = (currentKey * fontSize  + (currentKey - 1) * space) + 3 * lineHeight;
            var t2 = $lrc.height();
            var t3 = 0;
            if (t1 < t2) {
            	t3 = 0;
            }
            else {
            	
            	t3 = (parseInt((t1 - t2) / (t2/2)) + 1) * (t2/2);
            	
            }
            lrcShower.scrollTop = t3;
            var currentLine = document.getElementById("lyric"+currentKey);
            currentLine.className = currentCss;

            if ("" != prevLine){
                prevLine.className = normalCss;
            }
            
            prevLine = currentLine;
            prevKey = currentKey;
        }
    }
    
    function showLrc() {
		lrcShower = document.getElementById("lrc");
		interval=100;
		var lrcUri = "/2017" + currentMusic.path.replace(".mp3", ".lrc");
		$.ajax({
	        type: "get",
	        url: lrcUri,
	        success: function (data) {
	        	splitLrcString(data);
	        	sortLrcList();
	            joinLrcText();
	            $lrc.html(textLrc);
	            currentKey = 0;
	            setInterval(function(){showCurrentLyric();}, interval);
			}
		});
    }
	
    function play() {
		
		currentMusic = musicFiles[index];
		$.ajax({
	        type: "post",
	        // url: "http://www.cdfive.com:9090/api/v1/mp3/play" + '?r=' + Math.random(),
	        url: "http://localhost:9090/api/v1/mp3/play" + '?r=' + Math.random(),
	        dataType: "json",
	        data: {id:currentMusic.id},
	        success: function (data) {
	        	//console.log(data);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
		
		$lrc[0].scrollTop = 0;
		
		$playMsg.text(currentMusic.fullName);
		$songInfo.text(currentMusic.fullName);
		
		if (currentMusic.reason != null && currentMusic.reason != "") {
			$reasonDiv[0].innerHTML = currentMusic.reason;
			$reasonTr.show();
		} else {
			$reasonTr.hide();
		}
		
		var url = '/2017' + currentMusic.path;
		$musicBox.attr("src", url);
		
		$musicList.find("li").removeClass("liOn");
		$musicList.find("li:eq(" + index + ")").addClass("liOn");
		$musicBox[0].play();
		showLrc();
    }
    
    function nextMusic() {
		switch(parseInt(playMode)){
			case 1:
			index ++;
			if(index>=length){
				index = 0;
			}
			break;
			case 2:
			if(index == -1){
				index = 0;
			}
			break;
			case 3:
			index = Math.floor(Math.random()*length);
			break;
		}
		play();
	};
	
	function firstMusic() {
		index = 0;
		play();
	}
	
	function prevMusic() {
		if(index == 0){
			index = length-1; 
		}else{
			index --;
		}
		play();
	}
	
	function playOrPause() {
		$pop.addClass("Gray");
		var popVal = $pop.attr("imgVal");
		if(popVal == 0){
			$pop.attr("src","/static/image/mp3/play.png");
			$pop.attr("title","点击播放");
			$pop.attr("imgVal","1");
			$musicBox[0].pause();
		}else{
			$pop.attr("src","/static/image/mp3/pause.png");
			$pop.attr("title","点击暂停");
			$pop.attr("imgVal","0");
			$musicBox[0].play();
		}
	}
	
	function nextMusic() {
		var currentMusic = null;
		
		switch(parseInt(playMode)){
			case 1:
			index ++;
			if(index>=length){
				index = 0;
			}
			break;
			case 2:
			if(index == -1){
				index = 0;
			}
			break;
			case 3:
			index = Math.floor(Math.random()*length);
			
			break;
		}
		play();
	};
	
	function lastMusic() {
		if(index == 0){
			index = length-1; 
		}else{
			index--;
		}
		play();
	}
	
    function loadMusic() {
    	length = musicFiles.length;
		index = -1;
    	$musicList.html("");
    	
		for(var i in musicFiles){
			var html = "<li title='双击播放'";
			if (currentMusic != null && currentMusic.id == musicFiles[i].id) {
				html+= " class='liOn'";
			}
			html +=">" + musicFiles[i].fullName;
			html+= "</li>";
			$musicList.append(html);
		}
		
		$musicList.find("li").each(function(i) {
			$(this).off(".mp3").on("dblclick.mp3", function() {
				index = i;
				play();
			});
		});
    }
    
    function load() {
		loadMusic();
		var songheight = $songs.height();
		$infos.css("height",songheight+"px");
		$playMode.change(function(){
			playMode = $playMode.val();
		});
	};
	
	function init() {
		$musicBox = $("#musicBox");
		$playMode = $("#playMode");
		$musicList = $("#musicList");
		$playMsg = $("#playMsg");
		$name = $("#name");
		$author = $("#author");
		$CD = $("#CD");
		$picInfo = $("#picInfo");
		$pop =$("#pop");
		$songs = $("#songs");
		$songInfo = $("#songInfo");
		$infos = $("#infos");
		$lrc = $("#lrc");
		$reasonTr = $(".reasonTr");
		$reasonDiv = $(".reasonDiv");
		
		$.ajax({
	        // url : 'http://www.cdfive.com:9090/api/v1/mp3/all' +'?r='+Math.random(),
	        url : 'http://localhost:9090/api/v1/mp3/all' +'?r='+Math.random(),
	        dataType:'json',
	        type : 'post',
	        success : function(d) {
	        	if (d.code!='0') return;
	        	mp3List = d.data;
	        	
	        	$(".menu:eq(0)").find("a").each(function(i, ele) {
    				$(this).off(".mp3").on("click.mp3", function() {
    					$(".content a").removeClass("selected");
        				$(this).addClass("selected");
        				musicFiles = mp3List[$(this).attr("id")];
        				load();
    				});
    			});
	        	
	        	musicFiles = mp3List["d2018"];
	        	load();
	        	nextMusic();
	        	
	        	$musicBox[0].onended=nextMusic;
	        	
	        	$("#first").off(".mp3").on("click.mp3", function() {
	        		firstMusic();
	        	});
	        	
	        	$("#prev").off(".mp3").on("click.mp3", function() {
	        		prevMusic();
	        	});
	        	
	        	$("#pop").off(".mp3").on("click.mp3", function() {
	        		playOrPause();
	        	});
	        	
	        	$("#next").off(".mp3").on("click.mp3", function() {
	        		nextMusic();
	        	});
	        	
	        	$("#last").off(".mp3").on("click.mp3", function() {
	        		lastMusic();
	        	});
	        	
	        	$("#nav1 div").each(function(i,ele){
	        		$(this).off(".mp3").on("click.mp3", function(){
	        			$("#nav1 div").removeClass("sel").addClass("unsel");
	        			$(this).removeClass("unsel").addClass("sel");
	        			$("#line .line").removeClass("linesel").addClass("lineunsel");
	        			$("#line .line:eq(" + i + ")").removeClass("lineunsel").addClass("linesel");
	        			$(".menu").hide();
	        			$(".menu:eq(" + i + ")").show();
	        			
	        			$(".menu:eq(" + i + ")").find("a").each(function(i, ele) {
	        				$(this).off(".mp3").on("click.mp3", function() {
	        					$(".content a").removeClass("selected");
		        				$(this).addClass("selected");
		        				musicFiles = mp3List[$(this).attr("id")];
		        				load();
	        				});
	        			});
		        	});
	        	});
	        }
		});
	}
	
	return {init:init};
})();

$(function() {
	player.init();
	toolbox.init('index');
});

