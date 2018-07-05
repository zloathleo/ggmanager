<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>查询图片资源</title>
    <link type="text/css" rel="Stylesheet" href="../../Contents/Css/base.css"/>
    <link type="text/css" rel="Stylesheet" href="../../Contents/Css/platform.css"/>
    <link type="text/css" rel="Stylesheet" href="../../Scripts/jQEasyUI/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="../../Scripts/jQEasyUI/themes/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../Scripts/jQEasyUI/themes/demo.css">
    <link rel="stylesheet" type="text/css" href="http://sachinchoolur.github.io/lightGallery/static/css/main.css">
    <link rel="stylesheet" type="text/css" href="../../Scripts/lightGallery/dist/css/lightgallery.css"></link>
    <script type="text/javascript" src="../../Scripts/jquery.min.js"></script>
    <script type="text/javascript" src="../../Scripts/IllegalityCheck.js"></script>
    <script type="text/javascript" src="../../Scripts/jQEasyUI/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../Scripts/jQEasyUI/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../../Scripts/FunctionJS.js"></script>
    <script type="text/javascript" src="../../Scripts/lightGallery/dist/js/lightgallery-all.min.js"></script>
    <script type="text/javascript" src="../../Scripts/lightGallery/lib/jquery.mousewheel.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/picturefill/2.3.1/picturefill.min.js"></script>
    <script src="http://sachinchoolur.github.io/lightGallery/static/js/demos.js"></script>
    <script type="text/javascript">
        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
            (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
        })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
        ga('create', 'UA-49767543-2', 'sachinchoolur.github.io');
        ga('send', 'pageview');
    </script>
    <style type="text/css">
        .lg-backdrop.in {
            opacity: 0.85;
        }
    </style>
    <style type="text/css">
        .demo-gallery > ul {
            margin-bottom: 0;
        }
        .demo-gallery > ul > li {
            float: left;
            margin-bottom: 15px;
            margin-right: 20px;
            width: 200px;
        }
        .demo-gallery > ul > li a {
            border: 3px solid #FFF;
            border-radius: 3px;
            display: block;
            overflow: hidden;
            position: relative;
            float: left;
        }
        .demo-gallery > ul > li a > img {
            -webkit-transition: -webkit-transform 0.15s ease 0s;
            -moz-transition: -moz-transform 0.15s ease 0s;
            -o-transition: -o-transform 0.15s ease 0s;
            transition: transform 0.15s ease 0s;
            -webkit-transform: scale3d(1, 1, 1);
            transform: scale3d(1, 1, 1);
            height: 100%;
            width: 100%;
        }
        .demo-gallery > ul > li a:hover > img {
            -webkit-transform: scale3d(1.1, 1.1, 1.1);
            transform: scale3d(1.1, 1.1, 1.1);
        }
        .demo-gallery > ul > li a:hover .demo-gallery-poster > img {
            opacity: 1;
        }
        .demo-gallery > ul > li a .demo-gallery-poster {
            background-color: rgba(0, 0, 0, 0.1);
            bottom: 0;
            left: 0;
            position: absolute;
            right: 0;
            top: 0;
            -webkit-transition: background-color 0.15s ease 0s;
            -o-transition: background-color 0.15s ease 0s;
            transition: background-color 0.15s ease 0s;
        }
        .demo-gallery > ul > li a .demo-gallery-poster > img {
            left: 50%;
            margin-left: -10px;
            margin-top: -10px;
            opacity: 0;
            position: absolute;
            top: 50%;
            -webkit-transition: opacity 0.3s ease 0s;
            -o-transition: opacity 0.3s ease 0s;
            transition: opacity 0.3s ease 0s;
        }
        .demo-gallery > ul > li a:hover .demo-gallery-poster {
            background-color: rgba(0, 0, 0, 0.5);
        }
        .demo-gallery .justified-gallery > a > img {
            -webkit-transition: -webkit-transform 0.15s ease 0s;
            -moz-transition: -moz-transform 0.15s ease 0s;
            -o-transition: -o-transform 0.15s ease 0s;
            transition: transform 0.15s ease 0s;
            -webkit-transform: scale3d(1, 1, 1);
            transform: scale3d(1, 1, 1);
            height: 100%;
            width: 100%;
        }
        .demo-gallery .justified-gallery > a:hover > img {
            -webkit-transform: scale3d(1.1, 1.1, 1.1);
            transform: scale3d(1.1, 1.1, 1.1);
        }
        .demo-gallery .justified-gallery > a:hover .demo-gallery-poster > img {
            opacity: 1;
        }
        .demo-gallery .justified-gallery > a .demo-gallery-poster {
            background-color: rgba(0, 0, 0, 0.1);
            bottom: 0;
            left: 0;
            position: absolute;
            right: 0;
            top: 0;
            -webkit-transition: background-color 0.15s ease 0s;
            -o-transition: background-color 0.15s ease 0s;
            transition: background-color 0.15s ease 0s;
        }
        .demo-gallery .justified-gallery > a .demo-gallery-poster > img {
            left: 50%;
            margin-left: -10px;
            margin-top: -10px;
            opacity: 0;
            position: absolute;
            top: 50%;
            -webkit-transition: opacity 0.3s ease 0s;
            -o-transition: opacity 0.3s ease 0s;
            transition: opacity 0.3s ease 0s;
        }
        .demo-gallery .justified-gallery > a:hover .demo-gallery-poster {
            background-color: rgba(0, 0, 0, 0.5);
        }
        .demo-gallery .video .demo-gallery-poster img {
            height: 48px;
            margin-left: -24px;
            margin-top: -24px;
            opacity: 0.8;
            width: 48px;
        }
        .demo-gallery.dark > ul > li a {
            border: 3px solid #04070a;
        }
        .home .demo-gallery {
            padding-bottom: 80px;
        }
    </style>
</head>
<body class="home">
<div class="demo-gallery dark mrb35">
    <ul id="fixed-size" class="list-unstyled row">
        <li class="col-xs-6 col-sm-4 col-md-3" data-src="../static/img/1.jpg" data-sub-html="<h4>Fading Light</h4><p>Classic view from Rigwood Jetty on Coniston Water an old archive shot similar to an old post but a little later on.</p>">
            <a href="">
                <img class="img-responsive" src="http://sachinchoolur.github.io/lightGallery/static/img/thumb-1.jpg">
                <div class="demo-gallery-poster">
                    <img src="http://sachinchoolur.github.io/lightGallery/static/img/zoom.png">
                </div>
            </a>
        </li>
        <li class="col-xs-6 col-sm-4 col-md-3" data-src="../static/img/2.jpg" data-sub-html="<h4>Bowness Bay</h4><p>A beautiful Sunrise this morning taken En-route to Keswick not one as planned but I'm extremely happy I was passing the right place at the right time....</p>">
            <a href="">
                <img class="img-responsive" src="http://sachinchoolur.github.io/lightGallery/static/img/thumb-2.jpg">
                <div class="demo-gallery-poster">
                    <img src="http://sachinchoolur.github.io/lightGallery/static/img/zoom.png">
                </div>
            </a>
        </li>
        <li class="col-xs-6 col-sm-4 col-md-3" data-src="../static/img/13.jpg" data-sub-html="<h4>Sunset Serenity</h4><p>A gorgeous Sunset tonight captured at Coniston Water....</p>">
            <a href="">
                <img class="img-responsive" src="http://sachinchoolur.github.io/lightGallery/static/img/thumb-13.jpg">
                <div class="demo-gallery-poster">
                    <img src="http://sachinchoolur.github.io/lightGallery/static/img/zoom.png">
                </div>
            </a>
        </li>
        <li class="col-xs-6 col-sm-4 col-md-3" data-src="../static/img/4.jpg" data-sub-html="<h4>Coniston Calmness</h4><p>Beautiful morning</p>">
            <a href="">
                <img class="img-responsive" src="http://sachinchoolur.github.io/lightGallery/static/img/thumb-4.jpg">
                <div class="demo-gallery-poster">
                    <img src="http://sachinchoolur.github.io/lightGallery/static/img/zoom.png">
                </div>
            </a>
        </li>
    </ul>
</div>
</body>
</html>
