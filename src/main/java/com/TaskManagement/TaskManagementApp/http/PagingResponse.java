package com.TaskManagement.TaskManagementApp.http;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagingResponse {
    private List<?> data;
    private Paging paging;
}