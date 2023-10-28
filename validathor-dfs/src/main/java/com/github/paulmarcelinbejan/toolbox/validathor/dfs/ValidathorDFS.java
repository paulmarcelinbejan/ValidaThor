package com.github.paulmarcelinbejan.toolbox.validathor.dfs;

import java.util.List;
import java.util.stream.Collectors;

import com.github.paulmarcelinbejan.toolbox.validathor.base.ValidathorBase;
import com.github.paulmarcelinbejan.toolbox.validathor.dfs.inspector.InspectorDFS;
import com.github.paulmarcelinbejan.toolbox.validathor.exception.ValidathorException;
import com.github.paulmarcelinbejan.toolbox.validathor.info.Info;
import com.github.paulmarcelinbejan.toolbox.validathor.registry.ValidathorRegistry;
import com.github.paulmarcelinbejan.toolbox.validathor.utils.ValidathorUtils;

import lombok.NonNull;

public class ValidathorDFS extends ValidathorBase {

	public ValidathorDFS(ValidathorRegistry validathorRegistry, boolean collectAllValidationException) {
		super(validathorRegistry, collectAllValidationException);
		inspector = new InspectorDFS(validathorRegistry);
		inspector.setCollectAllValidationException(collectAllValidationException);
	}
	
	private final InspectorDFS inspector;
	
	@Override
	public void isValid(@NonNull Object toValidate) throws ValidathorException {
		
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
