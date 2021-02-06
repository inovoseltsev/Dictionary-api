package com.novoseltsev.dictionaryapi.domain.dto.activity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.dictionaryapi.domain.entity.Activity;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityDto extends AbstractActivityDto {

    @Positive
    private Long id;

    public ActivityDto(Long id, String name, String description) {
        super(name, description);
        this.id = id;
    }

    @Override
    public Activity toEntity() {
        Activity activity = super.toEntity();
        activity.setId(id);
        return activity;
    }

    public static ActivityDto from(Activity activity) {
        return new ActivityDto(
                activity.getId(),
                activity.getName(),
                activity.getDescription()
        );
    }
}
