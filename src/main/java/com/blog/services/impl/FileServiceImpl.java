package com.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.codec.CodecConfigurer.MultipartCodecs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService{

private String Upolad_path ="";

	public FileServiceImpl() throws IOException
	{
		
	}	
	
		
	
	
	
//	@Override
//	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
//		
//			
//		String fullpath= path+File.separator+fileName;
//		InputStream is= new FileInputStream(fullpath);
//		return is;
//	}
//




	public String uploadImage( String path,MultipartFile file) throws IOException {
		
//		Files.copy(file.getInputStream(),Paths.get(path+File.separator+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
		//FileName
		
		
		
	 String name= file.getOriginalFilename();
		 
		 //random name generate file
		 String randomID= UUID.randomUUID().toString();
		 String filename1=randomID.concat(name).substring(name.lastIndexOf("."));
		
		
		//FullPath
		
		String FilePath=path+File.separator+filename1;
		
		//createFolder if not created 
		File f= new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		//file copy
	Files.copy((file).getInputStream(),Paths.get(FilePath));
		
//		
//		
//		//return name;
//		
//		
//		
		return filename1;



//return file.getOriginalFilename();


		
	}





	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
	
		String fullpath= path+File.separator+fileName;
		InputStream is = new FileInputStream(fullpath);
		return is;
	}








	

}
