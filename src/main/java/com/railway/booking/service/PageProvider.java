package com.railway.booking.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class PageProvider {
    public int getPageNumberFromString(String page) {
        int result = 1;
        try {
            result = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            log.warn(String.format("Can not parse page number from string: %s", page), e);
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
