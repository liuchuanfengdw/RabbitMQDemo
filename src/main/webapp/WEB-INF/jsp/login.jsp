<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <!-- Meta tag Keywords -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="UTF-8" />
    <meta name="keywords" content=""/>
    <!-- Meta tag Keywords -->
    <!-- css files -->
    <link rel="stylesheet" href="/css/style.css" type="text/css" media="all" />
    <!-- Style-CSS -->
    <link rel="stylesheet" href="/css/fontawesome-all.css">
    <!-- Font-Awesome-Icons-CSS -->
    <!-- //css files -->
    <!-- web-fonts -->
    <link href="http://maxcdn.bootstrapcdn.com/css?family=Josefin+Sans:100,100i,300,300i,400,400i,600,600i,700,700i" rel="stylesheet">
    <link href="http://maxcdn.bootstrapcdn.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i" rel="stylesheet">
    <!-- //web-fonts -->
    <script>
        addEventListener("load", function () {
            setTimeout(hideURLbar, 0);
        }, false);

        function hideURLbar() {
            window.scrollTo(0, 1);
        }

        function login(){
            var name = $("#name").val();
            var pwd = $("#pwd").val();
            $.ajax({
                url: "http://localhost:9100/toLogin",
                data:{
                    "username":name,
                    "password":pwd
                },
                dataType:'JSON',//服务器返回json格式数据
                type:'GET',//HTTP请求类型
                success:function(data){
                    if(data.data.id){
                        location.href = "/user/toChat?userId=" + data.data.id;
                    }else{
                        alert("账号或密码错误");
                    }
                },
                error:function(data){
                    alert("请求error");
                }
            });
        }
    </script>
</head>
<body>
    <!-- bg effect -->
    <div id="bg">
        <canvas></canvas>
        <canvas></canvas>
        <canvas></canvas>
    </div>
    <!-- //bg effect -->
    <!-- title -->
    <h1>WeiChat Login</h1>
    <!-- //title -->
    <!-- content -->
    <div class="sub-main-w3">
        <form action="#" method="post" onsubmit="return false;">
            <h2>Login Now
                <i class="fas fa-level-down-alt"></i>
            </h2>
            <div class="form-style-agile">
                <label>
                    <i class="fas fa-user"></i>
                    Username
                </label>
                <input id="name" placeholder="Username" name="username" type="text" required="">
            </div>
            <div class="form-style-agile">
                <label>
                    <i class="fas fa-unlock-alt"></i>
                    Password
                </label>
                <input id="pwd" placeholder="Password" name="password" type="password" required="">
            </div>
            <!-- checkbox -->
            <div class="wthree-text">
                <ul>
                    <li>
                        <label class="anim">
                            <input type="checkbox" class="checkbox" required="">
                            <span>Stay Signed In</span>
                        </label>
                    </li>
                    <li>
                        <a href="#">Forgot Password?</a>
                    </li>
                </ul>
            </div>
            <!-- //checkbox -->
            <input type="submit" value="Log In" onclick="login()">
        </form>
    </div>
    <!-- //content -->

    <!-- copyright -->
    <div class="footer">
        <p>Copyright &copy; 2018.Company name All rights reserved.</p>
    </div>
    <!-- //copyright -->

    <!-- Jquery -->
    <script src="/js/jquery-1.8.3.min.js"></script>
    <!-- //Jquery -->

    <!-- effect js -->
    <script src="/js/canva_moving_effect.js"></script>
    <!-- //effect js -->
</body>
</html>