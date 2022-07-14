<%--
  Created by IntelliJ IDEA.
  User: ttt
  Date: 2021/6/17
  Time: 19:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>图书管理系统</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/frame/layui/css/layui.css">

</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">图书管理系统</div>
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
        <a href="javascript:;">
          <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
          root
        </a>
        <dl class="layui-nav-child">
          <dd><a href="">基本资料</a></dd>
          <dd><a href="">安全设置</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item"><a href="index.html">退出登录</a></li>
    </ul>
  </div>

  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree"  lay-filter="test">
        <li class="layui-nav-item layui-nav-itemed">
          <a class="" href="javascript:;">书籍管理</a>
          <dl class="layui-nav-child">
            <dd>
              <a href="${pageContext.request.contextPath}/book.jsp" target="main">书籍信息</a>
            </dd>
          </dl>
        </li>
      </ul>
    </div>
  </div>

  <div class="layui-body">

    <iframe frameborder="0" border="0" height="100%" width="100%" name="main"></iframe>
  </div>

  <div class="layui-footer">
    <!-- 底部固定区域 -->
    © layui.com - 底部固定区域
  </div>
</div>
<script src="${pageContext.request.contextPath}/frame/layui/layui.js"></script>
<script>
  //JavaScript代码区域
  layui.use('element', function(){
    var $ = layui.jquery,
            element = layui.element;

  });

</script>

</body>
</html>
