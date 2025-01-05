<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>欢迎访问</title>
    <script type="text/javascript" src="/static/plugin/jquery/jquery.1.10.2.js"></script>
    <script>
        function isChrome(){
            var isChrome = window.navigator.userAgent.indexOf("Chrome") !== -1
            return isChrome;
        }
        $(function() {
            if (!isChrome()) {
                window.location.href="/browser_not_suppert.html";
            }
        });
    </script>
    <style>
        body {
            font-family: 'Microsoft YaHei', Arial, sans-serif;
            background: linear-gradient(to right, #98bedf, #98bedf);
            color: #333;
            margin: 0;
            padding: 20px;
            line-height: 1.6;
        }
        header, nav, section, article, aside, footer {
            border: 1px solid #ccc;
            padding: 10px;
            margin: 10px 0;
            border-radius: 8px;
            background-color: rgba(255, 255, 255, 0.8);
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        nav ul {
            list-style: none;
            padding: 0;
        }
        nav ul li {
            display: inline;
            margin-right: 10px;
            font-weight: bold;
        }
        nav a {
            text-decoration: none;
            color: #333;
        }
        nav a:hover {
            text-decoration: underline;
        }
        table, th, td {
            border: 1px solid #ccc;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            text-align: left;
            background-color: #f9f9f9;
        }
        th {
            background-color: #4979aa;
            color: white;
        }
        figcaption {
            font-size: 0.9em;
            color: #666;
        }
        footer {
            text-align: center;
            padding: 10px 0;
            font-size: 0.9em;
            background-color: transparent;
            color: #fff;
        }
        footer p {
            margin: 0;
        }
    </style>
</head>
<body>
<header>
    <h1>网站简介：</h1>
    <p>这是一个简单的mp3播放器</p>
    <p>也是学习java开发的<a href="https://github.com/cdfive/cdfive-dubbo" target="_blank">开源项目</a></p>
</header>

<section id="section3">
    <table>
        <tr>
            <th>导航</th>
            <th>地址</th>
            <th>备注</th>
        </tr>
        <tr>
            <td>mp3</td>
            <td><a href="http://www.cdfive.com/mp3/index" target="_blank">http://www.cdfive.com/mp3/index</a></td>
            <td>一个简单的mp3播放器</td>
        </tr>
        <tr>
            <td>github</td>
            <td><a href="https://github.com/cdfive" target="_blank">https://github.com/cdfive</a></td>
            <td>github主页</td>
        </tr>
        <tr>
            <td>blog</td>
            <td><a href="https://www.cnblogs.com/cdfive2018" target="_blank">https://www.cnblogs.com/cdfive2018</a></td>
            <td>技术博客</td>
        </tr>
        <tr>
            <td>eureka</td>
            <td><a href="http://eureka.cdfive.com" target="_blank">http://eureka.cdfive.com</a></td>
            <td>eureka微服务注册中心</td>
        </tr>
        <tr>
            <td>nacos</td>
            <td><a href="http://nacos.cdfive.com/nacos" target="_blank">http://nacos.cdfive.com/nacos</a></td>
            <td>nacos微服务注册/配置中心，登录名/密码：nacos/nacos</td>
        </tr>
        <tr>
            <td>sentinel</td>
            <td><a href="http://sentinel.cdfive.com" target="_blank">http://sentinel.cdfive.com</a></td>
            <td>sentinel限流熔断web管理端，登录名/密码：sentinel/sentinel</td>
        </tr>
        <tr>
            <td>spring-boot-admin</td>
            <td><a href="http://spring-boot-admin.cdfive.com/wallboard" target="_blank">http://spring-boot-admin.cdfive.com/wallboard</a></td>
            <td>spring-boot服务web管理端</td>
        </tr>
    </table>
</section>

<footer>
    <p>Copyright © 2015 - 2024. <a href="https://beian.miit.gov.cn/#/Integrated/index" target="_blank">蜀ICP备15006292号-1</a></p>
</footer>
</body>
</html>
