package com.hyjk.im.server.core;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyjk.im.common.utils.CommonConstant;
import com.hyjk.im.common.utils.CommonResult;
import com.hyjk.im.server.component.im.IMTextHandler;
import com.hyjk.im.server.util.Constant;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2019-11-06
 * @version 1.00.00
 * @history:
 */
public abstract class Base {

    private final static Log _logger = LogFactory.getLog(Base.class);

    public void writeToChannel(String userOpId, CommonResult commonResult) {

        Channel channel = IMTextHandler.userChannels.get(userOpId);

        if(channel != null) {
            channel.writeAndFlush(new TextWebSocketFrame(getJSON(commonResult)));
        }
    }

    public void writeToChannel(Channel channel, CommonResult commonResult) {
        channel.writeAndFlush(new TextWebSocketFrame(getJSON(commonResult)));
    }

    public void renderJson(HttpServletResponse httpServletResponse, String json) {

        httpServletResponse.setContentType("application/json; charset=utf-8");

        try {

            _logger.debug(json);
            httpServletResponse.getWriter().print(json);
        } catch (Exception e) {

            _logger.debug(e.getMessage());
        }

        json = null;
    }

    public CommonResult<List> getResult() {

        CommonResult<List> result = new CommonResult<List>();
        result.setCode(CommonConstant.Status.SUCCESS_CODE);
        result.setSuccess(true);
        result.setMessage(CommonConstant.SUCCESS_SAVE_MSG);
        return result;
    }


    public String covertEndTime(String endTime) {

        if(StringUtils.isEmpty(endTime)) {
            return null;
        }

        endTime = endTime.substring(0, 10);
        return endTime + " 23:59:59";
    }

    public void renderObject(HttpServletResponse httpServletResponse,Object result) {
        renderJson(httpServletResponse,getJSON(result));
    }

    @SuppressWarnings("rawtypes")
    public void clearMap(Map map){
        map.clear();
        map = null;
    }

    public String renderError(String message) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(CommonConstant.Key.CODE, CommonConstant.Status.FAILURE_CODE);
        map.put(CommonConstant.Key.SUCCESS, false);
        map.put(Constant.Key.MESSAGE, message);
        List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
        map.put(CommonConstant.Key.DATAS, datas);

        return getJSON(map);
    }

    public String renderResponse(int code, boolean success, String type) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(CommonConstant.Key.CODE, code);
        map.put(CommonConstant.Key.SUCCESS, success);
        List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
        map.put(CommonConstant.Key.DATAS, datas);

        Map<String, Object> p = new HashMap<String, Object>();
        p.put(Constant.Key.TYPE, type);
        datas.add(p);

        return getJSON(map);
    }

    public String getJSON(Object obj) {

        ObjectMapper mapper = new ObjectMapper();
        String msg = "";
        try {
            msg = mapper.writeValueAsString(obj);
        } catch (Exception e) {
            _logger.error("", e);
        }
        _logger.info(msg);
        return msg ;
    }
}
