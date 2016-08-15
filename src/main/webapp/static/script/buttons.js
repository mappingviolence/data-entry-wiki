$(document).ready(function() {
	/* make edit button appear */
    $("#editbutton").on("click", function() {
      $("#edit").toggleClass("hidden");
      $("#view").toggleClass("hidden");
      $("textarea").expanding();
      google.maps.event.trigger(map1, 'resize');
      if ($("#latEdit").val() != "" && $("#lngEdit").val() != "") {
	      m1.updateMap({ "lat" : parseFloat($("#latEdit").val()), "lng" : parseFloat($("#lngEdit").val()) }, setZoom);
		  m1.updateAutocomplete({ "lat" : parseFloat($("#latEdit").val()), "lng" : parseFloat($("#lngEdit").val()) });
      } else {
    	  m1.updateMap({ "lat" : 31.9686, "lng" : -99.9018 }, unsetZoom);
		  //m1.updateAutocomplete({ "lat" : 31.9686, "lng" : -99.9018 });
      }
    });

    /* cancel button takes you back to view page from edit page w/o saving data */ 
    $("#cancel").on("click", function(e) {
      e.preventDefault();
      $("#edit").toggleClass("hidden"); 
      $("#view").toggleClass("hidden"); 
      return false;
    });

    /* show previous versions */ 
    $("#versionsbutton").on("click", function(e) {
      $("#view").toggleClass("hidden"); 
      $("#versions").toggleClass("hidden");
      return false; 
    });

    $("#currentversionbutton").on("click", function(e) { 
      $("#view").toggleClass("hidden"); 
      $("#versions").toggleClass("hidden"); 
      return false; 
    })

    /* put in pool button releases a draft to the entire team */ 
    $("#putinpool").on("click", function(e) { 
      e.preventDefault(); 
      var $POIid = $("#poiId").text(); 
      $.ajax({
        method: "PUT",
        url: "/mapviz/status",
        data: { 
          id: $POIid, 
          status: "IN_POOL"
        }
      }).done(function(data) {
        console.log(data);
        $("#edit").toggleClass("hidden");
        $("#view").toggleClass("hidden");
      }).fail(function(data) {
        console.log(data);
      });
    }); 

    /* adds a comment */ 
    $(".addcomment").on("click", function(e) {
      e.preventDefault();
      var $this = $(this);
      var $id = $(this).attr("data-id"); 
      var $POIid = $("#poiId").text(); 
      var $dataName = $(this).attr("data-name");
      var $textArea = $(this).first().parent().parent().children().first().children().first().children("textarea");
      var $commentText = $textArea.val(); /* find the value */ 
      $.ajax({
        method: "POST",
        url: "/mapviz/comment?id=" + $POIid, 
        data: { 
          formFieldId: $id, 
          commentText: $commentText
        }
      }).done(function(data) {
        console.log(this, data, $this.parent().parent().parent().find(".commentswrapper"));
        var $commentsWrapper = $this.parent().parent().parent().find(".commentswrapper");
        var $firstName = data.data.author.givenName; 
        var $lastName = data.data.author.familyName;
        var $commentId = data.data.id;
        $commentsWrapper.append(
        	"<div class=\"row comment\">" +
        		"<div class=\"col-xs-10\">" +
        			"<i class=\"commentName\">" + $firstName + " " + $lastName + ": </i>" +
        			"<span class=\"commentText\">" + $commentText + "</span>" +
        			"<input type=\"hidden\" class=\"hidden\" value=\"" + $commentId + "\" />" +
        		"</div>" +
        		"<div class=\"col-xs-2\">" +
        			"<button id=\"delete" + $dataName + "comment\" data-id=\"" + $id + "\" data-commentId=\"" + $commentId + "\" type=\"submit\" class=\"btn btn-default btn-sm deletecomment\">" +
        				"<span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span>" +
        			"</button>" +
        		"</div>" +
        	"</div>"
         );
         $textArea.val("");
         $(".deletecomment").off();
         $(".deletecomment").on("click", function(e) {
             e.preventDefault();
             var $this = $(this);
             var $id = $(this).attr("data-id"); 
             var $POIid = $("#poiId").text(); 
             var $commentId = $(this).attr("data-commentId"); 
             $.ajax({
               method: "DELETE",
               url: "/mapviz/comment?id=" + $POIid, 
               data: { 
                 formFieldId: $id, 
                 commentId: $commentId 
               }
             }).done(function(data) {
               $this.parent().parent().hide();
             });
         });
      });
    }); 

    /* deletes a comment */ 
    $(".deletecomment").on("click", function(e) {
      e.preventDefault();
      var $this = $(this);
      var $id = $(this).attr("data-id"); 
      var $POIid = $("#poiId").text(); 
      var $commentId = $(this).attr("data-commentId"); 
      $.ajax({
        method: "DELETE",
        url: "/mapviz/comment?id=" + $POIid, 
        data: { 
          formFieldId: $id, 
          commentId: $commentId 
        }
      }).done(function(data) {
        $this.parent().parent().hide();
      });
    });

    /* help button text */
    var helpBtns = $("label>span[role='helpBtn']");
    for (var i = 0; i < helpBtns.length; i++) {
        var helpBtn = helpBtns[i];
        $(helpBtn).on("click", function(e) {
            e.preventDefault();
            var id = this.id;
            $("#" + id + "Text").toggleClass("hidden");
        });
    }
    
    // Configure remove
    var doRemoveButton = function(e) {
      e.preventDefault();
      switch ($(this).attr("data-parent")) {
        case "1": 
          $(this).parent().remove();
          break; 
        case "2": 
          $(this).parent().parent().remove();
          break; 
        case "3": 
          $(this).parent().parent().parent().remove();
          break; 
        case "4": 
          $(this).parent().parent().parent().parent().remove();
          break; 
        case "5": 
          $(this).parent().parent().parent().parent().parent().remove();
          break; 
      };     
    };

    $(".removebutton").on("click", doRemoveButton);
    
    /* add new victim */ 
    $("#victimBtn").on("click", function(e) { 
      e.preventDefault();
      var $victim = $("div.hidden div[data-id='victim']").clone();
      $("#victimContainer").append($victim);
      $(".victimidentityBtn").off(); // so that event handlers don't build up 

      /* add victim identity */
      $(".victimidentityBtn").on("click", function(e, i, callback) {
          e.preventDefault(); 
          var $victimidentity = $("div.hidden div[data-id='victimidentity']").clone();
          var $this = $(this);
          randomId(function(id) {
        	  $victimidentity.find("input[type='hidden']").val(id);
              console.log($victimidentity);
              $this.parent().before($victimidentity);
              $(".removebutton").off();
              $(".removebutton").on("click", doRemoveButton);
              if (callback) {
            	  callback(i);
              }
          });        
          
          $(".removebutton").off();
          $(".removebutton").on("click", doRemoveButton);
      });
      
      /* remove button */ 
      $(".removebutton").off();
      $(".removebutton").on("click", doRemoveButton);
    });

    /* add victim identity (for preloaded victims) */
    $(".victimidentityBtn").on("click", function(e, i, callback) {
        e.preventDefault(); 
        var $victimidentity = $("div.hidden div[data-id='victimidentity']").clone();
        var $this = $(this);
        randomId(function(id) {
          $victimidentity.find("input[type='hidden']").val(id);
            console.log($victimidentity);
            $this.parent().before($victimidentity);
            $(".removebutton").off();
            $(".removebutton").on("click", doRemoveButton);
            if (callback) {
              callback(i);
            }
        });        
        
        $(".removebutton").off();
        $(".removebutton").on("click", doRemoveButton);
    });
    
    /* add new aggressor */ 
    $("#aggressorBtn").on("click", function(e) { 
      e.preventDefault();
      var $aggressor = $("div.hidden div[data-id='aggressor']").clone();
      $("#aggressorContainer").append($aggressor);
      $(".aggressoridentityBtn").off(); // so that event handlers don't build up 

      /* add aggressor identity */
      $(".aggressoridentityBtn").on("click", function(e, i, callback) {
          e.preventDefault(); 
          var $aggressoridentity = $("div.hidden div[data-id='aggressoridentity']").clone();
          var $this = $(this);
          randomId(function(id) {
            $aggressoridentity.find("input[type='hidden']").val(id);
              console.log($aggressoridentity);
              $this.parent().before($aggressoridentity);
              $(".removebutton").off();
              $(".removebutton").on("click", doRemoveButton);
              if (callback) {
                callback(i);
              }
          });        
          
          $(".removebutton").off();
          $(".removebutton").on("click", doRemoveButton);
      });
      
      /* remove button */ 
      $(".removebutton").off();
      $(".removebutton").on("click", doRemoveButton);
    });

    /* adds aggressor identity (for preloaded aggressors) */ 
    $(".aggressoridentityBtn").on("click", function(e, i, callback) {
        e.preventDefault(); 
        var $aggressoridentity = $("div.hidden div[data-id='aggressoridentity']").clone();
        var $this = $(this);
        randomId(function(id) {
          $aggressoridentity.find("input[type='hidden']").val(id);
            console.log($aggressoridentity);
            $this.parent().before($aggressoridentity);
            $(".removebutton").off();
            $(".removebutton").on("click", doRemoveButton);
            if (callback) {
              callback(i);
            }
        });        
        
        $(".removebutton").off();
        $(".removebutton").on("click", doRemoveButton);
    });

    /* add new tags */
    $("#tagBtn").on("click", function(e, i, callback) {
      e.preventDefault();
      var $input = $("div.hidden div[data-id='tag']").clone();
      randomId(function(id) {
    	  $input.find("input[type='hidden']").val(id);
          console.log($input);
          $("#tagContainer").append($input);
          $(".removebutton").off();
          $(".removebutton").on("click", doRemoveButton);
          if (callback) {
        	  callback(i);
          }
      })
    })
    
    /* add new primary sources */
    $("#primarysourceBtn").on("click", function(e, i, callback) {
      e.preventDefault();
      var $input = $("div.hidden div[data-id='primarysource']").clone();
      randomId(function(id) {
    	  $input.find("input[type='hidden']").val(id);
          console.log($input);
          $("#primarysourceContainer").append($input);
          $(".removebutton").off();
          $(".removebutton").on("click", doRemoveButton);
          if (callback) {
        	  callback(i);
          }
      })
    })

    /* add new secondary sources */ 
    $("#secondarysourceBtn").on("click", function(e, i, callback) {
      debugger;
      e.preventDefault();
      var $input = $("div.hidden div[data-id='secondarysource']").clone();
      randomId(function(id) {
    	  $input.find("input[type='hidden']").val(id);
          console.log($input);
          $("#secondarysourceContainer").append($input);
          $(".removebutton").off();
          $(".removebutton").on("click", doRemoveButton);
          if (callback) {
        	  callback(i);
          }
      })
    })
});