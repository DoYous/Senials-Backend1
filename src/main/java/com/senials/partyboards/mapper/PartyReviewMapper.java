package com.senials.partyboards.mapper;

import com.senials.entity.PartyReview;
import com.senials.partyboards.dto.PartyReviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PartyReviewMapper {

    /* PartyReview -> PartyReviewDTO */
    PartyReviewDTO toPartyReviewDTO(PartyReview partyReview);

    /* PartyReviewDTO -> PartyReview */
    PartyReview toPartyReview(PartyReviewDTO partyReviewDTO);
}
