package com.cdfive.log.service;

import com.cdfive.common.base.AbstractService;
import com.cdfive.common.exception.ServiceException;
import com.cdfive.log.exception.LogServiceException;

/**
 * @author cdfive
 */
public class AbstractLogService extends AbstractService {

    @Override
    protected ServiceException exception(String msg) {
        return new LogServiceException(msg);
    }
}
