package com.pfe.projectsmanagements.services.Client;

import com.pfe.projectsmanagements.dao.ClientRepository;
import com.pfe.projectsmanagements.entities.Client;
import com.pfe.projectsmanagements.entities.Project;
import com.pfe.projectsmanagements.exceptions.Clients.ClientContainProjectException;
import com.pfe.projectsmanagements.exceptions.Clients.ClientExistAlreadyException;
import com.pfe.projectsmanagements.exceptions.Clients.ClientUnFoundException;
import com.pfe.projectsmanagements.exceptions.Team.TeamNotFoundException;
import com.pfe.projectsmanagements.exceptions.functions.UnFoundFunctionException;
import com.pfe.projectsmanagements.services.Sequence.SequenceGeneratorService;
import com.pfe.projectsmanagements.services.images.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class ClientServiceImpl implements  ClientService{
    @Autowired
    private ClientRepository clientRepository ;

    @Autowired
    private SequenceGeneratorService service;

    @Autowired
    private ImageService storageService ;

    private final Random random;

    public ClientServiceImpl(Random random) {
        this.random = random;
    }

    @Override
    public Client saveClient(String clientName, MultipartFile file) {
        Optional<Client> clientOptional = clientRepository.findByName(clientName);
        if(clientOptional.isEmpty()) {
            Long clientId = service.getSequenceNumber(Client.SEQUENCE_NAME) ;
            String fileName = this.uploadPicture(file);
            Client client = Client.builder().id(clientId).name(clientName).photo(fileName).build();
            return clientRepository.save(client);
        }
        throw new ClientExistAlreadyException();
    }

    @Override
    public Client getClientByName(String clientName) {
        Optional<Client> clientOptional = clientRepository.findByName(clientName);
        if(clientOptional.isPresent())
            return clientOptional.get();
        throw new UnFoundFunctionException();
    }

    @Override
    public Client getClientById(Long clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if(clientOptional.isPresent())
            return clientOptional.get();
        throw new UnFoundFunctionException();
    }

    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public boolean deletedClientByName(String clientName) {
        Optional<Client> clientOptional = clientRepository.findByName(clientName);
        if(clientOptional.isPresent())
        {
            Client client = clientOptional.get();
            clientRepository.delete(client);
            return true ;
        }
        throw new UnFoundFunctionException();
    }

    @Override
    public boolean addProjectToClient(Project project, Client client) {
        Optional<Client> clientOptional = clientRepository.findByName(client.getName());
        if(clientOptional.isPresent())
        {
            Optional<Long> searchProjectById =  client
                    .getProjects()
                    .stream()
                    .filter(project1 -> project1.getId() == project.getId())
                    .map(project1 -> project1.getId())
                    .findFirst();
            if(searchProjectById.isEmpty()) {
                client.getProjects().add(project);
                clientRepository.save(client);
                return true ;
            }
        throw new ClientContainProjectException();
        }
        throw new ClientUnFoundException();
    }


    @Transactional
    public String uploadPicture(MultipartFile file) {
            String extension = ".jpg";
            if(file.getContentType().toString() == "image/jpeg")
                extension = ".jpg";
            else
            if(file.getContentType().toString() == "image/png")
                extension=".png";
            String fileName = UUID.randomUUID().toString()+extension ;
            storageService.fileUploadToServer("clients",fileName , file);
            return fileName ;
    }

}
