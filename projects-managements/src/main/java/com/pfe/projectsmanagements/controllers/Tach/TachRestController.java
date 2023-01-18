package com.pfe.projectsmanagements.controllers.Tach;

import com.pfe.projectsmanagements.Dto.Tach.TachRequestDto;
import com.pfe.projectsmanagements.entities.Tach;
import com.pfe.projectsmanagements.services.Tache.TachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taches")
@CrossOrigin(origins = "http://localhost:3000")
public class TachRestController {


    @Autowired
    private TachService tachService ;

    @PostMapping("")
    public ResponseEntity<Tach> saveTach(@RequestBody TachRequestDto request)
    {
        System.out.println(request);
        Tach tach = tachService.saveTach(request);
        if(tach != null)
            return  ResponseEntity.ok().body(tach);
        throw new RuntimeException("Error while creating a new taches ");
    }

    @GetMapping("")
    public ResponseEntity<?> getTaches()
    {
        List<Tach> taches = tachService.getTaches();
        return  ResponseEntity.ok().body(taches);
    }
}
