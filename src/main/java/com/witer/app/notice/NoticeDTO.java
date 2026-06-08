package com.witer.app.notice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.witer.app.members.MemberDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@Table(name = "notices")
public class NoticeDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    // @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String content;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column
    private boolean isPinned = false;

    @Column
    private String title;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column
    private Long views = 0L;

    @ManyToOne
    @JoinColumn(name = "username")
    private MemberDTO memberDTO;

    @OneToMany(mappedBy = "noticeDTO")
    private List<NoticeFileDTO> attaches = new ArrayList<>();

}
