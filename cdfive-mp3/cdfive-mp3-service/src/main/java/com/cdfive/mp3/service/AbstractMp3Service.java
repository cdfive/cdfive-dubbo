package com.cdfive.mp3.service;

import com.cdfive.common.base.AbstractService;
import com.cdfive.mp3.exception.Mp3ServiceException;

/**
 * @author cdfive
 */
public class AbstractMp3Service extends AbstractService {

    @Override
    protected void fail(String msg) {
        throw new Mp3ServiceException(msg);
    }
}
