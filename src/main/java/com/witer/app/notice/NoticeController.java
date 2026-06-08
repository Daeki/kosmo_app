package com.witer.app.notice;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@AllArgsConstructor
@RequestMapping("/notice/**")
public class NoticeController {

    private NoticeService noticeService;

    @GetMapping("list")
    public List<NoticeDTOResponseDTO> list() throws Exception {
        return noticeService.list();
    }

}
