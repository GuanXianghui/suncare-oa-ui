<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.*"%>
<%@ page import="org.apache.struts2.ServletActionContext" %>
<%
String root = ServletActionContext.getServletContext().getRealPath("/") + "/";//得到网站绝对路径
String fileRoute = request.getParameter("fileRoute");//得到文件名
String newName = request.getParameter("newName");//文件改名
// 设置响应头和下载保存的文件名
response.reset();
response.setContentType("application/octet-stream; charset=GBK"); //linux
response.addHeader("Content-Disposition", "attachment; filename=\"" + newName + "\"");//linux

//新建文件输入输出流
OutputStream output = null;
FileInputStream fis = null;
try{
  //新建File对象
  File f = new File(root + fileRoute);
  //新建文件输入输出流对象
  output = response.getOutputStream();
  fis = new FileInputStream(f);
  //设置每次写入缓存大小
  byte[] b = new byte[(int)f.length()];
  //out.print(f.length());
  //把输出流写入客户端
  int i = 0;
  while((i = fis.read(b)) > 0){
output.write(b, 0, i);
  }
  output.flush();
}
catch(Exception e){
  e.printStackTrace();
}
finally{
  if(fis != null){
fis.close();
fis = null;
  }
  if(output != null){
output.close();
output = null;
  }
}
%>