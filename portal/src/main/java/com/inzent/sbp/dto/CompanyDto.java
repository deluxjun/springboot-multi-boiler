package com.inzent.sbp.dto;

import com.inzent.sbp.domain.Company;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyDto {
    private String id;
    private String companyName;
    private String tel;
    private Integer usersLimit;

    // manager email
    private String managerEmail;

    // to dto
    public CompanyDto(Company company) {
        this.id = company.getId().toString();
        this.companyName = company.getCompanyName();
        this.tel = company.getTel();
        this.usersLimit = company.getUsersLimit();
    }

    @Builder
    public CompanyDto(String id, String companyName, String tel, Integer usersLimit) {
        this.id = id;
        this.companyName = companyName;
        this.tel = tel;
        this.usersLimit = usersLimit;
    }

    // to entity
    public Company toEntity() {
        return Company.builder()
                .companyName(companyName)
                .tel(tel)
                .usersLimit(usersLimit)
                .build();
    }
}
