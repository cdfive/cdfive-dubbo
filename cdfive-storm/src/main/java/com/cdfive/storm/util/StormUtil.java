package com.cdfive.storm.util;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.utils.Utils;

/**
 * @author cdfive
 */
public abstract class StormUtil {

    /**
     * 本地运行拓扑
     * @param topologyName 拓扑名称
     * @param config 配置
     * @param topology 拓扑
     */
    public static void runLocal(String topologyName, Config config, StormTopology topology) {
        runLocal(topologyName, config, topology, null);
    }

    /**
     * 本地运行拓扑
     * @param topologyName 拓扑名称
     * @param config 配置
     * @param topology 拓扑
     * @param sleepTime 休眠时间
     */
    public static void runLocal(String topologyName, Config config, StormTopology topology, Long sleepTime) {
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(topologyName, config, topology);
        if (sleepTime != null && sleepTime > 0) {
            Utils.sleep(sleepTime);
            if (cluster != null) {
                cluster.killTopology(topologyName);
                cluster.shutdown();
            }
        }
    }

    /**
     * 生产运行拓扑，提交到集群执行
     * @param topologyName 拓扑名称
     * @param config 配置
     * @param topology 拓扑
     * @param jmxPort jmx端口
     * @param jvm jvm参数
     * @throws AlreadyAliveException
     * @throws InvalidTopologyException
     * @throws AuthorizationException
     */
    public static void runCluster(String topologyName, Config config, StormTopology topology, Integer jmxPort, String jvm) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {
        runCluster(topologyName, config, topology, null, jmxPort, jvm);
    }

    /**
     * 生产运行拓扑，提交到集群执行
     * @param topologyName 拓扑名称
     * @param config 配置
     * @param topology 拓扑
     * @param workerNum 进程数
     * @param jmxPort jmx端口
     * @param jvm jvm参数
     * @throws AlreadyAliveException
     * @throws InvalidTopologyException
     * @throws AuthorizationException
     */
    public static void runCluster(String topologyName, Config config, StormTopology topology, Integer workerNum, Integer jmxPort, String jvm) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {
        runCluster(topologyName, config, topology, workerNum, 120, 2000, jmxPort, jvm);
    }

    /**
     * 生产运行拓扑，提交到集群执行
     * @param topologyName 拓扑名称
     * @param config 配置
     * @param topology 拓扑
     * @param workerNum 进程数
     * @param messageTimeoutSecs 消息超时(秒)
     * @param maxSpoutPending spout流控数
     * @param jmxPort jmx端口
     * @param jvm jvm参数
     * @throws AlreadyAliveException
     * @throws InvalidTopologyException
     * @throws AuthorizationException
     */
    public static void runCluster(String topologyName, Config config, StormTopology topology, Integer workerNum, Integer messageTimeoutSecs
                                  ,Integer maxSpoutPending, Integer jmxPort, String jvm) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {
        if (workerNum != null && workerNum > 0) {
            config.setNumWorkers(workerNum);
        }
        config.setMessageTimeoutSecs(messageTimeoutSecs);
        config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, maxSpoutPending);
        config.put(Config.TOPOLOGY_WORKER_CHILDOPTS, String.format("-Dcom.sun.management.jmxremote.port=%s -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false " +
                "-Dcom.sun.management.jmxremote.rmi.port=%s -Ddubbo.registry.file=/home/newad/dubbo-registry-%s.cache%s", jmxPort, jmxPort, topologyName, jvm));
        StormSubmitter.submitTopology(topologyName, config, topology);
    }

    /***
     * 生成storm启动脚本
     * @param jarPath 工程打包的jar路径
     * @param topologyClass 拓扑完整类名
     * @param topologyName 拓扑名称
     * @param workerNum 进程数
     * @param spoutQueueName spout监听队列名称
     * @param messageTimeoutSecs 消息超时(秒)
     * @param maxSpoutPending spout流控
     * @param parallNums 并行度，逗号分隔
     * @param jmxPort jmx端口号
     * @param jvm jvm参数
     * @return 脚本字符串
     */
    public static String generateStartScript(String jarPath, String topologyClass, String topologyName, Integer workerNum, String spoutQueueName
                , Integer messageTimeoutSecs, Integer maxSpoutPending, String parallNums, Integer jmxPort, String jvm) {
        StringBuilder script = new StringBuilder();
        script.append("storm trace ").append(jarPath);
        script.append(" ").append(topologyClass);
        script.append(" ").append(topologyName);
        script.append(" ").append(workerNum);
        script.append(" ").append(spoutQueueName);
        script.append(" ").append(messageTimeoutSecs);
        script.append(" ").append(maxSpoutPending);
        script.append(" ").append(parallNums);
        script.append(" ").append(jmxPort);
        script.append(" ").append(jvm);
        return script.toString();
    }
}
