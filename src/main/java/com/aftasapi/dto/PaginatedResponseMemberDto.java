package com.aftasapi.dto;

import com.aftasapi.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponseMemberDto {
    private List<Member> member;
  private int  pageNumber;
  private int  pageSize;
   private int totalElements;
   private int totalPages ;
}
