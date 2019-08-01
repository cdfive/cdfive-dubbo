package com.cdfive.ejob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cdfive
 */
public class TestJob implements SimpleJob  {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println(sdf.format(new Date()) + "=>" + shardingContext.getJobName() + "," + shardingContext.getShardingItem()
            + "," + shardingContext.getShardingTotalCount());
    }
}
