package com.novoseltsev.dictionaryapi.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(schema = "dictionary_schema")
public class Term extends AbstractEntity {

    @Column(nullable = false, length = 120)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @NotBlank
    private String definition;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "term_group_id", nullable = false)
    @ToString.Exclude
    private TermGroup termGroup;

    public Term(Long id, String name, String definition) {
        super(id);
        this.name = name;
        this.definition = definition;
    }

    public Term(String name, String definition) {
        this.name = name;
        this.definition = definition;
    }
}
