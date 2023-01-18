package com.pfe.projectsmanagements.services.Journalist;

import com.pfe.projectsmanagements.Dto.Images.ResponseMessage;
import com.pfe.projectsmanagements.Dto.Journalist.request.JournalistRequestDto;
import com.pfe.projectsmanagements.Dto.Journalist.response.JournalistResponseDto;
import com.pfe.projectsmanagements.Dto.ProjectDto.response.ProjectResponseDto;
import com.pfe.projectsmanagements.entities.Journalist;
import com.pfe.projectsmanagements.entities.Project;
import com.pfe.projectsmanagements.entities.Tach;
import com.pfe.projectsmanagements.entities.Team;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface JournalistService {

    public JournalistResponseDto saveJournalist(JournalistRequestDto requestDto);

    public Journalist addRoleToUser(Long journalistId ,  String Role);

    public Boolean addFunctionToUser(Long journalistId , String functionName);

    public JournalistResponseDto  getJournalist(Long id);

    public Journalist getFullJournalist(Long id );

    public Journalist getJournalistByUsername(String name);

    public List<JournalistResponseDto> getAllJournalistes();

    public List<Journalist> getFullJournalistes();


    public JournalistResponseDto updateJournalist(JournalistRequestDto  journalistRequestDto , Long id );

    public boolean deleteJournalist(Long id );

    public Journalist addTeamToJournalist(Journalist journalist , Team team);

    public List<Tach> getTaches(Long journalistId) ;

    public boolean addTachToJournalist(Long journalistId , Long projectId , Tach tach ) ;

    void addProjectAndTeamToJournalist(Journalist journalist , Project project);

    public List<ProjectResponseDto> getAffectations(Long journalistId);

    public boolean removeProjectFromJournalist(Long journalistId , Project project) ;


    ResponseMessage uploadPicture(MultipartFile file, Long id);

    Journalist updateProfilePicture(Long journalistId, String photoName);

    Journalist addFunctionsToJournalist(Long journalistId , Set<String> setFunctions) ;
}
