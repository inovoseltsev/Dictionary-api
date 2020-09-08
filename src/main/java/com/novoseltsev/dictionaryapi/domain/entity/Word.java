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
public class Word extends AbstractEntity {

    @Column(nullable = false, length = 60)
    @NotBlank
    private String name;

    @Column(nullable = false, length = 60)
    @NotBlank
    private String translation;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "word_set_id", nullable = false)
    @ToString.Exclude
    private WordSet wordSet;
}
