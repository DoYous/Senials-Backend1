package com.senials.partyboards.repository;

import com.senials.entity.Favorites;
import com.senials.entity.Hobby;
import com.senials.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HobbyRepository extends JpaRepository<Hobby, Integer> {

}
