package org.itransition.todolist.config.mvc;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;

public class AttachmentFileResponseMethodReturnHandler implements HandlerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.getParameterType().equals(AttachmentFileResponse.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        AttachmentFileResponse attachmentFileResponse = (AttachmentFileResponse) returnValue;

        HttpServletResponse servletResponse = webRequest.getNativeResponse(HttpServletResponse.class);
        assert servletResponse != null;
        servletResponse.setHeader("Content-Disposition", "attachment; filename=" + attachmentFileResponse.getName());
        servletResponse.getOutputStream().write(attachmentFileResponse.getContent());
        servletResponse.setStatus(attachmentFileResponse.getHttpStatus().value());
        mavContainer.setRequestHandled(true);
    }
}