# ValidaThor
Thor will use his powerful Mjölnir to validate instances of objects protecting the developer from bugs.

[![Maven Central](https://img.shields.io/maven-central/v/io.github.paulmarcelinbejan.toolbox/validathor.svg)](https://central.sonatype.com/artifact/io.github.paulmarcelinbejan.toolbox/validathor)
[![GitHub license](https://img.shields.io/github/license/Naereen/StrapDown.js.svg)](https://github.com/paulmarcelinbejan/ValidaThor/blob/develop/V3/LICENSE)

ValidaThor is composed of 4 modules:
- [![validathor-base maven central](https://img.shields.io/maven-central/v/io.github.paulmarcelinbejan.toolbox/validathor-base?style=for-the-badge&label=validathor-base)](https://central.sonatype.com/artifact/io.github.paulmarcelinbejan.toolbox/validathor-base)<br>
  Contains base classes and configuration
- [![validathor-bfs maven central](https://img.shields.io/maven-central/v/io.github.paulmarcelinbejan.toolbox/validathor-bfs?style=for-the-badge&label=validathor-bfs)](https://central.sonatype.com/artifact/io.github.paulmarcelinbejan.toolbox/validathor-bfs)<br>
  implements validathor-base using Breadth-first search algorithm<br>
  ![Breadth-first search](https://raw.githubusercontent.com/paulmarcelinbejan/ValidaThor/develop/V3/resources/BFS.png)<br>
- [![validathor-dfs maven central](https://img.shields.io/maven-central/v/io.github.paulmarcelinbejan.toolbox/validathor-dfs?style=for-the-badge&label=validathor-dfs)](https://central.sonatype.com/artifact/io.github.paulmarcelinbejan.toolbox/validathor-dfs)<br>
  implements validathor-base using Depth-first search algorithm<br>
  ![Depth-first search](https://raw.githubusercontent.com/paulmarcelinbejan/ValidaThor/develop/V3/resources/DFS.png)<br>
- [![validathor-impl maven central](https://img.shields.io/maven-central/v/io.github.paulmarcelinbejan.toolbox/validathor-impl?style=for-the-badge&label=validathor-impl)](https://central.sonatype.com/artifact/io.github.paulmarcelinbejan.toolbox/validathor-impl)<br>
  Contains some Validathors ready to use for: <br>
  - Object
  - String
  - List
  - Map <br>
  
  ![Depth-first search](https://raw.githubusercontent.com/paulmarcelinbejan/ValidaThor/develop/V3/resources/DFS.png)<br>

## How to...

### How to create a custom validation for specific type?

There are three types of Validathor:

1. Validathor for simple type, will extends io.github.paulmarcelinbejan.toolbox.validathor.<b>Validathor</b>

Example:
```java
import io.github.paulmarcelinbejan.toolbox.validathor.Validathor;

public class StringValidathor extends Validathor<String> {

	// The constructor must call the super constructor passing the class of the type we want to validate
	public StringValidathor() {
		super(String.class);
	}

	// The isValid method must be implemented choosing how we will want to validate that type of object.
	@Override
	public boolean isValid(String toValidate) {
		return toValidate != null && !toValidate.isBlank();
	}

}
```

2. Validathor for parameterized type, will extends io.github.paulmarcelinbejan.toolbox.validathor.<b>ValidathorParameterizedType</b>

ValidathorParameterizedType extends Validathor

Example:
```java
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("rawtypes")
public class MapValidathor extends ValidathorParameterizedType<Map> {

	// The constructor must call the super constructor passing the class of the type we want to validate 
	// and a boolean indicating if elements must be validated
	public MapValidathor(boolean toValidateParameterizedTypeElements) {
		super(Map.class, toValidateParameterizedTypeElements);
	}

	// The isValid method must be implemented choosing how we will want to validate that type of object.
	@Override
	public boolean isValid(Map toValidate) {
		return toValidate != null && !toValidate.isEmpty();
	}

	// parameterizedTypeElementsToValidate return a Function that will return a Collection of elements to be validated
	// for Map we can decide for example if we want to validate only keys, only values, or both.
	@Override
	public Function<Map, Collection<?>> parameterizedTypeElementsToValidate() {
		return (map) -> map.values();
	}

}
```

3. A default Validathor for all the objects that doesn't have a specific Validathor, will extends io.github.paulmarcelinbejan.toolbox.validathor.<b>AbstractObjectValidathor</b>

Example:
```java
import io.github.paulmarcelinbejan.toolbox.validathor.AbstractObjectValidathor;

/**
 * A concrete implementation of ObjectValidathor
 * object to validate is valid if not null
 * it will lookup into inner fields
 */
public class ObjectValidathor extends AbstractObjectValidathor {

	public ObjectValidathor() {
		super(true);
	}
	
	public ObjectValidathorImpl(boolean validateInnerFields) {
		super(validateInnerFields);
	}

	@Override
	public boolean isValid(Object toValidate) {
		return toValidate != null;
	}

}
```

### How to customize the validation flow ?

The class ValidathorRegistry is responsible for holding all the configuration of the validation flow.
The easyest way to create a ValidathorRegistry is using the builder pattern with Fluid API.

Let's see an example:

```java
ValidathorRegistry validathorRegistry = ValidathorRegistry.builder()
	.registerObjectValidathor(new ObjectValidathor())
	.registerValidathor(new StringValidathor()) // register single validathor, it can be called multiple times
	.registerValidathors(List.of(new StringValidathor(), new PersonValidathor())) // register multiple validathors 
	.registerValidathorParameterizedType(new ListValidathor()) // register single validathor parameterized type, it can be called multiple times
	.registerValidathorsParameterizedType(List.of(new ListValidathor(), new MapValidathor())) // register multiple validathors of parameterized type
	.useCompatibleValidathorIfSpecificNotPresent(false) // if true, an object can be validated by a compatible Validathor if the specific is not registered.
	.registerSkipBeforeValidationProcessor(skipBeforeValidationProcessor)
	.registerSkipAfterValidationProcessor(skipAfterValidationProcessor)
	.build();
```

Let me give you an example of why is useful the config useCompatibleValidathorIfSpecificNotPresent
Let's say you have a field of type ArrayList, and you haven't registered a ValidathorParameterizedType for that type, but you have registered a ValidathorParameterizedType for a compatible class, like Collection, if useCompatibleValidathorIfSpecificNotPresent is set to true, the CollectionValidathor can be used to validate the ArrayList


Is is also possible to configure the two SkipProcessor:

1. SkipBeforeValidationProcessor.
2. SkipAfterValidationProcessor.

They both have as fields two sets, a set of classes, and a set of String (package name).
Before validating an object, if the type of this object is present in the set of classes of SkipBeforeValidationProcessor, or the Class is in a package that starts with one of the value of the set of string, then it will be skipped, and no validation will be executed on this object.
While if the type of object is present in the set of classes of SkipAfterValidationProcessor, or the Class is in a package that starts with one of the value of the set of string, then it will be validated, but it will not lookup internally to validate the fields.

This is the reason why the SkipAfterValidationProcessor contains by default the "java" package.


### How to start the validation ?

You need to choose between BFS and DFS algorithm, then create an instance accordingly,
Constructors parameters are, the validathorRegistry, and a boolean indicating if you want to collect all the errors, or you want to trigger the exception as soon as one happende.

BFS:
```java
ValidathorBFS validathorBFS = new ValidathorBFS(validathorRegistry, false);
validathorBFS.validate(anObjectToValidate)
```

DFS
```java
ValidathorDFS validathorDFS = new ValidathorDFS(validathorRegistry, false);
validathorDFS.validate(anObjectToValidate)
```
