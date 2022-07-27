import static org.junit.Assert.*;

public class Test {

	@org.junit.Test
	public void test() {
		Databaze data= new Databaze();
		boolean output = data.connect();

		assertEquals(true,output);
		
		
	}

}
