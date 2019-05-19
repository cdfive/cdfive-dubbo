<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>A simple music player</title>
<%@ include file="/WEB-INF/view/common/preload.jsp" %>
<link href="/static/css/mp3/music.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="/static/css/mp3/style.css" media="screen" type="text/css" />
<script type="text/javascript" src="/static/plugin/jquery/jquery.1.10.2.js"></script>
<link rel="stylesheet" href="/static/plugin/dsdialog/dsdialog.css" media="screen" type="text/css" />
<script type="text/javascript" src="/static/plugin/dsdialog/dsdialog.js"></script>

<link rel="stylesheet" href="/static/js/plugin/toolbox/toolbox.css" media="screen" type="text/css" />
<script type="text/javascript" src="/static/js/plugin/toolbox/toolbox.js"></script>
<script type="text/javascript" src="/static/js/mp3/index.js"></script>
</head>
<body style="text-align: center;">
	<div id="box" style="text-align: left; margin-left: auto; margin-right: auto; display: table;">
		
		<div class="wrap red">
			<div class="shadow"></div>
			<div class="ball"></div>
		</div>

		<div class="wrap blue">
			<div class="shadow"></div>
			<div class="ball"></div>
		</div>

		<div class="wrap green">
			<div class="shadow"></div>
			<div class="ball"></div>
		</div>

		<div class="playDiv">
			<div class="currentMusic">
				<span class="currentSpan">当前正在播放：<span id="playMsg"></span></span>
			</div>
			<div style="background-color: #000;">
				<audio id="musicBox" controls style="width:99%;padding-left:2px;"></audio>
			</div>
			<div class="currentBtns">
				<div id="lastMusic" class="lastMusic">
					<img id="first" title="第一首歌曲" src="/static/image/mp3/first.png" />&nbsp;&nbsp; 
					<img id="prev" title="上一首歌曲" src="/static/image/mp3/last.png" />&nbsp;&nbsp;
					<img id="pop" title="点击暂停" src="/static/image/mp3/pause.png" imgVal="0" />&nbsp;&nbsp; 
					<img id="next" title="下一首歌曲" src="/static/image/mp3/next.png" />&nbsp;&nbsp; 
					<img id="last" title="最后一首歌曲" src="/static/image/mp3/end.png" />
				</div>
				<div id="play"></div>
				<div id="nextMusic"></div>
			</div>
		</div>
		
		<div id="navDiv">
		<table id="nav1" width="610" height="30" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td width="86" class="sel" id="dh1"><div align="center">一</div></td>
				<td width="6"></td>
				<td width="86" class="unsel" id="dh2"><div align="center">二</div></td>
				<td width="6"></td>
				<td width="86" class="unsel" id="dh3"><div align="center">三</div></td>
			</tr>
		</table>
		<table id="line" width="610" height="3" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td width="194" height="6" id="line1" class="linesel line"></td>
				<td width="13"><div align="center"></div></td>
				<td width="194" id="line2" class="line"></td>
				<td width="13"><div align="center"></div></td>
				<td width="196" id="line3" class="line"></td>
			</tr>
		</table>
			<table width="610" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr id="menu1" class="menu" style="display: ''">
					<td class="content">
						<a id="d2018" href="javascript:void(0);" style="width:65px;" class="selected">2018</a>
						<a id="d2017" href="javascript:void(0);" style="width:65px;">2017</a>
						<a id="d2016" href="javascript:void(0);" style="width:65px;">2016</a>
						<a id="d1" href="javascript:void(0);">1</a>
						<a id="d2" href="javascript:void(0);">2</a>
						<a id="d3" href="javascript:void(0);">3</a>
						<a id="d4" href="javascript:void(0);">4</a>
						<a id="d5" href="javascript:void(0);">5</a>
				</td>
				</tr>
				<tr id="menu2" class="menu" style="display: none">
					<td class="content">
						<a id="dyu" href="javascript:void(0);">鱼</a>
						<a id="dliZhi" href="javascript:void(0);">励志</a>
						<a id="daiQing" href="javascript:void(0);">爱情</a>
						<a id="dwenNuan" href="javascript:void(0);">温暖</a>
						<a id="dman" href="javascript:void(0);" style="width:auto;">Learn to be a Man</a>
					</td>
				</tr>
				<tr id="menu3" class="menu" style="display: none">
					<td class="content">
						<a id="den" href="javascript:void(0);">英语</a>
						<a id="dyy" href="javascript:void(0);">粤语</a>
						<a id="dwt" href="javascript:void(0);">无题</a>
						<a id="ddqxh" href="javascript:void(0);">单曲循环</a>
						<a id="dspec" href="javascript:void(0);">spec</a>
					</td>
				</tr>
				<tr class="reasonTr">
					<td>
						<div class="reasonDiv"></div>						
					</td>
				</tr>
			</table>
		</div>
		
		<div style="width: 100%">
			<div class="songStyle float_l">
				<h3>
					歌曲列表
					<div class="float_r">
						<span class="playMode"> <select id="playMode">
								<option value="1">全部循环</option>
								<option value="2">单曲循环</option>
								<option value="3">随机播放</option>
						</select>
						</span>
					</div>
				</h3>
				<div id="songs" onselectstart="return false;">
					<ul id="musicList">
					</ul>
				</div>
			</div>
			<div class="infoStyle float_l" style="">
					<h3>歌曲信息</h3>
					<div id="infos">
						<div class="info float_l">
							<div>
								<strong><span id="songInfo"></span></strong>
							</div>
							<div id="lrc" style="font-size: 12px; line-height: 20px;"></div>
						</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

