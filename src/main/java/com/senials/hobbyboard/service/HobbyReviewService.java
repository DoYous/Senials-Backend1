package com.senials.hobbyboard.service;

import com.senials.hobbyboard.dto.HobbyReviewDTO;
import com.senials.entity.Hobby;
import com.senials.entity.HobbyReview;
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
        List<HobbyReviewDTO> hobbyReviewDTOList=hobbyReviewList.stream().map(hobbyReview -> {
            HobbyReviewDTO dto=hobbyReviewMapper.toHobbyReviewDTO(hobbyReview);
            dto.setHobbyNumber(hobbyReview.getHobby().getHobbyNumber());
            dto.setUserNumber(hobbyReview.getUser().getUserNumber());
            dto.setUserName(hobbyReview.getUser().getUserName());
            return dto;
        }).toList();
        return hobbyReviewDTOList;
    }

}
