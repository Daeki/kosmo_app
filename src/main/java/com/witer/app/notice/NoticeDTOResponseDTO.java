package com.witer.app.notice;

import java.time.LocalDateTime;

public record NoticeDTOResponseDTO(
    Long id,
    String username,
    String title,
    Long views,
    LocalDateTime createdAt
) {

    

}
