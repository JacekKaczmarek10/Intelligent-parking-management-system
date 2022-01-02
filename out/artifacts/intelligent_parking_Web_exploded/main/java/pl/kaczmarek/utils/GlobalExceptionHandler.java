package pl.kaczmarek.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxUploadException(MaxUploadSizeExceededException e, HttpServletRequest request, HttpServletResponse response){
//        ModelAndView mav = new ModelAndView();
//        boolean isJson = request.getRequestURL().toString().contains(".json");
//        if (isJson) {
//            mav.setView(new MappingJackson2JsonView());
//            mav.addObject("result", "nok");
//        }
//        else mav.setViewName("uploadError");
//        return mav;
        return ResponseEntity.status(400).body("{\"message\":\"file_size_is_too_big\"}");
    }
}