package com.baizhi.zw.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class AliyunSendSmsNewUtils {

    public static void sendMsg() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4Fft2pfxzwE2P2qLez3N", "vfpqevOMfwxGPEX7PRSJ9T5a7FoZofs");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "18740690154");
        request.putQueryParameter("SignName", "风行");
        request.putQueryParameter("TemplateCode", "SMS_186953611");
        request.putQueryParameter("TemplateParam", "{\"code\":\"888888\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            String data = response.getData();
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendMsg();
    }
}
