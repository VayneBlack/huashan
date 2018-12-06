package cn.likepeng.tm.redis;

public class CustomRedisProperties {

    private String host;

    private Integer port;

    private String password;

    private Integer dataBase = 0;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDataBase() {
        return dataBase;
    }

    public void setDataBase(Integer dataBase) {
        this.dataBase = dataBase;
    }
}
