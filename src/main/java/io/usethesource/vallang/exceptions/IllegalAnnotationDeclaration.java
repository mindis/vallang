package io.usethesource.vallang.exceptions;

import io.usethesource.vallang.type.Type;

public class IllegalAnnotationDeclaration extends FactTypeDeclarationException {
	private static final long serialVersionUID = 3150260732700154774L;
	private Type onType;

	public IllegalAnnotationDeclaration(Type onType) {
		super("Can not declare annotations on " + onType);
		this.onType = onType;
	}

	public Type getType() {
		return onType;
	}
}
