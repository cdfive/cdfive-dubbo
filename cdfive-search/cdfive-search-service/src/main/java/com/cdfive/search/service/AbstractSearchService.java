package com.cdfive.search.service;

import com.cdfive.framework.base.AbstractService;
import com.cdfive.framework.exception.ServiceException;
import com.cdfive.search.exception.SearchServiceException;

/**
 * @author cdfive
 */
public class AbstractSearchService extends AbstractService {

    @Override
    protected ServiceException exception(String msg) {
        return new SearchServiceException(msg);
    }
}
