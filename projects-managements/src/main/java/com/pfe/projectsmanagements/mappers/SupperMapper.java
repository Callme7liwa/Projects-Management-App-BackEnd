package com.pfe.projectsmanagements.mappers;

import java.util.Random;

public  abstract class    SupperMapper<R,S,E> {

    protected  static  Random random = new Random() ;


    public  abstract E  DtoToEntity( R t);

    public    abstract S EntityToDto( E e);
}
