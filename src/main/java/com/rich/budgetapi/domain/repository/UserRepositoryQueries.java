package com.rich.budgetapi.domain.repository;

import com.rich.budgetapi.domain.model.PhotoUser;

public interface UserRepositoryQueries {

    PhotoUser save(PhotoUser photo);

    void delete(PhotoUser photo);
}
