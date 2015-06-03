package com.dangdang.digital.service.nearby.impl.monitor;

/**
 * 
 * Description: 线程池配置
 * All Rights Reserved.
 * @version 1.0  2014年7月21日 下午6:15:22  by 林永奇（linyongqi@dangdang.com）创建
 */
public class PoolConfig {


    private int corePoolSize = 10;

    private int maxPoolSize = 20;

    private int queueSize = 10 * 1000;

    private String poolName = "TP";

    //队列长度告警阀值
    private int qlthreshold = 50;

    public void setQlthreshold(int qlthreshold) {
        this.qlthreshold = qlthreshold;
    }

    public int getQlthreshold() {
        return qlthreshold;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoolConfig that = (PoolConfig) o;

        if (corePoolSize != that.corePoolSize) return false;
        if (maxPoolSize != that.maxPoolSize) return false;
        if (queueSize != that.queueSize) return false;
        if (poolName != null ? !poolName.equals(that.poolName) : that.poolName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = corePoolSize;
        result = 31 * result + maxPoolSize;
        result = 31 * result + queueSize;
        result = 31 * result + (poolName != null ? poolName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PoolConfig{");
        sb.append("poolName='").append(poolName).append('\'');
        sb.append(", queueSize=").append(queueSize);
        sb.append(", maxPoolSize=").append(maxPoolSize);
        sb.append(", corePoolSize=").append(corePoolSize);
        sb.append('}');
        return sb.toString();
    }
}
