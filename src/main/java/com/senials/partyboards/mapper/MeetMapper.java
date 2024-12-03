package com.senials.partyboards.mapper;

import com.senials.common.entity.Meet;
import com.senials.partyboards.dto.MeetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MeetMapper {

    // Meet -> MeetDTO
    MeetDTO toMeetDTO(Meet meet);

    /* MeetDTO -> Meet */
    Meet toMeet(MeetDTO meetDTO);
}
