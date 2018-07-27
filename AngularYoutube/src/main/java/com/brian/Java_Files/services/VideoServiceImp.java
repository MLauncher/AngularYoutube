package main.java.com.brian.Java_Files.services;

import org.springframework.stereotype.Service;

@Service("videoService")
public class VideoServiceImp implements VideoService {
	
	@Override
	public String printSpring(){
		return "Hello from Spring Service";
	}



}
