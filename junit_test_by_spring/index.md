# Cache 
<table>
	<tr>
		<th>config</th>
		<td>
			- com.test.config.CachingConfig<br>
			- cache/ehcache.xml<br>
			- pom.xml<br>
		</td>
	</tr>
	<tr>
		<th>domain</th>
		<td> 
			- com.test.domain.TestDomain
		</td>
	</tr>
	<tr>
		<th>repository</th>
		<td> 
			- com.test.persistence.TestDao<br>
			- com.test.persistence.TestMapper<br>
		</td>
	</tr>
	<tr>
		<th>test</th>
		<td> 
			- com.test.cache.CacheTest
		</td>
	</tr>
</table>


---


# Reflection

<table>
	<tr>
		<th>test package</th>
		<td>
			in src/test/java <br>
			com.test.reflectiontest.*
		</td>
	</tr>
</table>

---



# Profile
<table>
	<tr>
		<th>annotation</th>
		<td>com.test.config.ProfileConfig</td>
	</tr>
	<tr>
		<th>xml</th>
		<td>WEB-INF/spring/persistence-context.xml</td>
	</tr>
	<tr>
		<th>state</th>
		<td>com.test.domain.EnvProfile</td>
	</tr>
	<tr>
		<th>active</th>
		<td>web.xml</td>
	</tr>
	<tr>
		<th>JUnit</th>
		<td>
			in src/test/java<br>
			com.test.environment.*
		</td>
	</tr>
</table>

---

# Read properties (properties, xml)
<table>
	<tr>
		<th>properties</th>
		<td>
			in src/main/resources <br>
			test.properties <br>
			in src/test/java <br>
			com.test.configtest.PropertiesTest
		</td>
	</tr>
	<tr>
		<th>xml</th>
		<td>
			in src/main/resources <br>
			test_config.xml <br>
			in src/test/java <br>
			com.test.configtest.XmlTest
		</td>
	</tr>
</table>

---

# ClassResource

<table>
	<tr>
		<th>test</th>
		<td>
			in src/test/java <br>
			com.test.etc.ClassResourceTest	
		</td>
	</tr>
</table>

---

# Bean init test

<table>
	<tr>
		<th>configuration</th>
		<td>com.test.config.TestConfig</td>
	</tr>
	<tr>
		<th>bean</th>
		<td>com.test.domain.TestBean</td>
	</tr>
	<tr>
		<th>JUnit TEST</th>
		<td>
			in src/test/java <br>
			com.test.etc.BeanTest
		</td>
	</tr>
</table>



# AOP

<table>
	<tr>
		<th>config</th>
		<td>
			com.test.AopConfig.java
		</td>
	</tr>
	<tr>
		<th></th>
		<td>
			**in com.test.aop** <br />
			Audience1.java<br>
			Audience2.java<br>
			Audience3.java<br>
			note.md<br>
			pics/*<br>
		</td>
	</tr>	
	<tr>
		<th>Test</th>
		<td>
			**in src/test/java/com.test.aop** <br />
			AspectTest.java<br />
		</td>
	</tr>
</table>






















<table>
	<tr>
		<th></th>
		<td></td>
	</tr>
</table>