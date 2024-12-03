package com.senials.likes.service;

import com.senials.entity.Likes;
import com.senials.entity.PartyBoard;
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

    private PartyBoardMapper partyBoardMapper;

    public LikesService(LikesRepository likesRepository, UserRepository userRepository, PartyBoardMapperImpl partyBoardMapperImpl) {
        this.likesRepository = likesRepository;
        this.userRepository = userRepository;
        this.partyBoardMapper = partyBoardMapperImpl;
    }

    public List<PartyBoardDTOForCard> getLikedPartyBoardsByUserNumber(int userNumber) {
        User user = userRepository.findById(userNumber)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Likes> likesList = likesRepository.findAllByUser(user);

        List<PartyBoardDTOForCard> partyBoards = likesList.stream().map(likes -> {
            return partyBoardMapper.toPartyBoardDTOForCard(likes.getPartyBoard());
        }).toList();

        return partyBoards;

/*

 List<Likes> likes = likesRepository.findWithDetailsByUser(user);

        return likes.stream().map(like -> {
            PartyBoard partyBoard = like.getPartyBoard();
            long memberCount = partyBoard.getPartyMembers().size(); // 멤버 수 계산

            // PartyBoardDetailsDTO 생성
            return new PartyBoardDTOForCard(
                    partyBoard.getPartyBoardNumber(),
                    partyBoard.getPartyBoardName(),
                    partyBoard.getPartyBoardStatus(),
                    memberCount
            );
        }).collect(Collectors.toList());

    }


    int getCountByUserNumber(int userNumber){
        User user = UserRepository.findById(userNumber)
                .orElseThrow(IllegalArgumentException::new);
        return likesRepository.countByUser(user);
    }*/

    }
}
