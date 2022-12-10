package com.rich.budgetapi.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rich.budgetapi.domain.model.PhotoUser;
import com.rich.budgetapi.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryQueries {
    Optional<User> findByEmail(String email);

    @Query("select p from PhotoUser p join p.user u where p.user.id = :userId")
    Optional<PhotoUser> findPhotoById(Long userId);
}
