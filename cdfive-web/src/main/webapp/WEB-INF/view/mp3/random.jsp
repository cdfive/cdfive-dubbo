<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>A simple music player</title>
  <%@ include file="/WEB-INF/view/common/preload.jsp" %>
  <link href="/static/js/plugin/tapplayer/assets/css/tapplayer.css" rel="stylesheet">
</head>
<body>
<audio id="musicBox" controls style="display:none;"></audio>
  <div class="container" style="width:400px;">
    <div class="player-box">
      <div class="player-core">
        <div class="player-top"></div>
        <div class="player-body">
          <div class="artist-avatar" id="artist-avatar" style="background-image:url(/static/js/plugin/tapplayer/caches/artists/key.jpg);">
            <div class="disc-center"></div>
          </div>
          <div class="song-info">
            <h2 class="song-title" id="songTitle"></h2>
            <h4 class="song-meta">
              <span id="artistName"></span> / 
              <span id="albumName"></span>
            </h4>
          </div>
          <div class="progress-wrap">
            <span class="time-current" id="timeCurrent"></span>
            <span class="time-total" id="timeTotal"></span>
            <div class="progress-bar">
              <div class="progress-bar-indicator" id="progressBarIndicator"></div>
            </div>
          </div>
        </div>
        <div class="player-controler">
          <button class="player-button button-mode mode-shuffle" id="btnMode" title="随机循环"></button>
          <button class="player-button button-prev" id="btnPrev"></button>
          <button class="player-button button-pause" id="btnPlay"></button>
          <button class="player-button button-next" id="btnNext"></button>
          <button class="player-button button-volume" id="btnVolume"></button>
        </div>
      </div>
      <div class="album-background" id="album-background">
        <img class="new-album-background" src="/static/js/plugin/tapplayer/caches/artists/key.jpg">
        <img class="default-album-background" src="/static/js/plugin/tapplayer/assets/images/default_album.png">
      </div>
    </div>
  </div>
  <%@ include file="/WEB-INF/view/common/copyright.jsp" %>

  <script type="text/javascript" src="/static/plugin/jquery/jquery.1.10.2.js"></script>
  <link rel="stylesheet" href="/static/plugin/dsdialog/dsdialog.css" media="screen" type="text/css" />
  <script type="text/javascript" src="/static/plugin/dsdialog/dsdialog.js"></script>
  
  <link rel="stylesheet" href="/static/js/plugin/toolbox/toolbox.css" media="screen" type="text/css" />
  <script type="text/javascript" src="/static/js/plugin/toolbox/toolbox.js"></script>
  
  <script src="/static/js/plugin/tapplayer/js/tapplayer.js"></script>
  
</body>
</html>