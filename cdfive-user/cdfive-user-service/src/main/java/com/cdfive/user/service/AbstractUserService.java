package com.cdfive.user.service;

import com.cdfive.common.base.AbstractService;
import com.cdfive.user.exception.UserServiceException;

/**
 * @author cdfive
 */
public class AbstractUserService extends AbstractService {

    @Override
    protected void fail(String msg) {
        throw new UserServiceException(msg);
    }
}
