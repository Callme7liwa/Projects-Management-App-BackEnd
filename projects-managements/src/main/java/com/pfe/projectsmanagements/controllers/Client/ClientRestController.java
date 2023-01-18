package com.pfe.projectsmanagements.controllers.Client;

import com.pfe.projectsmanagements.entities.Client;
import com.pfe.projectsmanagements.services.Client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/clients")
public class ClientRestController {

    private static final String filePath = System.getProperty("user.home") + "/images/clients/";


    @Autowired
    private ClientService clientService ;

    @RequestMapping(path="" , method = RequestMethod.POST  , produces =  "application/json")
    public ResponseEntity<Client> saveClient(@RequestParam("name") String clientName , @RequestParam("file") MultipartFile file)
    {
        System.out.println(clientName + file.getOriginalFilename());
        Client client = clientService.saveClient(clientName , file );
        return new  ResponseEntity<>(client, HttpStatus.OK);
    }

    @RequestMapping(path = "/{name}" ,  method = RequestMethod.GET  , produces =  "application/json")
    public ResponseEntity<Client> getClientByName(@PathVariable("name") String clientName)
    {
        Client client = clientService.getClientByName(clientName);
        return new ResponseEntity<>(client , HttpStatus.OK);
    }

    @RequestMapping(path="/{id}" ,  method = RequestMethod.GET  , produces =  "application/json")
    public ResponseEntity<Client> getClientById(@PathVariable("id") Long clientId)
    {
        Client client = clientService.getClientById(clientId);
        return new ResponseEntity<>(client , HttpStatus.OK);
    }

    @RequestMapping(path="" ,  method = RequestMethod.GET  , produces =  "application/json")
    public ResponseEntity<List<Client>> getClient()
    {
        List<Client> clientList = clientService.getClients();
        return new ResponseEntity<>(clientList , HttpStatus.OK);
    }

    @GetMapping(path = "/{filename}",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImageFromTheServer(@PathVariable("filename") String filename) throws IOException {
        try {
            File file = new File(filePath+filename);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                    .body(Files.readAllBytes(file.toPath()));
        }catch(IOException e)
        {
            throw  new IOException("Cannot get the picture !");
        }

    }

}
