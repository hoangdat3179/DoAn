package vn.techmaster.storyreadingwebsite.exception;

import lombok.extern.log4j.Log4j2;

@Log4j2 // ghi lại log
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
        // Ghi vào log mỗi khi ném ra exception
        log.info(message);
    }

}
