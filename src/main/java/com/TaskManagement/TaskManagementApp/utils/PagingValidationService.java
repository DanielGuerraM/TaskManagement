package com.TaskManagement.TaskManagementApp.utils;

import com.TaskManagement.TaskManagementApp.exception.ExceptionDetails;
import com.TaskManagement.TaskManagementApp.exception.InvalidPagingException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PagingValidationService {
    public void ValidatePagingParams(String page, String perPage) throws InvalidPagingException {
        int finalPage = Integer.parseInt(page);
        int finalPerPage = Integer.parseInt(perPage);

        if(finalPage < 1 || finalPerPage < 1 || finalPerPage > 50) {
            throw new InvalidPagingException("Invalid paging values have been entered, the values entered were as follows: page " + finalPage + " per_page " + finalPerPage,
                    new ExceptionDetails(HttpStatus.BAD_REQUEST.value(), "The page cannot be 0 and the number of records per page cannot be less than 1 or more than 50"));
        }
    }
}