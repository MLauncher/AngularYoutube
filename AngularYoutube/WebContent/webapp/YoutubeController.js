/**
 * 
 */
app.controller('YoutubeController',function($scope,$http,$window)
 {
   
	$scope.message = "Initial message";
	$scope.query ="Search query";
    $scope.yt = {
    		width:400,
    		height:400,
    		id: "okTi4MmRimM",
    		ids:["okTi4MmRimM","M7lc1UVf-VE","25-BEGZCc0U"],
    		videos:[],
    		svideos:[]
    
    
    };
    $scope.ytchannel = {};
    
    $scope.getMessage2= function() {
    	$http.get('utube/webapp').success(function(springmess){
            $scope.message2 = springmess.videoTitle;
            console.log($scope.message2 + " Message")
        });
    };
    
    $scope.getData = function(){
    	$http.get('utube/data').success(function(mess){
    		$scope.data = mess.videoTitle;
    	})
    }
    $scope.changeVideo = function(newID){
    	$scope.yt.id = newID;
    }
    
    $scope.getResults = function(){
    	$http.get('utube/search').success(function(results){
    		console.log("The results " + results);
    		console.log("Should be nothing");
    		angular.forEach(results,function(value,key){
    			console.log("Video Title" + results.videoTitle);
    			
    		});
    		$scope.yt.svideos = results;
    	})
    }
    
    $scope.search = function(){
    	console.log($scope.query);
    	$http.post("utube/search/" + $scope.query).success(function(){
    		console.log("Success");
    		console.log("Changes were made");
    		$scope.getResults();
    	})
    	
    }
    
    $scope.addTab = function(){
    	
    }
   
    
    
    $scope.authorize = function(){
    	$http.get('utube/authorize').success(function(cinfo){
    		console.log("Before the autorize " + $scope.yt.ids);
    	
    		
    		$scope.yt.videos = cinfo;
    		console.log($scope.yt.videos[0].videoTitle);
    		
    		angular.forEach(cinfo,function(value,key){
    			
    			
    		});
    		
    	
    	
    	})
    }
    
    $scope.authwindow = function(){
    	$scope.authorize();
    	window.open("https://accounts.google.com/o/oauth2/auth?client_id=457995126311-kb3e023v94fac10gk6sgo4lpipf1gg9p.apps.googleusercontent.com&redirect_uri=http://localhost:9000/Callback&response_type=code&scope=https://www.googleapis.com/auth/youtube",
    			"Authorization Window");
    }
    
   
    
$scope.getMessage2();
    
})


app.controller('TestController',function($scope,$window){
	$scope.greet = "Greetings from Test Controller2";
});
	
