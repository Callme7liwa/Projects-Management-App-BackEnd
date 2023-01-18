package com.pfe.projectsmanagements.controllers.Journalist;

import com.pfe.projectsmanagements.Dto.Images.ResponseMessage;
import com.pfe.projectsmanagements.Dto.Journalist.request.JournalistRequestDto;
import com.pfe.projectsmanagements.Dto.Journalist.request.ListFunctionsToUserRequest;
import com.pfe.projectsmanagements.Dto.Journalist.response.JournalistResponseDto;
import com.pfe.projectsmanagements.Dto.Journalist.request.JournalistTachRequest;
import com.pfe.projectsmanagements.Dto.ProjectDto.response.ProjectResponseDto;
import com.pfe.projectsmanagements.Dto.Role.RoleRequestDto;
import com.pfe.projectsmanagements.entities.Journalist;
import com.pfe.projectsmanagements.entities.Project;
import com.pfe.projectsmanagements.entities.others.Affectation;
import com.pfe.projectsmanagements.services.Journalist.JournalistService;
import com.pfe.projectsmanagements.services.images.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/journalistes")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class JournalistRestController {

    private JournalistService journalistService ;

    private static final String filePath = System.getProperty("user.home") + "/images/employes";


    private ImageService imageService ;

    private final static Logger logger = LoggerFactory.getLogger(Journalist.class);


    @Autowired
    public JournalistRestController(JournalistService journalistService )
    {
        this.journalistService = journalistService;
    }

    @RequestMapping(value = "" , method = RequestMethod.POST , consumes = "application/json" , produces = "application/json")
    public ResponseEntity<JournalistResponseDto> saveJournalist(@RequestBody JournalistRequestDto journalistRequestDto)
    {

        JournalistResponseDto journalistResponseDto = journalistService.saveJournalist(journalistRequestDto);
        if(journalistResponseDto != null)
            return ResponseEntity.ok(journalistResponseDto) ;
        throw new RuntimeException("Error while creating a new employee ");
    }

   // @RequestMapping(value="/", method = RequestMethod.GET ,produces = "application/json")
    @GetMapping("")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<JournalistResponseDto>> getJournalists()
    {
        List<JournalistResponseDto> journalistResponseDtoList = journalistService.getAllJournalistes() ;
        if(journalistResponseDtoList.size()>0)
            return ResponseEntity.ok().body(journalistResponseDtoList);
        return   new ResponseEntity<>(null , HttpStatus.NO_CONTENT);

    }

    //@RequestMapping(value = "/addRole/{id}" , method = RequestMethod.POST , consumes = "application/json" , produces = "application/json")
    @PutMapping("/addRole/{id}")
    public ResponseEntity<?> addRoleToJournalist( @PathVariable("id") Long journalisteId , @RequestBody RoleRequestDto roleRequestDto)
    {
        Journalist journalist = journalistService.addRoleToUser(journalisteId,roleRequestDto.getName());
        return ResponseEntity.ok().body(journalist);
    }

    @PutMapping("/addFunction/{id}")
    public ResponseEntity<String> addFunctionToJournalist(@PathVariable("id") Long journalistId , @RequestParam("functionName") String functionName)
    {
        Boolean booleanResponse = journalistService.addFunctionToUser(journalistId , functionName);
        if(booleanResponse)
            return new ResponseEntity<>("Function  Added Succesfuly to the employe  ! " , HttpStatus.OK);
        return new ResponseEntity<>("Erreur lors de l'ajout de la fonction au journalist" , HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/addFunctions/{id}" )
    ResponseEntity<Journalist> addFunctionsToJournalist(@PathVariable("id") Long journalistId , @RequestBody ListFunctionsToUserRequest functionsName)
    {
        System.out.println(functionsName);
            Journalist journalist1 = journalistService.addFunctionsToJournalist(journalistId , functionsName.getFunctionsName());
            if(Objects.nonNull(journalist1))
                return new ResponseEntity<>(journalist1 , HttpStatus.OK);
            throw new RuntimeException("Error while add functions to journalists");
    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.GET , consumes = "application/json" , produces = "application/json")
    public ResponseEntity<?> getJournalist(@PathVariable("id") Long id )
    {
        JournalistResponseDto journalistResponseDto = journalistService.getJournalist(id);
        if(Objects.nonNull(journalistResponseDto))
            return ResponseEntity.status(HttpStatus.FOUND).body(journalistResponseDto);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO USER HERE WITH THIS ID");
    }

    /*@RequestMapping(value = "/affectations/{id}" , method = RequestMethod.GET  , consumes = "application/json" , produces = "application/json")
    public ResponseEntity<?> getAffectation(@PathVariable("id") Long id)
    {
        List<ProjectResponseDto> affectations = journalistService.getAffectations(id);
        if(affectations.size()>0)
            return new ResponseEntity<>(affectations , HttpStatus.FOUND);
        return new ResponseEntity<>("this journalist have no project working on !" , HttpStatus.OK);
    }*/

    @RequestMapping(value = "/fullJournalist/{id}" , method = RequestMethod.GET , consumes = "application/json" , produces = "application/json")
    public ResponseEntity<?> getFullJournalist(@PathVariable("id") Long id)
    {
        Journalist  journalist = journalistService.getFullJournalist(id);
        if(Objects.nonNull(journalist))
            return ResponseEntity.status(HttpStatus.FOUND).body(journalist);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO JOURNALIST HAS BEEN FOUND WITH THIS ID"+id);
    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.PUT , consumes = "application/json" , produces = "application/json")
    public ResponseEntity<?> updateJournalist(@PathVariable("id") Long id  , @RequestBody JournalistRequestDto requestDto)
    {
        JournalistResponseDto journalistResponseDto = journalistService.updateJournalist(requestDto,id);
        if(Objects.nonNull(journalistResponseDto))
            return ResponseEntity.status(HttpStatus.FOUND).body(journalistResponseDto);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO JOURNALIST HAS BEEN FOUND WITH THIS ID"+id);
    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.DELETE , consumes = "application/json" , produces = "application/json")
    public ResponseEntity<?> deleteJournalist(@PathVariable("id") Long id)
    {
        boolean booleanResponse = journalistService.deleteJournalist(id);
        if(booleanResponse)
            return ResponseEntity.status(HttpStatus.OK).body("JOURNALIST IS DELETED "+id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO JOURNALIST WITH THIS ID "+id);
    }

    @RequestMapping(value="/addTachToJournalist/{id}" , method = RequestMethod.POST  , consumes = "application/json" , produces = "application/json")
    public ResponseEntity<?> addTachToJournalist(@PathVariable("id") Long id , @RequestBody JournalistTachRequest request)
    {
        /*boolean booleanResponse = journalistService.addTachToJournalist(id , request.getProjectId() , request.getTachName());
            if(booleanResponse)
                return ResponseEntity.ok().body("Tach Added Succesfuly");
            return ResponseEntity.badRequest().body("Please make attention !!");*/
        return null ;
    }

    @RequestMapping(value="/getAffectations/{id}" , method = RequestMethod.GET)
    public ResponseEntity<List<ProjectResponseDto>> getAffectations(@PathVariable("id") Long journalistId)
    {
        List<ProjectResponseDto> affectations = journalistService.getAffectations(journalistId) ;
            return ResponseEntity.ok().body(affectations);
    }

    @RequestMapping(value="/uploadPicture" , method = RequestMethod.POST )
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file , @RequestParam("id") Long id) {
        String message = "";
        try {
            ResponseMessage  response = journalistService.uploadPicture(file , id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(null,message));
        }
    }

    @GetMapping(path = "/{filename}",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImageFromTheServer(@PathVariable("filename") String filename) throws IOException {
        try {
            File file = new File(filePath+"/"+filename);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                    .body(Files.readAllBytes(file.toPath()));
        }catch(IOException e)
        {
            throw  new IOException("Cannot get the picture !");
        }
    }

    @PostMapping(path="/changeImagePrincipal/{id}" )
    public ResponseEntity<Journalist> updateProfilePicture (@PathVariable("id") Long journalistId , @RequestParam("photoName") String photoName )
    {
        Journalist journalist = journalistService.updateProfilePicture(journalistId , photoName);
        return new ResponseEntity<>(journalist,HttpStatus.OK);
    }


}
