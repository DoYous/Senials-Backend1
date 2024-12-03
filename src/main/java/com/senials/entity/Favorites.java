package com.senials.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "FAVORITES")
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_number", nullable = false)
    private int favoriteNumber;

    @Column(name = "user_number", nullable = false)
    private int userNumber;

    @ManyToOne
    @JoinColumn(name = "hobby_number", nullable = false)
    private Hobby hobby;

    /* AllArgsConstructor */
    public Favorites(int favoriteNumber, int userNumber, Hobby hobby) {
        this.favoriteNumber = favoriteNumber;
        this.userNumber = userNumber;
        this.hobby = hobby;
    }
}
