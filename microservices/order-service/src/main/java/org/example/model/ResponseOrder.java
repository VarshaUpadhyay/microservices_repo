package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.ResponseOrderDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseOrder {
    private String status;
    private String msg;
    private List<ResponseOrderDto> respDtoList;
}
