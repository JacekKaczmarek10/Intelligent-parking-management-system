package pl.kaczmarek;



import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.ServletContextResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImagesController {

    @Value("${imageFilesPath}")
    private String imageFilesPath;

    @ResponseBody
    @RequestMapping(value = "/imagesX/{name}",
        method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Object> getImage(@PathVariable("name") String name) throws IOException {
            InputStream in = new FileInputStream(imageFilesPath + name);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE));
            return new ResponseEntity<Object>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
    }
}
