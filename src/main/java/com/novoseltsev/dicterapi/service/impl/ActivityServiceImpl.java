package com.novoseltsev.dicterapi.service.impl;

import com.novoseltsev.dicterapi.domain.entity.Activity;
import com.novoseltsev.dicterapi.domain.entity.User;
import com.novoseltsev.dicterapi.exception.ObjectNotFoundException;
import com.novoseltsev.dicterapi.repository.ActivityRepository;
import com.novoseltsev.dicterapi.service.ActivityService;
import com.novoseltsev.dicterapi.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final UserService userService;
    private final MessageSourceAccessor messageAccessor;

    @Autowired
    public ActivityServiceImpl(UserService userService, ActivityRepository activityRepository,
                               MessageSourceAccessor messageAccessor) {
        this.userService = userService;
        this.activityRepository = activityRepository;
        this.messageAccessor = messageAccessor;
    }

    @Override
    public Activity create(Activity activity) {
        Long userId = activity.getUser().getId();
        User user = userService.findById(userId);
        user.addActivity(activity);
        return activityRepository.save(activity);
    }

    @Override
    public Activity update(Activity activity) {
        Activity savedActivity = findById(activity.getId());
        savedActivity.setName(activity.getName());
        savedActivity.setDescription(activity.getDescription());
        return activityRepository.save(savedActivity);
    }

    @Override
    public void deleteById(Long id) {
        activityRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Activity findById(Long id) {
        String errorMessage = messageAccessor.getMessage("activity.not.found");
        return activityRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(errorMessage));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Activity> findAllByUserId(Long userId) {
        return activityRepository.findAllByUserIdOrderByIdDesc(userId);
    }
}
