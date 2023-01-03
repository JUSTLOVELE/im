package com.hyjk.im.server.env;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import io.minio.MinioClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangzl 2021.06.04
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
@Configuration
@MapperScan({"com.hyjk.im.server.mapper"})
public class Config {

    private final static Log _logger = LogFactory.getLog(Config.class);

    @Autowired
    private YmlProjectConfig _ymlProjectConfig;

    @Bean
    public MinioClient minioClient() {

        try {
            String minioUrl = "http://" + _ymlProjectConfig.getMinioip() + ":" + _ymlProjectConfig.getMinioport();
            MinioClient client = new MinioClient(minioUrl,
                    _ymlProjectConfig.getMinioaccesskey(),
                    _ymlProjectConfig.getMiniosecretkey());

            if(client.bucketExists(_ymlProjectConfig.getMiniobucketname())) {
                _logger.info("已创建桶");
            }else {
                client.makeBucket(_ymlProjectConfig.getMiniobucketname());
                _logger.info("成功创建桶");
            }

            return client;
        } catch (Exception e) {
            _logger.error("", e);
            return null;
        }
    }

    /**
     * 配置分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     *配置乐观锁插件
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

}
