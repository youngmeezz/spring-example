package springbook.user.dao;

import org.junit.Test;

import springbook.user.exception.DuplicatedUserIdException;

public class SimpleTest {
	
	@Test
	public void exceptionTest() {
		try {
			exception1();
		} catch(DuplicatedUserIdException e) {			
			System.out.println(e.getCause().getMessage());
		}
	}
	
	private void exception1() throws DuplicatedUserIdException {		
		throw new DuplicatedUserIdException(new Throwable("cause!"));
	}

}
