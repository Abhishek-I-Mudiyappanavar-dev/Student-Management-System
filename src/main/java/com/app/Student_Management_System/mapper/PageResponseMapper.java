package com.app.Student_Management_System.mapper;

import com.app.Student_Management_System.dto.response.PageResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class PageResponseMapper {

    public static <T, R> PageResponse<R> toPageResponse(Page<T> springPage, List<R> content){
        return new PageResponse<>(
                content,
                springPage.getNumber(),
                springPage.getSize(),
                springPage.getTotalPages(),
                springPage.getTotalElements(),
                springPage.hasNext(),
                springPage.hasPrevious(),
                springPage.isFirst(),
                springPage.isLast()
        );
    }
}
