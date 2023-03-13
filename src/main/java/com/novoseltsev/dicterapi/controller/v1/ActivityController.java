package com.novoseltsev.dicterapi.controller.v1;

import com.novoseltsev.dicterapi.domain.dto.activity.ActivityDto;
import com.novoseltsev.dicterapi.domain.dto.activity.UserActivityDto;
import com.novoseltsev.dicterapi.domain.entity.Activity;
import com.novoseltsev.dicterapi.service.ActivityService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("activities")
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping("/{id}")
    public ActivityDto findById(@PathVariable Long id) {
        return ActivityDto.from(activityService.findById(id));
    }

    @GetMapping("/users/{userId}")
    public List<ActivityDto> findAllByUserId(@PathVariable Long userId) {
        return activityService.findAllByUserId(userId).stream().map(ActivityDto::from)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ActivityDto> create(@Valid @RequestBody UserActivityDto activityDto) {
        Activity createdActivity = activityService.create(activityDto.toEntity());
        return new ResponseEntity<>(ActivityDto.from(createdActivity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ActivityDto update(@PathVariable Long id, @Valid @RequestBody ActivityDto activityDto) {
        activityDto.setId(id);
        Activity updatedActivity = activityService.update(activityDto.toEntity());
        return ActivityDto.from(updatedActivity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        activityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
