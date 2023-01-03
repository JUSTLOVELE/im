package com.hyjk.im.server.util;

import com.hyjk.im.common.utils.DateUtil;
import com.hyjk.im.server.env.YmlProjectConfig;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2020-06-08
 * @version 1.00.00
 * @history:
 */
@Service
public class MinioService {

    private final static Log _logger = LogFactory.getLog(MinioService.class);

    @Autowired
    private MinioClient _minioClient;

    @Autowired
    private YmlProjectConfig _ymlProjectConfig;

    /**
     * 得到文件URL
     * @param fileName
     * @return
     */
    public String getFileUrl(String fileName) {
        return _ymlProjectConfig.getMiniourl() + "/" + _ymlProjectConfig.getMiniobucketname() + "/" + fileName;
    }

    /**
     * 删除文件
     * @param fileName
     */
    public void deleteFile(String fileName) {

        try {
            _minioClient.removeObject(_ymlProjectConfig.getMiniobucketname(), fileName);
        } catch (Exception e) {
            _logger.error("", e);
        }
    }

    /**
     *
     * @param orgId
     * @param doctorOpId
     * @param fileName:文件名,带后缀
     * @param contentType
     * @param inputStream
     * @return
     */
    public String uploadFile(String orgId, String doctorOpId, String fileName, String contentType, InputStream inputStream) {

        String date = DateUtil.toString(new Date(), DateUtil.YMD);
        String path = orgId + "/" + doctorOpId + "/" + date + "/" + fileName;
        return uploadFile(inputStream, path, contentType);
    }

    /**
     * 上传文件
     * @param inputStream
     * @param fileName
     * @return
     */
    private String uploadFile(InputStream inputStream, String fileName, String contentType) {

        try {

            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", contentType);
            PutObjectOptions options = new PutObjectOptions(inputStream.available(), -1);
            options.setHeaders(headers);
            _minioClient.putObject(_ymlProjectConfig.getMiniobucketname(), fileName, inputStream, options);
            String url = _ymlProjectConfig.getMiniourl() + "/" + _ymlProjectConfig.getMiniobucketname() + "/" + fileName;
            return url;
            //return _minioClient.getObjectUrl(_ymlProjectConfig.getMiniobucketname(), fileName);
        } catch (Exception e) {
            _logger.error("", e);
            return null;
        }

    }

}
