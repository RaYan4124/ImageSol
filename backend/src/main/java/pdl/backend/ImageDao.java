package pdl.backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

@Repository
public class ImageDao implements InitializingBean, Dao<Image>{

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final Map<Long, Image> images = new HashMap<>();

  private ImageRowMapper irm = new ImageRowMapper();

  /*public ImageDao() {
    // placez une image test.jpg dans le dossier "src/main/resources" du projet
    final ClassPathResource imgFile = new ClassPathResource("images/test.jpg");
    byte[] fileContent;
    try {
      fileContent = Files.readAllBytes(imgFile.getFile().toPath());
      Image img = new Image("logo.jpg", fileContent, "jpg");
      images.put(img.getId(), img);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }*/

  //@Override
  public void afterPropertiesSet() throws Exception {
    //jdbcTemplate.execute("DROP TABLE IF EXISTS images");
    System.out.println("Chemin actuel d'exécution : " + System.getProperty("user.dir"));
    jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS images (id BIGINT PRIMARY KEY, name VARCHAR(255), type VARCHAR(255))");
    System.out.println("avant select");
    List<Image> l = jdbcTemplate.query("SELECT * FROM images", irm);
    System.out.println("apres select");
    for (Image img : l) {
      System.out.println("imageg : " + img.getName() + "  type : " + img.getType());
      images.put(img.getId(), img);
      System.out.println("apres le put");
    }
    System.out.println(images.size());
  }

  @Override
  public Optional<Image> retrieve(final long id) {
    return Optional.ofNullable(images.get(id));
  }

  @Override
  public List<Image> retrieveAll() {
    /*long n = images.size();
    ArrayList<Image> imgs = new ArrayList<Image>();
    for(long i = 0; i<n; i++){
      imgs.add(images.get(i));
    }
    return imgs;*/
    return new ArrayList<>(images.values());
  }

  @Override
  public void create(final Image img) {
    jdbcTemplate.update("INSERT INTO images (id, name, type) VALUES (?, ?, ?);",img.getId(), img.getName(), img.getType());
    File f = new File("src/main/resources/images/" + img.getName());
    try (FileOutputStream fos = new FileOutputStream(f)){
      fos.write(img.getData());
    }catch (Exception e) {
      e.printStackTrace();
    }
    images.put(img.getId(), img);
    System.out.println("puted");
  }

  @Override
  public void update(final Image img, final String[] params) {
    // Not used
  }

  @Override
  public void delete(final Image img) {
    if(images.containsValue(img)){
      images.remove(img.getId());
    }
  }
}

