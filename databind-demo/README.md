# Index
- <div href="#url-mapping> URL Mapping </a>
- <div href="#data-bind> Data Bind </a>

<div id="url-mapping"> </div>

### URL Mapping
; @RequestMapping

<table>
    <tr>
        <td> PathVariable</td>
        <td>
            com.demo.controller.PathVariableController
        </td>
    </tr>
    <tr>
        <td> Ant mapping</td>
        <td>
            com.demo.controller.AntMappingController
        </td>
    </tr>        
</table>

---

<div id="data-bind"> </div> 

### DataBind demo

1. Command Object

- Domain  

> Person

```
@Getter
@Setter
@ToString
public class Person {
    private String name;
    private Integer age;
    private boolean work;
    private int[] intArray;
    private List<Integer> intList;
    private Address address;
    private List<Hobby> hobbies;
}
```

> Address

```
@Getter
@Setter
@ToString
public class Address {
    private String addr;
    private String code;
}
```

> Hobby

```
@Getter
@Setter
@ToString
public class Hobby {
    private String hobby;
    private Integer priority;
}
```

- Controller side

```
@PostMapping("/person")
public String binding1(Person person, RedirectAttributes rttr) {    
    logger.info("## request /person hobbies size : {} , person : {}", person.getHobbies().size(), GsonUtil.prettyPrint(person));
    rttr.addFlashAttribute("message", person.toString());
    return "redirect:index";
}
```

- html code

```
<form action="/data-bind/person" method="POST">
    <table style="width: 80%;">
        <%-- person.name --%>
        <tr>
            <td>
                <label for="name">Name</label>
                <input name="name" id="name"></td>
            </td>
        </tr>
        <%-- person.age --%>
        <tr>
            <td>
                <label for="age">Age</label>
                <input name="age" id="age"></td>
            </td>
        </tr>
        <%-- person.work  --%>
        <tr>
            <td>
                <label for="work">Work</label>
                <input type="checkbox" name="work" id="work" value="true">
            </td>
        </tr>
        <%-- person.intArray  --%>
        <tr>
            <td>
                <label>intArray</label> <br/>
                <input type="text" name="intArray" value="1"> <br/>
                <input type="text" name="intArray" value="2"> <br/>
            </td>
        </tr>
        <%-- person.intList  --%>
        <tr>
            <td>
                <label>intList</label> <br/>
                <input type="text" name="intList"> <br/>
                <input type="text" name="intList"> <br/>
                <input type="text" name="intList"> <br/>
            </td>
        </tr>
        <%-- person.address  --%>
        <tr>
            <td>
                <label>Address.addr</label> <input type="text" name="address.addr"> <br/>
                <label>Address.code</label> <input type="text" name="address.code"> <br/>
            </td>
        </tr>
        <%-- person.hobbies :: List<Hobby>  --%>
        <tr>
            <td>
                <label>hobby1</label>
                <input type="text" name="hobbies[0].hobby"> <input type="text" name="hobbies[0].priority"> <br />

                <%-- skip index :: 1 => will be null in hobbies--%>
                <label>hobby3</label>
                <input type="text" name="hobbies[2].hobby"> <input type="text" name="hobbies[2].priority"> <br />
            </td>
        </tr>
        <tr>
            <td>
                <button type="submit">SUBMIT</button>
            </td>
        </tr>
    </table>
</form>
```
  
- Result  By using GSON`s pretty print 

```
INFO : com.demo.controller.DataBindController - ## request /person hobbies size : 3 , person : 
{
  "name": "testName",
  "age": 10,
  "work": false,
  "intArray": [
    1,
    2
  ],
  "intList": [
    1,
    null,
    null
  ],
  "address": {
    "addr": "",
    "code": ""
  },
  "hobbies": [
    {
      "hobby": "hobby1",
      "priority": 1
    },
    {
      "hobby": "",
      "priority": null
    },
    {
      "hobby": "hobby3",
      "priority": 3
    }
  ]
}
```
 

  












---   

<table>
    <tr>
        <td> </td>
        <td>
        </td>
    </tr>
    <tr>
        <td> </td>
        <td>
        </td>
    </tr>
    <tr>
        <td> </td>
        <td>
        </td>
    </tr>    
</table>