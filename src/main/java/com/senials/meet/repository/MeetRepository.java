package com.senials.meet.repository;

import com.senials.entity.Meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetRepository extends JpaRepository<Meet, Integer> {

    //사용자 별 참여한 모임 확인
    @Query("SELECT m FROM Meet m JOIN m.partyBoard pb WHERE pb.user.userNumber = :userNumber")
    List<Meet> findAllByUserNumber(int userNumber);
}
