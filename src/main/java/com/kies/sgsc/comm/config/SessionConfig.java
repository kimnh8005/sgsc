package com.kies.sgsc.comm.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="session")
public class SessionConfig {

    private List<String> skipUrl;
    private boolean isCheck = true;

    public List<String> getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(List<String> skipUrl) {
        this.skipUrl = skipUrl;
    }
    
    public boolean getCheck() {
        return isCheck;
    }

    public void setCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}
