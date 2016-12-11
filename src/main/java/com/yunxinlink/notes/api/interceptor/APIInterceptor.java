package com.yunxinlink.notes.api.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yunxinlink.notes.api.annotation.TokenIgnore;
import com.yunxinlink.notes.api.dto.ActionResult;
import com.yunxinlink.notes.api.model.Token;
import com.yunxinlink.notes.api.util.Constant;
import com.yunxinlink.notes.api.util.SystemUtil;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * API接口的拦截器，主要用户token的验证
 * @author tiger
 * @date 2016年12月4日 上午1:20:18
 */
public class APIInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = LoggerFactory.getLogger(APIInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        
        String url = request.getRequestURI();
        
        logger.info("api interceptor request url:" + url);
        
        TokenIgnore tokenIgnore = handlerMethod.getMethodAnnotation(TokenIgnore.class);
        if (tokenIgnore != null) {	//直接放过
			return true;
		}
        
		final String tokenStr = request.getHeader("Authorization");
		if (StringUtils.isBlank(tokenStr)) {
			logger.info("api request token is empty");
			validateTokenFailed(response);
			return false;
		}
		Token token = SystemUtil.parseToken(tokenStr);
		if (token == null || token.isExpired()) {
			logger.info("api requst token is null or is expired");
			validateTokenFailed(response);
			return false;
		}
		//从本地缓存中查找token
		boolean isTokenExists = isTokenExists(token);
		if (!isTokenExists) {	//内存中的token不存在 
			logger.info("api requst local token is null or is expired");
			validateTokenFailed(response);
			return false;
		}
		request.setAttribute(Constant.KEY_TOKEN_SUBJECT, token.getSubject());
		return super.preHandle(request, response, handler);
	}
	
	/**
	 * 判断内存中的token是否存在，true：存在
	 * @param token
	 * @return
	 */
	private boolean isTokenExists(Token token) {
		CacheManager cacheManager = CacheManager.getInstance();
		Cache cache = cacheManager.getCache(Constant.DEFAULT_TOKEN_CACHE);
		Element element = null;
		if (cache != null) {
			 element = cache.get(token.getId());
		}
		return element != null;
	}
	
	/**
	 * token校验失败
	 * @param response
	 */
	private void validateTokenFailed(HttpServletResponse response) {
		response.setStatus(401);  
        response.setHeader("Cache-Control", "no-store");  
        response.setDateHeader("Expires", 0);  
        response.setHeader("WWW-authenticate", "Basic realm=\"\"");
        
        ActionResult<Void> actionResult = new ActionResult<>();
        actionResult.setResultCode(ActionResult.RESULT_TOKEN_UNAUTHOZIED);
        actionResult.setReason("权限校验失败，无法进行访问");
        
        String json = SystemUtil.obj2json(actionResult);
        outPrint(json, response);
	}
	
	/**
     * 初始化out对象,用于输出相应格式文档到前台
     */
    public void outPrint(String s, HttpServletResponse response) {
        PrintWriter out;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;utf-8");
            out = response.getWriter();
            // 输出到前台
            out.print(s);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
