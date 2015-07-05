package com.ozstrategy.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozstrategy.util.Base64Utils;
import com.ozstrategy.util.RSAUtils;
import com.ozstrategy.util.ThreeDESUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;
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
    @Test
    public void testHTTP(){
        try {
            File file=new File("/Users/lihao1/Downloads/kkk.jpg");
            if(!file.exists()){
                file.createNewFile();
            }
            URL uri=new URL("http://images.forwallpaper.com/files/thumbs/preview/11/114443__cat-kitten-black-sleeping-ribbon-bow-box_p.jpg");
            byte[] bytes= IOUtils.toByteArray(uri);
            FileUtils.writeByteArrayToFile(file,bytes);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testJsoup(){
        try{
            String base="http://cn.forwallpaper.com";
            String url="http://cn.forwallpaper.com/images/cats.html";
            Document doc= Jsoup.connect(url)
                    .userAgent("Mozilla")
                    .timeout(3000)
                    .get();
            Elements pagea=doc.select("div[class=ui-pages]").select("a");
            List<String> urls=new ArrayList<String>();
            urls.add("/images/cats.html");
            Iterator<Element> iterator = pagea.iterator();
            while (iterator.hasNext()){
                Element element=iterator.next();
                urls.add(element.attr("href"));
            }
            urls.remove(urls.size()-1);
            String lastUrl=urls.get(urls.size()-1);


            System.out.println("pageTotal==="+lastUrl);
            List<String> itemUrl=new ArrayList<String>();


            for(String uu:urls){
                doc=Jsoup.connect(base+uu)
                        .userAgent("Mozilla")
                        .timeout(3000)
                        .get();
                Elements elements=doc.select("div[class=ui-cnt]").select("ul li a");
                Iterator<Element> al = elements.iterator();
                while (al.hasNext()){
                    Element element=al.next();
                    itemUrl.add(element.attr("href"));
//                    System.out.println(element.attr("href"));
                }

            }

            for(String item:itemUrl){
                doc=Jsoup.connect(base+item)
                        .userAgent("Mozilla")
                        .timeout(3000)
                        .get();
                Element element=doc.select("#bigImg").last();
                String src=element.attr("src");
                String fileName=src.substring(src.lastIndexOf("/")+1);
                System.out.println("src=="+src);
                System.out.println("fileName=="+fileName);

                try{
                    URL uri=new URL(src);
                    byte[] bytes= IOUtils.toByteArray(uri);
                    File file=new File("/Users/lihao1/Downloads/crawlerTest/"+fileName);
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    FileUtils.writeByteArrayToFile(file, bytes);
                }catch (Exception e){
                    e.printStackTrace();

                }



            }




        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 远程地址http://121.40.83.214:8080/luzhishen/
     */

    /**
     * 注册
     * 接口参数：username,password
     * 请求方式：POST
     * 
     * @throws Exception
     */
    @Test
    public void testRegister() throws Exception{
//        String url="http://121.40.83.214:8080/luzhishen/app/register";
        String url="http://127.0.0.1:8080/luzhishen/app/register";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("username", "ld"));
        nvps.add(new BasicNameValuePair("password", "111111"));

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
        System.out.println("charset=="+charset);

        String body = null;
        try {
            body = EntityUtils.toString(entity,"UTF-8");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(body);
        httpclient.getConnectionManager().shutdown();
    }

    /**
     * 登陆
     * 接口参数：login_name,login_password
     * 请求方式：POST
     *
     * @throws Exception
     */
    @Test
    public void testLogin() throws Exception{
//        String url="http://127.0.0.1:8081/luzhishen/app/login";
        String url="http://121.40.83.214:8080/luzhishen/app/login";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("login_name", "ld"));
        nvps.add(new BasicNameValuePair("login_password", "111111"));
//        nvps.add(new BasicNameValuePair("platform", "PC"));
        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        response = httpclient.execute(httpost);
        Header[] headers = response.getHeaders("Set-Cookie");
        for(Header header:headers){
            System.out.println(header.getName()+"==="+header.getValue());
            HeaderElement[] elements=  header.getElements();
            for(HeaderElement element:elements){

                System.out.println(element.getName()+"===="+element.getValue());
                System.out.println("element.toString()==="+element.toString());
            }
        }
        String coockie = response.getFirstHeader("Set-Cookie").getValue();
        HttpEntity entity = response.getEntity();
        String charset = EntityUtils.getContentCharSet(entity);
        String body = null;
        body = EntityUtils.toString(entity);
        System.out.println("coockie==="+coockie);
        /***
         * 在这里可以获取登录时的cookie
         */



        System.out.println(body);
        httpclient.getConnectionManager().shutdown();
    }
     /**
      * 反馈意见
     * 接口参数：comment，登录时的cookie,contract联系方式
     *
     * @throws Exception
     */
    @Test
    public void testComments() throws Exception{
        String url="http://127.0.0.1:8080/luzhishen/app/comments";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("comments", "的会计法律框架撒旦就发了"));
        nvps.add(new BasicNameValuePair("contract", "13541287474"));
        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        httpost.addHeader("Cookie","JSESSIONID=edxmvfg6ar0h;Path=/luzhishen");
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
      * 获取用户积分
     * 接口参数：登陆时的cookie
     *
     * @throws Exception
     */
    @Test
    public void testGetUserCredits() throws Exception{
        String url="http://127.0.0.1:8081/luzhishen/app/getUserCredits";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        httpost.addHeader("Cookie","JSESSIONID=1u2zo713aoxdh");
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
      * 登录时的cookie
      * creditsSum  出售积分数量  必须
      * price:出售积分价格  必须
     *
     * @throws Exception
     */
    @Test
    public void testCreateBills() throws Exception{
        String url="http://127.0.0.1:8080/luzhishen/app/createBills";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("creditsSum", "1"));
        nvps.add(new BasicNameValuePair("price", "0.01"));
        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        httpost.addHeader("Cookie","JSESSIONID=1stu0fs5dpuoe;Path=/luzhishen");
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
    /**用户下架商品，即取消积分出售
     * 接口参数：
      * 登录时的cookie
      * billId  出售积分id  必须
     *
     * @throws Exception
     */
    @Test
    public void testCancelBills() throws Exception{
        String url="http://127.0.0.1:8080/luzhishen/app/cancelBills";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("billId", "4"));
        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        httpost.addHeader("Cookie","JSESSIONID=158hur38idc3s;Path=/luzhishen");
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
     * 获取出售积分列表
     * 登录时的cookie
     * 接口参数：sort  1 为时间倒序，2 为出售积分倒序 3 为价格倒序
      * start:数据起始量，比如：从第0条数据开始，start=0,从第34条数据开始：start=34 （必须，并且为数字）必须
      * limit:每次获取的数据量,默认每次25条，（可以不传，默认25条）必须
     *
     * @throws Exception
     */
    @Test
    public void testListBills() throws Exception{
        String url="http://127.0.0.1:8080/luzhishen/app/listBills";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("sort", "1"));
        nvps.add(new BasicNameValuePair("start", "0"));
        nvps.add(new BasicNameValuePair("limit", "25"));
        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        httpost.addHeader("Cookie","JSESSIONID=158hur38idc3s;Path=/luzhishen");
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
      * 用户提交订单后如果30分钟内未支付，系统将自动取消
      *
      *
     * 提交订单
     * 登录时的cookie
      * billId：出售积分列表的ID
      * 该接口返回数据采用3DES+RSA算法加密，接口数据采用3DES加密，商户信息采用RSA加密
      * 返回的形式如下
      * {"message":"","data":"mnSDCs/1X9QABKmjHWsbzpDDkqRRCtu2zjckOSnRujM/YddSEvGvbTaNWv8V5MlVLc3jRK7ixc6NZSN6vmRPZjzympD8uoUGdRCb4pMyxjMWgJkIrqBy6SS058F+ZxTBZSER70R6E7AEAhsMrU/Q5NCa+XESqUxwcGo6EYz8PMvYDfpVwE8BwU6xjIVachOG4rr7EqAAibsKF/Ijet9x2+F7xJy6MwJ1/ic9VBSsIVkgf3Nx8801kjVSqKTFu7oFl3eT7NYTWejhmPt03jN6l97wPQKUQ0g2Al+r6BMTqhBVSbPrgDgK3HhZizx9CXRVyK5Uq03+WKBwmdcJRdUFhLgnozLDT19dUbRJigw0IRmLduYFFulCcKGTh1oRMTIvPxeR1p0UJMiaDIFLmk5PlQ9EPLlGf9+an2RFmJ6PWcy858BMmXkiBjh24+ffc0xRc2xe/HOanCh2S1hk3K9ZcDlFcpomr54lZxiVDGf4mubyUEdkVMAzflxz0qpw89CtZYmsGvvcp0GIKoN24tR2bZaYVtf8s3kQCFG82Bz2fXg=","success":true}
      其中data是接口数据采用3DES加密，解密 data的数据结构如下
      {"publicKey":"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0B21R0OM04Kz1ZpmhUS+zrgZI4p8RTCnMO3hrIv0wMY+Bpho6ks7QKJ34NgxjPIq9FxwIrON5UJWADnYDM/Zu7XDmLqQGSWWIFsILZvySma7oH8JCpgx/EoEIE2Ic1pRyfFMyxfToqhLKF8iaFX6HLQRGnpmAvrq0zznhWIRP4wIDAQAB","bills":"JSvWRI84ZSDeiSKLU335W8wdCaGBcM9e5qB11/xJLPNSpl9MijRLGCY+R5aNLI1KMgeizYW/JBgJwg844a6vwh7HVWNle78bI8wjv5aO5ER2eezizBWUJYwn7VGjAURzmyWpeLLr7edJeM14CPK+5bx4CThgfRBt1TqcFH7RWYY="}

      * publicKey为加密公钥
      * data返回的是业务数据，其先进行RSA私钥加密，然后采用BASE64编码。拿到data的数据后用返回的公钥进行解密。
      * 解密后的结构如下：
      * 其中notify_url表示支付宝异步通知服务器的URL
      * {"id":5,"orderNo":"15052823560005","notify_url":"http://kksdjfkj","money":2000.0}
      * 下面的测试有详细的说明
      *
      *
     *
     * @throws Exception
     */
    @Test
    public void testCreateOrder() throws Exception{
        String url="http://127.0.0.1:8080/luzhishen/app/createOrder";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("billId", "4"));
        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        httpost.addHeader("Cookie","JSESSIONID=158hur38idc3s;Path=/luzhishen");
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        response = httpclient.execute(httpost);
        HttpEntity entity = response.getEntity();

        String charset = EntityUtils.getContentCharSet(entity);

        String body = null;
        body = EntityUtils.toString(entity);
        System.out.println(body);
        httpclient.getConnectionManager().shutdown();


        if(body!=null && !body.equals("")){
            Map<String,Object> map=new ObjectMapper().readValue(body, Map.class);
            String data= ObjectUtils.toString(map.get("data"));//取得接口数据
            byte[] base64=Base64Utils.decode(data);//base64转码
            String desData=new String(ThreeDESUtils.decrypt(base64));//DES解密
            System.out.println(desData);//接口数据


            map=new ObjectMapper().readValue(desData,Map.class);//转换接口数据

            String publicKey=ObjectUtils.toString(map.get("publicKey"));//取得RAS解密公钥
            String bills=ObjectUtils.toString(map.get("bills"));//取得商户信息
            byte[] bills64=Base64Utils.decode(bills);//base64转码
            byte[] billsByte=RSAUtils.decryptByPublicKey(bills64, publicKey);
            String billsData=new String(billsByte);//取得商户信息
            System.out.println(billsData);
            //{"id":1,"orderNo":"15052912420001","notify_url":"http://kksdjfkj","money":2000.0}
//            billsData="{\"publicKey\":\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0B21R0OM04Kz1ZpmhUS+zrgZI4p8RTCnMO3hrIv0wMY+Bpho6ks7QKJ34NgxjPIq9FxwIrON5UJWADnYDM/Zu7XDmLqQGSWWIFsILZvySma7oH8JCpgx/EoEIE2Ic1pRyfFMyxfToqhLKF8iaFX6HLQRGnpmAvrq0zznhWIRP4wIDAQAB\",\"bills\":\"YmgxFy4wMu+JQvco20iPe88dxwgjANuO+Fzxu8pO9JGCbfqmsC60rhUnilD8k6TcnGZPtleNZSkVSdxdFWMhKEmyOs9uKqcE5SZT1uDYuINjCTEmdoz2387DXykdSqLLHk7i3lQoX+iMvSjd5Ayp26I4oMcol2miy9gNxUXm/a1TlLFh3Xjm2k7tFuYMohm6HmmJccgR5UuibPOq6npqPxJ49nszPg2Y8ri09JuI8/deiqz1XnX9Cw9XWUWs5xxBE/zSI+ZCPnX72r40I7cQmZEez15N4/WGAOPOcy62elNxAb1fbc9O0H1YNhzYTcLqOi2nI2RA3Vg83IYBytO/cQ==\"}";


            map=new ObjectMapper().readValue(billsData,Map.class);
            String id=ObjectUtils.toString(map.get("id"));//订单ID
            String orderNo=ObjectUtils.toString(map.get("orderNo"));//订单编号
            String money=ObjectUtils.toString(map.get("money"));//订单金额
            String pid=ObjectUtils.toString(map.get("pid"));//商户PID
            String secret=ObjectUtils.toString(map.get("secret"));//商户支付宝密钥
            String notify_url=ObjectUtils.toString(map.get("notify_url"));//服务器异步通知地址
        }
    }
    /**
     * 获取买家订单
     * 登录时的cookie
     * 接口参数：
     * status:订单状态(必须)   0 待支付 1 已支付 2 失败
     * start:数据起始量(必须)，比如：从第0条数据开始，start=0,从第34条数据开始：start=34 （必须，并且为数字）必须
     * limit:每次获取的数据量(必须),默认每次25条，（可以不传，默认25条）必须
     *
     * @throws Exception
     */
    @Test
    public void testGetOrders() throws Exception{
        String url="http://127.0.0.1:8081/luzhishen/app/getOrders";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("status", "1"));
        nvps.add(new BasicNameValuePair("start", "0"));
        nvps.add(new BasicNameValuePair("limit", "25"));
        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        httpost.addHeader("Cookie","JSESSIONID=1sci7xwvqugfs;Path=/luzhishen");
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
     * 获取卖家订单
     * 登录时的cookie
     * 接口参数：
     * status:订单状态(必须)   0 正在别人购买 1 正在出售的 2 出售成功的
     * start:数据起始量(必须)，比如：从第0条数据开始，start=0,从第34条数据开始：start=34 （必须，并且为数字）必须
     * limit:每次获取的数据量(必须),默认每次25条，（可以不传，默认25条）必须
     *
     * @throws Exception
     */
    @Test
    public void testGetBills() throws Exception{
        String url="http://127.0.0.1:8081/luzhishen/app/getBills";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("status", "0"));
        nvps.add(new BasicNameValuePair("start", "0"));
        nvps.add(new BasicNameValuePair("limit", "25"));
        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        httpost.addHeader("Cookie","JSESSIONID=1sci7xwvqugfs;Path=/luzhishen");
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
     * 获取用户平台金
     * 登录时的cookie
     *
     * @throws Exception
     */
    @Test
    public void testGetMoney() throws Exception{
        String url="http://127.0.0.1:8080/luzhishen/app/getMoney";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        httpost.addHeader("Cookie","JSESSIONID=794dmrdj483z;Path=/luzhishen");
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
     * 获取用户积分
     * 登录时的cookie
     *
     * @throws Exception
     */
    @Test
    public void testGetCredits() throws Exception{
        String url="http://127.0.0.1:8080/luzhishen/app/getCredits";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        httpost.addHeader("Cookie","JSESSIONID=794dmrdj483z;Path=/luzhishen");
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
     * 用户提现
     * 登录时的cookie
      * 接口参数：
      * total:用户提现平台金数量,应该和rmb相等
      * rmb:提现人民总额，精确到：0.00
      * email:用户支付宝账号
      * name:用户支付宝对应的真实姓名
     *
     * @throws Exception
     */
    @Test
    public void testApplyRmb() throws Exception{
        String url="http://127.0.0.1:8080/luzhishen/app/applyRmb";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("total", "0.01"));
        nvps.add(new BasicNameValuePair("rmb", "0.01"));
        nvps.add(new BasicNameValuePair("email", "18583910581"));
        nvps.add(new BasicNameValuePair("name", "李皓"));

        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        httpost.addHeader("Cookie","JSESSIONID=794dmrdj483z;Path=/luzhishen");
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
     * 获取用户提现列表
     * 登录时的cookie
      * 接口参数：status   0 未处理，1 成功 ， 2  失败，
    * start:数据起始量(必须)，比如：从第0条数据开始，start=0,从第34条数据开始：start=34 （必须，并且为数字）必须
     * limit:每次获取的数据量(必须),默认每次25条，（可以不传，默认25条）必须
     *
     * @throws Exception
     */
    @Test
    public void testGetApply() throws Exception{
        String url="http://121.40.83.214:8080/luzhishen/app/getApply";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("status", "1"));
        nvps.add(new BasicNameValuePair("start", "0"));
        nvps.add(new BasicNameValuePair("limit", "25"));

        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        httpost.addHeader("Cookie","JSESSIONID=C972A33D23384FE72E479D784AFACCDE; Path=/luzhishen");
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
     * 获取用户历史提现账号
     * 登录时的cookie
      * 接口参数：
    * start:数据起始量(必须)，比如：从第0条数据开始，start=0,从第34条数据开始：start=34 （必须，并且为数字）必须
     * limit:每次获取的数据量(必须),默认每次25条，（可以不传，默认25条）必须
     *
     * @throws Exception
     */
    @Test
    public void testListHistoryEmail() throws Exception{
        String url="http://127.0.0.1:8080/luzhishen/app/listHistoryEmail";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        httpost.addHeader("Cookie","JSESSIONID=794dmrdj483z;Path=/luzhishen");
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
     * 设置热点
     *
     * @throws Exception
     */
    @Test
    public void testSetAp() throws Exception{
        String url="http://127.0.0.1:8080/luzhishen/app/setAp";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("user_id", "1"));
        nvps.add(new BasicNameValuePair("SSID", "1"));
        nvps.add(new BasicNameValuePair("BSSID", "1"));
        nvps.add(new BasicNameValuePair("pass", "1"));


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
     * 获取热点
     *SSID用逗号隔开，详细如下例
     *BSSID用逗号隔开，详细如下例，这个暂时可以不传
     * @throws Exception
     */
    @Test
    public void testGetAp() throws Exception{
//        String url="http://127.0.0.1:8080/luzhishen/app/getAp";
        String url="http://271387591.pagekite.me/luzhishen/app/getAp";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("user_id", "11"));
        nvps.add(new BasicNameValuePair("SSID", "RDDX_630189"));
        nvps.add(new BasicNameValuePair("BSSID", "1,2,3,4,5,6"));
        nvps.add(new BasicNameValuePair("pass", "1"));


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
     * 上传积分
     * @throws Exception
     */
    @Test
    public void testReportPoints() throws Exception{
        String url="http://121.40.83.214:8080/luzhishen/app/reportPoints";
//        String url="http://127.0.0.1:8081/luzhishen/app/reportPoints";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("user_id", "2"));
        nvps.add(new BasicNameValuePair("SSID", "1,2,3,4,5,6"));
        nvps.add(new BasicNameValuePair("BSSID", "1,2,3,4,5,6"));
        nvps.add(new BasicNameValuePair("points", "1"));
        nvps.add(new BasicNameValuePair("points1", "1"));


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
     * 赠送积分
     * @throws Exception
     */
    @Test
    public void testGivePoints() throws Exception{
        String url="http://127.0.0.1:8080/luzhishen/app/givePoints";
        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("user_id", "1"));
        nvps.add(new BasicNameValuePair("give_id", "2"));
        nvps.add(new BasicNameValuePair("points", "1222"));


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
