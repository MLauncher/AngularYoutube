package main.java.com.brian.Java_Files.channelAut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import main.java.com.brian.Java_Files.hibernateFiles3.Videos;

import com.google.common.collect.Lists;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.ChannelSnippet;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;



public class YoutubeClient  {
	
	ArrayList<Videos> allvideos;
	static ArrayList<Videos> results;
	
	private static final String PROPERTIES_FILENAME = "youtube.properties";

    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;

	//Prints out all the videos' information in a playlist
	public ArrayList<Videos> makePretty(int size, Iterator<PlaylistItem> playlistEntries)
	{
		ArrayList<Videos> history = new ArrayList<Videos>();
		System.out.println("=============================================================");
        System.out.println("\t\tLiked Videos: " + size);
        System.out.println("=============================================================\n");

        ArrayList<Videos> videoList = new ArrayList<Videos>();
        while (playlistEntries.hasNext()) {
        	
        	//Storing it in a hashmap for the other Controller to get
            PlaylistItem playlistItem = playlistEntries.next();
            
              Videos vid = new Videos();
              vid.setIdVideos(1);
              vid.setVideoId(playlistItem.getContentDetails().getVideoId());
              vid.setVideoTitle(playlistItem.getSnippet().getTitle());
              history.add(vid);
              	
//            System.out.println(" video name  = " + playlistItem.getSnippet().getTitle());
//            System.out.println(" video id    = " + playlistItem.getContentDetails().getVideoId());
//            System.out.println(" upload date = " + playlistItem.getSnippet().getPublishedAt());
//            System.out.println(" description  = " + playlistItem.getSnippet().getDescription());
//            System.out.println("\n-------------------------------------------------------------\n");
//            history.put(playlistItem.getSnippet().getTitle(), playlistItem.getContentDetails().getVideoId());
        }
        allvideos = history;
        return history;
	}

	
	public ArrayList<Videos> getChannelInfo() {
		YouTube youtube;

		//Setup the Authencation process
		List<String> scopes = Lists
				.newArrayList("https://www.googleapis.com/auth/youtube");
		Credential credential;
		
		
		try {
			
			//More setup second param can be anything 
			//Auth is a class used for authenticating the user is about access their own data(ripped straight from the sample)
			credential = Auth.authorize(scopes, "random");
			System.out.println("SOmething");
			//Applicatoin name can be anything but it is best to used the name of the application that made on the api site or application name
			youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT,
					Auth.JSON_FACTORY, credential).setApplicationName(
					"YoutubePractice").build();

			//Makes the request to get the channels
			YouTube.Channels.List request = youtube.channels().list(
					"contentDetails");
			System.out.println("Words" + youtube.playlistItems().toString());
			
			
			//Gets the channel of the currently active user
			//Side Note: Must find a way to get any user request.forUsername gives bad request something about no filter
		
			request.setMine(true);

			//Gets the results from the request
			ChannelListResponse results = request.execute();
			//The results are the list of channels
			List<Channel> channelsList = results.getItems();

			System.out.println("Number of channels: " + results.size());
			System.out.println("First Channel: "+ channelsList.get(0).getContentDetails().toPrettyString());
			if (channelsList != null) {

				//Gets the current user channel watch history
				//Sidenote: get(0) is the userchannel the other channels may be the people you subscribe to
				//Since i am not subsribed to anyone it is null.....wait i thought was subscribed to the Project M channel but it keeps returning one
				//As of September 16, 2016, You can not get the User's watch history and watch later.
				String likedvideos = channelsList.get(0).getContentDetails()
						.getRelatedPlaylists().getLikes();

				
				//Creates a list to hold the vides in watch history or anyother playlist I decide to change .getWatchHistory()at line 81
				List<PlaylistItem> playlistItemList = new ArrayList<PlaylistItem>();
				
				//Makes a request for the playlist using the id from likedvideos
				YouTube.PlaylistItems.List playlistItemRequest = youtube
						.playlistItems().list("id,contentDetails,snippet");
				playlistItemRequest.setPlaylistId(likedvideos);

				
				playlistItemRequest
						.setFields("items(contentDetails/videoId,snippet/title,snippet/publishedAt),nextPageToken,pageInfo");

				String nextToken = "";

				//Adds video information to playlist
				do {
					playlistItemRequest.setPageToken(nextToken);
					PlaylistItemListResponse playlistItemResult = playlistItemRequest
							.execute();

					playlistItemList.addAll(playlistItemResult.getItems());
					
					nextToken = playlistItemResult.getNextPageToken();
				} while (nextToken != null);
				
				//Prints playlist out to the console....neatly
				System.out.println("Something32");
				return makePretty(playlistItemList.size(), playlistItemList.iterator());


			} else
				//Couldn't find the playlist
				System.out.println("No Videos");
				HashMap<String,String> nothing = new HashMap<String,String>();
			

			//Exception Checking
		} catch (GoogleJsonResponseException d) {
			d.printStackTrace();
			System.err.println("GoogleJsonResponseException code: "
					+ d.getDetails().getCode() + " : "
					+ d.getDetails().getMessage());
			d.printStackTrace();
			

		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
			e.printStackTrace();
			
		}
		return allvideos;
		

	}
	
	public ArrayList<Videos> searchYouTube(String query){
		YouTube youtube;
		List<String> scopes = Lists
				.newArrayList("https://www.googleapis.com/auth/youtube");
		Credential credential;
		ArrayList<Videos> qResults = new ArrayList<Videos>();
		
		try {
			
			//More setup second param can be anything 
			//Auth is a class used for authenticating the user is about access their own data(ripped straight from the sample)
			credential = Auth.authorize(scopes, "random");
			
			//Application name can be anything but it is best to used the name of the application that made on the api site or application name
			youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT,
					Auth.JSON_FACTORY, credential).setApplicationName(
					"YoutubePractice").build();
			
		
			
           

            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");

            // Set your developer key from the {{ Google Cloud Console }} for
            // non-authenticated requests. See:
            // {{ https://cloud.google.com/console }}
            search.setQ(query);
            
            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            search.setType("video");

            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            // Call the API and print results.
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();


      
            if (searchResultList != null) {
               qResults = getResults(searchResultList.iterator(), query);
            }
            else{
            	ArrayList<Videos> emptyList = new ArrayList<Videos>();
            	
            	Videos novids = new Videos();
            	novids.setIdVideos(1);
            	novids.setVideoId("No Id/No Results Found");
            	novids.setVideoTitle("No Title/No Results Found");
            	emptyList.add(novids);
            	
            	qResults = emptyList;
            }
            
            
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
		
		return qResults;

	}
		
		
		
		private static ArrayList<Videos> getResults(Iterator<SearchResult> iteratorSearchResults, String query) {

	        System.out.println("\n=============================================================");
	        System.out.println(
	                "   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
	        System.out.println("=============================================================\n");

	        ArrayList<Videos> sResults = new ArrayList<Videos>();
	        if (!iteratorSearchResults.hasNext()) {
	            System.out.println(" There aren't any results for your query.");
	            
	            return sResults;
	        }

	        while (iteratorSearchResults.hasNext()) {

	            SearchResult singleVideo = iteratorSearchResults.next();
	            ResourceId rId = singleVideo.getId();

	            // Confirm that the result represents a video. Otherwise, the
	            // item will not contain a video ID.
	            if (rId.getKind().equals("youtube#video")) {
	                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();

	                Videos nVideo = new Videos();
	                nVideo.setIdVideos(1);
	                nVideo.setVideoId(rId.getVideoId());
	                nVideo.setVideoTitle(singleVideo.getSnippet().getTitle());
	                sResults.add(nVideo);
	                
	                System.out.println(" Video Id" + rId.getVideoId());
	                System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
	                System.out.println(" Thumbnail: " + thumbnail.getUrl());
	                System.out.println("\n-------------------------------------------------------------\n");
	            }
	        }
	        results = sResults;
	        return sResults;



		}

}
