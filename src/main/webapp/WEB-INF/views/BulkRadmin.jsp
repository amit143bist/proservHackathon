<!DOCTYPE html>
<html dir="ltr" ng-attr-lang="{{ page.language.code }}" ng-app="dsApp" class="js flexbox no-touch cssanimations ng-scope" lang="en"><head><style type="text/css">@charset "UTF-8";[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide:not(.ng-hide-animate){display:none !important;}ng\:form{display:block;}.ng-animate-shim{visibility:hidden;}.ng-anchor{position:absolute;}</style><title ng-bind="page.title" class="ng-binding">Reminders - DocuSign</title><meta charset="utf-8"><meta content="IE=EDGE" http-equiv="X-UA-Compatible"><meta name="viewport" content="width=device-width,initial-scale=1.0"><meta name="robots" content="all,follow"><script async="" src="https://www.google-analytics.com/analytics.js"></script><script src="http://olive.docusignhq.com/js/libs/html5.js"></script><script src="http://olive.docusignhq.com/js/libs/modernizr.js"></script><link rel="icon" type="image/x-icon" href="http://olive.docusignhq.com/img/new_favicon.png"><link rel="stylesheet" ng-href="https://docucdn-a.akamaihd.net/olive/17.4.0/css/olive.min.css" href="https://docucdn-a.akamaihd.net/olive/17.4.0/css/olive.min.css"><!-- For source code formatting--><link rel="stylesheet" href="https://docucdn-a.akamaihd.net/olive/17.4.0/js/libs/prism.css"><!-- dynamically load prototype specific style sheets--><!-- ngIf: stylesheet --><link rel="stylesheet" ng-if="stylesheet" ng-href="https://docucdn-a.akamaihd.net/olive/17.4.0/css/radmin.dev.css" class="ng-scope" href="https://docucdn-a.akamaihd.net/olive/17.4.0/css/radmin.dev.css"><!-- end ngIf: stylesheet --><script>(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
ga('create', 'UA-39550292-7', 'auto');
ga('send', 'pageview');</script><style id="olive-css-mouse-active" type="text/css">
            .mouse-active :focus:not(input):not(select):not(textarea) {
                box-shadow: none !important;
                outline: 0 !important;
                text-decoration: none !important;
            }
            .mouse-active .menu .item:focus:not(:hover),
            .mouse-active .menu_item:focus:not(:hover) {
                background-color: inherit;
            }
            </style><style id="__web-inspector-hide-shortcut-style__" type="text/css">
.__web-inspector-hide-shortcut__, .__web-inspector-hide-shortcut__ *, .__web-inspector-hidebefore-shortcut__::before, .__web-inspector-hideafter-shortcut__::after
{
    visibility: hidden !important;
}
</style>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<script type="text/javascript">

    function BulkUpdate() {
        // is File API supportted?
        if (window.FileReader) {
            // FileReader is supported.
            getAsText(document.getElementById('csvFileInput').files[0]);
        } else {
            alert('FileReader is not supported in this browser.');
        }
    }

    function getAsText(fileToRead) {
        var reader = new FileReader();
        // Handle errors load
        reader.onload = loadHandler;
        reader.onerror = errorHandler;
        reader.readAsText(fileToRead);
    }

    function loadHandler(event) {
        var csv = event.target.result;
        AddBulkParamsCSV(csv);
        AddBulkParamsJSON(csv);
    }

    function AddBulkParamsJSON(csv) {

        var reminders_enabled = document.getElementById("reminders-enabled").checked;
        var reminder_delay_in_days = document.getElementById("reminder-delay-in-days").value;
        var reminder_frequency_in_days = document.getElementById("reminder-frequency-in-days").value;
        var reminders_override = document.getElementById("reminders-override").checked;
        var expire_after_in_days = document.getElementById("expire-after-in-days").value;
        var expire_warn_in_days = document.getElementById("expire-warn-in-days").value;

        var allTextLines = csv.split(/\r\n|\n/);
        var lines = [];
        while (allTextLines.length) {
            lines.push(allTextLines.shift().split(',')[0] + "," +
                reminders_enabled + "," +
                reminder_delay_in_days + "," +
                reminder_frequency_in_days + "," +
                reminders_override + "," +
                expire_after_in_days + "," +
                expire_warn_in_days + "<br>"
                );
            console.log(lines);
        }


        POSTJSONRequest(lines, reminders_enabled, reminder_delay_in_days, reminder_frequency_in_days, reminders_override, expire_after_in_days, expire_warn_in_days);

    }
    function AddBulkParamsCSV(csv) {

        var reminders_enabled = document.getElementById("reminders-enabled").checked;
        var reminder_delay_in_days = document.getElementById("reminder-delay-in-days").value;
        var reminder_frequency_in_days = document.getElementById("reminder-frequency-in-days").value;
        var reminders_override = document.getElementById("reminders-override").checked;
        var expire_after_in_days = document.getElementById("expire-after-in-days").value;
        var expire_warn_in_days = document.getElementById("expire-warn-in-days").value;

        var allTextLines = csv.split(/\r\n|\n/);
        var lines = [];
        while (allTextLines.length) {
            lines.push(allTextLines.shift().split(',')[0] + "," +
                reminders_enabled + "," +
                reminder_delay_in_days + "," +
                reminder_frequency_in_days + "," +
                reminders_override + "," +
                expire_after_in_days + "," +
                expire_warn_in_days + "<br>"
                );
            console.log(lines);
        }

        
        //POSTCreateMultipartFormRequest(lines);

    }

    function errorHandler(evt) {
        if (evt.target.error.name == "NotReadableError") {
            alert("Can't read the CSV file");
        }
    }

    function POSTJSONRequest(lines, reminders_enabled, reminder_delay_in_days, reminder_frequency_in_days, reminders_override, expire_after_in_days, expire_warn_in_days) {

        var json = '{"envelopeDataList": [';
        for (i = 1; i < lines.length; i++) {

            json = json + '{ "envelopeId":' + lines[i].split(',')[0] + ', ' +
            '"notification": {"expirations": {' +
                    '"expireAfter": "' + expire_after_in_days + '",' +
                    '"expireEnabled": "' + reminders_override + '",' +
                    '"expireWarn": "' + expire_warn_in_days + '"},' +
                '"reminders": {' +
                    '"reminderDelay": "' + reminder_delay_in_days + '",' +
                    '"reminderEnabled": "' + reminders_enabled + '",' +
                    '"reminderFrequency": "' + reminder_frequency_in_days + '"' +
            '}}}';

            if (i != lines.length-1) json = json + ',';
        }

        json = json + ']}';

        var URL = "account/340570/envelopes/notification";


        $.ajax({
            headers: {
                'X-DocuSign-Authentication': '<DocuSignCredentials><Username>barpe061@gmail.com</Username><Password>Aquino1115</Password><IntegratorKey>DOCU-e29abe61-0bd8-4e89-b4f9-50922b8c5b90</IntegratorKey></DocuSignCredentials>',
                'Content-Type' : 'application/json'
            },
            url: URL,
            type: 'PUT',
            data: json,
            dataType: 'json',
            success: function (result) {
            	$('#batchStatus').html(result.status);
            	$('#batchId').html(result.batchId);
            }
        });

    }


    function EnvelopeNode(id, reminders_enabled, reminder_delay_in_days, reminder_frequency_in_days, reminders_override, expire_after_in_days, expire_warn_in_days)
    {


        var node = '{ "EnvelopeId":"' + id +
            '","reminders_enabled":"' + reminders_enabled +
            '","reminder_delay_in_days":"' + reminder_delay_in_days +
            '","reminder_frequency_in_days":"' + reminder_frequency_in_days +
            '","reminders_override":"' + reminders_override +
            '","expire_after_in_days":"' + expire_after_in_days +
            '","expire_warn_in_days":"' + expire_warn_in_days + '"}';
    }


    function POSTCreateMultipartFormRequest(lines) {
        data = new FormData();

        var myBlob = new Blob(lines, { type: "application/csv;charset=utf-8" });
        data.append('CSV', myBlob);

        //data.append('envelopes', (line, {
        //    type: "application/csv;charset=utf-8"
        //}));

        //var wtf = encodeURIComponent(lines);

        //data.append('envelopes', wtf);

        var URL = "account/1764240/envelopes/bulk";

        alert(data);

        $.ajax({
            headers: {
                'X-DocuSign-Authentication': '<DocuSignCredentials><Username>d61de26d-a86c-4eaa-8ba9-9f7a26788fbb</Username><Password>fJG8vSrgekhui6v0wiRlwVkNb1U=</Password><IntegratorKey>DOCU-e29abe61-0bd8-4e89-b4f9-50922b8c5b90</IntegratorKey></DocuSignCredentials>',
                'Content-Type': null
            },

            url: URL,
            type: 'POST',
            data: data,
            dataType: 'json',
            processData: false,
            contentType: false,
            crossDomain: true,
            success: function (result) {
            	$('#batchStatus').html(result.status);
            }
        });

    }

    
    $(document).ready(function(){
		
		$("#submitBtn").click(function(){
			
			
			var batchGuid = $("#batchGuid").val();
			
			
			
			$.ajax({
                headers: {
                    'X-DocuSign-Authentication': '{"Username": "barpe061@gmail.com", "Password":"Aquino1115", "IntegratorKey":"16f81d9e-e9ee-408d-bc60-d6e1aecd9756"}'
                },
                url: 'account/340570/envelopes/batch/' + batchGuid,
                type: 'GET',
                dataType: 'json',
                success: function (result) {
             	   $('#batchStatus').html(result.status);

                }
            });
			
		});
		
	});
    
		$(document).ready(function(){
		
		$("#batchStatusBtn").click(function(){
			
			
			var batchGuid = $("#batchStatusGuid").val();
			var batchStatus = $("#batchStatusOption").val();
			
			
			
			var urlVal = 'account/340570/envelopes/batch/'+ batchGuid + "/records";
			if(batchStatus === 'both'){
				urlVal = 'account/340570/envelopes/batch/'+ batchGuid + "/records";
			}else{
				urlVal = 'account/340570/envelopes/batch/'+ batchGuid + "/records" + "?status=" + batchStatus;
			}
			
			
			
			$.ajax({
                headers: {
                    'X-DocuSign-Authentication': '{"Username": "barpe061@gmail.com", "Password":"Aquino1115", "IntegratorKey":"16f81d9e-e9ee-408d-bc60-d6e1aecd9756"}'
                },
                url: urlVal,
                type: 'GET',
                dataType: 'json',
                success: function (logResult) {
                	
                	
                	var resultData = '';
                	
              	   for(var key in logResult){
              		   
              		   
              		  resultData = resultData + logResult[key].envelopeConcurrentLogPK.envelopeId + logResult[key].envelopeTransactionComments;
              		  
              		 console.log('resultData---- ' + resultData);
              	   }
              	   
              	  $('#batchFetchStatus').html(resultData);

                }
            });
			
		});
		
	});
    </script>
 
</head>
    
<body ng-class="page.bodyClass" ds-events="" class="gt-1361 gt-1600 gt-1800"><noscript>This site requires JavaScript to function.
Please enable JavaScript in your browser settings.</noscript><!-- appViewSegment: 0 --><div app-view-segment="0" class="page_wrapper ng-scope"><div class="site site-classic ng-scope">

    <!-- ngInclude: '/views/admin/_header.html' -->
    <div class="header-admin ng-scope">
    <div class="header_wrap">
        <div class="header_nav-mobile">
            <button type="button" class="btn btn-lg btn-icon btn-inverse-minor">
                <span class="icon icon-menu"></span>
                <span class="sr-text">Menu</span>
            </button>
        </div>
        <div class="header_left">
            <a class="header_logo" ng-href="/admin" href="/admin">
                <img src="http://olive.docusignhq.com/img/Logo-DS-White.svg">
                <span class="header_name">Admin</span>
            </a>
            <nav>
                <ul class="nav_list">
                    <li class="nav_listItem">
                       <a class="nav_link" href="#"><i class="icon icon-palette-field-company"></i> OMG DTM FTW</a>
                    </li>
                </ul>
            </nav>
        </div>
        <div class="header_right">
            <div class="header_buttons">
                <a href="#">Help</a>
                <button type="button" class="btn-avatar" olive-menu="menuAccountNav" olive-menu-position="below right" olive-menu-trigger="menuAccountNav" olive-menu-initialized="true" aria-haspopup="true" aria-expanded="false">
                    <img src="http://olive.docusignhq.com/img/sam.png" alt="User Avatar">
                </button>
                <div id="menuAccountNav" class="menu invisible" style="opacity: 0; top: 0px; left: 0px; min-width: 120px;">

                    <div class="menu-section">
                        <!-- ngInclude: '/views/common/id-card.html' --><table class="id-card ng-scope">
    <tbody><tr>
        <td class="id-card-thumb">
            <a class="avatar" olive-tooltip="tooltipUploadPhoto" olive-tooltip-trigger="tooltipUploadPhoto" olive-tooltip-initialized="true">
                <img src="http://olive.docusignhq.com/img/sam.png" alt="User Avatar">
            </a>
        </td>
        <td class="id-card-info">
            <strong class="line-1">
                Sam Elliott
            </strong>
            <br>
            <span class="meta">
                sam@hollywood.com<br>
                MGM Studios
            </span>
        </td>
    </tr>
</tbody></table>
                    </div>

                    <ul class="menu-grouping" role="menu" aria-labelledby="
                    
                ">
                        <li role="menuitem"><a class="item" href="/admin">Go to Admin</a></li>
                        <li role="menuitem"><a class="item" href="/martini/preferences">Preferences</a></li>

                        <li role="menuitem">
                            <button type="button" class="item" olive-modal-trigger="btnSwitchAccounts" olive-modal-template-url="/views/common/accounts.html">
                                    Switch Accounts
                            </button>
                        </li>

                        <li role="menuitem"><a class="item">Switch to Classic</a></li>

                        <li role="menuitem">
                            <button type="button" class="item" olive-modal-trigger="btnFeedback" olive-modal-template-url="/views/common/feedback.html" olive-modal-size="lg">
                                     Feedback
                            </button>
                        </li>
                        <li class="mobile-display" role="menuitem">
                            <a class="item" href="https://www.docusign.com/DocuSignHelp/DocuSignHelp.htm">Help</a>
                        </li>
                        <li role="menuitem"><a class="item" ng-click="logout()">Log Out</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
    <div class="site_content site_container">

            <!-- ngInclude: '/views/admin/_sidebar.html' --><div id="filterMenu" class="content_sidebar content_sidebar-left ng-scope">

    <div olive-app-menu-nav="nav" class="ng-isolate-scope"><!-- ngRepeat: list in nav.lists --><nav role="navigation" aria-labelledby="" class="sidebar_group ng-scope" ng-repeat="list in nav.lists">

    <!-- ngIf: list.title --><h4 ng-id="" class="sidebar_title ng-binding ng-scope" ng-if="list.title">
        Account
    </h4><!-- end ngIf: list.title -->

    <div class="menu-nav">
        <ul class="menu_list">
            <!-- ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/organizations">

                    <!-- ngIf: x.icon -->

                    Organizations

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/billing">

                    <!-- ngIf: x.icon -->

                    Billing and Usage

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/profile">

                    <!-- ngIf: x.icon -->

                    Account Profile

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/security">

                    <!-- ngIf: x.icon -->

                    Security Settings

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/regional">

                    <!-- ngIf: x.icon -->

                    Regional Settings

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/branding">

                    <!-- ngIf: x.icon -->

                    Branding

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items -->
        </ul>
    </div>
</nav><!-- end ngRepeat: list in nav.lists --><nav role="navigation" aria-labelledby="" class="sidebar_group ng-scope" ng-repeat="list in nav.lists">

    <!-- ngIf: list.title --><h4 ng-id="" class="sidebar_title ng-binding ng-scope" ng-if="list.title">
        Users and Groups
    </h4><!-- end ngIf: list.title -->

    <div class="menu-nav">
        <ul class="menu_list">
            <!-- ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/users">

                    <!-- ngIf: x.icon -->

                    Users

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/permissions">

                    <!-- ngIf: x.icon -->

                    Permission Sets

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/groups">

                    <!-- ngIf: x.icon -->

                    Groups

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items -->
        </ul>
    </div>
</nav><!-- end ngRepeat: list in nav.lists --><nav role="navigation" aria-labelledby="" class="sidebar_group ng-scope" ng-repeat="list in nav.lists">

    <!-- ngIf: list.title --><h4 ng-id="" class="sidebar_title ng-binding ng-scope" ng-if="list.title">
        Signing and Sending
    </h4><!-- end ngIf: list.title -->

    <div class="menu-nav">
        <ul class="menu_list">
            <!-- ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/signing">

                    <!-- ngIf: x.icon -->

                    Signing Settings

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/sending">

                    <!-- ngIf: x.icon -->

                    Sending Settings

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/legal">

                    <!-- ngIf: x.icon -->

                    Legal Disclosure

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/reminders">

                    <!-- ngIf: x.icon -->

                    Reminders and Expirations

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/custom-fields">

                    <!-- ngIf: x.icon -->

                    Custom Fields

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/labels">

                    <!-- ngIf: x.icon -->

                    Document Labels

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/apps">

                    <!-- ngIf: x.icon -->

                    Connected Apps

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items -->
        </ul>
    </div>
</nav><!-- end ngRepeat: list in nav.lists --><nav role="navigation" aria-labelledby="" class="sidebar_group ng-scope" ng-repeat="list in nav.lists">

    <!-- ngIf: list.title --><h4 ng-id="" class="sidebar_title ng-binding ng-scope" ng-if="list.title">
        Integrations
    </h4><!-- end ngIf: list.title -->

    <div class="menu-nav">
        <ul class="menu_list">
           <!-- ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/reminders">

                    <!-- ngIf: x.icon -->

                    API and Keys

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/connect">

                    <!-- ngIf: x.icon -->

                    Connect

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items -->
        </ul>
    </div>
</nav><!-- end ngRepeat: list in nav.lists --><nav role="navigation" aria-labelledby="" class="sidebar_group ng-scope" ng-repeat="list in nav.lists">

    <!-- ngIf: list.title --><h4 ng-id="" class="sidebar_title ng-binding ng-scope" ng-if="list.title">
        Bulk Operations
    </h4><!-- end ngIf: list.title -->

    <div class="menu-nav">
        <ul class="menu_list">
            <!-- ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding  menu_item-isOn" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/api-integrator-key">

                    <!-- ngIf: x.icon -->

                    Reminders and Expirations

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/connect">

                    <!-- ngIf: x.icon -->

                    Document Fields

                    <!-- ngIf: x.count -->
                </a>
            </li>
            <!-- end ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/connect">

                    <!-- ngIf: x.icon -->

                    Purge

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items -->
        </ul>
    </div>
</nav><!-- end ngRepeat: list in nav.lists --><nav role="navigation" aria-labelledby="" class="sidebar_group ng-scope" ng-repeat="list in nav.lists">

    <!-- ngIf: list.title --><h4 ng-id="" class="sidebar_title ng-binding ng-scope" ng-if="list.title">
        Auditing
    </h4><!-- end ngIf: list.title -->

    <div class="menu-nav">
        <ul class="menu_list">
            <!-- ngRepeat: x in list.items --><li ng-repeat="x in list.items" class="menu_listItem ng-scope">
                <a menu-item="" class="menu_item ng-binding" ng-attr-href="{{x.route ? (x.route | routeSegmentUrl: x.data) : undefined}}" ng-click="nav.selectedId = x.id" ng-class="{'menu_item-isOn': (x.route &amp;&amp; (x.route | routeSegmentEqualsTo)) || (x.id &amp;&amp; x.id === nav.selectedId)}" href="/admin/audit-logs">

                    <!-- ngIf: x.icon -->

                    Audit Logs

                    <!-- ngIf: x.count -->
                </a>
            </li><!-- end ngRepeat: x in list.items -->
        </ul>
    </div>
</nav><!-- end ngRepeat: list in nav.lists -->
</div>

</div>

            <!-- appViewSegment: 1 --><div app-view-segment="1" class="content_main ng-scope"><div class="section-header ng-scope">
    <h1 class="header-title">
        Reminders and Expirations Updates
    </h1>
    <div class="header-description">
        This is where you can make your bulk updates to your Reminders and Expirations.
    </div>
</div>
<div class="section ng-scope">
   <div class="reminders site-main" ng-controller="RemindersSettingsController" ds-form="">
  <form name="remindersForm" class="section reminders ng-pristine ng-valid ng-valid-required">
    <!-- ngIf: remindersSettings.allowReminders -->
	<div class="header-title" ng-if="remindersSettings.allowReminders">

      <h2 translate="Select Envelopes">Select Envelopes</h2>
	  
        
																																																											 
																																																																																																								 
									   
								  
																																																																																																												 
											   
		
						 
																																			 
																													  
											  

      <div class="form-group">
        <div class="" ui-cfr="" ui-hint="" ui-models="">

<div ng-if="!editDocsCtrl.disableDocumentUpload()" class=""><label translate="Select a CSV file containg the Envelope IDs for your Bulk Update">Select a CSV file containg the Envelope IDs for your Bulk Update</label><label class="label" for="csvFileInput"></label>
        <input type="file" id="csvFileInput"  accept=".csv">
		</div>
		  </div>
      </div>
    </div>
	
	<div class="header-title" ng-if="remindersSettings.allowReminders">

      <h2 translate="Reminders">Reminders</h2>				  
      <div class="form-group">
        <div class="inline-input cb" ui-cfr="" ui-hint="" ui-models="[remindersSettings.allowRemindersMetadata]">
          <input class="cb_input ng-pristine ng-untouched ng-valid" type="checkbox" id="reminders-enabled" ng-model="remindersSettings.accountNotification.reminders.reminderEnabled" data-qa="reminders_send_auto_reminders_cb">
          <label class="cb_label" for="reminders-enabled" data-qa="reminders_send_auto_reminders_cb_lbl" translate="Send automatic reminders">Send automatic reminders</label>
        <!-- ngIf: isCfr -->
        <!-- ngIf: isCfr --></div>
      </div>
      <div class="form-group-inline" ui-cfr="" ui-hint="" ui-models="[remindersSettings.allowRemindersMetadata]">
        <span translate="Number of days before sending first reminder:">Number of days before sending first reminder:</span>
        <input type="number" class="input-number input-text input-noSpinButtons ng-pristine ng-untouched ng-valid ng-valid-required" required="" ds-valid="integer" ds-valid-min="1" ds-valid-max="999" id="reminder-delay-in-days" ng-model="remindersSettings.accountNotification.reminders.reminderDelay" data-qa="reminders_num_days_before_send_first_reminder_txtb">
      <!-- ngIf: isCfr -->
        <!-- ngIf: isCfr --></div>
      <div class="form-group-inline" ui-cfr="" ui-hint="" ui-models="[remindersSettings.allowRemindersMetadata]">
        <span translate="Number of days between reminders:">Number of days between reminders:</span>
        <input type="number" class="input-number input-text input-noSpinButtons ng-pristine ng-untouched ng-valid ng-valid-required" required="" ds-valid="integer" ds-valid-min="0" ds-valid-max="999" id="reminder-frequency-in-days" ng-model="remindersSettings.accountNotification.reminders.reminderFrequency" data-qa="reminders_num_days_between_reminders_txtb">
      <!-- ngIf: isCfr -->
        <!-- ngIf: isCfr --></div>
    </div><!-- end ngIf: remindersSettings.allowReminders -->
    <div class="header-title">
      <h2 translate="Expirations">Expirations</h2>
      <div class="form-group-inline">
        <span translate="Number of days before request expires:">Number of days before request expires:</span>
        <input type="number" class="input-text input-number input-noSpinButtons ng-pristine ng-untouched ng-valid ng-valid-required" required="" ds-valid="integer" ds-valid-min="1" ds-valid-max="999" id="expire-after-in-days" ng-model="remindersSettings.accountNotification.expirations.expireAfter" data-qa="reminders_num_days_before_request_expires_txtb">
      </div>
      <div class="form-group-inline">
        <span translate="Number of days to warn signers before expiration:">Number of days to warn signers before expiration:</span>
        <input type="number" class="input-text input-number input-noSpinButtons ng-pristine ng-untouched ng-valid ng-valid-required" required="" ds-valid="integer" ds-valid-min="0" ds-valid-max="120" id="expire-warn-in-days" ng-model="remindersSettings.accountNotification.expirations.expireWarn" data-qa="reminders_num_days_to_warn_user_before_expiration_txtb">
      </div>
      <div class="form-group">
        <div class="inline-input cb">
          <input class="cb_input ng-pristine ng-untouched ng-valid" type="checkbox" id="reminders-override" ng-model="remindersSettings.accountNotification.userOverrideEnabled" data-qa="reminders_allow_users_to_override_settings_cb">
          <label class="cb_label" for="reminders-override" data-qa="reminders_allow_users_to_override_settings_cb_lbl" translate="Allow users to override these settings">Allow users to override these settings</label>
        </div>
      </div>
    </div>
  </form>
  <div class="section">
    
    <div class="header-actions" ds-ackbar="remindersForm" ds-ackbar-save="save(remindersForm)" ds-ackbar-cancel="cancel(remindersForm)">
	
	    <button ng-class="{'btn-lg' : dsAckbarModal}" class="btn btn-primary" id="BulkUpdate" type="submit" onclick="BulkUpdate(document.getElementById('csvFileInput').files)"translate="Update Envelopes" >Update Envelopes</button>
    </div>
  </div>
  
  <div class="section">
  
  <form id="searchForm">
        	<input type="button" value="Search" id="submitBtn" name="submitBtn"/>
            <input type="text" name="batchGuid" id="batchGuid" placeholder="Batch ID..." size="40"/>   
        </form>
    <div id="batchStatus"></div> <div id="batchId"></div>
  </div>
  
  
  <div class="section">
  
  <form id="batchStatusSearchForm">
        	<input type="button" value="Search" id="batchStatusBtn" name="batchStatusBtn"/>
            <input type="text" name="batchStatusGuid" id="batchStatusGuid" placeholder="Batch ID..." size="40"/>   
            
            <div>Select Operations</div>
			<div>
				<select id="batchStatusOption" name="batchStatusOption">
					<option value="Fail">Fail</option>
					<option value="Success">Success</option>
					<option value="Both">Both</option>
				</select>
			</div>
			
			<div id="batchFetchStatus"></div>
        </form>
    
  </div>
  
  
</div>
</div>
</div>

    </div>

    <!-- ngInclude: '/views/common/footer.html' --><footer role="contentinfo" class="site_footer ng-scope">
    <div class="site_container">

        <div class="footer_left">
            <!-- nothing here -->
        </div>

        <div class="footer_main">

            <ul class="footer-links">
                <li>
                    <a class="footer-links_item btn-trigger ng-binding" olive-menu="menuLanguages" olive-menu-position="above left" olive-menu-trigger="menuLanguages" olive-menu-initialized="true" aria-haspopup="true" aria-expanded="false">
                        <span class="icon icon-globe"></span>
                        English
                    </a>
                </li>
                <li><span class="footer-links_item">Powered by DocuSign</span></li>
                <li><a class="footer-links_item" target="_blank" href="http://www.docusign.com/company/terms-of-use">Terms of Use</a></li>
                <li><a class="footer-links_item" target="_blank" href="http://www.docusign.com/company/privacy-policy">Privacy</a></li>
                <li><a class="footer-links_item" target="_blank" href="http://www.docusign.com/IP">Intellectual Property</a></li>
                <li><span class="footer-links_item ng-binding">Copyright © 2017 DocuSign, Inc. All rights reserved.</span></li>

                <li>
                    <button class="footer-links_item btn-trigger" olive-menu="menuPrototypes" olive-menu-position="above left" olive-menu-trigger="menuPrototypes" olive-menu-initialized="true" aria-haspopup="true" aria-expanded="false">
                        Prototypes
                    </button>
                </li>
            </ul>

            <div class="menu select-menu invisible" id="menuLanguages" style="opacity: 0; top: 0px; left: 0px; min-width: 120px;">
                <ul role="menu" aria-labelledby="
                        
                        English
                    ">
                    <!-- ngRepeat: x in page.languages | orderBy:['priority', 'name'] --><li ng-repeat="x in page.languages | orderBy:['priority', 'name']" class="ng-scope" role="menuitem">
                        <button class="item ng-binding on" ng-class="{'on': page.language.code === x.code}" ng-click="page.language = x">
                            English
                        </button>
                    </li><!-- end ngRepeat: x in page.languages | orderBy:['priority', 'name'] --><li ng-repeat="x in page.languages | orderBy:['priority', 'name']" class="ng-scope" role="menuitem">
                        <button class="item ng-binding" ng-class="{'on': page.language.code === x.code}" ng-click="page.language = x">
                            Deutsch
                        </button>
                    </li><!-- end ngRepeat: x in page.languages | orderBy:['priority', 'name'] --><li ng-repeat="x in page.languages | orderBy:['priority', 'name']" class="ng-scope" role="menuitem">
                        <button class="item ng-binding" ng-class="{'on': page.language.code === x.code}" ng-click="page.language = x">
                            Español
                        </button>
                    </li><!-- end ngRepeat: x in page.languages | orderBy:['priority', 'name'] --><li ng-repeat="x in page.languages | orderBy:['priority', 'name']" class="ng-scope" role="menuitem">
                        <button class="item ng-binding" ng-class="{'on': page.language.code === x.code}" ng-click="page.language = x">
                            Français
                        </button>
                    </li><!-- end ngRepeat: x in page.languages | orderBy:['priority', 'name'] --><li ng-repeat="x in page.languages | orderBy:['priority', 'name']" class="ng-scope" role="menuitem">
                        <button class="item ng-binding" ng-class="{'on': page.language.code === x.code}" ng-click="page.language = x">
                            Italiano
                        </button>
                    </li><!-- end ngRepeat: x in page.languages | orderBy:['priority', 'name'] --><li ng-repeat="x in page.languages | orderBy:['priority', 'name']" class="ng-scope" role="menuitem">
                        <button class="item ng-binding" ng-class="{'on': page.language.code === x.code}" ng-click="page.language = x">
                            Português (Brasil)
                        </button>
                    </li><!-- end ngRepeat: x in page.languages | orderBy:['priority', 'name'] --><li ng-repeat="x in page.languages | orderBy:['priority', 'name']" class="ng-scope" role="menuitem">
                        <button class="item ng-binding" ng-class="{'on': page.language.code === x.code}" ng-click="page.language = x">
                            Português (Portugal)
                        </button>
                    </li><!-- end ngRepeat: x in page.languages | orderBy:['priority', 'name'] --><li ng-repeat="x in page.languages | orderBy:['priority', 'name']" class="ng-scope" role="menuitem">
                        <button class="item ng-binding" ng-class="{'on': page.language.code === x.code}" ng-click="page.language = x">
                            Русский
                        </button>
                    </li><!-- end ngRepeat: x in page.languages | orderBy:['priority', 'name'] --><li ng-repeat="x in page.languages | orderBy:['priority', 'name']" class="ng-scope" role="menuitem">
                        <button class="item ng-binding" ng-class="{'on': page.language.code === x.code}" ng-click="page.language = x">
                            中文
                        </button>
                    </li><!-- end ngRepeat: x in page.languages | orderBy:['priority', 'name'] --><li ng-repeat="x in page.languages | orderBy:['priority', 'name']" class="ng-scope" role="menuitem">
                        <button class="item ng-binding" ng-class="{'on': page.language.code === x.code}" ng-click="page.language = x">
                            日本語
                        </button>
                    </li><!-- end ngRepeat: x in page.languages | orderBy:['priority', 'name'] --><li ng-repeat="x in page.languages | orderBy:['priority', 'name']" class="ng-scope" role="menuitem">
                        <button class="item ng-binding" ng-class="{'on': page.language.code === x.code}" ng-click="page.language = x">
                            한국어
                        </button>
                    </li><!-- end ngRepeat: x in page.languages | orderBy:['priority', 'name'] -->
                </ul>
            </div>

            <div class="menu select-menu invisible" id="menuPrototypes" style="opacity: 0; top: 0px; left: 0px; min-width: 120px;">
                <ul role="menu" aria-labelledby="
                        Prototypes
                    ">
                    <!-- ngRepeat: x in page.prototypes --><li ng-repeat="x in page.prototypes" class="ng-scope" role="menuitem">
                        <a class="item ng-binding" ng-class="{'on': (x.route | routeSegmentStartsWith)}" ng-href="/styleguide" href="/styleguide">
                            Styleguide
                        </a>
                    </li><!-- end ngRepeat: x in page.prototypes --><li ng-repeat="x in page.prototypes" class="ng-scope" role="menuitem">
                        <a class="item ng-binding" ng-class="{'on': (x.route | routeSegmentStartsWith)}" ng-href="/martini" href="/martini">
                            Martini
                        </a>
                    </li><!-- end ngRepeat: x in page.prototypes --><li ng-repeat="x in page.prototypes" class="ng-scope" role="menuitem">
                        <a class="item ng-binding" ng-class="{'on': (x.route | routeSegmentStartsWith)}" ng-href="/tagger" href="/tagger">
                            Tagger
                        </a>
                    </li><!-- end ngRepeat: x in page.prototypes --><li ng-repeat="x in page.prototypes" class="ng-scope" role="menuitem">
                        <a class="item ng-binding" ng-class="{'on': (x.route | routeSegmentStartsWith)}" ng-href="/account-server" href="/account-server">
                            Account Server
                        </a>
                    </li><!-- end ngRepeat: x in page.prototypes --><li ng-repeat="x in page.prototypes" class="ng-scope" role="menuitem">
                        <a class="item ng-binding on" ng-class="{'on': (x.route | routeSegmentStartsWith)}" ng-href="/admin" href="/admin">
                            Admin
                        </a>
                    </li><!-- end ngRepeat: x in page.prototypes --><li ng-repeat="x in page.prototypes" class="ng-scope" role="menuitem">
                        <a class="item ng-binding" ng-class="{'on': (x.route | routeSegmentStartsWith)}" ng-href="/signing" href="/signing">
                            Signing
                        </a>
                    </li><!-- end ngRepeat: x in page.prototypes --><li ng-repeat="x in page.prototypes" class="ng-scope" role="menuitem">
                        <a class="item ng-binding" ng-class="{'on': (x.route | routeSegmentStartsWith)}" ng-href="/opentrust" href="/opentrust">
                            Protect and Sign
                        </a>
                    </li><!-- end ngRepeat: x in page.prototypes -->
                </ul>
            </div>

        </div>

        <div class="footer_right">
            <!-- ngIf: page.showFeedback -->
        </div>

    </div>
</footer>

</div>