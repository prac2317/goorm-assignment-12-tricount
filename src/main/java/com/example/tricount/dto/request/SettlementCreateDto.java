package com.example.tricount.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettlementCreateDto {

    private String title;
    private List<SettlementCreateMemberDto> participantsDto;
}
