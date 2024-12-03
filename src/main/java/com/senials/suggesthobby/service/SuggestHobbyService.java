package com.senials.suggesthobby.service;

import com.senials.common.entity.Favorites;
import com.senials.common.entity.Hobby;
import com.senials.common.entity.User;
import com.senials.common.repository.FavoritesRepository;
import com.senials.common.repository.HobbyRepository;
import com.senials.common.repository.UserRepository;
import com.senials.common.mapper.HobbyMapper;
import com.senials.hobbyboard.dto.HobbyDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SuggestHobbyService {

    private final HobbyRepository hobbyRepository;
    private final HobbyMapper hobbyMapper;
    private final UserRepository userRepository;
    private final FavoritesRepository favoritesRepository;

    public SuggestHobbyService(HobbyRepository hobbyRepository, HobbyMapper hobbyMapper, UserRepository userRepository, FavoritesRepository favoritesRepository) {
        this.hobbyRepository = hobbyRepository;
        this.hobbyMapper = hobbyMapper;
        this.userRepository = userRepository;
        this.favoritesRepository = favoritesRepository;
    }

    public HobbyDTO suggestHobby(int hobbyAbility, int hobbyBudget, int hobbyLevel, int hobbyTendency){
        List<Hobby> hobbyList = null;
        List<Hobby> tempList=hobbyRepository.findByHobbyAbility(hobbyAbility);
        if (!tempList.isEmpty()) {
            hobbyList=tempList;
            tempList = tempList.stream()
                    .filter(hobby -> hobby.getHobbyBudget() == hobbyBudget)
                    .collect(Collectors.toList());
            if (!tempList.isEmpty()) {
                hobbyList=tempList;
                tempList = tempList.stream()
                        .filter(hobby -> hobby.getHobbyBudget() == hobbyTendency)
                        .collect(Collectors.toList());
                if (!tempList.isEmpty()) {
                    hobbyList=tempList;
                    tempList = tempList.stream()
                            .filter(hobby -> hobby.getHobbyBudget() == hobbyLevel)
                            .collect(Collectors.toList());
                    if (!tempList.isEmpty()) {
                        hobbyList=tempList;
                    }
                }
            }
        }

        List<HobbyDTO> hobbyDTOList=hobbyList.stream().map(hobby -> hobbyMapper.toHobbyDTO(hobby)).toList();

        Random random=new Random();
        int randomIndex = random.nextInt(hobbyDTOList.size());
        HobbyDTO hobbyDTO = hobbyDTOList.get(randomIndex);

        return hobbyDTO;
    }


    public Favorites setFavoritesByHobby(int hobbyNumber, int userNumber){
        User user= userRepository.findById(userNumber).orElseThrow(() -> new IllegalArgumentException("해당 유저 번호가 존재하지 않습니다: " + userNumber));
        Hobby hobby=hobbyRepository.findById(hobbyNumber).orElseThrow(() -> new IllegalArgumentException("해당 취미 번호가 존재하지 않습니다: " + hobbyNumber));
        Favorites favoritesEntity = new Favorites();
        favoritesEntity.InitializeHobby(hobby);
        favoritesEntity.InitializesUser(user);

        Favorites favorites=favoritesRepository.save(favoritesEntity);

        return favorites;
    }
}
