package com.senials.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_number", nullable = false)
    private int categoryNumber;

    @Column(name = "category_name", nullable = false, length = 255)
    private String categoryName;

    // Category와 연결된 Hobby 목록을 가져오기 위한 관계 설정 (1:N)
    @OneToMany(mappedBy = "category")
    private List<Hobby> hobbies;

    public Category(int categoryNumber, String categoryName) {
        this.categoryNumber = categoryNumber;
        this.categoryName = categoryName;
    }

}
