
<table>	
	<tr>
		<th>AspectJ designator</th>
		<td>Description</td>
	</tr>
	<tr>
		<th>args()</th>
		<td>
			Limits join-point matches to the execution of methods whose arguments are
			instances of the given types 
		</td>
	</tr>
	<tr>
		<th>@args()</th>
		<td>
			Limits join-point matches to the execution of methods whose arguments are
			annotated with the given annotation types
		</td>
	</tr>
	<tr>
		<th>execution()</th>
		<td>
			Matches join points that are method executions 
		</td>
	</tr>
	<tr>
		<th>this()</th>
		<td>
			Limits join-point matches to those where the bean reference of the AOP proxy
			is of a given type 
		</td>
	</tr>
	<tr>
		<th>target()</th>
		<td>Limits join-point matches to those where the target object is of a given type </td>
	</tr>
	<tr>
		<th>@target()</th>
		<td>
			Limits matching to join points where the class of the executing object has an
annotation of the given type 
		</td>
	</tr>
	<tr>
		<th>within()</th>
		<td>Limits matching to join points within certain types </td>
	</tr>
	<tr>
		<th>@within()</th>
		<td>
			Limits matching to join points within types that have the given annotation (the
			execution of methods declared in types with the given annotation when using
			Spring AOP) 
		</td>
	</tr>
	<tr>
		<th>@annotation</th>
		<td>
			Limits join-point matches to those where the subject of the join point has the
			given annotation 
		</td>
	</tr>	
</table>

e.g)

<table>
<tr><td>

**execution(* concert.Performance.perform(..))** <br />
execution : Trigger on a method`s execution<br />
* : Returning any type <br /><br />
concert.Performance.perform : The type that the method belongs to <br />
.. : Taking any args	<br />

</td></tr>

<tr><td>

**execution(* concert.Performance.perform(..))&& within(concert.*))** <br />
within(concert.*)) : When the method is called from within any class in the concert package

</td></tr>

|| : or
! : negate 
&& : and

</table>

<table>
	<tr>
		<th>Annotation</th>
		<td>Advice</td>
	</tr>
	<tr>
		<th>@After</th>
		<td>
			The advice method is called after the advised method returns or throws an
exception. 	
		</td>
	</tr>
	<tr>
		<th>@AfterReturning</th>
		<td>
			The advice method is called after the advised method returns.	
		</td>
	</tr>
	<tr>
		<th>@AfterThrowing</th>
		<td>The advice method is called after the advised method throws an exception.</td>
	</tr>
	<tr>
		<th>@Around</th>
		<td>The advice method wraps the advised method.</td>
	</tr>
	<tr>
		<th>@Before</th>
		<td>The advice method is called before the advised method is called./td>
	</tr>
</table>







































