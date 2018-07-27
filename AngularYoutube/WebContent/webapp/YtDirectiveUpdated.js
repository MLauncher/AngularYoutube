/**
 * 
 */



app.constant('YT_event', {
  STOP:            0, 
  PLAY:            1,
  PAUSE:           2,
  STATUS_CHANGE:   3
});
//
//app.controller('YouTubeCtrl', function($scope, YT_event) {
//  //initial settings
//  this.width = 600, 
//  this.height = 480, 
//  this.videoid = "M7lc1UVf-VE",
//  this.playerStatus = "NOT PLAYING"
//
//  this.YT_event = YT_event;
//
//  this.sendControlEvent = function(ctrlEvent) {
//    console.log("SENDING");
//    console.log(ctrlEvent);
//    $scope.$broadcast(ctrlEvent);
//  }
//
//  $scope.$on(YT_event.STATUS_CHANGE, function(event, data) {
//      this.yt.playerStatus = data;
//  });
//
//});

app.directive('youtubeup', function($window,$timeout, YT_event, youTubeApiService) {
  return {
    restrict: "E",

    scope: {
      height: "@",
      width: "@",
      videoid: "@"
    },

    template: '<div></div>',

    link: function(scope, element, attrs, $rootScope) {
      var tag = document.createElement('script');
      tag.src = "https://www.youtube.com/iframe_api";
      var firstScriptTag = document.getElementsByTagName('script')[0];
      firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
      
      var player;

      youTubeApiService.onReady(function() {
        player = setupPlayer(scope, element);
      });

      function setupPlayer(scope, element) {
        return new YT.Player(element.children()[0], {
          playerVars: {
            autoplay: 0,
            html5: 1,
            theme: "light",
            modesbranding: 0,
            color: "white",
            iv_load_policy: 3,
            showinfo: 1,
            controls: 1
          },
          
          height: scope.height,
          width: scope.width,
          videoId: scope.videoid 

//          events: {
//            'onStateChange': function(event) {
//              
//              var message = {
//                event: YT_event.STATUS_CHANGE,
//                data: ""
//              };
//              
//              switch(event.data) {
//                case YT.PlayerState.PLAYING:
//                  message.data = "PLAYING";
//                  break;
//                case YT.PlayerState.ENDED:
//                  message.data = "ENDED";
//                  break;
//                case YT.PlayerState.UNSTARTED:
//                  message.data = "NOT PLAYING";
//                  break;
//                case YT.PlayerState.PAUSED:
//                  message.data = "PAUSED";
//                  break;
//              }
//
////              scope.$apply(function() {
////                scope.$emit(message.event, message.data);
////              });
//            }
//          } 
        });        
      }

      scope.$watch('height + width', function(newValue, oldValue) {
        if (newValue == oldValue) {
          return;
        }
    
        player.setSize(scope.width, scope.height);
      
      });

      scope.$watch('videoid', function(newValue, oldValue) {
        if (newValue == oldValue) {
          return;
        }
        
        $timeout(function(){player.cueVideoById(scope.videoid);},0);
      
      });

      scope.$on(YT_event.STOP, function () {
        player.seekTo(0);
        player.stopVideo();
      });

      scope.$on(YT_event.PLAY, function () {
        console.log("RECEIVING");
        player.playVideo();
      }); 

      scope.$on(YT_event.PAUSE, function () {
        player.pauseVideo();
      });  

    }  
  };
});

app.factory("youTubeApiService", function($q, $window) {
  
  var deferred = $q.defer();
  var apiReady = deferred.promise;

  var failure= function(){console.log("Failure")};
  
  $window.onYouTubeIframeAPIReady = function() {
    deferred.resolve();
  }

  return {
    onReady: function(callback) {
      apiReady.then(callback,failure);
    }
  }   

});