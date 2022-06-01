package com.example.jvspringboottestcontainer.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageRequestUtil {
    public static PageRequest getPageRequest(Integer count, Integer page, String sortBy) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            List.of(sortBy.split(";")).forEach(field -> orders.add(parseSorting(field)));
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, sortBy);
            orders.add(order);
        }
        Sort sort = Sort.by(orders);
        return PageRequest.of(page, count, sort);
    }

    private static Sort.Order parseSorting(String field) {
        Sort.Order order;
        if (field.contains(":")) {
            String[] fieldAndDirections = field.split(":");
            order = new Sort.Order(Sort.Direction.valueOf(fieldAndDirections[1]),
                fieldAndDirections[0]);
        } else {
            order = new Sort.Order(Sort.Direction.DESC, field);
        }
        return order;
    }
}
