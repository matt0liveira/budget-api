package com.rich.budgetapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rich.budgetapi.domain.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

}
