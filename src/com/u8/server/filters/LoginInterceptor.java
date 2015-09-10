package com.u8.server.filters;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.u8.server.common.UActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by ant on 2015/8/29.
 */
public class LoginInterceptor extends MethodFilterInterceptor {


    @Override
    protected String doIntercept(ActionInvocation actionInvocation) throws Exception {

        HttpServletRequest request= ServletActionContext.getRequest();
        String path=request.getRequestURI();
        if(!path.startsWith("/admin")){

            return actionInvocation.invoke();
        }

        UActionSupport action = (UActionSupport)actionInvocation.getAction();
        Map session = action.getSession();

        if(session.containsKey("adminName")){
            return actionInvocation.invoke();
        }else{
            return Action.LOGIN;
        }
    }
}
