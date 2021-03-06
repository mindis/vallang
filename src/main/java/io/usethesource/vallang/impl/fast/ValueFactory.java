/*******************************************************************************
* Copyright (c) 2009, 2012-2017 Centrum Wiskunde en Informatica (CWI)
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Jurgen Vinju - interface and implementation
*    Arnold Lankamp - implementation
*    Anya Helene Bagge - rational support, labeled maps and tuples
*    Davy Landman - added PI & E constants
*    Michael Steindorfer - extracted factory for numeric data   
*******************************************************************************/
package io.usethesource.vallang.impl.fast;

import java.util.Map;

import io.usethesource.vallang.IConstructor;
import io.usethesource.vallang.IList;
import io.usethesource.vallang.IListWriter;
import io.usethesource.vallang.IMap;
import io.usethesource.vallang.IMapWriter;
import io.usethesource.vallang.INode;
import io.usethesource.vallang.ISet;
import io.usethesource.vallang.ISetWriter;
import io.usethesource.vallang.ITuple;
import io.usethesource.vallang.IValue;
import io.usethesource.vallang.IValueFactory;
import io.usethesource.vallang.exceptions.FactTypeUseException;
import io.usethesource.vallang.exceptions.UnexpectedElementTypeException;
import io.usethesource.vallang.impl.primitive.AbstractPrimitiveValueFactory;
import io.usethesource.vallang.type.Type;
import io.usethesource.vallang.type.TypeFactory;

public class ValueFactory extends AbstractPrimitiveValueFactory implements IValueFactory {
	private final static TypeFactory tf = TypeFactory.getInstance();
	
	protected ValueFactory() {
		super();
	}

	private static class InstanceKeeper{
		public final static ValueFactory instance = new ValueFactory();
	}
	
	public static ValueFactory getInstance(){
		return InstanceKeeper.instance;
	}
		
	@Deprecated
	public IListWriter listWriter(Type elementType){
		return new ListWriter(elementType);
	}
	
	public IListWriter listWriter(){
		return new ListWriter();
	}
	
	@Deprecated
	public IMapWriter mapWriter(Type keyType, Type valueType){
		return new MapWriter();
	}

	public IMapWriter mapWriter(Type mapType){
		return new MapWriter();
	}
	
	public IMapWriter mapWriter(){
		return new MapWriter();
	}
	
	public ISetWriter setWriter(Type elementType){
		return new SetWriter(elementType);
	}
	
	public ISetWriter setWriter(){
		return new SetWriter();
	}
	
	public ISetWriter relationWriter(Type tupleType){
		return new SetWriter(tupleType);
	}
	
	public ISetWriter relationWriter(){
		return new SetWriter();
	}
	
	public IListWriter listRelationWriter(Type tupleType) {
		return new ListWriter(tupleType);
	}

	public IListWriter listRelationWriter() {
		return new ListWriter();
	}
	
	public IList list(Type elementType){
		return listWriter(elementType).done();
	}
	
	public IList list(IValue... elements){
		IListWriter listWriter = listWriter();
		listWriter.append(elements);
		
		return listWriter.done();
	}
	
	public IMap map(Type mapType){
		return mapWriter(mapType).done();
	}

	public IMap map(Type keyType, Type valueType){
		return mapWriter(keyType, valueType).done();
	}
	
	public ISet set(Type elementType){
		return setWriter(TypeFactory.getInstance().voidType()).done();
	}
	
	public ISet set(IValue... elements){
		Type elementType = lub(elements);
		
		ISetWriter setWriter = setWriter(elementType);
		setWriter.insert(elements);
		return setWriter.done();
	}
	
	public ISet relation(Type tupleType){
		return relationWriter(tupleType).done();
	}
	
	public ISet relation(IValue... elements) {
		Type elementType = lub(elements);
		
		if (!elementType.isFixedWidth()) throw new UnexpectedElementTypeException(tf.tupleType(tf.voidType()), elementType);
		
		ISetWriter relationWriter = relationWriter(elementType);
		relationWriter.insert(elements);
		return relationWriter.done();
	}
	
	public IList listRelation(Type tupleType) {
		return listRelationWriter(tupleType).done();
	}

	public IList listRelation(IValue... elements) {
		Type elementType = lub(elements);
		
		if (!elementType.isFixedWidth()) throw new UnexpectedElementTypeException(tf.tupleType(tf.voidType()), elementType);
		
		IListWriter listRelationWriter = listRelationWriter(elementType);
		listRelationWriter.append(elements);
		return listRelationWriter.done();
	}
	
	public INode node(String name) {
		return Node.newNode(name, new IValue[0]);
	}

	public INode node(String name, Map<String, IValue> annos, IValue... children) {
		return Node.newNode(name, children.clone()).asAnnotatable().setAnnotations(annos);
	}

	public INode node(String name, IValue... children) {
		return Node.newNode(name, children.clone());
	}
	
	@Override
	public INode node(String name, IValue[] children, Map<String, IValue> keyArgValues)
			throws FactTypeUseException {
		return Node.newNode(name, children.clone(), keyArgValues);
	}
	
	@Override
	public IConstructor constructor(Type constructorType) {
		return Constructor.newConstructor(constructorType, new IValue[0]);
	}
	
	@Override
	public IConstructor constructor(Type constructorType, IValue... children){
		return Constructor.newConstructor(constructorType, children.clone());
	}
	
	@Override
  public IConstructor constructor(Type constructorType, IValue[] children, Map<String,IValue> kwParams){
    return Constructor.newConstructor(constructorType, children.clone(), kwParams);
  }
	
	@Override
	public IConstructor constructor(Type constructorType,
			Map<String, IValue> annotations, IValue... children)
			throws FactTypeUseException {
		return Constructor.newConstructor(constructorType, children.clone()).asAnnotatable().setAnnotations(annotations);
	}
	
	public ITuple tuple() {
		return Tuple.newTuple();
	}

	public ITuple tuple(IValue... args) {
		return Tuple.newTuple(args.clone());
	}

	@Deprecated
	public ITuple tuple(Type type, IValue... args) {
		return Tuple.newTuple(args.clone());
	}

	private static Type lub(IValue... elements) {
		Type elementType = TypeFactory.getInstance().voidType();

		for (int i = elements.length - 1; i >= 0; i--) {
			elementType = elementType.lub(elements[i].getType());
		}

		return elementType;
	}

	@Override
	public String toString() {
		return "VF_PDB_FAST";
	}
	
}
