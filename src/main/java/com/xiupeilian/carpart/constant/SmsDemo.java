package com.xiupeilian.carpart.constant;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;


public class SmsDemo {
    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIQvSPB5YICXD9", "W5SjRWDlYTABmUrNVLnugNCvX85Fm5");
        IAcsClient client = new DefaultAcsClient(profile);
        int code=  (int)((Math.random()*9+1)*100000);
        String str="{'la1234':"+code+"}";


        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers", "18738027780");
        request.putQueryParameter("SignName", "白芥子");
        request.putQueryParameter("TemplateCode", "SMS_172884042");
        request.putQueryParameter("TemplateParam", str);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
