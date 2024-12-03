package com.senials.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "FAVORITES")
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_number", nullable = false)
    private int favoriteNumber;

    @ManyToOne
    @JoinColumn(name = "user_number", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "hobby_number", nullable = false)
    private Hobby hobby;

    /* AllArgsConstructor */
    public Favorites(int favoriteNumber, User user, Hobby hobby) {
        this.favoriteNumber = favoriteNumber;
        this.user = user;
        this.hobby = hobby;
    }
}
