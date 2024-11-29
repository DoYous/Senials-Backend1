package com.senials.hobbyboard.service;

import com.senials.hobbyboard.dto.HobbyDTO;
import com.senials.entity.Hobby;
import com.senials.hobbyboard.mapper.HobbyMapper;
import com.senials.hobbyboard.repository.HobbyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HobbyService {

    private final HobbyMapper hobbyMapper;

    private final HobbyRepository hobbyRepository;

    public HobbyService(HobbyMapper hobbyMapper,HobbyRepository hobbyRepository){
        this.hobbyMapper=hobbyMapper;
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
}
