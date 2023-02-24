package com.aeon.restapi.models.repos;

import com.aeon.restapi.models.entities.Training;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TrainingRepo extends CrudRepository<Training, Long> {
    // search Training by Tema, insensitive case
    Page<Training> searchTrainingByTemaContainsIgnoreCase(String tema, Pageable pageable);
}
