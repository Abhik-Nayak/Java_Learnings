package com.authApp.oauthJwt.repository;

import com.authApp.oauthJwt.model.Task;
import com.authApp.oauthJwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository {
    List<Task> findByOwner(User owner)
}
