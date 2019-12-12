package cu.vlired.submod;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubmodApplicationTests {

	@Value("${dir.config}")
	private String dir_config;

	@Value("${app.input-json-file}")
	private String inputJsonFile;

	@Test
	public void contextLoads() {
	}

	@Test
	public void jsonToMap() {
		try {
			byte[] mapData = Files.readAllBytes(
					Paths.get(dir_config, inputJsonFile)
			);


			ObjectMapper objectMapper = new ObjectMapper();
			Map map = objectMapper.readValue(mapData, Map.class);

			System.out.println(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
