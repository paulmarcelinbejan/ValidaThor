package io.github.paulmarcelinbejan.toolbox.validathor.dfs;

import java.util.List;
import java.util.stream.Collectors;

import io.github.paulmarcelinbejan.toolbox.validathor.base.AlgorithmValidathorBase;
import io.github.paulmarcelinbejan.toolbox.validathor.dfs.inspector.InspectorDFS;
import io.github.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import io.github.paulmarcelinbejan.toolbox.validathor.info.Info;
import io.github.paulmarcelinbejan.toolbox.validathor.registry.ValidathorRegistry;
import io.github.paulmarcelinbejan.toolbox.validathor.utils.ValidathorUtils;
import lombok.NonNull;

/**
 * ValidathorBFS uses Depth-first search to validate the object
 */
public class ValidathorDFS extends AlgorithmValidathorBase {

	public ValidathorDFS(ValidathorRegistry validathorRegistry, boolean collectAllValidationException) {
		super(validathorRegistry, collectAllValidationException);
		inspector = new InspectorDFS(validathorRegistry);
		inspector.setCollectAllValidationException(collectAllValidationException);
	}
	
	/**
	 * inspector
	 */
	private final InspectorDFS inspector;
	
	@Override
	public void validate(@NonNull Object toValidate) throws ValidathorException {
		
		List<Info> fieldsToValidate = ValidathorUtils.getFields(toValidate.getClass())
				.stream()
				.map(field -> ValidathorUtils.buildInfo(toValidate, field))
				.toList();
		
		for(Info info : fieldsToValidate) {
			
			inspector.validate(info);
			
		}
		
		if(!inspector.getExceptions().isEmpty()) {
	        String errorMessage = inspector.getExceptions().stream()
	        		.map(ValidathorException::getMessage)
	                .collect(Collectors.joining("\n\n"));
	        throw new ValidathorException(errorMessage);
		}
		
	}

}
