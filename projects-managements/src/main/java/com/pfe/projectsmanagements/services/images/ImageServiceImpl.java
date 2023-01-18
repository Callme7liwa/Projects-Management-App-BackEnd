package com.pfe.projectsmanagements.services.images;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements ImageService{

    private static Path root = Paths.get("uploads");

    private static String path = System.getProperty("home.user")+"/uploads";

    private static final String filePath = System.getProperty("user.home") + "/images";


    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }


    @Override
    public void uploadMyPicture(MultipartFile image) {

    }

    @Override
    public byte[] getImage(String namefull) throws IOException {
        String name = "salam.jpg";
        try {
            File file=new File(System.getProperty("user.home")+"/uploads/"+name);
            Path path = Paths.get(file.toURI());
            return Files.readAllBytes(path);
        }catch(Exception e)
        {
            throw new RuntimeException("Image File Unfound !!!");
        }

    }

    private static String imageDirectory = System.getProperty("user.dir") + "/images/";

    public boolean uploadImage(MultipartFile file,
                                          String name) {
        /*makeDirectoryIfNotExist(imageDirectory);
        Path fileNamePath = Paths.get(imageDirectory,
                name.concat(".").concat(FilenameUtils.getExtension(file.getOriginalFilename())));
        try {
            Files.write(fileNamePath, file.getBytes());
            return true
        } catch (IOException ex) {
            return false;
        }*/
        return true ;
    }

    private void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public byte[] displayImage(String name)
    {

        Path pathFile = root.resolve(name);
        try {
            return Files.readAllBytes(pathFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null ;
    }


    @Override
    public Boolean fileUploadToServer(String folderName , String filename, MultipartFile image){
        try {
            InputStream inputStream = image.getInputStream();
            // saves the files in same class path
            File imageDirectory = new File(filePath+"/"+folderName);
            if(!imageDirectory.exists()){
                imageDirectory.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(imageDirectory.getAbsolutePath()+"/"+filename);
            byte[] array = new byte[inputStream.available()];
            inputStream.read(array);
            fileOutputStream.write(array);
            fileOutputStream.flush();
            fileOutputStream.close();
            return  true;
        }catch (IOException e){
            throw new RuntimeException("cannot upload the file to the server !");
        }
    }

    @Override
    public byte[] getFileFromTheServer(String filename) throws IOException {
        System.out.println(filename);
        File file=new File(filePath + "/" +filename);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);

    }


}
