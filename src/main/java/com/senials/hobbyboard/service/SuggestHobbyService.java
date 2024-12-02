package com.senials.hobbyboard.service;

import com.senials.entity.Hobby;
import com.senials.hobbyboard.dto.HobbyDTO;
import com.senials.hobbyboard.mapper.HobbyMapper;
import com.senials.hobbyboard.repository.HobbyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SuggestHobbyService {

    private final HobbyRepository hobbyRepository;
    private final HobbyMapper hobbyMapper;

    public SuggestHobbyService(HobbyRepository hobbyRepository,HobbyMapper hobbyMapper){
        this.hobbyRepository=hobbyRepository;
        this.hobbyMapper=hobbyMapper;
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
}
