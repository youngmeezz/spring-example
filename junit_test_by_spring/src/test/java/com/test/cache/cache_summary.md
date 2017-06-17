



*Spring offers several SpEL extensions specifically for defining cache rules*
<table>
	<tr>
		<th>Expression</th>
		<th>Description</th>
	</tr>
	<tr>
		<td>#root.args</td>
		<td>The arguments passed in to the cached method, as an array </td>
	</tr>
	<tr>
		<td>#root.caches</td>
		<td>The caches this method is executed against, as an array</td>
	</tr>
	<tr>
		<td>#root.target</td>
		<td>The target object </td>
	</tr>
	<tr>
		<td>#root.targetClass</td>
		<td>The target object’s class; a shortcut for #root.target.class</td>
	</tr>
	<tr>
		<td>#root.method</td>
		<td>The cached method </td>
	</tr>
	<tr>
		<td>#root.methodName</td>
		<td>The cached method’s name; a shortcut for #root.method.name</td>
	</tr>
	<tr>
		<td>#result</td>
		<td>The return value from the method call (not available with @Cacheable)</td>
	</tr>
	<tr>
		<td>#Argument</td>
		<td>The name of any method argument (such as #argName) or argument
		index (such as #a0 or #p0) </td>
	</tr>
</table> 