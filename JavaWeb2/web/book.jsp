<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="./frame/layui/css/layui.css">

</head>
<form style="display: none" id="bookForm" lay-filter="bookForm"class="layui-form" >

    <input type="text" name="bookid" id="bookid" style="display: none"/>
    <div class="layui-form-item">
        <label class="layui-form-label">图书名称</label>
        <div class="layui-input-inline">
            <input type="text" id="bookname" name="bookname" placeholder="请输入图书名字" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">作者</label>
        <div class="layui-input-inline">
            <input type="text" id="author" name="author" placeholder="请输入作者" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">描述</label>
        <div class="layui-input-inline">
            <input type="text" id="remark" name="remark" placeholder="请输入信息" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">出版社</label>
        <div class="layui-input-inline">
            <input type="text" id="publisher" name="publisher" placeholder="请输入出版社" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">价格</label>
        <div class="layui-input-inline">
            <input type="text" id="price" name="price" placeholder="请输入价格" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">上下架</label>
        <div class="layui-input-inline">
            <select name="status" >
                <option value="">--上下架状态--</option>
                <option value="0">上架</option>
                <option value="1">下架</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">书籍类型</label>
        <div class="layui-input-inline">
            <select name="typeid" id="typeIdSelect" >
                <option value="">--书籍类型--</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="*">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<body>

<div class="demoTable layui-form">
    <div class="layui-inline">
        <input class="layui-input" name="bookname" id="bk" autocomplete="off" placeholder="搜索书名">
    </div>
    <div class="layui-inline">
        <input class="layui-input" name="author" id="au" autocomplete="off" placeholder="搜索作者">
    </div>

    <div class="layui-inline" >
        <select name="typeid" id="typeSelect" lay-filter="aihao">
            <option value=""></option>
        </select>
    </div>
    <button class="layui-btn" data-type="reload" id="reloadTabBtn">搜索</button>

</div>
<table class="layui-hide" id="test" lay-filter="test"></table>

<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="getCheckData">获取选中行数据</button>
        <button class="layui-btn layui-btn-sm" lay-event="getCheckLength">获取选中数目</button>
        <button class="layui-btn layui-btn-sm" lay-event="isAll">验证是否全选</button>
        <button class="layui-btn layui-btn-sm" lay-event="addBtn">新增</button>
    </div>
</script>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    {{#  if(d.status == 0){ }}
    <button class="layui-btn layui-btn-sm" lay-event="updateStatus">上架</button>
    {{#  } else { }}
    <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="updateStatus">下架</button>
    {{#  } }}
</script>

<script type="text/html" id="titleTpl">
    {{#  if(d.status == 0){ }}
    <button class="layui-btn layui-btn-sm layui-btn-danger"  >下架状态</button>
    {{#  } else { }}
    <button class="layui-btn layui-btn-sm" >上架状态</button>
    {{#  } }}
</script>
<script src="./frame/layui/layui.js"></script>

<script>
    layui.use(['jquery','form','layer','table'], function(){
        var $ =layui.jquery;
        var form =layui.form;
        var layer =layui.layer;
        var table=layui.table;
        //绑定搜索事件，更新表格
        $("#reloadTabBtn").click(function () {
            console.log($("#bk").val())
            table.reload('table1',{
                where:{
                    bookname:$("#bk").val(),
                    author:$("#au").val(),
                    typeid:$("#typeSelect").val()
                },
                page: {
                    curr:1//重新从第一页开始
                }

            });
        })

        //页面加载完成就立即填充书籍类型的下拉框中
        $.ajax({
            type:'get',
            url:"${pageContext.request.contextPath}/type/getAllType",
            dataType:'json',//默认接收到的类型为json
            success:function (types) {
                // console.log(types);
                //查出类型信息后，将类型信息填充到下拉框中
                $.each(types,function (i,obj) {
                    // console.log(obj.typeid+"=="+obj.typename)
                    /*
                    dom操作，给下拉框添加option
                    并给option添加值
                     */
                    $("#typeSelect").append(
                        $("<option></option>").attr("value",obj.typeid).text(obj.typename)
                    )

                    $("#typeIdSelect").append(
                        $("<option></option>").attr("value",obj.typeid).text(obj.typename)
                    )
                })
                // 渲染下拉框
                form.render('select');
            }

        })

        table.render({
            id:'table1',
            elem: '#test'
            ,url:'${pageContext.request.contextPath}/book/getAllBook'
            ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            ,title: '图书表'
            ,cols: [[
                {type: 'checkbox', fixed: 'left'}
                ,{field:'bookid', title:'图书编号', fixed: 'left', unresize: true, sort: true}
                ,{field:'bookname', title:'图书名称'}
                ,{field:'author', title:'作者'}
                ,{field:'count', title:'数量'}
                ,{field:'remark', title:'描述'}
                ,{field:'typeid', title:'类型id',hide:true}
                ,{field:'publisher', title:'出版社'}
                ,{field:'price', title:'价格'}
                ,{field:'status', title:'图书状态', templet:'#titleTpl'}
                ,{field:'typename', title:'图书类型'}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:200}
            ]]
            ,page: true,
            limit:10,
            limits:[5,10,15,20]
        });

        //头工具栏事件
        //此处的test是上面table中的lay-filter中的值
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'getCheckData':
                    var data = checkStatus.data;
                    layer.alert(JSON.stringify(data));
                    break;
                case 'getCheckLength':
                    var data = checkStatus.data;
                    layer.msg('选中了：'+ data.length + ' 个');
                    break;
                case 'isAll':
                    layer.msg(checkStatus.isAll ? '全选': '未全选');
                    break;
                //自定义头工具栏右侧图标 - 提示
                case 'LAYTABLE_TIPS':
                    layer.alert('这是工具栏右侧自定义的一个图标按钮');
                    break;
                case 'addBtn':
                    //打开弹出框之前先清空表单
                    $("#bookForm")[0].reset();
                    layer.open({
                        type: 1,//页面层
                        title:'新增图书信息',
                        area: ['400px', '500px'],
                        offset:'100px',
                        content: $("#bookForm")
                    });
                    break;
            };
        });
        form.on('submit(*)', function(data){
            console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
            $.ajax({
                type:'post',
                url:'${pageContext.request.contextPath}/book/addOrUpdateBook',
                data:data.field,
                dataType:'json',
                success: function (result) {
                    layer.closeAll();
                    if (result.code==1){
                        layer.msg(result.msg,{icon:1});
                        table.reload("table1")
                    }else {
                        layer.msg(result.msg,{icon:5});
                    }
                }
            })
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });

        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;
            //console.log(obj)
            if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    $.ajax({
                        type:'get',
                        url:'${pageContext.request.contextPath}/book/delBook',
                        data:{"bookid": data.bookid},
                        success:function (msg) {
                            layer.msg(msg);
                            obj.del();
                            layer.close(index);
                        }
                    })

                });
            } else if(obj.event === 'edit'){
                //打开弹出框之前先清空表单
                $("#bookForm")[0].reset();
                layer.open({
                    type: 1,//页面层
                    title:'修改图书信息',
                    area: ['400px', '550px'],
                    offset:'100px',
                    content: $("#bookForm")
                })
                //将当前点击行的数据填充到表单中去
                form.val('bookForm', data);
            }else if (obj.event==='updateStatus'){
                layer.confirm('确定要上架/下架吗?',function (index) {
                    $.ajax({
                        type:'get',
                        url:'${pageContext.request.contextPath}/book/updateStatus',
                        data:{"bookid": data.bookid,"status":data.status},
                        dataType: 'json',//预期的服务器返回的数据类型
                        success:function (result) {//result是前面返回过来的
                            if (result.code==1){
                                layer.msg(result.msg);
                                //成功后刷新表格
                                table.reload("table1");
                            }else {
                                layer.msg(result.msg);
                            }
                        }
                    })
                    layer.close(index);
                });

            }
        });
    });
</script>

</body>
</html>
