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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "word_set_folder", schema = "dictionary_schema")
public class WordSetFolder extends AbstractEntity {

    @Column(nullable = false, length = 50)
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "wordSetFolder", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<WordSet> wordSets = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private User user;
}
