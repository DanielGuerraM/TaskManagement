package com.TaskManagement.TaskManagementApp.http;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paging {
    private int page;
    private int per_page;
    private int total_pages;
    private long total_items;
}
