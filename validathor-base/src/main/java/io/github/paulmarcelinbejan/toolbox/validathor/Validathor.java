package io.github.paulmarcelinbejan.toolbox.validathor;

import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Validathor<T> {

	private final Class<T> typeParameterClass;
	
	public abstract boolean isValid(T toValidate);
	
	public List<String> getFieldsNameToSkip() {
		return Collections.emptyList();
	}
	
}
