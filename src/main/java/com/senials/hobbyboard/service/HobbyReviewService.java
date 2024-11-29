package com.senials.hobbyboard.service;

import com.senials.hobbyboard.dto.HobbyDTO;
import com.senials.hobbyboard.dto.HobbyReviewDTO;
import com.senials.hobbyboard.entity.Hobby;
import com.senials.hobbyboard.entity.HobbyReview;
import com.senials.hobbyboard.mapper.HobbyMapper;
import com.senials.hobbyboard.mapper.HobbyReviewMapper;
import com.senials.hobbyboard.repository.HobbyRepository;
import com.senials.hobbyboard.repository.HobbyReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HobbyReviewService {
    private final HobbyReviewMapper hobbyReviewMapper;
    private final HobbyReviewRepository hobbyReviewRepository;
    private final HobbyRepository hobbyRepository;

    HobbyReviewService(HobbyReviewRepository hobbyReviewRepository, HobbyReviewMapper hobbyReviewMapper, HobbyRepository hobbyRepository){
        this.hobbyReviewMapper=hobbyReviewMapper;
        this.hobbyReviewRepository=hobbyReviewRepository;
        this.hobbyRepository=hobbyRepository;
    }

    public List<HobbyReviewDTO> getReviewsByHobbyNumber(int hobbyNumber) {
        Hobby hobby = hobbyRepository.findById(hobbyNumber)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Hobby Number: " + hobbyNumber));
        List<HobbyReview> hobbyReviewList=hobbyReviewRepository.findByHobby(hobby);
        List<HobbyReviewDTO> hobbyReviewDTOList=hobbyReviewList.stream().map(hobbyReview -> hobbyReviewMapper.toHobbyReviewDTO(hobbyReview)).toList();
        return hobbyReviewDTOList;
    }

}
