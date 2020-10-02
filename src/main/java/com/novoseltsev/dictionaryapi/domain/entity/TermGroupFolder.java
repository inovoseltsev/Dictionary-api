package com.novoseltsev.dictionaryapi.domain.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.DESCRIPTION_ERROR;
import static com.novoseltsev.dictionaryapi.validation.ValidationUtil.DESCRIPTION_PATTERN;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "term_group_folder", schema = "dictionary_schema")
public class TermGroupFolder extends AbstractEntity {

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Pattern(regexp = DESCRIPTION_PATTERN, message = DESCRIPTION_ERROR)
    private String description;

    @OneToMany(mappedBy = "termGroupFolder", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<TermGroup> termGroups = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private User user;

    public void addTermGroup(TermGroup termGroup) {
        termGroup.setTermGroupFolder(this);
        this.termGroups.add(termGroup);
    }
}
