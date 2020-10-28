package com.inzent.sbp.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Company extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(unique=true)
    @NotNull    // requires 'spring-boot-starter-validation' dependency
    private String companyName;

    private String tel;

    @NotNull
    private Integer active = 0;
    private String serviceUrl;

    @Lob
    private Byte[] logo;

    @Min(value = 1, message = "Users should not be less than 1")
//    @Max(value = 65, message = "Age should not be greater than 65")
    private Integer usersLimit;

    private LocalDateTime trialEndDate;

    @NotNull
    private Integer deleted = 0;

    @Builder
    public Company(String companyName, String tel, Integer usersLimit) {
        this.companyName = companyName;
        this.tel = tel;
        this.usersLimit = usersLimit;
    }
}
