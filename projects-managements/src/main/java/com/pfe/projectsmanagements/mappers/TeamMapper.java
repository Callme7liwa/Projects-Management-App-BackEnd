package com.pfe.projectsmanagements.mappers;

import com.pfe.projectsmanagements.Dto.Journalist.response.JournalistInfo;
import com.pfe.projectsmanagements.Dto.Journalist.response.JournalistResponseDto;
import com.pfe.projectsmanagements.Dto.Team.request.TeamRequestDto;
import com.pfe.projectsmanagements.Dto.Team.response.TeamResponseDto;
import com.pfe.projectsmanagements.entities.Team;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class TeamMapper extends SupperMapper<TeamRequestDto, TeamResponseDto, Team> {

    public static TeamMapper instance = null ;

    private  JournalistMapper journalistMapper ;



    public static TeamMapper getInstance() {
        if(instance == null)
            instance = new TeamMapper();
        return  instance ;
    }

    @Override
    public Team DtoToEntity(TeamRequestDto teamRequestDto) {
        Team team  = new Team();
        BeanUtils.copyProperties(teamRequestDto,team);
        return team ;
    }

    @Override
    public TeamResponseDto EntityToDto(Team team) {
        TeamResponseDto teamResponseDto = new TeamResponseDto() ;
        journalistMapper = JournalistMapper.getInstance();
        List<JournalistResponseDto> journalistes =
                    team
                    .getJournalists()
                    .stream()
                    .map(journalist ->
                            {
                                return journalistMapper.EntityToDto(journalist);
                            }
                    ).collect(Collectors.toList());

        BeanUtils.copyProperties(team,teamResponseDto);
        teamResponseDto.setJournalistes(journalistes);
        //teamResponseDto.setJournalistes(null);
        return teamResponseDto;
    }


   /* public TeamResponseDto EntityToDtoSpecial(Team team) {
        TeamResponseDto teamResponseDto = new TeamResponseDto() ;
        journalistMapper = JournalistMapper.getInstance();
        List<JournalistInfo> journalistes =
                team
                        .getJournalists()
                        .stream()
                        .map(journalist ->
                                {
                                    return journalistMapper.EntityToSpecialDto(journalist);
                                }
                        ).collect(Collectors.toList());
        BeanUtils.copyProperties(team,teamResponseDto);
        teamResponseDto.setJournalistes(journalistes);
        return teamResponseDto;
    }*/


}
