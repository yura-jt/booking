package com.railway.booking.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class PageUtil {
    private static final Logger LOGGER = LogManager.getLogger(PageUtil.class);

    public int getPageNumberFromString(String page) {
        int result = 1;
        try {
            result = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            LOGGER.warn(String.format("Can not parse page number from string: %s", page), e);
        }
        return result;
    }

    public int getMaxPage(int itemPerPage, int totalItems) {
        int page = totalItems / itemPerPage;
        if (totalItems % itemPerPage != 0) {
            page++;
        }
        return page == 0 ? 1 : page;
    }

}
