package com.novoseltsev.dicterapi.domain.dto.term;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dicterapi.domain.entity.Term;
import com.novoseltsev.dicterapi.domain.entity.TermGroup;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupTermDto extends AbstractTermDto {

    @Positive
    @NotNull
    private Long termGroupId;

    @Override
    public Term toEntity() {
        Term term = super.toEntity();
        term.setTermGroup(new TermGroup(termGroupId));
        return term;
    }
}