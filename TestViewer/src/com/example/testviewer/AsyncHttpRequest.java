package com.example.testviewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.AsyncTask;

public class AsyncHttpRequest extends AsyncTask<URL,Void,ArrayList<String>> {


	Activity activity;
	public AsyncHttpRequest(Activity activity){
		this.activity = activity;
	}


	@Override
	protected ArrayList<String> doInBackground(URL... params) {
		// TODO 自動生成されたメソッド・スタブ

		return parse();

	}

	@Override
	protected void onCancelled() {
		// TODO 自動生成されたメソッド・スタブ
		super.onCancelled();
	}


	@Override
	protected void onPostExecute(ArrayList<String> result) {
		// TODO 自動生成されたメソッド・スタブ

		super.onPostExecute(result);
	}



	private String getPage_1(){

		String URLstr = "http://matome.naver.jp/odai/2138656993796205301";
		String src = new String();

		  HttpURLConnection http = null;
		  InputStream in = null;
		  try {
		   // URLにHTTP接続
		   URL url = new URL(URLstr);
		   http = (HttpURLConnection) url.openConnection();
		   http.setRequestMethod("GET");
		   http.connect();
		   // データを取得
		   in = http.getInputStream();

		   // HTMLソースを読み出す
		   byte[] line = new byte[1024];
		   int size;
		   while (true) {
		    size = in.read(line);
		    if (size <= 0)
		     break;
		    src += new String(line);
		   }
		   // HTMLソースを表示
		  } catch (Exception e) {
		   return null;
		  } finally {
		   try {
		    if (http != null)
		     http.disconnect();
		    if (in != null)
		     in.close();
		   } catch (Exception e) {

		   }
		  }
		   return src;
	}


	//単に取得する方法その１

    private void httprequest(){

		String URLstr = "http://matome.naver.jp/odai/2138614731930729201";

    	HttpClient c = new DefaultHttpClient();
        HttpGet g = new HttpGet(URLstr);
        byte[] result = null;

        try{
            HttpResponse response = c.execute(g);
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() != HttpURLConnection.HTTP_OK){
                //Log.e("Connection.download", "download failed");
            }
            else{
                result = EntityUtils.toByteArray(response.getEntity());
            }
            //Log.d("log", new String(result, "Shift-JIS"));
        }
        catch(Exception e){
        }
        System.out.println(result);
   }

   //パーサを使う
    private ArrayList<String> parse(){
    	String URLstr = "http://matome.naver.jp/odai/2138656993796205301";
    	Document document;

    	ArrayList<String> retArray = new ArrayList<String>();

    	try {
    	    document = Jsoup.connect(URLstr).get();


    	    Elements imgTags = document.getElementsByTag("img");
    	    Iterator<Element> ite = imgTags.iterator();
    	    while(ite.hasNext()){
    	    	Element imgTag = ite.next();

    	    	Attributes attrs = imgTag.attributes();

    	    	Iterator<Attribute> ite2 = attrs.iterator();
    	    	while(ite2.hasNext()){
    	    		Attribute att  = ite2.next();
    	    		String key = att.getKey();
    	    		if(key.equals("src")){
    	    				System.out.println(att.getValue());
    	    				retArray.add(att.getValue());
    	    		}
    	    	}
    	    }
    	    return retArray;


    	    /*
    	     * 没
    	     */
    	    /*
    	    Elements srcAttTag = document.getElementsByAttribute("src");
    	    Iterator<Element> ite = srcAttTag.iterator();
    	    while(ite.hasNext()){
    	    	Element imgTag = ite.next();

    	    	Attributes attrs = imgTag.attributes();

    	    	Iterator<Attribute> ite2 = attrs.iterator();
    	    	while(ite2.hasNext()){
    	    		Attribute att  = ite2.next();
    	    		if(att.getKey().equals("src")){
    	    				System.out.println(att.getValue());
    	    		}
    	    	}
    	    }
			*/



        	//Element body = document.body();

        	//System.out.println(body.text());

    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    	return null;
    }

}
