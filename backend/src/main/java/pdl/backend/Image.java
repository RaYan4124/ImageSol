package pdl.backend;
import java.io.File;
import java.io.FileInputStream;

import org.springframework.http.MediaType;

public class Image {
  private static Long count = Long.valueOf(0);
  private Long id;
  private String name;
  private byte[] data;
  private String type;

  //for upload, ID autot generate
  public Image(final String name, final byte[] data, String type) {
    id = ++count;
    this.name = name;
    this.data = data;
    this.type = type;
  }

  //for download from DB
  public Image(Long id, String name, String type, byte[] data){
    this.id =id;
    this.name = name;
    this.type = type;
    this.data = data;

    //count updating
    if(id > count){
      count = id;
    }
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public byte[] getData() {
    return data;
  }

  public String getType(){
    return type;
  }
}
