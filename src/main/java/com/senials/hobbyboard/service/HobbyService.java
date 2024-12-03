package com.senials.hobbyboard.service;

import com.senials.common.entity.HobbyReview;
import com.senials.common.mapper.HobbyMapper;
import com.senials.common.entity.Hobby;
import com.senials.common.mapper.HobbyReviewMapper;
import com.senials.common.repository.HobbyRepository;
import com.senials.common.repository.HobbyReviewRepository;
import com.senials.hobbyboard.dto.HobbyDTO;
import com.senials.hobbyboard.dto.HobbyReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HobbyService {

    private final HobbyReviewMapper hobbyReviewMapper;
    private final HobbyMapper hobbyMapper;

    private final HobbyReviewRepository hobbyReviewRepository;
    private final HobbyRepository hobbyRepository;

    public HobbyService(HobbyReviewMapper hobbyReviewMapper,HobbyMapper hobbyMapper,HobbyReviewRepository hobbyReviewRepository,HobbyRepository hobbyRepository){
        this.hobbyReviewMapper=hobbyReviewMapper;
        this.hobbyMapper=hobbyMapper;
        this.hobbyReviewRepository=hobbyReviewRepository;
        this.hobbyRepository=hobbyRepository;
    }

    public List<HobbyDTO>findAll(){
        List<Hobby> hobbyList=hobbyRepository.findAll();

        List<HobbyDTO> hobbyDTOList=hobbyList.stream().map(hobby -> hobbyMapper.toHobbyDTO(hobby)).toList();

        return hobbyDTOList;
    }

    public HobbyDTO findById(int hobbyNumber){
        Hobby hobby=hobbyRepository.findById(hobbyNumber).orElseThrow(() -> new IllegalArgumentException("해당 취미가 존재하지 않습니다: " + hobbyNumber));
        HobbyDTO hobbyDTO=hobbyMapper.toHobbyDTO(hobby);

        return hobbyDTO;
    }

    public List<HobbyDTO>findByCategory(int categoryNumber){
        List<Hobby> hobbyList=hobbyRepository.findByCategoryNumber(categoryNumber);

        List<HobbyDTO> hobbyDTOList=hobbyList.stream().map(hobby -> hobbyMapper.toHobbyDTO(hobby)).toList();

        return hobbyDTOList;
    }

    //취미 번호가 같은 취미리뷰 리스트 불러오기
    public List<HobbyReviewDTO> getReviewsListByHobbyNumber(int hobbyNumber) {
        Hobby hobby = hobbyRepository.findById(hobbyNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 취미 번호가 존재하지 않습니다: " + hobbyNumber));
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
