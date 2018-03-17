package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.*;

@RestController
public class HomeController {
	@Autowired
	private HttpServletResponse response;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String Home() {
		try {
			File file = new File("src/main/resources/static/home.html");
			if (!file.exists()) {
				response.setStatus(500);
				return "INTERNAL SERVER ERROR";
			}
			byte[] encoded = Files.readAllBytes(Paths.get("src/main/resources/static/home.html"));
			return new String(encoded, Charset.defaultCharset());
		}
		catch(IOException ex) {
			response.setStatus(500);
			return "INTERNAL SERVER ERROR";
		}
	}
}