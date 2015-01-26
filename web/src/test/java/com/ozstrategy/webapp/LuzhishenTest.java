package com.ozstrategy.webapp;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lihao on 12/30/14.
 */
public class LuzhishenTest {
    /***
     * 
     */
    @Test
    public void testUsername(){
        Pattern pattern=Pattern.compile("^[a-zA-Z0-9_]{3,16}$");
        Matcher matcher = pattern.matcher("2233");
        System.out.println(matcher.matches());
        String uid = UUID.randomUUID().toString();
        System.out.println(uid);
        
    }

    /**
     * 接口参数：username,password
     * 请求方式：POST
     * 
     * @throws Exception
     */
    @Test
    public void testRegister() throws Exception{
        String url="http://120.24.234.71/luzhishen/app/user/register";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("username", "admin1"));
        nvps.add(new BasicNameValuePair("password", "tomcat"));

        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;

        try {
            response = httpclient.execute(httpost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();

        String charset = EntityUtils.getContentCharSet(entity);

        String body = null;
        try {
            body = EntityUtils.toString(entity);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(body);
        httpclient.getConnectionManager().shutdown();
    }

    /**
     * 接口参数：username,password
     * 请求方式：POST
     *
     * @throws Exception
     */
    @Test
    public void testLogin() throws Exception{
//        String url="http://120.24.234.71/im/app/login";
        String url="http://120.24.234.71/luzhishen/security/login";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("username", "admin"));
        nvps.add(new BasicNameValuePair("password", "tomcat"));
        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        response = httpclient.execute(httpost);
        HttpEntity entity = response.getEntity();

        String charset = EntityUtils.getContentCharSet(entity);

        String body = null;
        body = EntityUtils.toString(entity);
        System.out.println(body);
        httpclient.getConnectionManager().shutdown();
        
    }

    /**
     * 接口参数：
     * start:数据起始量，比如：从第0条数据开始，start=0,从第34条数据开始：start=34 （必须，并且为数字）
     * limit:每次获取的数据量,默认每次25条，（可以不传，默认25条）
     * 
     * 参数示例：比如每页显示30条数据，参数传递为：
     * 第一页：start=0&limit=30
     * 第二页：start=31&limit=30
     * 第三页：start=61&limit=30
     * .......
     * 
     * 请求方式：POST/GET
     * 返回字段说明见：save测试方法中得注释
     * @throws Exception
     */
    /*
    GET请求方式可以通过一下URL直接获取数据：
             http://120.24.234.71/luzhishen/app/lever/list?start=0&limit=25
     */
    @Test
    public void testList() throws Exception{
        String url="http://120.24.234.71/luzhishen/app/lever/list";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("start", "0"));
        nvps.add(new BasicNameValuePair("limit", "25"));
        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        response = httpclient.execute(httpost);
        HttpEntity entity = response.getEntity();

        String charset = EntityUtils.getContentCharSet(entity);

        String body = null;
        body = EntityUtils.toString(entity);
        System.out.println(body);
        httpclient.getConnectionManager().shutdown();
    }
    /*
    接口参数见下面测试方法，
     */
    @Test
    public void testSave() throws Exception{
        String url="http://120.24.234.71/luzhishen/app/lever/save";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("xnName", "克里斯多夫据了解第三方连接连接"));  //县名
        nvps.add(new BasicNameValuePair("xnNo", "25")); //县名编号
        nvps.add(new BasicNameValuePair("xzNo", "25e"));//乡镇编号
        nvps.add(new BasicNameValuePair("police", "水电费"));//所属派出所
        nvps.add(new BasicNameValuePair("policeNo", "33")); //派出所编号
        nvps.add(new BasicNameValuePair("village", "33"));//街道或村名
        nvps.add(new BasicNameValuePair("villageNo", "33")); //街道或村名编号
        nvps.add(new BasicNameValuePair("leverNo", "33")); //杆体编号
        nvps.add(new BasicNameValuePair("longitude", "33"));//经度
        nvps.add(new BasicNameValuePair("latitude", "33")); //纬度
        nvps.add(new BasicNameValuePair("allNo", "33"));  //全部编号
        nvps.add(new BasicNameValuePair("leverType", "33"));//杆体类别
        nvps.add(new BasicNameValuePair("leverAddress", "33"));//杆体具体地址
        nvps.add(new BasicNameValuePair("leverAddressAlias", "33"));//杆体具体地址俗称
        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        response = httpclient.execute(httpost);
        HttpEntity entity = response.getEntity();
        String charset = EntityUtils.getContentCharSet(entity);
        String body = null;
        body = EntityUtils.toString(entity);
        System.out.println(body);
        httpclient.getConnectionManager().shutdown();
    }
    /*
    接口参数见下面测试方法，id为必须值
     */
    @Test
    public void testUpdate() throws Exception{
        String url="http://120.24.234.71/luzhishen/app/lever/update";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("id", "1"));  //id
        nvps.add(new BasicNameValuePair("xnName", "克里斯多夫据了解第三方连接连接"));  //县名
        nvps.add(new BasicNameValuePair("xnNo", "25")); //县名编号
        nvps.add(new BasicNameValuePair("xzNo", "25e"));//乡镇编号
        nvps.add(new BasicNameValuePair("police", "水电费"));//所属派出所
        nvps.add(new BasicNameValuePair("policeNo", "33")); //派出所编号
        nvps.add(new BasicNameValuePair("village", "33"));//街道或村名
        nvps.add(new BasicNameValuePair("villageNo", "33")); //街道或村名编号
        nvps.add(new BasicNameValuePair("leverNo", "33")); //杆体编号
        nvps.add(new BasicNameValuePair("longitude", "33"));//经度
        nvps.add(new BasicNameValuePair("latitude", "33")); //纬度
        nvps.add(new BasicNameValuePair("allNo", "33"));  //全部编号
        nvps.add(new BasicNameValuePair("leverType", "33"));//杆体类别
        nvps.add(new BasicNameValuePair("leverAddress", "33"));//杆体具体地址
        nvps.add(new BasicNameValuePair("leverAddressAlias", "33"));//杆体具体地址俗称
        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        response = httpclient.execute(httpost);
        HttpEntity entity = response.getEntity();
        String charset = EntityUtils.getContentCharSet(entity);
        String body = null;
        body = EntityUtils.toString(entity);
        System.out.println(body);
        httpclient.getConnectionManager().shutdown();
    }

    /**
     * 接口名称：通过ID获取单个值
     * 请求方式:GET
     * 
     * 说明：该接口是一个rest风格的请求地址，请求方式如下：
     * http://120.24.234.71/luzhishen/app/lever/get/{0}
     * 其中{0}是一个占位符，表示的是ID，比如想要获取ID=2的数据，其格式为：
     * http://120.24.234.71/luzhishen/app/lever/get/2，获取其他数据，类似这样。
     * 
     * 
     * 
     * @throws Exception
     */
    
    @Test
    public void testGet() throws Exception{
        String url="http://120.24.234.71/luzhishen/app/lever/get/1";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        response = httpclient.execute(httpost);
        HttpEntity entity = response.getEntity();

        String charset = EntityUtils.getContentCharSet(entity);

        String body = null;
        body = EntityUtils.toString(entity);
        System.out.println(body);
        httpclient.getConnectionManager().shutdown();
    }
    /**
     * 接口名称：通过ID删除
     * 请求方式:GET
     * 
     * 说明：该接口风格同上
     * 
     * 
     * 
     * @throws Exception
     */
    
    @Test
    public void testDelete() throws Exception{
        String url="http://120.24.234.71/luzhishen/app/lever/delete/1";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        response = httpclient.execute(httpost);
        HttpEntity entity = response.getEntity();

        String charset = EntityUtils.getContentCharSet(entity);

        String body = null;
        body = EntityUtils.toString(entity);
        System.out.println(body);
        httpclient.getConnectionManager().shutdown();
    }
    
    
}
