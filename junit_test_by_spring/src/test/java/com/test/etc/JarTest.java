package com.test.etc;

import org.junit.Test;

public class JarTest {
	@Test
	public void jarTest() {
		try {			
			String[] cmds = {
					"cmd.exe /c d:",
					//"cmd.exe /c jar xf d:\\agent.jar"
					"cmd.exe /c mkdir test"
			};
			for(String cmd : cmds) {
				Runtime.getRuntime().exec(cmd);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
