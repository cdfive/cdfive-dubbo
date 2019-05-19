package com.cdfive.mp3.vo.song;

import lombok.Data;

import java.io.Serializable;

@Data
public class SongListVo implements Serializable {

	private static final long serialVersionUID = 8260474547624917655L;

	private Integer id;
	
	private String songName;
	
	private String fullName;
	
	private String author;
	
	private String description;
	
	private String path;
	
	private Integer digit;
	
	private String reason;
}
