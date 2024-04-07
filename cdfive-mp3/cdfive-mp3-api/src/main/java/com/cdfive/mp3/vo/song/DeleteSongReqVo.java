package com.cdfive.mp3.vo.song;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cdfive
 */
@Data
public class DeleteSongReqVo implements Serializable {

    private static final long serialVersionUID = 7699091956024889089L;

    private Integer id;
}
