package io.usethesource.vallang.impl;

import java.util.function.Function;

import io.usethesource.vallang.IList;
import io.usethesource.vallang.IListRelation;
import io.usethesource.vallang.IListWriter;
import io.usethesource.vallang.ITuple;
import io.usethesource.vallang.IValue;
import io.usethesource.vallang.IValueFactory;
import io.usethesource.vallang.impl.func.ListFunctions;

public class DefaultRelationViewOnList implements IListRelation<IList> {

	protected final IValueFactory vf;
	protected final IList rel1;
	
	public DefaultRelationViewOnList(final IValueFactory vf, final IList rel1) {
		this.vf = vf;
		this.rel1 = rel1;
	}

	@Override
	public IList compose(IListRelation<IList> rel2) {
		return ListFunctions.compose(vf, rel1, rel2.asList());
	}

	@Override
	public IList closure() {
		return ListFunctions.closure(vf, rel1);
	}

	@Override
	public IList closureStar() {
		return ListFunctions.closureStar(vf, rel1);
	}
	
	@Override
	public int arity() {
		return rel1.getElementType().getArity();
	}	
	
	@Override
	public IList project(int... fieldIndexes) {
		return ListFunctions.project(vf, rel1, fieldIndexes);
	}

	@Override
	public IList projectByFieldNames(String... fieldsNames) {
		return ListFunctions.projectByFieldNames(vf, rel1, fieldsNames);
	}

	@Override
	public IList carrier() {
		return ListFunctions.carrier(vf, rel1);
	}

	@Override
	public IList domain() {
		return ListFunctions.domain(vf, rel1);
	}

	@Override
	public IList range() {
		return ListFunctions.range(vf, rel1);
	}

	@Override
	public IList asList() {
		return rel1;
	}

	@Override
	public String toString() {
		return rel1.toString();
	}
	
	@Override
	public IList index(IValue key) {
	    return ListFunctions.index(vf, rel1, key);
	}
	
}
