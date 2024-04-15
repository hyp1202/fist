// This file is auto-generated, don't edit it. Thanks.
package aiguquan.dingding.util;

import cache.DataCache;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.aliyun.tea.*;

public class Sample {

    /**
     * 使用 Token 初始化账号Client
     * @return Client
     * @throws Exception
     */
    public com.aliyun.dingtalkoauth2_1_0.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkoauth2_1_0.Client(config);
    }

    public String getAccessToken(String appKey, String appSecret) throws Exception {
        JSONObject accessTokenJson = DataCache.accessTokenMap.get(appKey);
        if (accessTokenJson != null) {
            Long time = (Long) accessTokenJson.get("createTime");
            Long now = System.currentTimeMillis();
            if (now - time < 700000) {
                return accessTokenJson.get("accessToken").toString();
            }
        }
        String accessToken = "";
        com.aliyun.dingtalkoauth2_1_0.Client client = createClient();
        com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest getAccessTokenRequest = new com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest()
                .setAppKey(appKey)
                .setAppSecret(appSecret);
        try {
            GetAccessTokenResponse getAccessTokenResponse = client.getAccessToken(getAccessTokenRequest);
            System.out.println(JSON.toJSONString(getAccessTokenResponse));
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(getAccessTokenResponse));
            JSONObject body = jsonObject.getJSONObject("body");
            accessToken = body.getString("accessToken");
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                System.out.println("code:" + err.code);
                System.out.println("message:" + err.message);
            }

        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                System.out.println("code:" + err.code);
                System.out.println("message:" + err.message);
            }

        }
        JSONObject  jsonObject = new JSONObject();
        jsonObject.put("createTime",System.currentTimeMillis());
        jsonObject.put("accessToken",accessToken);
        DataCache.accessTokenMap.put(appKey,jsonObject);
        return accessToken;
    }

//    public static void main(String[] args_) throws Exception {
//        java.util.List<String> args = java.util.Arrays.asList(args_);
//        com.aliyun.dingtalkrobot_1_0.Client client = createClient1();
//        com.aliyun.dingtalkrobot_1_0.models.OrgGroupSendHeaders orgGroupSendHeaders = new com.aliyun.dingtalkrobot_1_0.models.OrgGroupSendHeaders();
//        orgGroupSendHeaders.xAcsDingtalkAccessToken = getAccessToken("ding85qp3lkrffslpl6p","nbXShzFM8IQYxG3RkMuBDXMZbTqySa70PxHrXYdN-MWv1wsW7uoyDEHko9W62c9B");
//        com.aliyun.dingtalkrobot_1_0.models.OrgGroupSendRequest orgGroupSendRequest = new com.aliyun.dingtalkrobot_1_0.models.OrgGroupSendRequest()
//                .setMsgParam("{\n" +
//                        "   \"mediaId\":\"@lArPDf0i9xqtWmDOPAot5s4VHD8p\",\n" +
//                        "   \"fileName\":\"20200804-申万宏源-2014年A股复盘.pdf\",\n" +
//                        "   \"fileType\":\"pdf\",\n" +
//                        "}")
//                .setMsgKey("sampleFile")
//                .setOpenConversationId("cid6KeBBLoveMJOGXoYKF5x7EeiodoA==")
//                .setRobotCode("dingue4kfzdxbynxxxxxx")
//                .setCoolAppCode("COOLAPP-1-10182EEDD1AC0BA600D9000J");
//        try {
//            client.orgGroupSendWithOptions(orgGroupSendRequest, orgGroupSendHeaders, new com.aliyun.teautil.models.RuntimeOptions());
//        } catch (TeaException err) {
//            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
//                // err 中含有 code 和 message 属性，可帮助开发定位问题
//            }
//
//        } catch (Exception _err) {
//            TeaException err = new TeaException(_err.getMessage(), _err);
//            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
//                // err 中含有 code 和 message 属性，可帮助开发定位问题
//            }
//
//        }
//    }
}