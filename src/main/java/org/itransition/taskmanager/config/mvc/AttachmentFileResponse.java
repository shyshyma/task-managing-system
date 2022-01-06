package org.itransition.taskmanager.config.mvc;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Represents an HTTP response file,
 * returns file to the client side
 *
 * @see AttachmentFileResponseMethodReturnHandler
 */
@Builder
@Getter
@Setter
public class AttachmentFileResponse {

    private byte[] content;
    private String name;
    private HttpStatus httpStatus;
}
