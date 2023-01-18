package com.pfe.projectsmanagements.controllers.Function;

import com.pfe.projectsmanagements.entities.Function;
import com.pfe.projectsmanagements.services.Function.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/functions")
public class FunctionController {

    @Autowired
    private FunctionService functionService ;

    @RequestMapping(path = "" , method = RequestMethod.POST , produces = "application/json")
    public ResponseEntity<Function> saveFunction(@RequestParam("functionName") String  functionName)
    {
        System.out.println("the function : " + functionName);
        Function function =  functionService.saveFunction(functionName);
        return ResponseEntity.ok().body(function);
    }

    @RequestMapping(path = ""  , method = RequestMethod.GET , produces = "application/json")
    public ResponseEntity<List<Function>> getFunction()
    {
        List<Function> functions = functionService.getFunctions();
        return ResponseEntity.ok().body(functions);
    }
}
