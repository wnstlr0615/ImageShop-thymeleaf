package com.joon.imageshopthymeleaf.service.impl;

import com.joon.imageshopthymeleaf.common.exception.NotFoundGroupCodeException;
import com.joon.imageshopthymeleaf.common.model.CodeLabelValue;
import com.joon.imageshopthymeleaf.entity.CodeGroup;
import com.joon.imageshopthymeleaf.repository.CodeGroupRepository;
import com.joon.imageshopthymeleaf.service.CodeGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodeGroupServiceImpl implements CodeGroupService {
    @Autowired
    CodeGroupRepository codeGroupRepository;

    @Override
    @Transactional
    public void register(CodeGroup codeGroup) {
        codeGroupRepository.save(codeGroup);
    }

    @Override
    public List<CodeGroup> list() {
        return codeGroupRepository.findAll();
    }

    @Override
    public CodeGroup getGroupCode(String groupCode) {
        CodeGroup codeGroup = codeGroupRepository.findByGroupCode(groupCode).orElseThrow(()->new NotFoundGroupCodeException("없는 그룹 코드입니다."));
        return codeGroup;
    }

    @Override
    @Transactional
    public void remove(String groupCode) {
        codeGroupRepository.deleteByGroupCode(groupCode);
    }

    @Override
    @Transactional
    public void update(CodeGroup codeGroup) {
        CodeGroup basicCodeGroup = codeGroupRepository.findByGroupCode(codeGroup.getGroupCode()).orElseThrow(() -> new NotFoundGroupCodeException("없는 그룹 코드입니다."));
        basicCodeGroup.updateGroupName(codeGroup);
    }

    @Override
    public List<CodeLabelValue> getCodeGroupList() {
        List<CodeGroup> codeGroups = codeGroupRepository.findAll(Sort.by(Sort.Direction.ASC, "groupCode"));
        return codeGroups.stream().map(codeGroup -> new CodeLabelValue(codeGroup.getGroupCode(), codeGroup.getGroupName())).collect(Collectors.toList());
    }
}
