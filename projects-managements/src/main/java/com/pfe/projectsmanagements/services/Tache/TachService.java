package com.pfe.projectsmanagements.services.Tache;

import com.pfe.projectsmanagements.Dto.Tach.TachRequestDto;
import com.pfe.projectsmanagements.entities.Tach;

import java.util.List;

public interface TachService {

    public Tach saveTach(TachRequestDto request);

    public Tach getTach(String name) ;

    public List<Tach> getTaches();

    public List<Tach> getTachesById(Long journalistId);

    public Tach changeStatus(String status);

}
