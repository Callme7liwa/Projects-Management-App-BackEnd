package com.pfe.projectsmanagements.services.Function;

import com.pfe.projectsmanagements.entities.Function;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface FunctionService {
    public Function saveFunction (String functionName);

    public List<Function> getFunctions() ;

    public Function getFunctionByName(String name) ;

    public Function deleteFunction (String name);
}
