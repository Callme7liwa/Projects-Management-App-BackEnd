package com.pfe.projectsmanagements.mappers;

import com.pfe.projectsmanagements.Dto.Journalist.request.JournalistRequestDto;
import com.pfe.projectsmanagements.Dto.Journalist.response.JournalistInfo;
import com.pfe.projectsmanagements.Dto.Journalist.response.JournalistResponseDto;
import com.pfe.projectsmanagements.Enums.Gender;
import com.pfe.projectsmanagements.entities.Journalist;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.stream.Collectors;

public class JournalistMapper extends  SupperMapper<JournalistRequestDto, JournalistResponseDto, Journalist> {


    private static JournalistMapper instance = null;

    public  static JournalistMapper getInstance () {
        if(instance == null )
            instance = new JournalistMapper();
        return  instance ;
    }

    @Override
    public Journalist DtoToEntity(JournalistRequestDto journalistRequestDto) {
        Journalist journalist = new Journalist();
        BeanUtils.copyProperties(journalistRequestDto,journalist);
        if(journalistRequestDto.getGender().toLowerCase().equals("male"))
            journalist.setGender(Gender.MALE);
        else
            journalist.setGender(Gender.FEMALE);
        return  journalist ;
    }

    @Override
    public JournalistResponseDto EntityToDto(Journalist journalist) {
        JournalistResponseDto responseDto = new JournalistResponseDto() ;
        BeanUtils.copyProperties(journalist,responseDto);
        Set<String> functions = journalist
                .getFunctions()
                .stream()
                .map(function -> {
                    return function.getName();
                })
                .collect(Collectors.toSet());
        Set<String> roles = journalist
                .getRoles()
                .stream()
                .map(role -> {
                    return role.getRole().toString();
                })
                .collect(Collectors.toSet());
        responseDto.setFunctions(functions);
        responseDto.setRoles(roles);
        return  responseDto;
    }

    public JournalistInfo EntityToSpecialDto(Journalist journalist)
    {
        JournalistInfo journalistInfo = new JournalistInfo( ) ;
        BeanUtils.copyProperties(journalist,journalistInfo);
        return journalistInfo ;
    }
}
