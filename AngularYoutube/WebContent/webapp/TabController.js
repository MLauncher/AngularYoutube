/**
 * 
 */


//Problem: When I change the videoId, It changes it for ALL the players instead of the one.


//Solution 1: Leave the first one(Liked Videos) as is and keep using the changeId function on it.
//Create a array that will store all the search results in the new tabs. The first result
var app = angular.module('ui.bootstrap.demo', ['ngAnimate', 'ui.bootstrap']);

app.directive('compileHtml', ['$sce', '$parse', '$compile',
                              function($sce, $parse, $compile) {
                                return {
                                  restrict: 'A',
                                  compile: function ngBindHtmlCompile(tElement, tAttrs) {
                                    var ngBindHtmlGetter = $parse(tAttrs.compileHtml);
                                    var ngBindHtmlWatch = $parse(tAttrs.compileHtml, function getStringValue(value) {
                                      return (value || '').toString();
                                    });
                                    $compile.$$addBindingClass(tElement);

                                    return function ngBindHtmlLink(scope, element, attr) {
                                      $compile.$$addBindingInfo(element, attr.compileHtml);

                                      scope.$watch(ngBindHtmlWatch, function ngBindHtmlWatchAction() {

                                        element.html($sce.trustAsHtml(ngBindHtmlGetter(scope)) || '');
                                        $compile(element.contents())(scope);
                                      });
                                    };
                                  }
                                };
                              }
                            ]);

app.controller('TabController',function ($scope,$sce,$compile,$rootScope)
		
		
{

	
	$scope.words = "Words";
	$scope.greetings = "";
	$scope.tabs = [
	               { title:'Liked Videos', content:'<div ng-include src = "tab.tabUrl"></div>',tabUrl:'VHtml.html',index:0 },
	             ];
	
	$scope.sayHi = function(){
		
	}
	
	$scope.addTab = function(){
		 var len = $scope.tabs.length + 1;
		 var numLbl = '' + ((len > 9) ? '' : '0') + String(len);

		 
//		 var mrkUp = '<h1>The MarkUp</h1>' +
//		 '<div ng-include src = "tab.tabUrl"></div>';
	 

		 $scope.tabs.push({
		      title: 'Tab ' + numLbl,
		      content: mrkUp,
		      tabUrl:'SearchResults.html',
		      index: len
		     
		    });
	}
	
	//This creates a listener object(Listener can be called anything ) that is waiting to be called to do something
	//In this case add a new tab with the search results.
	var Listener = $scope.$on("Parent",function(event,data){
		console.log(data + "Hello from child");
		$scope.addTab();
	});

	//This destroys the listener after it completes its job.
	//If you do not, the app will only work once.
	
	
	$scope.$on('$destroy',Listener);

	
});

app.controller('YoutubeController3',function($scope,$http,$window,$rootScope)
		 {
		   
			$scope.message = "Initial message";
			$scope.message2 = "";
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
		    $scope.allResults = [];
		    $scope.tabVideoid = [];
//		    $scope.getMessage2= function() {
//		    	$http.get('utube/webapp').then(function (response){
//		            $scope.message2 = response.data;
//		   
//		            console.log($scope.message2.videoTitle + " Message");
//		        });
//		    };
		    
		    $scope.getData = function(){
		    	$http.get('utube/data').success(function(mess){
		    		$scope.data = mess.videoTitle;
		    	})
		    }
		    $scope.changeVideo = function(newID){
		    	$scope.yt.id = newID;
		    }
		    $scope.changeVideo = function(newID,index){
		    	$scope.tabVideoid[index] = newID;
		    }
		    
		    $scope.getResults = function(){
		    	console.log("In the getResults");
		    	$http.get('utube/results').then(function(results){
		    		
		    		console.log("In the get");
		    		$scope.yt.svideos = results.data;
		    		$scope.newResults = results.data;
		    		
		    		$scope.allResults.push($scope.newResults);
		    		$scope.tabVideoid.push($scope.newResults[0].videoId);
		    		
		    		
		    		
		    		console.log("First result " + $scope.yt.svideos[0].videoId);
		    		angular.forEach(results.data,function(value,key){
		    			console.log("Value " + value.videoTitle);
		    			
		    		});
		    		
		    	})
		    }
		    
		    $scope.search = function(){
		    	console.log($scope.query);
		    	$http.post("utube/search/" + $scope.query).then(function(){
		    		console.log("Success");
		    		console.log("Changes were made");
		    		$scope.getResults();
		    		//Learning events in AngularJs
		    		
		    		//Broadcast(called Parent) sends a message to the controller below it(In this case TabController, 
		    		//check the Home.html TabController is nested under YoutubeController3)
		    		$scope.$broadcast("Parent","Activate");
//		    		$scope.searchTab();
		    		
		    	})
		    	
		    }
		    
	
		    
  
		    $scope.authorize = function(){
		    	
		    	console.log("Before the http caledsl");
		    	$http.get('utube/authorize').then(function(cinfo){
		    		console.log("Before the authorize " + $scope.yt.ids);
		    	
		    		
		    		$scope.yt.videos = cinfo.data;
		    		console.log($scope.yt.videos[0].videoTitle);
		    		
		    		
		    	
		    	
		    	})
		    	console.log("Afterwards");
		    	
		    }
		    
		    $scope.authwindow = function(){
		    	
		    	console.log("In the function");
		    	$scope.authorize();
		    	window.open("https://accounts.google.com/o/oauth2/auth?client_id=457995126311-kb3e023v94fac10gk6sgo4lpipf1gg9p.apps.googleusercontent.com&redirect_uri=http://localhost:9000/Callback&response_type=code&scope=https://www.googleapis.com/auth/youtube",
    			"Authorization Window");
		    
		    	
		    	
		    }
		    
//		 $scope.searchTab = function(){
//			 $rootScope.$emit("Parent",{})
//			 console.log("In the child method");
//			 
//		 }
		    
		 
		 
		
		    
		})