package com.pfe.projectsmanagements.services.Client;

import com.pfe.projectsmanagements.entities.Client;
import com.pfe.projectsmanagements.entities.Project;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ClientService  {

    public Client saveClient(String clientName, MultipartFile file) ;

    public Client getClientByName(String clientName);

    public Client getClientById(Long clientId);

    public List<Client> getClients();

    public boolean deletedClientByName(String clientName) ;

    public boolean addProjectToClient(Project project , Client client);


}
