package com.pfe.projectsmanagements.services.Function;

import com.pfe.projectsmanagements.dao.FunctionRepository;
import com.pfe.projectsmanagements.entities.Function;
import com.pfe.projectsmanagements.entities.Project;
import com.pfe.projectsmanagements.exceptions.functions.FunctionAlreadyExistException;
import com.pfe.projectsmanagements.exceptions.functions.UnFoundFunctionException;
import com.pfe.projectsmanagements.services.Sequence.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class FunctionServiceImpl implements  FunctionService{

    @Autowired
    private FunctionRepository functionRepository ;

    @Autowired
    private SequenceGeneratorService service;

    private final Random random ;

    public FunctionServiceImpl(Random random) {
        this.random = random;
    }

    @Override
    public Function saveFunction(String functionName) {
        Optional<Function> functionOptional = functionRepository.findByName(functionName);
        if(functionOptional.isEmpty()) {
            Function function = Function.builder().id(service.getSequenceNumber(Function.SEQUENCE_NAME)).name(functionName).build();
            functionRepository.save(function);
            return function ;
        }
        throw new FunctionAlreadyExistException();
    }

    @Override
    public List<Function> getFunctions() {
        List<Function> function = functionRepository.findAll();
        return function ;
    }

    @Override
    public Function getFunctionByName(String functionName) {
        Optional<Function> functionOptional = functionRepository.findByName(functionName);
        if(functionOptional.isPresent())
            return functionOptional.get();
        throw new UnFoundFunctionException();
    }

    @Override
    public Function deleteFunction(String name) {
        return null;
    }
}
