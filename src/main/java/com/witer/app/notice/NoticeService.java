package com.witer.app.notice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NoticeService {

    private NoticeRepository noticeRepository;

    public List<NoticeDTOResponseDTO> list() throws Exception {
        List<NoticeDTO> ar = noticeRepository.findAll();
        List<NoticeDTOResponseDTO> list = new ArrayList<>();
        for (NoticeDTO n : ar) {
            NoticeDTOResponseDTO nr = new NoticeDTOResponseDTO(n.getId(), n.getMemberDTO().getUsername(), n.getTitle(),
                    n.getViews(), n.getCreatedAt());
            list.add(nr);
        }

        return list;
    }

}
