package com.hyjk.im.server.env;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Description:YmlProjectConfig 读取projectconfig配置信息
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2019-11-06 
 * @version 1.00.00
 * @history:
 */
@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "projectconfig")    
public class YmlProjectConfig {
	/**3des的秘钥**/
	private String encryptKey;

	private String miniourl;

	private String minioaccesskey;

	private String miniosecretkey;

	private String miniobucketname;

	private String minioport;

	private String minioip;

	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	public String getMiniourl() {
		return miniourl;
	}

	public void setMiniourl(String miniourl) {
		this.miniourl = miniourl;
	}

	public String getMinioaccesskey() {
		return minioaccesskey;
	}

	public void setMinioaccesskey(String minioaccesskey) {
		this.minioaccesskey = minioaccesskey;
	}

	public String getMiniosecretkey() {
		return miniosecretkey;
	}

	public void setMiniosecretkey(String miniosecretkey) {
		this.miniosecretkey = miniosecretkey;
	}

	public String getMiniobucketname() {
		return miniobucketname;
	}

	public void setMiniobucketname(String miniobucketname) {
		this.miniobucketname = miniobucketname;
	}

	public String getMinioport() {
		return minioport;
	}

	public void setMinioport(String minioport) {
		this.minioport = minioport;
	}

	public String getMinioip() {
		return minioip;
	}

	public void setMinioip(String minioip) {
		this.minioip = minioip;
	}
}
