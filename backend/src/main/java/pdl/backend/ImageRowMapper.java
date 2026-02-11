package pdl.backend;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class ImageRowMapper implements RowMapper<Image>{

    private static final String REPO_PATH = "src/main/resources/images/";
    @Override
    public Image mapRow(ResultSet rs, int rowNum) throws SQLException{
        long id = rs.getLong("id");
        System.out.println("id recup"+ id);
        String name = rs.getString("name");
        System.out.println("name recup" + name);
        String type = rs.getString("type");

        byte[] data =null;
        try{
            File file = new File(REPO_PATH + name);
            if(file.exists()){
                data = Files.readAllBytes(file.toPath());
            }else{
                System.err.println("fichier non existant");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("creation image : id = " + id + "name = " + name);
        return new Image(id,name, type, data);
    }
}
