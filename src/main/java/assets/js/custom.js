jQuery(document).ready(function($){

	// Top navbar animation
	// ------------------------------------------------------------------------
	$(document).bind('shrink-menu-init', function(e, status) {
		var topBarHeight = $('.navbar-extra-top').outerHeight(); // getting the height of the nabar-extra-top
		scrollMark = Math.max(topBarHeight, 30); // forced minimum of 30
		style = ".menu-shrink {top : -"+topBarHeight+"px !important;}";
		if ( !$('#ShrinkMenu').length ) {
			$( "<style></style>" ).attr('id','ShrinkMenu').data('scrollMark',scrollMark).appendTo( "head" ); // add custom CSS for height offset
		}
		$('#ShrinkMenu').html(style);
	});
	// navbar adjustments on scroll
	$(document).bind('shrink-menu', function(e, status){
		scrollMark = $('#ShrinkMenu').data('scrollMark');
		// when scroll hits height of navbar top, apply style changes
		if ( $(this).scrollTop() < scrollMark ) {
			$('#MainMenu').removeClass('scrolled menu-shrink');
		} else {
			$('#MainMenu').addClass('scrolled menu-shrink');
		}
	});
	// trigger shrink-menu on scroll
	$(window).resize( function(){
		$(document).trigger('shrink-menu-init');
	});
	$(window).scroll( function(){
		$(document).trigger('shrink-menu');
	});

    // Sub-navbar affix on scroll
    // ------------------------------------------------------------------------
    if ($('#SubMenu').length) {
        $('#SubMenu').affix({
            offset: {
                top: function () {
                    return $('#SubMenu').parent().offset().top - $('#navbar-main-container').outerHeight();
                },
            }
        }).css('top',$('#navbar-main-container').outerHeight());
        // Update values on window resize
        $(window).resize( function() {
            theTop = $('#SubMenu').parent().offset().top - $('#navbar-main-container').outerHeight();
            $('#SubMenu').data('bs.affix').options.offset = { top: theTop };
        });
    }

    // accordions - always have 1 panel open
    // ------------------------------------------------------------------------
    if ( $('.panel-heading').length ) {

        $('.panel-heading').on('click',function(e){
            if($(this).parents('.panel').children('.panel-collapse').hasClass('in')){
                e.preventDefault();
                e.stopPropagation();
            }
        });
    }

    // Tooltips
    // ------------------------------------------------------------------------
    $('[data-toggle="tooltip"]').tooltip({
        placement: function(tip, trigger) {
            // show above, unless no space. show bottom on affixed sub-nav
            return ( $(trigger).parents('#SubMenu.affix').length ) ? 'bottom' : 'auto top';
        }
    });

    // owl carousel
    // ------------------------------------------------------------------------
    if ( $('.featured-carousel').length ) {
        $(".featured-carousel").owlCarousel({
            items: 1,
            loop: true,
            autoplay: true,
            autoplayHoverPause: true,
            autoplayTimeout: 3800,
            autoplaySpeed: 800,
            navSpeed: 500,
            dots: false,
            nav: true,
            navText: [
                '<i class="fa fa-angle-left"></i>',
                '<i class="fa fa-angle-right"></i>'
            ]
        });
    }

    // Navbar Hover/Click Responsive Behavior
	// ------------------------------------------------------------------------
	collapseSize = 1299; // 768;

	// hover sub-menu items
	$('.navbar-nav a').click( function(e) {
		$this = $(e.target);
		href = $this.attr('href'); // Link URL

		// Check link value
		if (href === undefined || !href.length || href === '#' || href === 'javascript:;') {
			href = false;
		}
		// Link behavior
		if ($this.hasClass('dropdown-toggle')) {
			// Parent menu items
			if ($(window).width() > collapseSize) {
				if (href) {
					// large screens, follow the parent menu link when clicked
					if (e.which !== 2) {
						window.location.href = href;
					}
				}
			 } else if ( $this.parent().hasClass('open') && href !== false) {
				// small screens, 1st tap opens sub-menu & 2nd tap follows link
				$(document).trigger('collapse-menus');
				window.location.href = href;
			}
		} else {
			// All other menu items, close menu on click
			$(document).trigger('collapse-menus');
		}
	});
	// Keep parent menus open on sub-menu expand
	$(document).on('show.bs.dropdown', function(obj) {
		if ($(window).width() <= collapseSize) {
			$(obj.target).parents('.show-on-hover').addClass('open');
		}
	});
	$('.navbar a:not(.dropdown-toggle)').click( function(e) {

		$this = $(e.target);
		href = $this.attr('href'); // Link URL

		// Check link value
		if (href === undefined || !href.length || href === '#' || href === 'javascript:;') {
			href = false;
		}
		// Link behavior
		if ($(window).width() > collapseSize) {
			if (href) {
				// large screens, follow the parent menu link when clicked
				if (e.which !== 2) {
					window.location.href = href;
				}
			}
		 } else if ( $this.parent().hasClass('open') && href !== false) {
			// small screens, 1st tap opens sub-menu & 2nd tap follows link
			$(document).trigger('collapse-menus');
			window.location.href = href;
		}
	});
	// Close all menus
	$(document).on('collapse-menus', function () {
		$('.collapse.in').removeClass('in').children().removeClass('open');
	});
	// Hover styling helpers
	$('.navbar-nav > li.show-on-hover').hover(function() {
		if ($(window).width() > collapseSize) {
			$(this).addClass('open');
		}
	}, function() {
		if ($(window).width() > collapseSize) {
			$(this).removeClass('open');
		}
	});



    // Things we want to trigger once, forcefully, after loading the page
    // ------------------------------------------------------------------------

    // Fire the menu shrink function
    $(document).trigger('shrink-menu-init');
    $(document).trigger('shrink-menu');

});
