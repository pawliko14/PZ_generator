import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

import Objects.Filesave;

public class FileCreationTests {

	@Test
	public void ShouldReturnCorrectFilePath() {

		Filesave file =  new Filesave("C://Users/el08/Desktop","testowy_dokument.pdf");
		
		assertEquals("C://Users/el08/Desktop", file.getPathToSaveHours());
		
			

	}
	
	@Test
	public void ShouldReturnCorrectFullFilePathWIthCUrrentDate()
	{
		Filesave file =  new Filesave("C://Users/el08/Desktop","testowy_dokument.pdf");

		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat doNazwy = new SimpleDateFormat("yyyy.MM.dd");
		String todaysDate = doNazwy.format(calendar.getTime());
		
		assertEquals("C://Users/el08/Desktop/"+todaysDate+"/", file.getPath());
	}

}
