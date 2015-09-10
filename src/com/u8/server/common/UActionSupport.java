package com.u8.server.common;

import com.opensymphony.xwork2.ActionSupport;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

/**
 * 所有Action继承该类
 */
public class UActionSupport extends ActionSupport
        implements ServletRequestAware, SessionAware, ServletResponseAware
{
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected Map session;
    protected String scripts;
    private final Log logger = LogFactory.getLog(getClass());

    protected void render(String content, String contentType)
    {
        try
        {
            HttpServletResponse localHttpServletResponse = ServletActionContext.getResponse();
            if (localHttpServletResponse != null)
            {
                localHttpServletResponse.setHeader("Pragma", "No-cache");
                localHttpServletResponse.setHeader("Cache-Control", "no-cache");
                localHttpServletResponse.setDateHeader("Expires", 0L);
                localHttpServletResponse.setContentType(contentType);
                localHttpServletResponse.getWriter().write(content);
            }
        }
        catch (IOException localIOException)
        {
            this.logger.error(localIOException.getMessage(), localIOException);
        }
    }

    protected boolean isPost()
    {
        return "POST".equals(this.request.getMethod());
    }

    protected void renderText(String content)
    {
        render(content, "text/plain; charset=UTF-8");
    }

    protected void renderJson(String content)
    {
        render(content, "text/json; charset=UTF-8");
    }

    protected void renderHtml(String content)
    {
        render(content, "text/html; charset=UTF-8");
    }

    protected void renderXml(String content)
    {
        render(content, "text/xml; charset=UTF-8");
    }

    protected void putIntoSession(String key, Object value)
    {
        this.session.put(key, value);
    }

    public void setServletRequest(HttpServletRequest req)
    {
        this.request = req;
    }

    public void setServletResponse(HttpServletResponse response)
    {
        this.response = response;
    }

    public void setSession(Map<String, Object> session)
    {
        this.session = session;
    }

    public Map getSession()
    {
        return this.session;
    }

    public String getScripts()
    {
        return this.scripts;
    }

    public void setScripts(String scripts)
    {
        this.scripts = scripts;
    }
}
