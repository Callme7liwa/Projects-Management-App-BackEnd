package com.pfe.projectsmanagements.services.Tache;

import com.pfe.projectsmanagements.Dto.Tach.TachRequestDto;
import com.pfe.projectsmanagements.dao.TachRepository;
import com.pfe.projectsmanagements.entities.Journalist;
import com.pfe.projectsmanagements.entities.Tach;
import com.pfe.projectsmanagements.exceptions.Tach.TachExisteException;
import com.pfe.projectsmanagements.exceptions.Tach.TachUnfoundException;
import com.pfe.projectsmanagements.services.Sequence.SequenceGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class TachServiceImpl implements  TachService{

    @Autowired
    private TachRepository tachRepository ;

    private Random random ;

    private final static Logger logger = LoggerFactory.getLogger(Tach.class);

    @Autowired
    private SequenceGeneratorService service;

    public TachServiceImpl(Random random) {
        this.random = random;
    }


    @Override
    public Tach saveTach(TachRequestDto request) {
        Tach tachSearch = tachRepository.findByName(request.getName().toLowerCase());
        if(Objects.nonNull(tachSearch))
            throw new TachExisteException();
        else
        {
            Tach tach = new Tach();
            tach.setId(service.getSequenceNumber(Tach.SEQUENCE_NAME));
            tach.setName(request.getName().toLowerCase());
            tach = tachRepository.save(tach);
            if(tach != null)
                return tach ;
            return null ;
        }
    }

    @Override
    public Tach getTach(String name) {
        Tach tach = tachRepository.findByName(name.toLowerCase());
        if(Objects.nonNull(tach))
            return tach;
        throw new TachUnfoundException();
    }

    @Override
    public List<Tach> getTaches() {
        return tachRepository.findAll();
    }

    @Override
    public List<Tach> getTachesById(Long journalistId) {
        return null ;
    }

    @Override
    public Tach changeStatus(String  status) {
      return null ;
    }
}
