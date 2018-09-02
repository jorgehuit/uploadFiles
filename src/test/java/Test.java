import org.apache.log4j.Logger;

public class Test {
	
	Logger logger = Logger.getLogger(Test.class);
	
	@org.junit.Test
	public void test(){
		
		int result = 0;
		for(int i = 0; i < 5; i++){
			if(i == 3){
				result += 10;
			}else{
				result += i;
			}
			
		}
		logger.info(result);
		
	}

}
