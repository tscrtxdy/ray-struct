package com.ray.common.log;

import ch.qos.logback.core.PropertyDefinerBase;
import com.ray.common.helper.ServerConstants;

/**
 * @author chengrui
 * @date 2022/10/9 11:22:39
 */
public class IPLogPropertyDefiner extends PropertyDefinerBase {
    @Override
    public String getPropertyValue() {
        return ServerConstants.SERVER_IP_ADDRESS;
    }
}