package com.cdfive.mp3.service;

import com.cdfive.common.base.AbstractService;
import com.cdfive.common.exception.ServiceException;
import com.cdfive.mp3.exception.Mp3ServiceException;

/**
 * @author cdfive
 */
public class AbstractMp3Service extends AbstractService {

    @Override
    protected ServiceException exception(String msg) {
        return new Mp3ServiceException(msg);
    }
}
