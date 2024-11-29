package com.senials.hobbyboard.service;

import com.senials.hobbyboard.dto.HobbyDTO;
import com.senials.hobbyboard.entity.Hobby;
import com.senials.hobbyboard.mapper.HobbyMapper;
import com.senials.hobbyboard.mapper.HobbyMapperImpl;
import com.senials.hobbyboard.repository.HobbyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HobbyService {

    private HobbyMapper hobbyMapper;

    private HobbyRepository hobbyRepository;

    public HobbyService(HobbyMapper hobbyMapper,HobbyRepository hobbyRepository){
        this.hobbyMapper=hobbyMapper;
        this.hobbyRepository=hobbyRepository;
    }

    public List<HobbyDTO>findAll(){
        List<Hobby> hobbyList=hobbyRepository.findAll();

        List<HobbyDTO> hobbyDTOList=hobbyList.stream().map(hobby -> hobbyMapper.toHobbyDTO(hobby)).toList();

        return hobbyDTOList;
    }
}
