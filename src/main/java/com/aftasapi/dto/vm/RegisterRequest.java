package com.aftasapi.dto.vm;



import com.aftasapi.entity.enums.IdentityDocumentType;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    @Size(min = 2, max = 50, message = "Family name must be between 2 and 50 characters")
    private String familyName;
    @PastOrPresent(message = "Access date must be in the past or present")
    private LocalDate accessionDate;
    @NotNull(message = "identity Document Type  cannot be null")
    private IdentityDocumentType identityDocumentType;
    @Size(min = 2, max = 50, message = "Identity number must be between 2 and 50 characters")
    private String identityNumber;
    @Email
    private String email;
    @Size(min = 4, max = 60)
    private String password ;
    @NotNull(message = "nationality cannot be null")
    private String nationality;

}
