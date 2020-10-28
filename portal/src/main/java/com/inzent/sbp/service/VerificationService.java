package com.inzent.sbp.service;

import com.inzent.sbp.domain.Company;
import com.inzent.sbp.dto.CompanyDto;
import com.inzent.sbp.error.NotFoundException;
import com.inzent.sbp.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@RequiredArgsConstructor    // inject dependency
@Service
public class VerificationService {

    // TODO: verify a code
    public void verifyCode() {

    }

    // TODO: create a code
}
