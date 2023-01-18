package com.pfe.projectsmanagements.services.Journalist;

import com.pfe.projectsmanagements.Dto.Images.ResponseMessage;
import com.pfe.projectsmanagements.Dto.Journalist.request.JournalistRequestDto;
import com.pfe.projectsmanagements.Dto.Journalist.response.JournalistResponseDto;
import com.pfe.projectsmanagements.Dto.ProjectDto.response.ProjectResponseDto;
import com.pfe.projectsmanagements.Dto.ProjectDto.response.TaskResp;
import com.pfe.projectsmanagements.Enums.ERole;
import com.pfe.projectsmanagements.dao.JournalistRepository;
import com.pfe.projectsmanagements.dao.RoleRepository;
import com.pfe.projectsmanagements.entities.*;
import com.pfe.projectsmanagements.entities.others.Affectation;
import com.pfe.projectsmanagements.entities.others.EmailDetails;
import com.pfe.projectsmanagements.exceptions.Journalist.ImageNotFoundException;
import com.pfe.projectsmanagements.exceptions.Journalist.JournalistExisteAlready;
import com.pfe.projectsmanagements.exceptions.Journalist.JournalistNotFoundException;
import com.pfe.projectsmanagements.exceptions.Project.ProjectUnfoundException;
import com.pfe.projectsmanagements.exceptions.Role.RoleNotFoundException;
import com.pfe.projectsmanagements.exceptions.Team.TeamNotFoundException;
import com.pfe.projectsmanagements.exceptions.functions.FunctionAlreadyExistException;
import com.pfe.projectsmanagements.mappers.JournalistMapper;
import com.pfe.projectsmanagements.mappers.ProjectMapper;
import com.pfe.projectsmanagements.services.Email.EmailService;
import com.pfe.projectsmanagements.services.Function.FunctionService;
import com.pfe.projectsmanagements.services.Project.ProjectService;
import com.pfe.projectsmanagements.services.Sequence.SequenceGeneratorService;
import com.pfe.projectsmanagements.services.Tache.TachService;
import com.pfe.projectsmanagements.services.images.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JournalistServiceImpl implements JournalistService{

    private final JournalistRepository journalistRepository ;

    private final RoleRepository roleRepository;

    private final JournalistMapper journalistMapper ;

    private final ProjectMapper projectMapper ;

    @Autowired
    private  TachService tachService;

    @Autowired
    private FunctionService functionService ;

    @Autowired
    private ImageService storageService ;

    private EmailService emailService ;

    private final Random random ;

    private final BCryptPasswordEncoder bCryptPasswordEncoder ;

    private final static Logger logger = LoggerFactory.getLogger(Journalist.class);

    @Autowired
    private SequenceGeneratorService service;



    @Autowired
    public  JournalistServiceImpl(JournalistRepository journalistRepository, EmailService emailService, RoleRepository roleRepository, ProjectService projectService, ProjectMapper projectMapper, Random random, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.journalistRepository = journalistRepository ;
        this.roleRepository = roleRepository ;
        this.projectMapper = projectMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.journalistMapper = JournalistMapper.getInstance();
        this.emailService = emailService ;
        this.random = random ;
    }

    @Override
    // Test If Exist By Username and Email  -> if not -> save user with simple role -> sending the password on email for the journaliste
    public JournalistResponseDto saveJournalist(JournalistRequestDto requestDto) {

        Optional <Journalist> journalistOptionalByUsername = journalistRepository.findByUserName(requestDto.getUserName());
        Optional <Journalist> journalistOptionalByEmail = journalistRepository.findByEmail(requestDto.getEmail());

        if(journalistOptionalByEmail.isPresent() || journalistOptionalByUsername.isPresent()) {
            throw new JournalistExisteAlready();
        }
        Journalist journalist = journalistMapper.DtoToEntity(requestDto);


        journalist.setId(service.getSequenceNumber(Journalist.SEQUENCE_NAME));
        String password = UUID.randomUUID().toString()  ;
        journalist.setPassword(bCryptPasswordEncoder.encode(password));
        // Add Role
        JournalistRole simpleRole = roleRepository.findByRole(String.valueOf(ERole.SIMPLE)).get() ;
        // Add functions
        Set<Function> functions = requestDto
                .getFunctions()
                .stream()
                .map(functionName->{
                    return functionService.getFunctionByName(functionName);
                })
                .collect(Collectors.toSet());
        journalist.setFunctions(functions);
        //
        Journalist journalistResponse = journalistRepository.save(journalist) ;
        //
        if(Objects.nonNull(journalistResponse)) {
            JournalistResponseDto journalistResponseDto = journalistMapper.EntityToDto(journalistResponse);
            EmailDetails emailDetails = EmailDetails
                    .builder()
                    .subject("ACCOUNT CREATING ")
                    .msgBody("Hey " +journalist.getUserName() +
                            " This is your password " + password
                            +" For any requirement contact me , welcome !"
                            )
                    .recipient(journalist.getEmail())
                    .build();
            emailService.sendSimpleMail(emailDetails);
            return journalistResponseDto;
        }
        return null ;
    }

    @Override
    public Journalist addRoleToUser(Long id , String roleName) {

        Optional<Journalist> journalistOptional = journalistRepository.findById(id);
        if(journalistOptional.isPresent())
        {
            Journalist journalist  = journalistOptional.get();
            Optional<JournalistRole> role = roleRepository.findByRole(roleName);
            if(role.isPresent()) {
                journalist.getRoles().add(role.get());
                journalistRepository.save(journalist);
                return journalist ;
            }
            throw new RoleNotFoundException();
        }
        throw new JournalistNotFoundException();

    }

    @Override
    public Boolean addFunctionToUser(Long journalistId, String functionName) {
        Optional<Journalist> journalistOptional = journalistRepository.findById(journalistId);
        if(journalistOptional.isPresent())
        {
           Journalist journalist = journalistOptional.get();
           Optional<Function> functionSearch=  journalist
                   .getFunctions()
                   .stream()
                   .filter(function -> function.getName().equals(functionName))
                   .findFirst();
           if(functionSearch.isEmpty()) {
               Function function = functionService.getFunctionByName(functionName);
               journalist.getFunctions().add(function);
               journalistRepository.save(journalist);
               return Boolean.TRUE;
           }
           throw new  FunctionAlreadyExistException();
        }
        throw new JournalistNotFoundException();
    }

    @Override
    public JournalistResponseDto getJournalist(Long id) {
        Optional<Journalist> journalistOptional = journalistRepository.findById(id);
        if(journalistOptional.isPresent())
            return journalistMapper.EntityToDto(journalistOptional.get());
        throw new JournalistNotFoundException();
    }

    @Override
    public Journalist getFullJournalist(Long id) {
        Optional<Journalist> journalistOptional = journalistRepository.findById(id);
        if(journalistOptional.isPresent())
                return journalistOptional.get();
        throw new JournalistNotFoundException();
    }

    @Override
    public Journalist getJournalistByUsername(String username) {
        Optional<Journalist> journalist = journalistRepository.findByUserName(username);
        if(journalist.isPresent())
            return journalist.get();
        throw new JournalistNotFoundException();
    }

    @Override
    public List<JournalistResponseDto> getAllJournalistes() {
        List<Journalist> journalists = journalistRepository.findAll();
        return journalists
                .stream()
                .map(journalist -> {
                    return  journalistMapper.EntityToDto(journalist);
                }).collect(Collectors.toList());
    }

    @Override
    public List<Journalist> getFullJournalistes() {
        return journalistRepository.findAll();
    }

    @Override
    public JournalistResponseDto updateJournalist(JournalistRequestDto journalistRequestDto, Long id) {
        Optional<Journalist> journalistOptional = journalistRepository.findById(id);
        if(journalistOptional.isPresent())
        {
            Journalist journalist = journalistOptional.get();
            BeanUtils.copyProperties(journalistRequestDto,journalist);
            journalistRepository.save(journalist);
        }
        throw new JournalistNotFoundException() ;
    }

    @Override
    public boolean deleteJournalist(Long id) {
        Optional<Journalist> journalistOptional = journalistRepository.findById(id);
        if(journalistOptional.isPresent())
        {
            Journalist journalist = journalistOptional.get();
            journalistRepository.delete(journalist);
            return  true ;
        }
        return false ;
    }
    //Appeler a partir d'autre service .
    @Override
    public Journalist addTeamToJournalist(Journalist journalist, Team team) {
        if(Objects.nonNull(journalist) && Objects.nonNull(team))
        {
            journalist.getTeams().add(team);
            journalistRepository.save(journalist);
            return journalist ;
        }
        else {
            if(Objects.isNull(journalist))
                throw new JournalistNotFoundException();
            if(Objects.isNull(team))
                throw new TeamNotFoundException();
            return null ;
        }
    }

    @Override
    public List<Tach> getTaches(Long journalistId) {
       /* Optional<Journalist> journalist = journalistRepository.findById(journalistId);
        if(journalist.isPresent())
        {
            return  journalist.get().getAffectation().getTaches();
        }*/
        return null ;
    }

    // called from project service ( AffecterTacheToJournalist )
    // on ajoute une tach d'un projet que l'employe a ete deja affecter !
    // Verifie si journalist exist
    // sinon => exception
    // sioui => on verifie si le projet existe
    // sinon => exception
    // sioui => on ajout !
    @Override
    public boolean addTachToJournalist(Long journalistId, Long projectId, Tach  tach) {
        Optional<Journalist> journalistOptional = journalistRepository.findById(journalistId);
        if(journalistOptional.isPresent()) {
            Journalist journalist = journalistOptional.get();
            if(journalist.getAffectations().size() > 0) {
                Optional<Affectation> affectationOptional = journalist
                        .getAffectations()
                        .stream()
                        .filter(affectation -> affectation.getProject().getId()!=null && affectation.getTeam().getId()!=null)
                        .filter(affectation -> affectation.getProject().getId().equals(projectId))
                        .findFirst();
            if(affectationOptional.isEmpty()) {
                throw new RuntimeException("Aucun project existe");
            }
            else {
                Affectation affectation =  affectationOptional.get();
                journalist.getAffectations().remove(affectation);
                affectation.getTaches().add(tach);
                journalist.getAffectations().add(affectation);
                journalistRepository.save(journalist);
                return true ;
            }
            }
        }
         throw new JournalistNotFoundException();
    }

    // Appeler a partir d'autre service
    // a fin pour ajouter une nouvelle affectation on doit verifier , si le journalist et deja affecter
    // si oui on leve une exception
    // sinon ,  a fin de garder la trace sur les teams que l'employe a integre ( au cas ou l'employe a ete enlever
    // d'un projet ) , on ' a un tableau des teams dans le document des employes qui contient tous les teams que ces dernies
    // on integrees
    @Override
    public void addProjectAndTeamToJournalist(Journalist journalist, Project project) {
        if(journalist.getAffectations().size() > 0) {
            Optional<Affectation> affectationOptional =
                    journalist
                            .getAffectations()
                            .stream()
                            .filter(affectation -> affectation.getProject().getId() == project.getId())
                            .findFirst();
            if (affectationOptional.isPresent())
                throw new RuntimeException(" This Project already Exist ! ");
        }


        if(journalist.getTeams().size()>0)
        {
            if(!journalist.getTeams().contains(project.getTeam()))
                journalist.getTeams().add(project.getTeam());
        }
        Affectation affectation1 = new Affectation();
        affectation1.setProject(project);
        affectation1.setTeam(project.getTeam());
        journalist.getAffectations().add(affectation1);
        journalistRepository.save(journalist);
    }

    @Override
    public List<ProjectResponseDto> getAffectations(Long journalistId) {
        Optional<Journalist> journalistOptional = journalistRepository.findById(journalistId) ;
        if(journalistOptional.isPresent())
        {
            Journalist journalist = journalistOptional.get();
            List<ProjectResponseDto> affectations =
                    journalist
                    .getAffectations()
                    .stream()
                    .filter(affectation -> affectation.getProject().getId()!=null)
                    .map(affectation -> affectation.getProject())
                    .map(project -> projectMapper.EntityToDto(project))
                    .map(projectResponseDto -> {
                        System.out.println("************"+projectResponseDto);
                        Set<TaskResp> tasks = projectResponseDto.getTasks()
                                .stream().filter(taskResp -> taskResp.getJournalistId()!=null ).collect(Collectors.toSet());
                        Set<TaskResp> tasks2 = tasks.stream().filter(task->task.getJournalistId().equals(journalistId)).collect(Collectors.toSet());
                        projectResponseDto.setTasks(tasks);
                        return projectResponseDto;
                    })
                    .collect(Collectors.toList());
            return affectations ;
        }
        throw new JournalistNotFoundException();
    }

    // Lorsqu'on supprime un projet on doit supprimer l'affectation du l'elmploye de l'autre cote
    // on verifie si l'employe exitst
    // sinon => exception
    // sioui => On supprime ! , toute en gardant la trace !
    @Override
    public boolean removeProjectFromJournalist(Long journalistId, Project project) {
        Optional<Journalist> journalistOptional = journalistRepository.findById(journalistId);
        if(journalistOptional.isPresent())
        {
            Journalist journalist = journalistOptional.get();
            Optional<Affectation> affectationOptional = journalist
                    .getAffectations()
                    .stream()
                    .filter(affectation -> affectation.getProject().getId().equals(project.getId()))
                    .findFirst();
            if(affectationOptional.isPresent())
            {
                journalist.getAffectations().remove(affectationOptional.get());
                journalistRepository.save(journalist);
                return  true ;
            }
            throw new  ProjectUnfoundException();
        }
        throw new JournalistNotFoundException();
    }

    @Override
    @Transactional
    public ResponseMessage uploadPicture(MultipartFile file, Long id) {
        Optional<Journalist> journalistOptional = journalistRepository.findById(id);
        if(journalistOptional.isPresent())
        {
            Journalist journalist  = journalistOptional.get();

            String extension = ".jpg";
            if(file.getContentType().toString() == "image/jpeg")
                extension = ".jpg";
            else
                if(file.getContentType().toString() == "image/png")
                    extension=".png";

            String fileName = UUID.randomUUID().toString()+extension ;
            storageService.fileUploadToServer("employes" , fileName , file);
            journalist.getOldPics().add(fileName);
            journalist.setPhoto(fileName);
            Journalist journalistResponse =  journalistRepository.save(journalist);
            return
                        ResponseMessage
                                .builder()
                                .journalist(journalistResponse)
                                .message("The file was uploaded sucessfuly  ! ")
                                .build()
                    ;
        }
        throw new JournalistNotFoundException();
    }

    @Override
    public Journalist updateProfilePicture(Long journalistId, String photoName) {
        Optional<Journalist> journalistOptional = journalistRepository.findById(journalistId) ;
        if(journalistOptional.isPresent())
        {
            Journalist journalist = journalistOptional.get();
            journalist
                .getOldPics()
                        .stream()
                                .filter(picName->photoName.equals(photoName))
                                        .findFirst()
                                                .orElseThrow(()-> new ImageNotFoundException());
            journalist.setPhoto(photoName);
            return journalistRepository.save(journalist);
        }
        throw new JournalistNotFoundException();
    }

    @Override
    public Journalist addFunctionsToJournalist(Long journalistId, Set<String> setFunctions) {
        Optional<Journalist> journalistOptional = journalistRepository.findById(journalistId);
        if(journalistOptional.isPresent())
        {
            Journalist journalist = journalistOptional.get();
            Set<Function> functions = setFunctions
                    .stream()
                    .map(functionName->{
                        Function function = functionService.getFunctionByName(functionName);
                        return function;
                    })
                    .collect(Collectors.toSet());
            functions
                    .stream()
                            .forEach(function -> {
                                journalist
                                        .getFunctions()
                                        .add(function);
                            });
             return journalistRepository.save(journalist);
        }
        throw new JournalistNotFoundException();
    }


}
