package com.cdfive.user.service;

import com.cdfive.common.base.AbstractService;
import com.cdfive.common.exception.ServiceException;
import com.cdfive.user.exception.UserServiceException;


/**
 * @author cdfive
 */
public class AbstractUserService extends AbstractService {

    @Override
    protected ServiceException exception(String msg) {
        return new UserServiceException(msg);
    }
}
