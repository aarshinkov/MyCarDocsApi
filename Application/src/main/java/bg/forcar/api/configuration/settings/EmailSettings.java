package bg.forcar.api.configuration.settings;

import java.io.Serializable;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public class EmailSettings implements Serializable {

    private String host;
    private int port;
    private String protocol;
    private String sender;
    private String username;
    private String password;
//    private Integer delayCheck = 60000;
//    private Integer initialDelay = 20000;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
