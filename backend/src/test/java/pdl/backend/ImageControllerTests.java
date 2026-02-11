package pdl.backend;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class ImageControllerTests {

	@MockitoBean
	private ImageDao imageDAO;
	@MockitoBean
	private Image image;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getImageShouldReturnSuccess() throws Exception {
		when(imageDAO.retrieve(0)).thenReturn(Optional.ofNullable(image));
		this.mockMvc.perform(get("/images/0")).andExpect(status().isOk());
		verify(imageDAO).retrieve(0);
	}

	@Test
	public void getImageShouldReturnNotFound() throws Exception {
		when(imageDAO.retrieve(0)).thenReturn(Optional.ofNullable(null));
		this.mockMvc.perform(get("/images/0")).andExpect(status().isNotFound());
		verify(imageDAO).retrieve(0); //verifi que denpdant l'execution du teste cette methode a etet appelé et avec ces parametres
	}

	@Test
	public void addImageShouldReturnSuccess() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file","test-img.jpg", "image/jpeg", "test".getBytes());
		this.mockMvc.perform(multipart("/images").file(file)).andExpect(status().isCreated()).andExpect(header().exists("Location"));
		verify(imageDAO).create(any(Image.class));
	}

	@Test
	public void addImageShouldReturnUnsupportedMediaType() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file","test-img.svg", "image/svg", "test".getBytes());
		this.mockMvc.perform(multipart("/images").file(file)).andExpect(status().isUnsupportedMediaType());
		verify(imageDAO, never()).create(any(Image.class));
	}

	@Test
	public void deleteImagesShouldReturnMethodNotAllowed() throws Exception {
		this.mockMvc.perform(delete("/images")).andExpect(status().isMethodNotAllowed());
		verify(imageDAO, never()).delete(any());

	}

	@Test
	public void deleteImageShouldReturnNotFound() throws Exception {
		when(imageDAO.retrieve(0)).thenReturn(Optional.empty());
		this.mockMvc.perform(delete("/images/0")).andExpect(status().isNotFound());
		verify(imageDAO).retrieve(0);
		verify(imageDAO, never()).delete(any());
	}

	@Test
	public void deleteImageShouldReturnSuccess() throws Exception {
		when(imageDAO.retrieve(0)).thenReturn(Optional.ofNullable(image));
		this.mockMvc.perform(delete("/images/0")).andExpect(status().isNoContent());
		verify(imageDAO).retrieve(0);
		verify(imageDAO).delete(image);
	}
	
	@Test
	public void getImageListShouldReturnSuccess() throws Exception {
		ArrayList<Image> imgs = new ArrayList<Image>();
		imgs.add(new Image("test1.jpg", null, "jpg"));
		imgs.add(new Image("test2.jpg", null, "jpg"));

		when(imageDAO.retrieveAll()).thenReturn(imgs);
		this.mockMvc.perform(get("/images")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())  //$ => racine du json, ici c'est imgs
								.andExpect(jsonPath("$.length()").value(2))
								.andExpect(jsonPath("$[0].name").value("test1.jpg"))
								.andExpect(jsonPath("$[1].name").value("test2.jpg"));
		verify(imageDAO).retrieveAll();
	}
}
