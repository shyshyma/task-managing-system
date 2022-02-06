package org.itransition.taskmanager.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Logs incoming requests and outcoming responses for controllers
 */
@Slf4j
public class ControllerLoggingHandlerInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("Leaving controller with status code {}\n", response.getStatus());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String controllerName = handlerMethod.getBean().getClass().getSimpleName();
        String methodName = handlerMethod.getMethod().getName();
        log.info("[{}] {}, incoming request traversing into->[{}|{}]", request.getMethod(),
                request.getRequestURL().toString(), controllerName, methodName);
        return true;
    }
}
