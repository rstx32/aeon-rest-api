package com.aeon.restapi.service;

import com.aeon.restapi.models.entities.Training;
import com.aeon.restapi.models.repos.TrainingRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class TrainingService {
    @Autowired
    private TrainingRepo trainingRepo;

    public Training createTraining(Training training) {
        training.setCreated_date(new Date());
        return trainingRepo.save(training);
    }

    public Training updateTraining(Training training) {
        if (training.getNama_pengajar() == null) {
            training.setNama_pengajar(
                    findTraining(training.getId()).getNama_pengajar()
            );
        }

        if (training.getTema() == null) {
            training.setTema(
                    findTraining(training.getId()).getTema()
            );
        }

        training.setCreated_date(
                findTraining(training.getId()).getCreated_date()
        );
        training.setUpdated_date(new Date());

        return trainingRepo.save(training);
    }

    public Training deleteTraining(Training training){
        training.setDeleted_date(new Date());
        return trainingRepo.save(training);
    }

    public Training findTraining(Long id) {
        Optional<Training> training = trainingRepo.findById(id);
        if (!training.isPresent() || training.get().getDeleted_date() != null) {
            return null;
        }
        return training.get();
    }

    public Iterable<Training> searchTraining(String tema, Pageable pageable) {
        return trainingRepo.searchTrainingByTemaContainsIgnoreCase(tema, pageable);
    }
}
