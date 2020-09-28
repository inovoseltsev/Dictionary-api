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
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "word_set", schema = "dictionary_schema")
public class WordSet extends AbstractEntity {

    @Column(nullable = false, length = 50)
    @NotBlank
    private String name;

    @Column(nullable = false, length = 100)
    @NotNull
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "wordSet", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Word> words = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "word_set_folder_id")
    @ToString.Exclude
    private WordSetFolder wordSetFolder;

    public WordSet(Long id, @NotBlank String name, @NotNull String description) {
        super(id);
        this.name = name;
        this.description = description;
    }
}
