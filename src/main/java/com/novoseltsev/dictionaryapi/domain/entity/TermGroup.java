package com.novoseltsev.dictionaryapi.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
import java.util.ArrayList;
import java.util.List;

import static com.novoseltsev.dictionaryapi.validation.Pattern.DESCRIPTION_PATTERN;
import static com.novoseltsev.dictionaryapi.validation.ValidationMessage.DESCRIPTION_ERROR;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "term_group")
public class TermGroup extends AbstractEntity {

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Pattern(regexp = DESCRIPTION_PATTERN, message = DESCRIPTION_ERROR)
    private String description;

    @OneToMany(mappedBy = "termGroup", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Term> terms = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    @ToString.Exclude
    private Folder folder;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    @ToString.Exclude
    private Activity activity;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private User user;

    public TermGroup(Long id) {
        super(id);
    }

    public TermGroup(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addTerm(Term term) {
        term.setTermGroup(this);
        this.terms.add(term);
    }
}
