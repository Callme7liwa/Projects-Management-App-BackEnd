package com.pfe.projectsmanagements;

import com.pfe.projectsmanagements.Enums.ERole;
import com.pfe.projectsmanagements.dao.CategoryRepository;
import com.pfe.projectsmanagements.dao.FunctionRepository;
import com.pfe.projectsmanagements.dao.RoleRepository;
import com.pfe.projectsmanagements.entities.Category;
import com.pfe.projectsmanagements.entities.Function;
import com.pfe.projectsmanagements.entities.JournalistRole;
import com.pfe.projectsmanagements.mappers.ConversationMapper;
import com.pfe.projectsmanagements.mappers.JournalistMapper;
import com.pfe.projectsmanagements.mappers.ProjectMapper;
import com.pfe.projectsmanagements.mappers.TeamMapper;
import com.pfe.projectsmanagements.security.JWTUtils;
import com.pfe.projectsmanagements.services.Sequence.SequenceGeneratorService;
import com.pfe.projectsmanagements.services.images.ImageService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableMongoRepositories
@OpenAPIDefinition(info = @Info(title="Projects Managements",version="2.0",description = ""))
public class ProjectsManagementsApplication  implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository ;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private FunctionRepository functionRepository ;


	@Autowired
	private SequenceGeneratorService service;

	public static void main(String[] args) {

		SpringApplication.run(ProjectsManagementsApplication.class, args);

	}

	@Resource
	ImageService storageService ;

	@Override
	public void run(String... arg) throws Exception {
		/*storageService.deleteAll();
		storageService.init();*/



		/*Function function  = new Function(service.getSequenceNumber(Function.SEQUENCE_NAME), "Web Developper");
		Function function1  = new Function(service.getSequenceNumber(Function.SEQUENCE_NAME) , "Marketer");
		Function function2  = new Function(service.getSequenceNumber(Function.SEQUENCE_NAME), "Desginer UI/UX");
		Function function3  = new Function(service.getSequenceNumber(Function.SEQUENCE_NAME) , "Mobile Developper");

		functionRepository.saveAll(List.of(function , function1 , function2,function3));


		Category category = new Category(service.getSequenceNumber(Category.SEQUENCE_NAME),"marketing");
		Category category1 = new Category(service.getSequenceNumber(Category.SEQUENCE_NAME) , "Developpement web");
		Category category2 = new Category(service.getSequenceNumber(Category.SEQUENCE_NAME) , "Developpement mobil");

		categoryRepository.save(category);
		categoryRepository.save(category1);
		categoryRepository.save(category2);
		JournalistRole role = new JournalistRole(service.getSequenceNumber(JournalistRole.SEQUENCE_NAME), ERole.CHEF);
		JournalistRole role1 = new JournalistRole(service.getSequenceNumber(JournalistRole.SEQUENCE_NAME),ERole.ADMIN);
		JournalistRole role2 = new JournalistRole(service.getSequenceNumber(JournalistRole.SEQUENCE_NAME),ERole.SIMPLE);

		roleRepository.save(role);
		roleRepository.save(role1);
		roleRepository.save(role2);*/




	}

	@Bean
	public Random getRandom()
	{
		return new Random();
	}

	@Bean
	public JWTUtils getJWTUtils()
	{
		return new JWTUtils();
	}

	@Bean
	public BCryptPasswordEncoder getBcryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ProjectMapper getProjectMapperInstance()
	{
		return  new ProjectMapper();
	}

	@Bean
	public TeamMapper getTeamMapperInstance()
	{
		return new TeamMapper();
	}

	@Bean
	public JournalistMapper journalistMapperInstance()
	{
		return new JournalistMapper();
	};

	@Bean
	public ConversationMapper conversationMapperInstance() { return new ConversationMapper();};




}
