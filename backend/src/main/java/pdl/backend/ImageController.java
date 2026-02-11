package pdl.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ImageController {

  @Autowired
  private ObjectMapper mapper;

  private final ImageDao imageDao;

  public ImageController(ImageDao imageDao) {
    this.imageDao = imageDao;
  }

  @RequestMapping(value = "/images/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
  public ResponseEntity<?> getImage(@PathVariable long id) {
    Optional<Image> img = imageDao.retrieve(id);
    if (img.isPresent()) {
      return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(img.get().getData());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }

  @RequestMapping(value = "/images/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteImage(@PathVariable long id) {
    Optional<Image> img = imageDao.retrieve(id);

    if (img.isPresent()) {
      imageDao.delete(img.get());
      return ResponseEntity.noContent().build(); // ResponseEntity.noContent() renvoie pas un objet ResponseEntity mais
                                                 // un objet intermediere "constructeur", .build() construit l'objet
                                                 // ResponseEntity ensuite.
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(value = "/images", method = RequestMethod.POST)
  public ResponseEntity<?> addImage(@RequestParam MultipartFile file,
      RedirectAttributes redirectAttributes) {
    if(!MediaType.IMAGE_JPEG_VALUE.equals(file.getContentType())){
      return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
    try {
      byte[] bytes = file.getBytes();
      Image img = new Image(file.getOriginalFilename(), bytes, "jpg");
      imageDao.create(img);
      return ResponseEntity
          .created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(img.getId()).toUri())
          .build();
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/images", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
  @ResponseBody
  public ArrayNode getImageList() {
    ArrayNode nodes = mapper.createArrayNode();
    List<Image> imgs = imageDao.retrieveAll();
    ObjectNode squelt;
    for (Image img : imgs) {
      if(img == null){
        System.out.println("image null detecté");
        continue;
      }
      squelt = mapper.createObjectNode();
      squelt.put("name", img.getName());
      squelt.put("id", img.getId());
      squelt.put("url",
          ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(img.getId()).toUriString());
      nodes.add(squelt);
    }
    return nodes;
  }
}
