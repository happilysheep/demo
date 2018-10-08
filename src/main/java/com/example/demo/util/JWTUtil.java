package com.example.demo.util;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.domain.User;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangzhijun
 * @create 2018-09-29 15:53
 * @description
 */

public class JWTUtil {

    private static final String SECRET="aaaaaaaaaaaaaa*865468*921402454";

    private static final String EXP="exp";

    private static final String PAYLOAD="payload";
    //2.2.0
    /*//使用SECRET压缩生成TOKE  https://blog.csdn.net/change_on/article/details/71191894
    public static<T> String sign(T object,long maxAge){
        try {
            JWTSigner signer = new JWTSigner(SECRET);
            HashMap<String, Object> claims = new HashMap<>();
            String s = JSON.toJSONString(object);
            claims.put(EXP, s);
            claims.put(PAYLOAD, System.currentTimeMillis() + maxAge);
            String token = signer.sign(claims);
            return token;
        }catch(Exception e){
            return e.getMessage();
        }
    }
    //使用SECRET解压TOKE
    public static Object unSign(String token){
        JWTVerifier jwtVerifier = new JWTVerifier(SECRET);
        try {
            Map<String, Object> claims = jwtVerifier.verify(token);
            if(claims.containsKey(EXP)&&claims.containsKey(PAYLOAD)){
                long time = (long)claims.get(PAYLOAD);
                long currentTimeMillis = System.currentTimeMillis();
                if(time>currentTimeMillis){
                    String userStr = (String)claims.get(EXP);
                    return  JSONObject.parseObject(userStr);
                }else{
                    return null;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (JWTVerifyException e) {
            e.printStackTrace();
        }
        return null;
    }*/


    //3.4.0
    public static<T> String sign(T t){
        User user = (User)t;
        String token = JWT.create().
                withAudience(user.getId() + "", user.getName()).
                sign(Algorithm.HMAC256(user.getPass()));
        return token;
    }

    public static Object unSign(String token){
        List<String> decode = JWT.decode(token).getAudience();
        JWTVerifier jwtVerifier  = JWT.require(Algorithm.HMAC256("aaaaa")).build();
        try{
            DecodedJWT verify = jwtVerifier.verify(token);
            System.out.println("token = [" + JSON.toJSONString(verify) + "]");
        }catch(Exception e){
            System.out.println( e.getMessage() );
        }
        return decode;
    }
}
