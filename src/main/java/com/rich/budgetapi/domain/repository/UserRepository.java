package com.rich.budgetapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rich.budgetapi.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
