package com.guomin.security.provider.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.asymmetric.RSA;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guomin.security.api.dto.CheckToken;
import com.guomin.security.api.dto.GenerateToken;
import com.guomin.security.api.service.TokenService;
import com.guomin.security.api.vo.CheckTokenVo;
import com.guomin.security.api.vo.TokenVo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.JacksonSerializer;
import io.jsonwebtoken.security.WeakKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@com.alibaba.dubbo.config.annotation.Service(group = "security-group",version = "1.0")
@SuppressWarnings(value = {"deprecation","unchecked"})
public class TokenServiceImpl implements TokenService {
    private JwtParser jwtParser = Jwts.parser().setAllowedClockSkewSeconds(60*3);
    private String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCLnXnpBbczHbzbE2EdV476/nTLgCh+tZMribGtrxGK1dzx0D8aJmoTStqj7FpeZlxVeorP6dxwRN7IrFDU8DQFZO1q/TLVUr5gVKffyoH4C2yK+YyFxVg2ILKsFmapbz294dLrTQ4PTjfGAy+P7QCYmxMoSTUQEbBU+AG207tN8BKIcSS+G9wGKRR1Ii8lx7wKCrza+d7w9JBfIXNjc8XmGgSnz7O+ad+EteYQebcZ5BO7ZSTFhMSdeQ1MRj1YcMJP/Xamq4995dTDC1m5bg1oATRlgXAJjO+P+Ps505aTasjpzK/aVNEkdL43MA10eqjNg27jK5mS30lcJAWaTRBDAgMBAAECggEASp+IKrHAYtTxPZNVJA7JwIRDPwYoHtnEaM/3JSNe1/tJVgJ9x+diaR9oT5ekEbLahWGzUj/ocCWPgmj/52DgHUX91GKzSewpD2oQNICUCVVp1bpejp2VcZD2jx6GDugDczuBtf0Al9Ox4eeQA3ilCexRSAYhE46w1imdGPCAu73aGK5JpK6+72y7i/GQtYbeBrPv79i6RnqR56KIM33tKlB56qtp7/x6GVVB5YmlhGeegDj5dKLe7lqKtVkrHEn74lnBJeBGvu3RP991Ohu/ssbNLb6X94G7wiWkPjZhRFwKgy56MN4/QXmCj7OR652ZTPvhKzIUlx2JGulooIGOWQKBgQDeStGIoKGKG82u6kR20EYvaEg5Rn+EBHBgIYvxr+FnOSz8BC8hnY9wL7FIcEdBNk+WfuoVwfQ0CT9l5YB++1OZiIDxHndY8MYSaNfI1cl7HWNK6C4oKPaW/z+mHcKQw6UEpsqBIGyhPNAA+/A2B/y75ID5Fa9scLuOFMNDdTgq1wKBgQCgyTU22GAqp4aS6g/x2z4Rg2T2xh4u0TTmDZ/MPscqv65zoW6uLoRBcBPq4BErzhmjd32+PSiSYetRCJl3jQpj91YGiEYg2gRjeMu62zO4G5PR2cpr8VNIoH/fTzC46LS5i9epF7oSQAYOKUcJGpH1OQLw/zkaWYrXfhzj5RbkdQKBgEfD093OLhjOXRsJWs0Y9G+9tQN4tRZD8Ju4eNuwMo8GsHU0TJsHqRVI8G04gmiVRiCKHLylf1eFXMhizZcxQI4iua4ebd1y7h9nZbLbAk8S78lxJ+WjQ+j/wua4WOYvo4ja6CnD9A4h+e03aReogHmQjerci2PxCTwXi+qtX1LFAoGAcz1DQrf61ReVQJ86JmK5Ldw69RQydePEQk7phX5JSb5wzreZmIYGjNBDtkg6hmcFwT/XhA5ZkfFUs5OySxlvpQ6VPcFWYh1gn9roSljpzL8XR4UNrCgfRSKkA+3CQgWn4oT0YWF8H7B7ppK3MzM0AcqG7SxART6AYMsm8cA0lDECgYAuPz9iUAS1JWrImNTf079Ef20+5OLzNtd8VLuZcNHjX8uOBSZRX/k5YYSsXUcbh+Uy2p0gXcFVsi+EYGXReizRgFZBgzQpdBbZITFvs7QaisN1QkzTHO7u0MY/L93YW6lpxC+lP5fd/97jv108YWi/G8CJlJZzS+0BeN/AhsHCFA==";
    private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi5156QW3Mx282xNhHVeO+v50y4AofrWTK4mxra8RitXc8dA/GiZqE0rao+xaXmZcVXqKz+nccETeyKxQ1PA0BWTtav0y1VK+YFSn38qB+AtsivmMhcVYNiCyrBZmqW89veHS600OD043xgMvj+0AmJsTKEk1EBGwVPgBttO7TfASiHEkvhvcBikUdSIvJce8Cgq82vne8PSQXyFzY3PF5hoEp8+zvmnfhLXmEHm3GeQTu2UkxYTEnXkNTEY9WHDCT/12pquPfeXUwwtZuW4NaAE0ZYFwCYzvj/j7OdOWk2rI6cyv2lTRJHS+NzANdHqozYNu4yuZkt9JXCQFmk0QQwIDAQAB";
    private RSA rsa = new RSA(privateKey, publicKey);

    @Autowired
    ObjectMapper objMap;

    @Override
    public TokenVo generateToken(GenerateToken  record) throws Exception {
        Date date = new Date();
        JwtBuilder jwtBuilder = Jwts.builder()
                .setHeaderParam(JwsHeader.TYPE, JwsHeader.JWT_TYPE)
                .setIssuer("www.likepeng.cn")
                .serializeToJsonWith(new JacksonSerializer(objMap))
                .setSubject(record.getName())
                .setIssuedAt(date)
                .setNotBefore(date)
                .setId(record.getId())
                .signWith(rsa.getPrivateKey())
                .compressWith(CompressionCodecs.GZIP);
        String token;
        if (record.getIsMobile()){
            token  = jwtBuilder.compact();
        }
        else {
            DateTime offset = DateUtil.offset(date, DateField.SECOND, 30);
            token = jwtBuilder.setExpiration(offset).compact();
        }
        TokenVo tokenVo = new TokenVo();
        tokenVo.setToken(token);
        return tokenVo;
    }


    @Override
    public CheckTokenVo checkToken(CheckToken record) throws Exception {
        String token = record.getToken();
        return checkToken(token);
    }

    @Override
    public TokenVo freshToken(GenerateToken  record) throws Exception {
        return generateToken(record);
    }

    @Override
    public CheckTokenVo checkToken(String token) throws Exception {
        CheckTokenVo checkTokenVo = new CheckTokenVo();
        try {
            Jws<Claims> jws = jwtParser.setSigningKey(rsa.getPrivateKey())
                    .setAllowedClockSkewSeconds(30)
                    .parseClaimsJws(token);
            Claims body = jws.getBody();

            String issuer = body.getIssuer();
            if (!"www.likepeng.cn".equals(issuer)){
                throw new JwtException("Token发行人不对");
            }
        }
        catch (JwtException e){
            if (e instanceof WeakKeyException){
                checkTokenVo.setFlag(false);
                checkTokenVo.setMsg("RSA长度不够");
                checkTokenVo.setCode(-402);
                return checkTokenVo;
            }
            if (e instanceof UnsupportedJwtException){
                checkTokenVo.setFlag(false);
                checkTokenVo.setMsg("Token格式不支持");
                checkTokenVo.setCode(-402);
                return checkTokenVo;
            }
            if (e instanceof MalformedJwtException){
                checkTokenVo.setFlag(false);
                checkTokenVo.setMsg("Token未正确构造");
                checkTokenVo.setCode(-402);
                return checkTokenVo;
            }
            if (e instanceof RequiredTypeException){
                checkTokenVo.setFlag(false);
                checkTokenVo.setMsg("所需Token必要字段类型未给出");
                checkTokenVo.setCode(-402);
                return checkTokenVo;
            }
            if (e instanceof ExpiredJwtException ){
                checkTokenVo.setFlag(false);
                checkTokenVo.setMsg("Token失效");
                checkTokenVo.setCode(-402);
                return checkTokenVo;
            }
            if (e instanceof PrematureJwtException  ){
                checkTokenVo.setFlag(false);
                checkTokenVo.setMsg("Token失效");
                checkTokenVo.setCode(-402);
                return checkTokenVo;
            }
            if (e instanceof MissingClaimException){
                checkTokenVo.setFlag(false);
                checkTokenVo.setMsg("Token缺少声明");
                checkTokenVo.setCode(-402);
                return checkTokenVo;
            }
            if (e instanceof IncorrectClaimException){
                checkTokenVo.setFlag(false);
                checkTokenVo.setMsg("Token声明中缺少必要参数");
                checkTokenVo.setCode(-402);
                return checkTokenVo;
            }
            if (e instanceof CompressionException){
                checkTokenVo.setFlag(false);
                checkTokenVo.setMsg("Token压缩失败");
                checkTokenVo.setCode(-402);
                return checkTokenVo;
            }
            if (e instanceof SignatureException){
                checkTokenVo.setFlag(false);
                checkTokenVo.setMsg("非法Token签名数据");
                checkTokenVo.setCode(-402);
                return checkTokenVo;
            }
            else {
                checkTokenVo.setFlag(false);
                checkTokenVo.setMsg(e.getLocalizedMessage());
                checkTokenVo.setCode(-402);
                return checkTokenVo;
            }
        }
        return checkTokenVo;
    }
}
