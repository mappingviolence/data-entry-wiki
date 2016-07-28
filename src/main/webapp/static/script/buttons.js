$(document).ready(function() {
	/* make edit button appear */
    $("#editbutton").on("click", function() {
      $("#edit").toggleClass("hidden");
      $("#view").toggleClass("hidden");
      google.maps.event.trigger(map1, 'resize');
      if ($("#latEdit").val() != "" && $("#lngEdit").val() != "") {
	      m1.updateMap({ "lat" : parseFloat($("#latEdit").val()), "lng" : parseFloat($("#lngEdit").val()) }, setZoom);
		  m1.updateAutocomplete({ "lat" : parseFloat($("#latEdit").val()), "lng" : parseFloat($("#lngEdit").val()) });
      } else {
    	  m1.updateMap({ "lat" : 31.9686, "lng" : -99.9018 }, unsetZoom);
		  //m1.updateAutocomplete({ "lat" : 31.9686, "lng" : -99.9018 });
      }
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
    $(".removebutton").on("click", function(e) { 
      e.preventDefault();
      $(this).parent().remove(); 
    });
    
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
        	  $victimidentity.children("input[type='hidden']").val(id);
              console.log($victimidentity);
              $this.parent().before($victimidentity);
              $(".removebutton").off();
              $(".removebutton").on("click", function(e) { 
                e.preventDefault();
                $(this).parent().remove(); 
              });
              if (callback) {
            	  callback(i);
              }
          });        
          
          $(".removebutton").off();
          $(".removebutton").on("click", function(e) { 
            e.preventDefault();
            $(this).parent().remove(); 
          });
      });
      
      /* remove button */ 
      $(".removebutton").off();
      $(".removebutton").on("click", function(e) { 
        e.preventDefault();
        $(this).parent().remove(); 
      });
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
            $aggressoridentity.children("input[type='hidden']").val(id);
              console.log($aggressoridentity);
              $this.parent().before($aggressoridentity);
              $(".removebutton").off();
              $(".removebutton").on("click", function(e) { 
                e.preventDefault();
                $(this).parent().remove(); 
              });
              if (callback) {
                callback(i);
              }
          });        
          
          $(".removebutton").off();
          $(".removebutton").on("click", function(e) { 
            e.preventDefault();
            $(this).parent().remove(); 
          });
      });
      
      /* remove button */ 
      $(".removebutton").off();
      $(".removebutton").on("click", function(e) { 
        e.preventDefault();
        $(this).parent().remove(); 
      });
    });

    /* add new tags */
    $("#tagBtn").on("click", function(e, i, callback) {
      e.preventDefault();
      var $input = $("div.hidden div[data-id='tag']").clone();
      randomId(function(id) {
    	  $input.children("input[type='hidden']").val(id);
          console.log($input);
          $("#tagContainer").append($input);
          $(".removebutton").off();
          $(".removebutton").on("click", function(e) { 
            e.preventDefault();
            $(this).parent().remove(); 
          });
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
    	  $input.children("input[type='hidden']").val(id);
          console.log($input);
          $("#primarysourceContainer").append($input);
          $(".removebutton").off();
          $(".removebutton").on("click", function(e) { 
            e.preventDefault();
            $(this).parent().remove(); 
          });
          if (callback) {
        	  callback(i);
          }
      })
    })

    /* add new secondary sources */ 
    $("#secondarysourceBtn").on("click", function(e, i, callback) {
      e.preventDefault();
      var $input = $("div.hidden div[data-id='secondarysource']").clone();
      randomId(function(id) {
    	  $input.children("input[type='hidden']").val(id);
          console.log($input);
          $("#secondarysourceContainer").append($input);
          $(".removebutton").off();
          $(".removebutton").on("click", function(e) { 
            e.preventDefault();
            $(this).parent().remove(); 
          });
          if (callback) {
        	  callback(i);
          }
      })
    })
});