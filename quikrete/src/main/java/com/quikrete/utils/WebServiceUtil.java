package com.quikrete.utils;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class WebServiceUtil {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";

    public static final String FAILURE_MESSAGE = "Something went wrong";

    public static final String URL = "https://quikrete.staging.wpengine.com/api/"; //Staging
   // public static final String URL = "https://quikrete.wpengine.com/api/";         //Production

//    //NSString *BaseApiURL = @"https://quikrete.staging.wpengine.com/api/"; // Staging
//    NSString *BaseApiURL = @"https://quikrete.wpengine.com/api/"; // Live

    public static final String METHOD_1_GET_PRODUCT_CAT_LIST = "productCategories";

    public static final String METHOD_2_GET_PROJECT_CAT_LIST = "projectCategories";

    public static final String METHOD_2_1_GET_PROJECT_LIST = "productList";
    public static final String METHOD_2_1_1_GET_PROJECT_LIST_CAT = "cat";
    public static final String METHOD_2_1_2_GET_PROJECT_LIST_TYPE = "type";

    public static final String METHOD_4_1_GET_PRODUCT_LIST = "projectList";
    public static final String METHOD_4_1_1_GET_PRODUCT_LIST_CAT = "cat";

    public static final String METHOD_5_1_GET_PRODUCT_DETAILS = "productDetails";
    public static final String METHOD_5_1_1_GET_PRODUCT_LIST_CAT = "cat";
    public static final String METHOD_5_1_2_GET_PRODUCT_LIST_ID = "id";

    public static final String METHOD_8_1_GET_PRODUCT_DETAILS = "projectDetails";

    public static final String METHOD_9_1_GET_PRODUCT_BARCODE = "barcodeProduct";
    public static final String METHOD_9_1_1_GET_PRODUCT_BARCODE = "barcode";

    public static final String METHOD_3_GET_VIDEO_LIST = "videoList";

    public static final String METHOD_3_1_GET_VIDEO_LIST_DETAILS = "videoDetails";
    public static final String METHOD_3_2_GET_VIDEO_LIST_DETAILS_ID = "id";

    public static final String METHOD_6_1_GET_CALCULATOR_LIST = "calculatorList";
    public static final String METHOD_6_1_1_GET_CALCULATOR_DETAILS = "calculatorDetails";
    public static final String METHOD_6_1_1_1_GET_CALCULATOR_DETAILS_ID = "id";

    public static final String METHOD_7_1_GET_CALCULATOR_RESULT = "calculateResult";
    public static final String METHOD_7_1_GET_CALCULATOR_RESULT_ID = "id";
    public static final String METHOD_7_1_GET_CALCULATOR_RESULT_VALUE = "value";
    public static final String METHOD_7_1_GET_CALCULATOR_RESULT_OPTION = "option";

    public static final String METHOD_10_1_GET_STORE_DETAILS = "getStoreLocators";
    public static final String METHOD_10_1_GET_STORE_DETAILS_LAT = "lat";
    public static final String METHOD_10_1_GET_STORE_DETAILS_LON = "lon";

    public static final String METHOD_11_1_GET_SEARCH_KEYWORD = "searchKeyword";
    public static final String METHOD_11_1_GET_SEARCH_KEYWORD_KEY = "keyword";
    public static final String METHOD_11_1_GET_SEARCH_KEYWORD_TYPE = "types";

    public static final String METHOD_12_1_ADD_TO_FAVOURITE = "favourite";
    public static final String METHOD_13_1_ADD_TO_UNFAVOURITE = "unfavourite";
    public static final String METHOD_12_1_ADD_TO_FAVOURITE_ID = "id";

    public static final String METHOD_14_1_GET_FAVOURITE = "favList";
    public static final String METHOD_14_1_1_GET_FAVOURITE = "list";

    public static final String METHOD_15_1_GET_RELATED = "relatedList";
    public static final String METHOD_15_1_GET_RELATED_RELATED = "related";
    public static final String METHOD_15_1_GET_RELATED_FOR = "for";


    public static final String METHOD_16_1_STORE_FROM_ZIP = "getStoreLocatorsFromZip";
    public static final String METHOD_16_1_STORE_FROM_ZIP_ZIPCODE = "zipcode";

    public static final String METHOD_17_1_APP_SWEEP = "sweeptakesOptions";

    public static final String METHOD_SOCIAL_MEDIA = "getSocialMediaMessage";


    //http://quikrete.staging.wpengine.com/api//

    public static InputStream retrieveStream(String url, Context context) {

        DefaultHttpClient client = new DefaultHttpClient();

        HttpGet getRequest = new HttpGet(url);

        try {

            HttpResponse getResponse = client.execute(getRequest);
            final int statusCode = getResponse.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                Log.d("newlog", "Status code error");
                return null;
            }

            HttpEntity getResponseEntity = getResponse.getEntity();
            String jsonString = convertStreamToString(getResponseEntity
                    .getContent());

            return convertStringToInputstream(jsonString);

        } catch (IOException e) {
            // getRequest.abort();
            Log.d("newlog", "Error for URL " + url, e);
        }

        return null;

    }

    /*01-21 22:28:34.769: E/newlog(18943): {"status":"ok","result":[{"id":"32","name":"Blacktop \/ Pavement Products","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2015\/01\/RN669-134A.jpg"},{"id":"33","name":"Bulk Masonary Mortars &amp; Grouts","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2015\/01\/RN669-261D.jpg"},{"id":"64","name":"Bulk Stuccos &amp; Plastering Products","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2015\/01\/RN669-261B.jpg"},{"id":"41","name":"Caulks &amp; Crack Sealants","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2015\/01\/RN669-260B.jpg"},{"id":"56","name":"Cements","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2015\/01\/RN669-257B.jpg"},{"id":"36","name":"Colorants (Concrete, Mortar,Cement &amp; Stucco)","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2015\/01\/RN669-238-239.jpg"},{"id":"57","name":"Concrete &amp; Masonry Coatings","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"38","name":"Concrete &amp; Masonry Sealers","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"42","name":"Concrete &amp; Masonry Stains","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"37","name":"Concrete &amp; Masonry Surface Preparation &amp; Cleaners","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"43","name":"Concrete Mixes","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"44","name":"Concrete Repair &amp; Rehabilitation Products","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"48","name":"Crack Sealers &amp; Caulks","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"60","name":"De-Icer","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"59","name":"Division 2 - Pavement","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"28","name":"Division 3 - Concrete","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"63","name":"Division 3 - Dry Process Shotcrete","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"30","name":"Division 32 - Exterior Improvements","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"29","name":"Division 4 - Masonry","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"45","name":"Division 4 - Masonry grouts","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"55","name":"Division 4 - Masonry Mortar","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"50","name":"Division 9 - Finishes","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"47","name":"Fastset\u2122 Products","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"49","name":"Flooring Underlayment &amp; Leveling Products","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"46","name":"Garage Floor Coatings","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"27","name":"Grouts &amp; Anchoring Products","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"9","name":"Hardscapes","image":"http:\/\/quikrete.staging.wpengine.com\/wp-content\/uploads\/2014\/12\/prod_1134-801.jpg"},{"id":"31","name":"Lime Products",
     */
    public static InputStream convertStringToInputstream(String params) {
        try {
            String str = params;

            //Log.e("newlog", params+"---");
            // convert String into InputStream
            InputStream is = new ByteArrayInputStream(str.getBytes());

            return is;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }

    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                is.close();

            } catch (IOException e) {

                e.printStackTrace();

            }
        }
        return sb.toString();
    }

    public static String getPostResponce(List<NameValuePair> nameValuePair,
                                         String Url) throws Exception {

        HttpParams httpParameters = new BasicHttpParams();
        // Set the timeout in milliseconds until a connection is established.
        int timeoutConnection = 5000;
        HttpConnectionParams.setConnectionTimeout(httpParameters,
                timeoutConnection);
        // Set the default socket timeout (SO_TIMEOUT)
        // in milliseconds which is the timeout for waiting for data.
        int timeoutSocket = 5000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient(httpParameters);
        // Creating HTTP Post
        HttpPost httpPost = new HttpPost(Url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            // writing error to Log
            e.printStackTrace();
        }

        // Making HTTP Request
        try {
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();

                String resp = WebServiceUtil.convertStreamToString(instream);

                // Closing the input stream will trigger connection release

                instream.close();

                //Log.e("newlog", "resp----"+resp);
                return resp;
            }

            // writing response to log

        } catch (ClientProtocolException e) {
            // writing exception to log

            e.printStackTrace();
        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();

        }

        return null;
    }

    public static String getResponse(String url) {
        // TODO Auto-generated method stub
        HttpResponse response = null;
        String respString = "";
        try {

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            //Log.e("newlog", url);
            request.setURI(new URI(url));

            response = client.execute(request);

            respString = WebServiceUtil.convertStreamToString(response
                    .getEntity().getContent());

        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return respString;

    }
}
