package com.senials.hobbyboard.service;

import com.senials.hobbyboard.dto.HobbyReviewDTO;
import com.senials.entity.Hobby;
import com.senials.entity.User;
import com.senials.entity.HobbyReview;
import com.senials.hobbyboard.mapper.HobbyReviewMapper;
import com.senials.hobbyboard.repository.HobbyRepository;
import com.senials.hobbyboard.repository.HobbyReviewRepository;
import com.senials.hobbyboard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HobbyReviewService {
    private final HobbyReviewMapper hobbyReviewMapper;
    private final HobbyReviewRepository hobbyReviewRepository;
    private final HobbyRepository hobbyRepository;
    private final UserRepository userRepository;

    HobbyReviewService(HobbyReviewRepository hobbyReviewRepository, HobbyReviewMapper hobbyReviewMapper, HobbyRepository hobbyRepository, UserRepository userRepository){
        this.hobbyReviewMapper=hobbyReviewMapper;
        this.hobbyReviewRepository=hobbyReviewRepository;
        this.hobbyRepository=hobbyRepository;
        this.userRepository=userRepository;
    }

    //취미 번호가 같은 취미리뷰 리스트 불러오기
    public List<HobbyReviewDTO> getReviewsByHobbyNumber(int hobbyNumber) {
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


    //취미 번호와 유저 번호를 통해 취미후기를 생성
    public HobbyReview saveHobbyReview(HobbyReviewDTO hobbyReviewDTO,int userNumber, int hobbyNumber) {
        User user= (User) userRepository.findById(userNumber).orElseThrow(() -> new IllegalArgumentException("해당 유저 번호가 존재하지 않습니다: " + userNumber));;
        Hobby hobby=hobbyRepository.findById(hobbyNumber).orElseThrow(() -> new IllegalArgumentException("해당 취미 번호가 존재하지 않습니다: " + hobbyNumber));
        HobbyReview hobbyReviewEntity = hobbyReviewMapper.toHobbyReviewEntity(hobbyReviewDTO);
        hobbyReviewEntity.InitializersUser(user);
        hobbyReviewEntity.InitializersHobby(hobby);
        HobbyReview hobbyReview=hobbyReviewRepository.save(hobbyReviewEntity);

        return hobbyReview;
    }

    //취미번호, 유저번호, 리뷰번호를 통한 대조후 취미리뷰 삭제
    public void deleteHobbyReview(int hobbyReviewNumber, int userNumber, int hobbyNumber){
        User user= (User) userRepository.findById(userNumber).orElseThrow(() -> new IllegalArgumentException("해당 유저 번호가 존재하지 않습니다: " + userNumber));;
        Hobby hobby=hobbyRepository.findById(hobbyNumber).orElseThrow(() -> new IllegalArgumentException("해당 취미 번호가 존재하지 않습니다: " + hobbyNumber));
        HobbyReview hobbyReview = hobbyReviewRepository.findById(hobbyReviewNumber).orElseThrow(() -> new IllegalArgumentException("해당 리뷰 번호가 존재하지 않습니다: " + hobbyReviewNumber));
        if(!hobbyReview.getUser().equals(user)){
            throw new IllegalArgumentException("해당 유저의 리뷰가 아닙니다.");
        }
        if
        (!hobbyReview.getHobby().equals(hobby)) {
            throw new IllegalArgumentException("해당 취미의 리뷰가 아닙니다.");
        }
        hobbyReviewRepository.delete(hobbyReview);

    }

}