package com.senials.common.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "FAVORITES")
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_number")
    private int favoriteNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number", referencedColumnName = "user_number", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hobby_number", referencedColumnName = "hobby_number", nullable = false)
    private Hobby hobby;

    /* AllArgsConstructor */
    @Builder
    public Favorites(int favoriteNumber, User user, Hobby hobby) {
        this.favoriteNumber = favoriteNumber;
        this.user = user;
        this.hobby = hobby;
    }

    public void InitializeHobby(Hobby hobby) {
        this.hobby = hobby;
    }

    public void InitializesUser(User user){
        this.user=user;
    }
}
