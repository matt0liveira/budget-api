package com.rich.budgetapi.infrasctruture.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.rich.budgetapi.domain.model.PhotoUser;
import com.rich.budgetapi.domain.repository.UserRepositoryQueries;

@Repository
public class UserRepositoryImpl implements UserRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public PhotoUser save(PhotoUser photo) {
        return manager.merge(photo);
    }

    @Transactional
    @Override
    public void delete(PhotoUser photo) {
        manager.remove(photo);
    }

}
