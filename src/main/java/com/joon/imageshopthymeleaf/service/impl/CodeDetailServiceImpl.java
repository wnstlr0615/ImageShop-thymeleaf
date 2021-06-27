package com.joon.imageshopthymeleaf.service.impl;

import com.joon.imageshopthymeleaf.common.exception.NotFoundGroupCodeException;
import com.joon.imageshopthymeleaf.common.model.CodeLabelValue;
import com.joon.imageshopthymeleaf.dto.CodeDetailDto;
import com.joon.imageshopthymeleaf.entity.CodeDetail;
import com.joon.imageshopthymeleaf.entity.CodeGroup;
import com.joon.imageshopthymeleaf.repository.CodeDetailRepository;
import com.joon.imageshopthymeleaf.repository.CodeGroupRepository;
import com.joon.imageshopthymeleaf.service.CodeDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodeDetailServiceImpl implements CodeDetailService {
    private final CodeDetailRepository codeDetailRepository;
    private final CodeGroupRepository codeGroupRepository;

    @Override
    @Transactional
    public void register(CodeDetailDto codeDetailDto) {
        CodeGroup codeGroup = findCodeGroup(codeDetailDto.getGroupCode());
        int seqByGroup = codeDetailRepository.countByCodeGroup(codeGroup);
        CodeDetail codeDetail=CodeDetail.of(codeDetailDto, codeGroup, seqByGroup);
        codeDetailRepository.save(codeDetail);
    }

    @Override
    public List<CodeDetail> list() {
        return codeDetailRepository.findAll();
    }

    @Override
    public CodeDetailDto findByGroupAndCodeValue(String groupCode, String codeValue) {
        CodeGroup codeGroup = findCodeGroup(groupCode);
        CodeDetail codeDetail = codeDetailRepository.findByCodeGroupAndCodeValue(codeGroup, codeValue).orElseThrow(() -> new NotFoundGroupCodeException("없는 그룹 코드입니다."));
        return CodeDetailDto.of(codeDetail);
    }

    @Override
    @Transactional
    public void remove(String groupCode, String codeValue) {
        CodeGroup codeGroup = findCodeGroup(groupCode);
        codeDetailRepository.deleteByCodeGroupAndCodeValue(codeGroup, codeValue);
    }

    @Override
    @Transactional
    public void update(CodeDetailDto codeDetailDto) {
        CodeGroup codeGroup = findCodeGroup(codeDetailDto.getGroupCode());
        CodeDetail codeDetail = codeDetailRepository.findByCodeGroupAndCodeValue(codeGroup, codeDetailDto.getCodeValue()).orElseThrow(() -> new NotFoundGroupCodeException("없는 코드입니다."));
        codeDetail.update(codeGroup, codeDetailDto.getCodeName(), codeDetailDto.getCodeValue());
    }

    @Override
    public List<CodeLabelValue> getCodeList(String classCode) {
        CodeGroup codeGroup = findCodeGroup(classCode);
        List<CodeDetail> codeDetails = codeDetailRepository.findAllByCodeGroup(codeGroup);
        return codeDetails.stream().map(c->new CodeLabelValue(c.getCodeValue(), c.getCodeName())).collect(Collectors.toList());
    }

    private CodeGroup findCodeGroup(String groupCode) {
        return codeGroupRepository.findByGroupCode(groupCode).orElseThrow(() -> new NotFoundGroupCodeException("없는 그룹 코드입니다."));
    }
}
