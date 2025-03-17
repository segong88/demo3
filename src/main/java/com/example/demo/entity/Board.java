package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long bno;

    private String title;

    private String content;

    private String writer;

    @Column(updatable = false)
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    @PrePersist //엔티티가 처음 저장될 때 실행
    public void proPersist(){
        this.createDate = LocalDateTime.now();
        this.modifiedDate = this.createDate;

    }

    @PreUpdate  //엔티티가 수정될 때 실행
    public void preUpdate(){
        this.modifiedDate = LocalDateTime.now();
    }

}
