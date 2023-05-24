package com.cdfive.user.service;

import com.cdfive.framework.base.AbstractService;
import com.cdfive.framework.exception.ServiceException;
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
