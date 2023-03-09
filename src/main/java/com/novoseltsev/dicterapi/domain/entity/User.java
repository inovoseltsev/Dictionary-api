package com.novoseltsev.dicterapi.domain.entity;

import com.novoseltsev.dicterapi.domain.role.UserRole;
import com.novoseltsev.dicterapi.domain.status.UserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

import static com.novoseltsev.dicterapi.validation.Pattern.LOGIN_PATTERN;
import static com.novoseltsev.dicterapi.validation.Pattern.NAME_PATTERN;
import static com.novoseltsev.dicterapi.validation.ValidationMessage.FIRST_NAME_ERROR;
import static com.novoseltsev.dicterapi.validation.ValidationMessage.LAST_NAME_ERROR;
import static com.novoseltsev.dicterapi.validation.ValidationMessage.LOGIN_ERROR;
import static com.novoseltsev.dicterapi.validation.ValidationMessage.PASSWORD_ERROR;

@Data
@EqualsAndHashCode(callSuper = true, of = "login")
@NoArgsConstructor
@Entity
@Table(name = "usr")
public class User extends AbstractEntity {

    @Column(name = "first_name")
    @NotBlank
    @Pattern(regexp = NAME_PATTERN, message = FIRST_NAME_ERROR)
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    @Pattern(regexp = NAME_PATTERN, message = LAST_NAME_ERROR)
    private String lastName;

    @Column(unique = true, nullable = false)
    @NotBlank
    @Pattern(regexp = LOGIN_PATTERN, message = LOGIN_ERROR)
    private String login;

    @Column(nullable = false)
    @NotBlank(message = PASSWORD_ERROR)
    private String password;

    @Column(nullable = false, length = 25)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column(nullable = false, length = 25)
    @Enumerated(value = EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TermGroup> termGroups = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Folder> folders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Activity> activities = new ArrayList<>();


    public User(Long id) {
        super(id);
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addTermGroup(TermGroup termGroup) {
        termGroup.setUser(this);
        this.termGroups.add(termGroup);
    }

    public void addFolder(Folder folder) {
        folder.setUser(this);
        this.folders.add(folder);
    }

    public void addActivity(Activity activity) {
        activity.setUser(this);
        this.activities.add(activity);
    }
}
