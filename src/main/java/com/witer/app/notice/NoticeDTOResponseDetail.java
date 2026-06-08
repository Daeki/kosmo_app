package com.witer.app.notice;

import java.time.LocalDateTime;
import java.util.List;

public record NoticeDTOResponseDetail(

                Long id,

                String content,

                LocalDateTime createdAt,

                boolean isPinned,

                String title,

                LocalDateTime updatedAt,

                String username,

                Long views,

                List<NoticeFileDetailDTO> attaches

) {

}
