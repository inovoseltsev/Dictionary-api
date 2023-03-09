package com.novoseltsev.dicterapi.repository;

import com.novoseltsev.dicterapi.domain.entity.Activity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {

    List<Activity> findAllByUserIdOrderByIdDesc(Long userId);
}
