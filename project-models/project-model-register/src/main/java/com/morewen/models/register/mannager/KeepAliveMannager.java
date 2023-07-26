package com.morewen.models.register.mannager;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Wangbw
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeepAliveMannager {
    /**
     *
     */
    private String containerId;
    /**
     *
     */
    Date lastKeepAliveTime;
    /**
     * 超时时间
     */
    private Integer timeout;
    public KeepAliveMannager(Date lastKeepAliveTime, Integer timeout) {
        this.containerId = UUID.fastUUID().toString();
        this.lastKeepAliveTime = lastKeepAliveTime;
        this.timeout = timeout;
    }

    public boolean isTimeout() {
        DateTime dateTime = new DateTime(lastKeepAliveTime);
        dateTime.offset(DateField.SECOND, timeout);
        return dateTime.isBefore(new Date());
    }

    public void updateLastKeepAliveTime() {
        this.lastKeepAliveTime = new Date();
    }
}
