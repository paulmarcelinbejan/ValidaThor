package com.paulmarcelinbejan.validator.algorithm.bfs.validators.base;

import static com.paulmarcelinbejan.validator.enums.ValidatorType.COLLECTION;
import static com.paulmarcelinbejan.validator.enums.ValidatorType.MAP;
import static com.paulmarcelinbejan.validator.enums.ValidatorType.OBJECT;
import static com.paulmarcelinbejan.validator.enums.ValidatorType.STRING;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.paulmarcelinbejan.validator.algorithm.bfs.validators.CollectionValidator;
import com.paulmarcelinbejan.validator.algorithm.bfs.validators.MapValidator;
import com.paulmarcelinbejan.validator.algorithm.bfs.validators.ObjectValidator;
import com.paulmarcelinbejan.validator.algorithm.bfs.validators.StringValidator;
import com.paulmarcelinbejan.validator.entities.FieldInfo;
import com.paulmarcelinbejan.validator.entities.Info;
import com.paulmarcelinbejan.validator.enums.ValidatorType;
import com.paulmarcelinbejan.validator.exception.ValidatorException;
import com.paulmarcelinbejan.validator.utils.ValidatorUtils.FunctionWithException;

public class FieldValidator {

	private static final Map<ValidatorType, FunctionWithException<FieldInfo, List<? extends Info>, ValidatorException>> VALIDATOR_PROVIDER = new EnumMap<>(ValidatorType.class);
	
	public FieldValidator(final StringValidator stringValidator, final CollectionValidator collectionValidator, final MapValidator mapValidator, final ObjectValidator objectValidator) {
		VALIDATOR_PROVIDER.put(STRING, stringValidator::validate);
		VALIDATOR_PROVIDER.put(COLLECTION, collectionValidator::validate);
		VALIDATOR_PROVIDER.put(MAP, mapValidator::validate);
		VALIDATOR_PROVIDER.put(OBJECT, objectValidator::validate);
	}
	
	public List<? extends Info> validate(final FieldInfo fieldInfo) throws ValidatorException {
		return VALIDATOR_PROVIDER.get(ValidatorType.getValidatorType(fieldInfo.getToValidateClass())).apply(fieldInfo);
	}
	
}
