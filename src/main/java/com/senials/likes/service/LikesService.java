package com.senials.likes.service;

import com.senials.entity.Likes;
import com.senials.entity.PartyBoard;
import com.senials.entity.PartyReview;
import com.senials.entity.User;
import com.senials.partyboards.dto.PartyBoardDTOForCard;
import com.senials.partyboards.mapper.PartyBoardMapper;
import com.senials.partyboards.mapper.PartyBoardMapperImpl;
import com.senials.user.repository.UserRepository;
import com.senials.likes.repository.LikesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final PartyBoardMapper partyBoardMapper;

    public LikesService(LikesRepository likesRepository, UserRepository userRepository, PartyBoardMapperImpl partyBoardMapper) {
        this.likesRepository = likesRepository;
        this.userRepository = userRepository;
        this.partyBoardMapper = partyBoardMapper;
    }

    public List<PartyBoardDTOForCard> getLikedPartyBoardsByUserNumber(int userNumber) {
        User user = userRepository.findById(userNumber)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Likes> likesList = likesRepository.findAllByUser(user);

        return likesList.stream().map(likes -> {
            PartyBoard partyBoard = likes.getPartyBoard();

            // 별점 평균 계산
            double averageRating = 0.0;
            List<PartyReview> reviews = partyBoard.getPartyReviews();

            if (!reviews.isEmpty()) {
                int totalRating = 0;
                for (PartyReview review : reviews) {
                    totalRating += review.getPartyReviewRate();
                }
                averageRating = (double) totalRating / reviews.size();
            }

            // 첫 번째 이미지 가져오기
            String firstImage = partyBoard.getImages().isEmpty() ? null : partyBoard.getImages().get(0).getPartyBoardImg();

            // DTO 생성
            return new PartyBoardDTOForCard(
                    partyBoard.getPartyBoardNumber(),
                    partyBoard.getPartyBoardName(),
                    partyBoard.getPartyBoardStatus(),
                    partyBoard.getPartyMembers().size(), // 참여 인원 수
                    averageRating,
                    firstImage
            );
        }).collect(Collectors.toList());

    }


/*    public List<PartyBoardDTOForCard> getLikedPartyBoardsByUserNumber(int userNumber) {
        User user = userRepository.findById(userNumber)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Likes> likesList = likesRepository.findAllByUser(user);

        List<PartyBoardDTOForCard> partyBoards = likesList.stream().map(likes -> {
            return partyBoardMapper.toPartyBoardDTOForCard(likes.getPartyBoard());
        }).toList();

        return partyBoards;*/
}
