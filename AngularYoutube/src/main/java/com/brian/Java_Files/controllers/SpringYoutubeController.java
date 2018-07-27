package main.java.com.brian.Java_Files.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import main.java.com.brian.Java_Files.DAO.UtubeDAO;
import main.java.com.brian.Java_Files.channelAut.YoutubeClient;
import main.java.com.brian.Java_Files.hibernateFiles3.Videos;
import main.java.com.brian.Java_Files.services.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;




@Controller
@Component
@RequestMapping("/utube")
public class SpringYoutubeController {
	
	@Autowired
	VideoService vidservice;
	
	@Autowired
	UtubeDAO udao;

	ArrayList<Videos> results;

	public static void printMap(Map mp) {
	    Iterator it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}
	public static ArrayList<String> getKeys(Map mp){
		
		ArrayList<String> yids = new ArrayList<String>();
		Iterator it = mp.entrySet().iterator();
		
		while (it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			System.out.println("The Key " + (String)pair.getKey());
			System.out.println("The Value " + (String)pair.getValue());
			yids.add((String)pair.getValue());
		}
		return yids;
	}
	@RequestMapping("/webapp")
	  public @ResponseBody Videos home(ModelMap model) {
//	    Map<String,Object> model = new HashMap<String,Object>();
//	    model.put("id", UUID.randomUUID().toString());
//	    model.put("content", "Hello from Spring Controller");
	
		Videos vid = new Videos();
		vid.setIdVideos(1);
		vid.setVideoId("3432dfds");
	
		vid.setVideoTitle("Hello From Spring");
	    return vid;
	  }
	
	@RequestMapping("/data")
		public @ResponseBody Videos getVids(ModelMap model){
			List<Videos> vids = udao.getVideos();
			
			return vids.get(0);
	}
	
	@RequestMapping("/authorize")
		public @ResponseBody ArrayList<Videos> getAut(){
		YoutubeClient client = new YoutubeClient();
		ArrayList<Videos> cinfo = client.getChannelInfo();
		

		for(Videos video: cinfo){
			System.out.println("Video Title " + video.getVideoTitle() + " Video Id " + video.getVideoId());
		}
		//ArrayList<String> ids = getKeys(cinfo);
//		for(String id: ids){
//			System.out.println("u7The id is "  + id);
//		}
		return cinfo;
		
	}
	@RequestMapping("/search/{query}")
		public @ResponseBody void search(@PathVariable("query")String query){
		System.out.println("The query " + query);
		System.out.println("In the search controller");
		
		ArrayList<Videos> myResults = new ArrayList<Videos>();
		YoutubeClient client = new YoutubeClient();
		
		myResults = client.searchYouTube(query);
		results = myResults;
		System.out.println("Search Results: /n");
		for(Videos video: myResults){
			System.out.println("Video Title " + video.getVideoTitle() + " Video Id " + video.getVideoId());
		}
	}
	
	@RequestMapping("/results")
		public @ResponseBody ArrayList<Videos> getResults(){
		return results;
	}
//	@RequestMapping("/index.html")
//	public String returnHome()
//	{
//		return "index";
//	}
//	
	
	
}
