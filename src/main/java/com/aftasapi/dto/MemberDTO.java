package com.aftasapi.dto;

import com.aftasapi.entity.enums.IdentityDocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    @NotNull(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    @NotNull(message = "Family name cannot be null")
    @Size(min = 2, max = 50, message = "Family name must be between 2 and 50 characters")
    private String familyName;
    @NotNull(message = "Access date cannot be null")
    @PastOrPresent(message = "Access date must be in the past or present")
    @Temporal(TemporalType.DATE)
    private Date accessionDate;
    @NotNull(message = "nationality cannot be null")
    private String nationality;
    @NotNull
    private IdentityDocumentType identityDocumentType;
    @NotNull(message = "Identity number cannot be null")
    @Size(min = 2, max = 50, message = "Identity number must be between 2 and 50 characters")
    @Column(unique = true)
    private String identityNumber;
}